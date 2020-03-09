package com.payline.payment.splitit.integration;

import com.payline.payment.splitit.MockUtils;
import com.payline.payment.splitit.service.impl.ConfigurationServiceImpl;
import com.payline.payment.splitit.service.impl.PaymentServiceImpl;
import com.payline.payment.splitit.service.impl.PaymentWithRedirectionServiceImpl;
import com.payline.pmapi.bean.payment.ContractProperty;
import com.payline.pmapi.bean.payment.PaymentFormContext;
import com.payline.pmapi.bean.payment.request.PaymentRequest;
import com.payline.pmapi.bean.payment.request.RedirectionPaymentRequest;
import com.payline.pmapi.bean.payment.response.PaymentResponse;
import com.payline.pmapi.bean.payment.response.impl.PaymentResponseRedirect;
import com.payline.pmapi.bean.payment.response.impl.PaymentResponseSuccess;
import com.payline.pmapi.integration.AbstractPaymentIntegration;
import com.payline.pmapi.service.ConfigurationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TestIT extends AbstractPaymentIntegration {

    private ConfigurationService configurationService = new ConfigurationServiceImpl();
    private PaymentServiceImpl paymentService = new PaymentServiceImpl();
    private PaymentWithRedirectionServiceImpl paymentWithRedirectionService = new PaymentWithRedirectionServiceImpl();


    @Override
    protected Map<String, ContractProperty> generateParameterContract() {
        return null;
    }

    @Override
    protected PaymentFormContext generatePaymentFormContext() {
        return null;
    }

    @Override
    protected String cancelOnPartnerWebsite(String s) {
        return null;
    }

    @Override
    protected String payOnPartnerWebsite(String partnerUrl) {
        // Start browser
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
        try {

            // Go to partner's website
            driver.get(partnerUrl);
            System.out.println(partnerUrl);

            System.out.println(driver.getPageSource());

            // todo faire passer ces valeur en parametre du lancement de l'appli (voir ce qui est fait sur equens)
            driver.findElement(By.id(".CardNumber")).sendKeys("4111111111111111");
            driver.findElement(By.id(".ExpDate")).sendKeys("03/22");
            driver.findElement(By.id(".CardCvv")).sendKeys("123");

            // pour permettre de cocher la case
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".cb-view--Mt8WQ")));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".cb-view--Mt8WQ")));

            // Le champs Checkbox contient le champs texte et le champs bouton
            // on le coche directement par le js sinon il essaye de cliquer pendant le scrolling de la page
            // et atteint un autre element
            JavascriptExecutor jse = (JavascriptExecutor)driver;
            jse.executeScript("arguments[0].click();", driver.findElement(By.cssSelector(".cb-view--Mt8WQ")));

            jse.executeScript("arguments[0].click();", driver.findElement(By.cssSelector(".button--2ctjk")));

            wait.until(ExpectedConditions.urlContains("succes"));
            // Wait for redirection to success or cancel url
//            wait.until(ExpectedConditions.or(ExpectedConditions.urlToBe("https://example.org/store/redirection")));
            return driver.getCurrentUrl();
        } finally {
            driver.quit();
        }
    }

    private WebElement goTo(WebDriver driver, String cssSelector) {
        WebElement element = driver.findElement(By.cssSelector(cssSelector));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        return element;
    }

    private void clickOn(WebDriver driver, String cssSelector) {
        goTo(driver, cssSelector).click();
    }

    private void setValue(WebDriver driver, String cssSelector, String value) {
        goTo(driver, cssSelector).sendKeys(value);
    }

    @Test
        // scenario nominal
    void fullPaymentTest() {

        // Login
        Map<String, String> errors = configurationService.check(MockUtils.aContractParametersCheckRequest());
        Assertions.assertEquals(0, errors.size());

        // Initialise a payment
        PaymentRequest request = createDefaultPaymentRequest();
        PaymentResponse paymentResponseFromPaymentRequest = paymentService.paymentRequest(request);
        Assertions.assertEquals(PaymentResponseRedirect.class, paymentResponseFromPaymentRequest.getClass());

        // PaymentWithRedirectionService
        PaymentResponseRedirect paymentResponseRedirect = (PaymentResponseRedirect)paymentResponseFromPaymentRequest;
        String partnerUrl = paymentResponseRedirect.getRedirectionRequest().getUrl().toString();

        this.payOnPartnerWebsite(partnerUrl);

        RedirectionPaymentRequest redirectionPaymentRequest = (RedirectionPaymentRequest)RedirectionPaymentRequest.builder()
                .withContractConfiguration(request.getContractConfiguration())
                .withPaymentFormContext(this.generatePaymentFormContext())
                .withEnvironment(request.getEnvironment())
                .withTransactionId(request.getTransactionId())
                .withRequestContext(paymentResponseRedirect.getRequestContext())
                .withAmount(request.getAmount())
                .withOrder(request.getOrder())
                .withBuyer(request.getBuyer())
                .withBrowser(request.getBrowser())
                .withPartnerConfiguration(request.getPartnerConfiguration())
                .build();

        PaymentResponse paymentResponseFromPaymentRedirect = paymentWithRedirectionService.finalizeRedirectionPayment(redirectionPaymentRequest);
        Assertions.assertEquals(paymentResponseFromPaymentRedirect.getClass(), PaymentResponseSuccess.class);
    }

    @Override
    public PaymentRequest createDefaultPaymentRequest() {
        return MockUtils.aPaylinePaymentRequest();
    }
}
