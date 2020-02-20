package com.payline.payment.moneytrack.bean.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseHeader {
    @SerializedName("Succeeded")
    Boolean succeeded;
    @SerializedName("Errors")
    List<ResponseErrors> errors;

    public Boolean isSucceeded() {
        return succeeded;
    }

    public List<ResponseErrors> getErrors() {
        return errors;
    }

    public static class ResponseHeaderBuilder {
        Boolean succeeded;
        List<ResponseErrors> errors;

        public ResponseHeaderBuilder withSucceeded(boolean succeeded) {
            this.succeeded = succeeded;
            return this;
        }

        public ResponseHeaderBuilder withErrors(List<ResponseErrors> errors) {
            this.errors = errors;
            return this;
        }

        public ResponseHeader build() {
            ResponseHeader responseHeader = new ResponseHeader();
            responseHeader.succeeded = succeeded;
            responseHeader.errors = errors;
            return responseHeader;
        }
    }
}
