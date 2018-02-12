package com.pertamina.pertaminatuban.marine.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WaitingTime {

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


    @SerializedName("pilot")
    @Expose
    private int pilot;
    @SerializedName("laboratoriumAnalysis")
    @Expose
    private int labAnalysis;
    @SerializedName("tugBoat")
    @Expose
    private int tugBoat;
    @SerializedName("jetty")
    @Expose
    private int jetty;
    @SerializedName("daylight")
    @Expose
    private int daylight;
    @SerializedName("tide")
    @Expose
    private int tide;
    @SerializedName("ballastingDeballasting")
    @Expose
    private int ballast;
    @SerializedName("tankCleaning")
    @Expose
    private int tankCleaning;
    @SerializedName("nomination")
    @Expose
    private int nomination;
    @SerializedName("manPower")
    @Expose
    private int manPower;
    @SerializedName("badWeather")
    @Expose
    private int badWeater;
    @SerializedName("line")
    @Expose
    private int line;
    @SerializedName("cargo")
    @Expose
    private int cargo;
    @SerializedName("ullage")
    @Expose
    private int ullage;
    @SerializedName("supplyBunker")
    @Expose
    private int supplyBunker;
    @SerializedName("supplyFreshWater")
    @Expose
    private int supplyFreshWater;
    @SerializedName("actLoadDateActDischDate")
    @Expose
    private int actLoadDate;
    @SerializedName("preparation")
    @Expose
    private int preparation;
    @SerializedName("shoreOrder")
    @Expose
    private int shoreOrder;
    @SerializedName("shipClearence")
    @Expose
    private int shipClearence;
    @SerializedName("cargoDocument")
    @Expose
    private int cargoDocument;
    @SerializedName("slowPumpVessel")
    @Expose
    private int slowPumpVessel;
    @SerializedName("slowPumpShore")
    @Expose
    private int slowPumpShore;
    @SerializedName("cargoCalculation")
    @Expose
    private int cargoCalculation;
    @SerializedName("steamingINOUT")
    @Expose
    private int steamingInOut;
    @SerializedName("shipUnready")
    @Expose
    private int shipUnready;

    public WaitingTime(int pilot,
                       int labAnalysis,
                       int tugBoat,
                       int jetty,
                       int daylight,
                       int tide,
                       int ballast,
                       int tankCleaning,
                       int nomination,
                       int manPower,
                       int badWeater,
                       int line,
                       int cargo,
                       int ullage,
                       int supplyBunker,
                       int supplyFreshWater,
                       int actLoadDate,
                       int preparation,
                       int shoreOrder,
                       int shipClearence,
                       int cargoDocument,
                       int slowPumpVessel,
                       int slowPumpShore,
                       int cargoCalculation,
                       int steamingInOut,
                       int shipUnready) {
        this.pilot = pilot;
        this.labAnalysis = labAnalysis;
        this.tugBoat = tugBoat;
        this.jetty = jetty;
        this.daylight = daylight;
        this.tide = tide;
        this.ballast = ballast;
        this.tankCleaning = tankCleaning;
        this.nomination = nomination;
        this.manPower = manPower;
        this.badWeater = badWeater;
        this.line = line;
        this.cargo = cargo;
        this.ullage = ullage;
        this.supplyBunker = supplyBunker;
        this.supplyFreshWater = supplyFreshWater;
        this.actLoadDate = actLoadDate;
        this.preparation = preparation;
        this.shoreOrder = shoreOrder;
        this.shipClearence = shipClearence;
        this.cargoDocument = cargoDocument;
        this.slowPumpVessel = slowPumpVessel;
        this.slowPumpShore = slowPumpShore;
        this.cargoCalculation = cargoCalculation;
        this.steamingInOut = steamingInOut;
        this.shipUnready = shipUnready;
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

    public int getPilot() {
        return pilot;
    }

    public int getLabAnalysis() {
        return labAnalysis;
    }

    public int getTugBoat() {
        return tugBoat;
    }

    public int getJetty() {
        return jetty;
    }

    public int getDaylight() {
        return daylight;
    }

    public int getTide() {
        return tide;
    }

    public int getBallast() {
        return ballast;
    }

    public int getTankCleaning() {
        return tankCleaning;
    }

    public int getNomination() {
        return nomination;
    }

    public int getManPower() {
        return manPower;
    }

    public int getBadWeater() {
        return badWeater;
    }

    public int getLine() {
        return line;
    }

    public int getCargo() {
        return cargo;
    }

    public int getUllage() {
        return ullage;
    }

    public int getSupplyBunker() {
        return supplyBunker;
    }

    public int getSupplyFreshWater() {
        return supplyFreshWater;
    }

    public int getActLoadDate() {
        return actLoadDate;
    }

    public int getPreparation() {
        return preparation;
    }

    public int getShoreOrder() {
        return shoreOrder;
    }

    public int getShipClearence() {
        return shipClearence;
    }

    public int getCargoDocument() {
        return cargoDocument;
    }

    public int getSlowPumpVessel() {
        return slowPumpVessel;
    }

    public int getSlowPumpShore() {
        return slowPumpShore;
    }

    public int getCargoCalculation() {
        return cargoCalculation;
    }

    public int getSteamingInOut() {
        return steamingInOut;
    }

    public int getShipUnready() {
        return shipUnready;
    }
}
