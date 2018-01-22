package com.pertamina.pertaminatuban.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by laptop on 1/22/2018.
 */

public class RegisterResponse {

    @SerializedName("sucess")
    @Expose
    private boolean success;

    @SerializedName("msg")
    @Expose
    private String msg;

    public RegisterResponse(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
