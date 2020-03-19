package com.payline.payment.splitit.bean.nesteed;

import com.google.gson.annotations.SerializedName;

public class ConsumerData {
    @SerializedName("FullName")
    private String fullName;
    @SerializedName("Email")
    private String email;
    @SerializedName("PhoneNumber")
    private String phoneNumber;
    @SerializedName("CultureName")
    private String cultureName;

    private ConsumerData(ConsumerDataBuilder builder) {
        fullName = builder.fullName;
        email = builder.email;
        phoneNumber = builder.phoneNumber;
        cultureName = builder.cultureName;
    }

    public static class ConsumerDataBuilder {
        private String fullName;
        private String email;
        private String phoneNumber;
        private String cultureName;

        public ConsumerDataBuilder withFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public ConsumerDataBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public ConsumerDataBuilder withPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public ConsumerDataBuilder withCultureName(String cultureName) {
            this.cultureName = cultureName;
            return this;
        }

        public ConsumerData build() {
            return new ConsumerData(this);
        }
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCultureName() {
        return cultureName;
    }

}
