package com.payline.payment.splitit.bean.nesteed;

import com.google.gson.annotations.SerializedName;

public class BillingAddress {
    @SerializedName("AddressLine")
    private String addressLine;
    @SerializedName("AddressLine2")
    private String addressLine2;
    @SerializedName("City")
    private String city;
    @SerializedName("State")
    private String state;
    @SerializedName("Country")
    private String country;
    @SerializedName("Zip")
    private String zip;

    private BillingAddress(BillingAddressBuilder builder) {
        addressLine = builder.addressLine;
        addressLine2 = builder.addressLine2;
        city = builder.city;
        state = builder.state;
        country = builder.country;
        zip = builder.zip;
    }


    public static class BillingAddressBuilder {
        private String addressLine;
        private String addressLine2;
        private String city;
        private String state;
        private String country;
        private String zip;

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
            return new BillingAddress(this);
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
