package com.payline.payment.splitit.bean;

import com.google.gson.annotations.SerializedName;

public class InstallmentPlanStatus {
    public enum Code {
        @SerializedName("Initializing")
        INITIALIZING,
        @SerializedName("Canceled")
        CANCELED,
        @SerializedName("PendingApproval")
        PENDING_APPROVAL,
        @SerializedName("PendingMerchantShipmen")
        PENDING_MERCHANT_SHIPMEN,
        @SerializedName("Notice")
        NOTICE,
        @SerializedName("InProgress")
        IN_PROGRESS,
        @SerializedName("Deleted")
        DELETED,
        @SerializedName("Cleared")
        CLEARED,
        @SerializedName("PendingPaymentUpdate")
        PENDING_PAYMENT_UPDATE,
        @SerializedName("Delayed")
        DELAYED
    }
    @SerializedName("Code")
    Code code;

    @SerializedName("Id")
    String id;
    @SerializedName("Description")
    String description;

    public Code getCode() {
        return code;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }


}
