package com.pertamina.pertaminatuban.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("success")
    @Expose
    private boolean success;

    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("role")
    @Expose
    private String role;

    @SerializedName("key")
    @Expose
    private String key;


    public LoginResponse() {
    }

    /*public LoginResponse(boolean success, String token, String role, String key) {
        this.success = success;
        this.token = token;
        this.role = role;
        this.key = key;
    }*/

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getToken() {
        return token;
    }

    public String getKey() {
        return key;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
