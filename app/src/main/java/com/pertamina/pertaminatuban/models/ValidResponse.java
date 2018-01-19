package com.pertamina.pertaminatuban.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by laptop on 1/19/2018.
 */

public class ValidResponse {
    @SerializedName("success")
    @Expose
    private boolean success;

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("key")
    @Expose
    private String key;

    public ValidResponse(boolean success, String token, String key) {
        this.success = success;
        this.token = token;
        this.key = key;
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
}
