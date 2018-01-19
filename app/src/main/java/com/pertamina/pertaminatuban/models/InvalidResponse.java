package com.pertamina.pertaminatuban.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by laptop on 1/19/2018.
 */

public class InvalidResponse {
    @SerializedName("success")
    @Expose
    private boolean success;

    @SerializedName("msg")
    @Expose
    private String msg;

    public InvalidResponse() {
    }

    public InvalidResponse(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMsg() {
        return msg;
    }
}
