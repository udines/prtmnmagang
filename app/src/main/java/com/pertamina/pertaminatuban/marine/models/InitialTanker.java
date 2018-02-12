package com.pertamina.pertaminatuban.marine.models;

import android.content.res.Resources;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InitialTanker {

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


    @SerializedName("callTanker")
    @Expose
    private String call;
    @SerializedName("periode")
    @Expose
    private String period;
    @SerializedName("voyageTanker")
    @Expose
    private String voyage;
    @SerializedName("noBillOfLoading")
    @Expose
    private String noBill;
    @SerializedName("dateNoBillOfLoading")
    @Expose
    private String dateOfBill;
    @SerializedName("statusTanker")
    @Expose
    private String status;
    @SerializedName("statusOperasionalReport")
    @Expose
    private String statusOps;
    @SerializedName("grades")
    @Expose
    private String grades;
    @SerializedName("handingAgent")
    @Expose
    private String handlingAgent;
    @SerializedName("generalAgent")
    @Expose
    private String generalAgent;
    @SerializedName("cargoStatus")
    @Expose
    private String cargoStatus;
    @SerializedName("tankerActivity")
    @Expose
    private String tankerActivity;
    @SerializedName("pumpingMethode")
    @Expose
    private String pumpMethod;
    @SerializedName("barthingAtSPM")
    @Expose
    private String barthing;
    @SerializedName("portOfCall")
    @Expose
    private String portOfCall;
    @SerializedName("portOfCallReport")
    @Expose
    private String portOfCallReport;
    @SerializedName("lastPort")
    @Expose
    private String lastPort;
    @SerializedName("nextPort")
    @Expose
    private String nextPort;

    public InitialTanker(String call, String period, String voyage, String noBill, String dateOfBill, String status, String statusOps, String grades, String handlingAgent, String generalAgent, String cargoStatus, String tankerActivity, String pumpMethod, String barthing, String portOfCall, String portOfCallReport, String lastPort, String nextPort) {
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

    public String getBulan() {
        return bulan;
    }

    public String getKapal() {
        return kapal;
    }

    public String getPeriode() {
        return periode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCall() {
        return call;
    }

    public void setCall(String call) {
        this.call = call;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getVoyage() {
        return voyage;
    }

    public void setVoyage(String voyage) {
        this.voyage = voyage;
    }

    public String getNoBill() {
        return noBill;
    }

    public void setNoBill(String noBill) {
        this.noBill = noBill;
    }

    public String getDateOfBill() {
        return dateOfBill;
    }

    public void setDateOfBill(String dateOfBill) {
        this.dateOfBill = dateOfBill;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusOps() {
        return statusOps;
    }

    public void setStatusOps(String statusOps) {
        this.statusOps = statusOps;
    }

    public String getGrades() {
        return grades;
    }

    public void setGrades(String grades) {
        this.grades = grades;
    }

    public String getHandlingAgent() {
        return handlingAgent;
    }

    public void setHandlingAgent(String handlingAgent) {
        this.handlingAgent = handlingAgent;
    }

    public String getGeneralAgent() {
        return generalAgent;
    }

    public void setGeneralAgent(String generalAgent) {
        this.generalAgent = generalAgent;
    }

    public String getCargoStatus() {
        return cargoStatus;
    }

    public void setCargoStatus(String cargoStatus) {
        this.cargoStatus = cargoStatus;
    }

    public String getTankerActivity() {
        return tankerActivity;
    }

    public void setTankerActivity(String tankerActivity) {
        this.tankerActivity = tankerActivity;
    }

    public String getPumpMethod() {
        return pumpMethod;
    }

    public void setPumpMethod(String pumpMethod) {
        this.pumpMethod = pumpMethod;
    }

    public String getBarthing() {
        return barthing;
    }

    public void setBarthing(String barthing) {
        this.barthing = barthing;
    }

    public String getPortOfCall() {
        return portOfCall;
    }

    public void setPortOfCall(String portOfCall) {
        this.portOfCall = portOfCall;
    }

    public String getPortOfCallReport() {
        return portOfCallReport;
    }

    public void setPortOfCallReport(String portOfCallReport) {
        this.portOfCallReport = portOfCallReport;
    }

    public String getLastPort() {
        return lastPort;
    }

    public void setLastPort(String lastPort) {
        this.lastPort = lastPort;
    }

    public String getNextPort() {
        return nextPort;
    }

    public void setNextPort(String nextPort) {
        this.nextPort = nextPort;
    }
}
