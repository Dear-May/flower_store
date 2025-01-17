package com.example.demo.Entity;

public class UserEntity {
    private String userName;
    private String userPassword;
    private String userPhone;
    private String userImage;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPhone() { return userPhone;}

    public void setUserPhone(String userPhone) { this.userPhone = userPhone; }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    @Override
    public String toString() {
        return "{" +
                "\"userName\": \"" + userName + "\"," +
                "\"userPassword\": \"" + userPassword + "\"," +
                "\"userPhone\": \"" + userPhone + "\"," +
                "\"userImage\": \"" + userImage + "\"" +
                "}";
    }
}
