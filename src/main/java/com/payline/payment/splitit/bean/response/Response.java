package com.payline.payment.splitit.bean.response;

import com.google.gson.annotations.SerializedName;

public abstract class Response {
    @SerializedName("ResponseHeader")
    private ResponseHeader responseHeader;

    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }
}
