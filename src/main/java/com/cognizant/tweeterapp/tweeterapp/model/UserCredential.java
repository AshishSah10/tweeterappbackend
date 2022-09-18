package com.cognizant.tweeterapp.tweeterapp.model;

public class UserCredential {
    private String loginId;
    private String rawPassword;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getRawPassword() {
        return rawPassword;
    }

    public void setRawPassword(String rawPassword) {
        this.rawPassword = rawPassword;
    }
}
