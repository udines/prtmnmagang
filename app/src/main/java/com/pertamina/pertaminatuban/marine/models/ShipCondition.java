package com.pertamina.pertaminatuban.marine.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class ShipCondition {
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


    @SerializedName("descriptionDraft")
    @Expose
    private String descriptionDraft;
    @SerializedName("draftActualTimeArrival")
    @Expose
    private String actualTimeArrival;
    @SerializedName("draftActualTimeDeparture")
    @Expose
    private String actualTimeDeparture;
    @SerializedName("gradesBunker")
    @Expose
    private String gradeBunker;
    @SerializedName("remainingOnBoardLastPort")
    @Expose
    private String robLastPort;
    @SerializedName("remainingOnBoardActualTimeArrival")
    @Expose
    private String robAta;
    @SerializedName("remainingOnBoardActualTimeDeparture")
    @Expose
    private String robAtd;
    @SerializedName("repl")
    @Expose
    private String repl;
    @SerializedName("comREPL")
    @Expose
    private String comRepl;
    @SerializedName("comPREPL")
    @Expose
    private String compRepl;
    @SerializedName("locationOfREPL")
    @Expose
    private String replLocation;
    @SerializedName("bunkerConsumptionPort")
    @Expose
    private String bunkerConsumptionPort;
    @SerializedName("bunkerConsumptionSeaTime")
    @Expose
    private String bunkerConsumptionSeatime;
    @SerializedName("slopTankActualTimeArrival")
    @Expose
    private String slopTankAta;
    @SerializedName("slopTankActualTimeDeparture")
    @Expose
    private String slopTankAtd;

    public ShipCondition(String descriptionDraft, String actualTimeArrival, String actualTimeDeparture, String gradeBunker, String robLastPort, String robAta, String robAtd, String repl, String comRepl, String compRepl, String replLocation, String bunkerConsumptionPort, String bunkerConsumptionSeatime, String slopTankAta, String slopTankAtd) {
        this.descriptionDraft = descriptionDraft;
        this.actualTimeArrival = actualTimeArrival;
        this.actualTimeDeparture = actualTimeDeparture;
        this.gradeBunker = gradeBunker;
        this.robLastPort = robLastPort;
        this.robAta = robAta;
        this.robAtd = robAtd;
        this.repl = repl;
        this.comRepl = comRepl;
        this.compRepl = compRepl;
        this.replLocation = replLocation;
        this.bunkerConsumptionPort = bunkerConsumptionPort;
        this.bunkerConsumptionSeatime = bunkerConsumptionSeatime;
        this.slopTankAta = slopTankAta;
        this.slopTankAtd = slopTankAtd;
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

    public String getDescriptionDraft() {
        return descriptionDraft;
    }

    public void setDescriptionDraft(String descriptionDraft) {
        this.descriptionDraft = descriptionDraft;
    }

    public String getActualTimeArrival() {
        return actualTimeArrival;
    }

    public void setActualTimeArrival(String actualTimeArrival) {
        this.actualTimeArrival = actualTimeArrival;
    }

    public String getActualTimeDeparture() {
        return actualTimeDeparture;
    }

    public void setActualTimeDeparture(String actualTimeDeparture) {
        this.actualTimeDeparture = actualTimeDeparture;
    }

    public String getGradeBunker() {
        return gradeBunker;
    }

    public void setGradeBunker(String gradeBunker) {
        this.gradeBunker = gradeBunker;
    }

    public String getRobLastPort() {
        return robLastPort;
    }

    public void setRobLastPort(String robLastPort) {
        this.robLastPort = robLastPort;
    }

    public String getRobAta() {
        return robAta;
    }

    public void setRobAta(String robAta) {
        this.robAta = robAta;
    }

    public String getRobAtd() {
        return robAtd;
    }

    public void setRobAtd(String robAtd) {
        this.robAtd = robAtd;
    }

    public String getRepl() {
        return repl;
    }

    public void setRepl(String repl) {
        this.repl = repl;
    }

    public String getComRepl() {
        return comRepl;
    }

    public void setComRepl(String comRepl) {
        this.comRepl = comRepl;
    }

    public String getCompRepl() {
        return compRepl;
    }

    public void setCompRepl(String compRepl) {
        this.compRepl = compRepl;
    }

    public String getReplLocation() {
        return replLocation;
    }

    public void setReplLocation(String replLocation) {
        this.replLocation = replLocation;
    }

    public String getBunkerConsumptionPort() {
        return bunkerConsumptionPort;
    }

    public void setBunkerConsumptionPort(String bunkerConsumptionPort) {
        this.bunkerConsumptionPort = bunkerConsumptionPort;
    }

    public String getBunkerConsumptionSeatime() {
        return bunkerConsumptionSeatime;
    }

    public void setBunkerConsumptionSeatime(String bunkerConsumptionSeatime) {
        this.bunkerConsumptionSeatime = bunkerConsumptionSeatime;
    }

    public String getSlopTankAta() {
        return slopTankAta;
    }

    public void setSlopTankAta(String slopTankAta) {
        this.slopTankAta = slopTankAta;
    }

    public String getSlopTankAtd() {
        return slopTankAtd;
    }

    public void setSlopTankAtd(String slopTankAtd) {
        this.slopTankAtd = slopTankAtd;
    }
}
