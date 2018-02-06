package com.pertamina.pertaminatuban.marine.models;

import java.sql.Timestamp;

public class ShipCondition {
    private String descriptionDraft;
    private Timestamp actualTimeArrival;
    private Timestamp actualTimeDeparture;
    private String gradeBunker;
    private float robLastPort;
    private float robAta;
    private float robAtd;
    private float repl;
    private Timestamp comRepl;
    private Timestamp compRepl;
    private String replLocation;
    private float bunkerConsumptionPort;
    private float bunkerConsumptionSeatime;
    private float slopTankAta;
    private float slopTankAtd;

    public ShipCondition(String descriptionDraft,
                         Timestamp actualTimeArrival,
                         Timestamp actualTimeDeparture,
                         String gradeBunker,
                         float robLastPort,
                         float robAta,
                         float robAtd,
                         float repl,
                         Timestamp comRepl,
                         Timestamp compRepl,
                         String replLocation,
                         float bunkerConsumptionPort,
                         float bunkerConsumptionSeatime,
                         float slopTankAta,
                         float slopTankAtd) {
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

    public String getDescriptionDraft() {
        return descriptionDraft;
    }

    public Timestamp getActualTimeArrival() {
        return actualTimeArrival;
    }

    public Timestamp getActualTimeDeparture() {
        return actualTimeDeparture;
    }

    public String getGradeBunker() {
        return gradeBunker;
    }

    public float getRobLastPort() {
        return robLastPort;
    }

    public float getRobAta() {
        return robAta;
    }

    public float getRobAtd() {
        return robAtd;
    }

    public float getRepl() {
        return repl;
    }

    public Timestamp getComRepl() {
        return comRepl;
    }

    public Timestamp getCompRepl() {
        return compRepl;
    }

    public String getReplLocation() {
        return replLocation;
    }

    public float getBunkerConsumptionPort() {
        return bunkerConsumptionPort;
    }

    public float getBunkerConsumptionSeatime() {
        return bunkerConsumptionSeatime;
    }

    public float getSlopTankAta() {
        return slopTankAta;
    }

    public float getSlopTankAtd() {
        return slopTankAtd;
    }
}
