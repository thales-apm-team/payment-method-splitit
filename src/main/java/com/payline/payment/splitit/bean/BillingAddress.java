package com.payline.payment.splitit.bean;

public class BillingAddress {
    String addressLine;
    String addressLine2;
    String city;
    String state;
    String country;
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

    @Override
    public String toString() {
        return "BillingAddress{" +
                "addressLine='" + addressLine + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", zip='" + zip + '\'' +
                '}';
    }
}
