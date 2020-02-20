package com.payline.payment.moneytrack.bean.response;

import com.google.gson.GsonBuilder;
import com.payline.payment.moneytrack.utils.http.StringResponse;

public class ResponseParse {
    StringResponse response;

    public static class ResponseParseBuilder {
        StringResponse response;

        public ResponseParseBuilder withResponse(StringResponse response) {
            this.response = response;
            return this;
        }

        public ResponseParse build() {
            ResponseParse responseParse = new ResponseParse();
            responseParse.response = response;
            return responseParse;
        }
    }

    public boolean loginSuccess() {
        LoginResponse body = new GsonBuilder().create().fromJson(response.getContent(), LoginResponse.class);
        return (body.sessionId != null) && (body.responseHeader.succeeded == true);
    }
}
