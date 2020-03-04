package com.payline.payment.splitit.bean.nesteed;
// todo attention au import inutiles
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

// todo: passe en private tout ce que tu peux, plus c'est encapsulé mieux c'est
public class RequestHeader {
    @SerializedName("SessionId")
    String sessionId;
    @SerializedName("ApiKey")
    String apiKey;

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    // todo oubli pas le constructeur private pour ecraser le public, sinon on pourrais creer une instance vide sans passer par le Builder

    public static class RequestHeaderBuilder {
        String sessionId;
        String apiKey;

        public RequestHeaderBuilder withSessionId(String sessionId) {
            this.sessionId = sessionId;
            return this;
        }

        public RequestHeaderBuilder withApiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        public RequestHeader build() {
            RequestHeader requestHeader = new RequestHeader();
            requestHeader.sessionId = sessionId;
            requestHeader.apiKey = apiKey;
            return requestHeader;
        }
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getApiKey() {
        return apiKey;
    }

}
