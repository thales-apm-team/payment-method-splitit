package com.payline.payment.splitit.bean.nesteed;
// todo attention les imports inutiles
import com.google.gson.Gson;
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

    // todo oubli pas le constructeur private pour ecraser le public, sinon on pourrais creer une instance vide sans passer par le Builder


    // todo: passe en private tout ce que tu peux, plus c'est encapsulé mieux c'est (meme dans le builder)
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
