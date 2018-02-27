package com.pertamina.pertaminatuban.finance.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostRealisasiAnggaran {

    @SerializedName("cost_center")
    @Expose
    private String cost_center;

    @SerializedName("cost_group")
    @Expose
    private String cost_group;

    public PostRealisasiAnggaran(String cost_center, String cost_group) {
        this.cost_center = cost_center;
        this.cost_group = cost_group;
    }
}
