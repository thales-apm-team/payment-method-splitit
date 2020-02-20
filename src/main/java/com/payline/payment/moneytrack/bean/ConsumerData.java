package com.payline.payment.moneytrack.bean;

public class ConsumerData {
    String fullName;
    String email;
    String phoneNumber;
    String cultureName;

    public static class ConsumerDataBuilder {
        String fullName;
        String email;
        String phoneNumber;
        String cultureName;

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
            ConsumerData consumerData = new ConsumerData();
            consumerData.fullName = fullName;
            consumerData.email = email;
            consumerData.phoneNumber = phoneNumber;
            consumerData.cultureName = cultureName;
            return consumerData;
        }
    }

    @Override
    public String toString() {
        return "ConsumerData{" +
                "fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", cultureName='" + cultureName + '\'' +
                '}';
    }
}
