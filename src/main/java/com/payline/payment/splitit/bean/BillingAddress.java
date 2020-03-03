package com.payline.payment.splitit.bean;

import com.google.gson.annotations.SerializedName;

public class BillingAddress {
    @SerializedName("AddressLine")
    String addressLine;
    @SerializedName("AddressLine2")
    String addressLine2;
    @SerializedName("City")
    String city;
    @SerializedName("State")
    String state;
    @SerializedName("Country")
    String country;
    @SerializedName("Zip")
    String zip;

    public static class BillingAddressBuilder {
        String addressLine;
        String addressLine2;
        String city;
        String state;
        String country;
        String zip;

        public BillingAddressBuilder withAddressLine(String addressLine) {
            this.addressLine = addressLine;
            return this;
        }

        public BillingAddressBuilder withAddressLine2(String addressLine2) {
            this.addressLine2 = addressLine2;
            return this;
        }

        public BillingAddressBuilder withCity(String city) {
            this.city = city;
            return this;
        }

        public BillingAddressBuilder withState(String state) {
            this.state = state;
            return this;
        }

        public BillingAddressBuilder withCountry(String country) {
            this.country = country;
            return this;
        }

        public BillingAddressBuilder withZip(String zip) {
            this.zip = zip;
            return this;
        }

        public BillingAddress build() {
            BillingAddress billingAddress = new BillingAddress();
            billingAddress.addressLine = addressLine;
            billingAddress.addressLine2 = addressLine2;
            billingAddress.city = city;
            billingAddress.state = state;
            billingAddress.country = country;
            billingAddress.zip = zip;
            return billingAddress;
        }
    }

    public String getAddressLine() {
        return addressLine;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getZip() {
        return zip;
    }

}
