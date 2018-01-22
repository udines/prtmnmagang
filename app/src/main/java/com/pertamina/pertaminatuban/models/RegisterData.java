package com.pertamina.pertaminatuban.models;

public class RegisterData {
    private String username;
    private String email;
    private String password;
    private String passwordMatch;

    public RegisterData() {
    }

    public RegisterData(String username, String email, String password, String passwordMatch) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.passwordMatch = passwordMatch;
    }


}
