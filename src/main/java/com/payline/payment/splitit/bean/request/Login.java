package com.payline.payment.splitit.bean.request;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class Login {
    @SerializedName("UserName")
    private String username;
    @SerializedName("Password")
    private String password;

    private Login(LoginBuilder builder) {
        username = builder.username;
        password = builder.password;
    }

    public static class LoginBuilder {
        private String username;
        private String password;

        public LoginBuilder withUsername(String username) {
            this.username = username;
            return this;
        }

        public LoginBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Login build() {
            return new Login(this);
        }

    }

    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
