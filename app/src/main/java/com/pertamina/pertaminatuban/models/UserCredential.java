package com.pertamina.pertaminatuban.models;

public class UserCredential {

    /*variabel konstan agar string konsisten di semua class*/
    public static final String DIVISI_1 = "";
    public static final String DIVISI_2 = "";
    public static final String DIVISI_3 = "";
    public static final String DIVISI_4 = "";
    public static final String DIVISI_5 = "";
    public static final String DIVISI_6 = "";

    private String username;
    private String password;

    public UserCredential(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
