package com.pertamina.pertaminatuban.marine.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class TemporaryStop {
    @SerializedName("unique_group")
    @Expose
    private String id;
    @SerializedName("bulan")
    @Expose
    private String bulan;
    @SerializedName("call_tanker")
    @Expose
    private String kapal;
    @SerializedName("periode")
    @Expose
    private String periode;


    @SerializedName("reasonStop_1")
    @Expose
    private String reason1;
    @SerializedName("start_1")
    @Expose
    private String stopTime1;
    @SerializedName("resume_1")
    @Expose
    private String resumeTime1;
    @SerializedName("reasonStop_2")
    @Expose
    private String reason2;
    @SerializedName("start_2")
    @Expose
    private String stopTime2;
    @SerializedName("resume_2")
    @Expose
    private String resumeTime2;
    @SerializedName("reasonStop_3")
    @Expose
    private String reason3;
    @SerializedName("start_3")
    @Expose
    private String stopTime3;
    @SerializedName("resume_3")
    @Expose
    private String resumeTime3;
    @SerializedName("reasonStop_4")
    @Expose
    private String reason4;
    @SerializedName("start_4")
    @Expose
    private String stopTime4;
    @SerializedName("resume_4")
    @Expose
    private String resumeTime4;
    @SerializedName("reasonStop_5")
    @Expose
    private String reason5;
    @SerializedName("start_5")
    @Expose
    private String stopTime5;
    @SerializedName("resume_5")
    @Expose
    private String resumeTime5;

    public TemporaryStop(String reason1, String stopTime1, String resumeTime1, String reason2, String stopTime2, String resumeTime2, String reason3, String stopTime3, String resumeTime3, String reason4, String stopTime4, String resumeTime4, String reason5, String stopTime5, String resumeTime5) {
        this.reason1 = reason1;
        this.stopTime1 = stopTime1;
        this.resumeTime1 = resumeTime1;
        this.reason2 = reason2;
        this.stopTime2 = stopTime2;
        this.resumeTime2 = resumeTime2;
        this.reason3 = reason3;
        this.stopTime3 = stopTime3;
        this.resumeTime3 = resumeTime3;
        this.reason4 = reason4;
        this.stopTime4 = stopTime4;
        this.resumeTime4 = resumeTime4;
        this.reason5 = reason5;
        this.stopTime5 = stopTime5;
        this.resumeTime5 = resumeTime5;
    }

    public String getId() {
        return id;
    }

    public String getBulan() {
        return bulan;
    }

    public String getKapal() {
        return kapal;
    }

    public String getPeriode() {
        return periode;
    }

    public String getReason1() {
        return reason1;
    }

    public void setReason1(String reason1) {
        this.reason1 = reason1;
    }

    public String getStopTime1() {
        return stopTime1;
    }

    public void setStopTime1(String stopTime1) {
        this.stopTime1 = stopTime1;
    }

    public String getResumeTime1() {
        return resumeTime1;
    }

    public void setResumeTime1(String resumeTime1) {
        this.resumeTime1 = resumeTime1;
    }

    public String getReason2() {
        return reason2;
    }

    public void setReason2(String reason2) {
        this.reason2 = reason2;
    }

    public String getStopTime2() {
        return stopTime2;
    }

    public void setStopTime2(String stopTime2) {
        this.stopTime2 = stopTime2;
    }

    public String getResumeTime2() {
        return resumeTime2;
    }

    public void setResumeTime2(String resumeTime2) {
        this.resumeTime2 = resumeTime2;
    }

    public String getReason3() {
        return reason3;
    }

    public void setReason3(String reason3) {
        this.reason3 = reason3;
    }

    public String getStopTime3() {
        return stopTime3;
    }

    public void setStopTime3(String stopTime3) {
        this.stopTime3 = stopTime3;
    }

    public String getResumeTime3() {
        return resumeTime3;
    }

    public void setResumeTime3(String resumeTime3) {
        this.resumeTime3 = resumeTime3;
    }

    public String getReason4() {
        return reason4;
    }

    public void setReason4(String reason4) {
        this.reason4 = reason4;
    }

    public String getStopTime4() {
        return stopTime4;
    }

    public void setStopTime4(String stopTime4) {
        this.stopTime4 = stopTime4;
    }

    public String getResumeTime4() {
        return resumeTime4;
    }

    public void setResumeTime4(String resumeTime4) {
        this.resumeTime4 = resumeTime4;
    }

    public String getReason5() {
        return reason5;
    }

    public void setReason5(String reason5) {
        this.reason5 = reason5;
    }

    public String getStopTime5() {
        return stopTime5;
    }

    public void setStopTime5(String stopTime5) {
        this.stopTime5 = stopTime5;
    }

    public String getResumeTime5() {
        return resumeTime5;
    }

    public void setResumeTime5(String resumeTime5) {
        this.resumeTime5 = resumeTime5;
    }
}
