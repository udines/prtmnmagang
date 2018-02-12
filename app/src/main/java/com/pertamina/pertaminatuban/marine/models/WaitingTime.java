package com.pertamina.pertaminatuban.marine.models;

public class WaitingTime {
    private int pilot;
    private int labAnalysis;
    private int tugBoat;
    private int jetty;
    private int daylight;
    private int tide;
    private int ballast;
    private int tankCleaning;
    private int nomination;
    private int manPower;
    private int badWeater;
    private int line;
    private int cargo;
    private int ullage;
    private int supplyBunker;
    private int supplyFreshWater;
    private int actLoadDate;
    private int preparation;
    private int shoreOrder;
    private int shipClearence;
    private int cargoDocument;
    private int slowPumpVessel;
    private int slowPumpShore;
    private int cargoCalculation;
    private int steamingInOut;
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
