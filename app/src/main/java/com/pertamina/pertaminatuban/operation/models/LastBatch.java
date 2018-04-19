package com.pertamina.pertaminatuban.operation.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LastBatch {
    @SerializedName("last_batch")
    @Expose
    private int lastBatch;

    public String getLastBatch() {
        return String.valueOf(lastBatch);
    }
}
