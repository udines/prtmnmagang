package com.pertamina.pertaminatuban.marine.models;

import java.sql.Date;

public class InitialTanker {
    private String id;
    private int call;
    private Date period;
    private String voyage;
    private String noBill;
    private Date dateOfBill;
    private String status;
    private String statusOps;
    private String grades;
    private String handlingAgent;
    private String generalAgent;
    private String cargoStatus;
    private String tankerActivity;
    private String pumpMethod;
    private String barthing;
    private String portOfCall;
    private String portOfCallReport;
    private String lastPort;
    private String nextPort;

    public InitialTanker(String id,
                         int call,
                         Date period,
                         String voyage,
                         String noBill,
                         Date dateOfBill,
                         String status,
                         String statusOps,
                         String grades,
                         String handlingAgent,
                         String generalAgent,
                         String cargoStatus,
                         String tankerActivity,
                         String pumpMethod,
                         String barthing,
                         String portOfCall,
                         String portOfCallReport,
                         String lastPort,
                         String nextPort) {
        this.id = id;
        this.call = call;
        this.period = period;
        this.voyage = voyage;
        this.noBill = noBill;
        this.dateOfBill = dateOfBill;
        this.status = status;
        this.statusOps = statusOps;
        this.grades = grades;
        this.handlingAgent = handlingAgent;
        this.generalAgent = generalAgent;
        this.cargoStatus = cargoStatus;
        this.tankerActivity = tankerActivity;
        this.pumpMethod = pumpMethod;
        this.barthing = barthing;
        this.portOfCall = portOfCall;
        this.portOfCallReport = portOfCallReport;
        this.lastPort = lastPort;
        this.nextPort = nextPort;
    }

    public String getStatusOps() {
        return statusOps;
    }

    public String getId() {
        return id;
    }

    public int getCall() {
        return call;
    }

    public Date getPeriod() {
        return period;
    }

    public String getVoyage() {
        return voyage;
    }

    public String getNoBill() {
        return noBill;
    }

    public Date getDateOfBill() {
        return dateOfBill;
    }

    public String getStatus() {
        return status;
    }

    public String getGrades() {
        return grades;
    }

    public String getHandlingAgent() {
        return handlingAgent;
    }

    public String getGeneralAgent() {
        return generalAgent;
    }

    public String getCargoStatus() {
        return cargoStatus;
    }

    public String getTankerActivity() {
        return tankerActivity;
    }

    public String getPumpMethod() {
        return pumpMethod;
    }

    public String getBarthing() {
        return barthing;
    }

    public String getPortOfCall() {
        return portOfCall;
    }

    public String getPortOfCallReport() {
        return portOfCallReport;
    }

    public String getLastPort() {
        return lastPort;
    }

    public String getNextPort() {
        return nextPort;
    }
}
