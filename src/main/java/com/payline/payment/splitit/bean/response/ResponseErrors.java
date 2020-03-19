package com.payline.payment.splitit.bean.response;

import com.google.gson.annotations.SerializedName;

public class ResponseErrors {
    @SerializedName("ErrorCode")
    private String errorCode;
    @SerializedName("Message")
    private String message;
    @SerializedName("AdditionalInfo")
    private String additionalInfo;

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

}
