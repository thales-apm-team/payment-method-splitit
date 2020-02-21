package com.payline.payment.splitit.utils.http;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.payline.payment.splitit.bean.InstallmentPlanStatus;
import com.payline.payment.splitit.bean.appel.VerifyPayment;
import com.payline.payment.splitit.bean.appel.Initiate;
import com.payline.payment.splitit.bean.appel.Login;
import com.payline.payment.splitit.bean.configuration.RequestConfiguration;
import com.payline.payment.splitit.bean.response.InitiateResponse;
import com.payline.payment.splitit.bean.response.LoginResponse;
import com.payline.payment.splitit.bean.response.VerifyPaymentResponse;
import com.payline.payment.splitit.exception.InvalidDataException;
import com.payline.payment.splitit.exception.PluginException;
import com.payline.payment.splitit.utils.Constants;
import com.payline.payment.splitit.utils.properties.ConfigProperties;
import com.payline.pmapi.bean.common.FailureCause;
import com.payline.pmapi.logger.LogManager;
import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;


public class HttpClient {
    private static final Logger LOGGER = LogManager.getLogger(HttpClient.class);
    private Gson parser;


    // Exceptions messages
    private static final String SERVICE_URL_ERROR = "Service URL is invalid";

    /**
     * The number of time the client must retry to send the request if it doesn't obtain a response.
     */
    private int retries;

    private org.apache.http.client.HttpClient client;

    // --- Singleton Holder pattern + initialization BEGIN

    private HttpClient() {
        this.parser = new GsonBuilder().create();

        int connectionRequestTimeout;
        int connectTimeout;
        int socketTimeout;
        try {
            // request config timeouts (in seconds)
            ConfigProperties config = ConfigProperties.getInstance();
            connectionRequestTimeout = Integer.parseInt(config.get("http.connectionRequestTimeout"));
            connectTimeout = Integer.parseInt(config.get("http.connectTimeout"));
            socketTimeout = Integer.parseInt(config.get("http.socketTimeout"));

            // retries
            this.retries = Integer.parseInt(config.get("http.retries"));
        } catch (NumberFormatException e) {
            throw new PluginException("plugin error: http.* properties must be integers", e);
        }

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(connectionRequestTimeout * 1000)
                .setConnectTimeout(connectTimeout * 1000)
                .setSocketTimeout(socketTimeout * 1000)
                .build();

        // instantiate Apache HTTP client
        this.client = HttpClientBuilder.create()
                .useSystemProperties()
                .setDefaultRequestConfig(requestConfig)
                .setSSLSocketFactory(new SSLConnectionSocketFactory(HttpsURLConnection.getDefaultSSLSocketFactory(), SSLConnectionSocketFactory.getDefaultHostnameVerifier()))
                .build();

    }

    private static class Holder {
        private static final HttpClient instance = new HttpClient();
    }


    public static HttpClient getInstance() {
        return Holder.instance;
    }
    // --- Singleton Holder pattern + initialization END

    private Header[] createHeaders() {
        Header[] headers = new Header[2];
        headers[0] = new BasicHeader("Content-Type", "application/json");
        return headers;
    }

    /**
     * Send the request, with a retry system in case the client does not obtain a proper response from the server.
     *
     * @param httpRequest The request to send.
     * @return The response converted as a {@link StringResponse}.
     * @throws PluginException If an error repeatedly occurs and no proper response is obtained.
     */
    StringResponse execute(HttpRequestBase httpRequest) {
        StringResponse strResponse = null;
        int attempts = 1;

        while (strResponse == null && attempts <= this.retries) {
            LOGGER.info("Start call to partner API [{} {}] (attempt {})", httpRequest.getMethod(), httpRequest.getURI(), attempts);
            try (CloseableHttpResponse httpResponse = (CloseableHttpResponse) this.client.execute(httpRequest)) {
                strResponse = StringResponse.fromHttpResponse(httpResponse);
            } catch (IOException e) {
                LOGGER.error("An error occurred during the HTTP call :", e);
                strResponse = null;
            } finally {
                attempts++;
            }
        }

        if (strResponse == null) {
            throw new PluginException("Failed to contact the partner API", FailureCause.COMMUNICATION_ERROR);
        }
        LOGGER.info("Response obtained from partner API [{} {}]", strResponse.getStatusCode(), strResponse.getStatusMessage());
        return strResponse;
    }


    /**
     * Manage Get API call
     *
     * @param url     the url to call
     * @param headers header(s) of the request
     * @return
     */
    StringResponse get(String url, Header[] headers) {
        URI uri;
        try {
            // Add the createOrderId to the url
            uri = new URI(url);
        } catch (URISyntaxException e) {
            throw new InvalidDataException(SERVICE_URL_ERROR, e);
        }

        final HttpGet httpGet = new HttpGet(uri);
        httpGet.setHeaders(headers);

        // Execute request
        return this.execute(httpGet);
    }


    /**
     * Manage Post API call
     *
     * @param url     the url to call
     * @param headers header(s) of the request
     * @param body    the body of the request
     * @return
     */
    StringResponse post(String url, Header[] headers, StringEntity body) {
        URI uri;
        try {
            // Add the createOrderId to the url
            uri = new URI(url);
        } catch (URISyntaxException e) {
            throw new InvalidDataException(SERVICE_URL_ERROR, e);
        }

        final HttpPost httpPost = new HttpPost(uri);
        httpPost.setHeaders(headers);
        httpPost.setEntity(body);

        // Execute request
        return this.execute(httpPost);
    }

    public void checkConnection(RequestConfiguration configuration, Login login) {
        String url = configuration.getPartnerConfiguration().getProperty(Constants.PartnerConfigurationKeys.URL);
        Header[] headers = createHeaders();
        String body = login.toString();

        StringResponse response = post(url, headers, new StringEntity(body, StandardCharsets.UTF_8));

        LoginResponse loginResponse = parser.fromJson(response.getContent(), LoginResponse.class);

        if (!response.isSuccess()) {
            throw new InvalidDataException("page introuvable, mauvaise URL");
        } else if (!loginResponse.getResponseHeader().isSucceeded()) {
            throw new InvalidDataException("Le Login s'est mal passé");
        }
    }

    public InitiateResponse initiate(RequestConfiguration configuration, Initiate initiate) {
        String url = configuration.getPartnerConfiguration().getProperty(Constants.PartnerConfigurationKeys.URL);
        Header[] headers = createHeaders();
        String body = initiate.toString();

        StringResponse response = post(url, headers, new StringEntity(body, StandardCharsets.UTF_8));
        InitiateResponse initiateResponse = parser.fromJson(response.getContent(), InitiateResponse.class);

        if (initiateResponse.getResponseHeader().isSucceeded()
                && initiateResponse.getInstallmentPlan().getInstallmentPlanStatus().getCode() == InstallmentPlanStatus.Code.INITIALIZING) {
            return initiateResponse;
        } else {
            throw new InvalidDataException("something went wrong");
        }
    }


}
