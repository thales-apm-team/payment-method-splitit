package com.payline.payment.splitit.bean.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseHeader {
    @SerializedName("Succeeded")
    private boolean succeeded;
    @SerializedName("Errors")
    private List<ResponseErrors> errors;

    public boolean isSucceeded() {
        return succeeded;
    }

    public List<ResponseErrors> getErrors() {
        return errors;
    }

}
