package com.payline.payment.splitit.bean.response;

import com.google.gson.annotations.SerializedName;

public class ResponseErrors {
    // todo les private
    @SerializedName("ErrorCode")
    String errorCode;
    @SerializedName("Message")
    String message;
    @SerializedName("AdditionalInfo")
    String additionalInfo;

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
