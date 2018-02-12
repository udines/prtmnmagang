package com.pertamina.pertaminatuban.marine.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TankerMovement {

    @SerializedName("unique_group")
    @Expose
    private String id;
    @SerializedName("bulan")
    @Expose
    private String bulan;
    @SerializedName("call_tanker")
    @Expose
    private String call_number;
    @SerializedName("kapal_nama")
    @Expose
    private String kapal;
    @SerializedName("periode")
    @Expose
    private String periode;


    @SerializedName("allFast")
    @Expose
    private String allFast;
    @SerializedName("channelConnection")
    @Expose
    private String channelConnection;
    @SerializedName("dryCertificateIssued_1")
    @Expose
    private String dryCertifIssued1;
    @SerializedName("completedCargoCalculation_1")
    @Expose
    private String completedCargoCalculation1;
    @SerializedName("labTestReleasedBeforeDisch")
    @Expose
    private String labTestReleased1;
    @SerializedName("commenceDischargeLoading")
    @Expose
    private String commenceDisLoad;
    @SerializedName("completedLoadingDischarge")
    @Expose
    private String completedDisLoad;
    @SerializedName("completedHoseDisconnect")
    @Expose
    private String completedHoseDis;
    @SerializedName("dryCertificateIssued_2")
    @Expose
    private String dryCertifIssued2;
    @SerializedName("completedCargoCalculation_2")
    @Expose
    private String completedCargoCalculation2;
    @SerializedName("labTestReleasedAfterLoad")
    @Expose
    private String labTestReleased2;
    @SerializedName("cargoDocumentOnBoard")
    @Expose
    private String cargoDocument;
    @SerializedName("portClearence")
    @Expose
    private String portClearence;
    @SerializedName("bookingPilotforUnberthing")
    @Expose
    private String bookingPilotUnberthing;
    @SerializedName("pilotOnBoardforUnberthing")
    @Expose
    private String pilotOnBoardUnberthing;
    @SerializedName("castOff")
    @Expose
    private String castOff;
    @SerializedName("anchoredOnBoard")
    @Expose
    private String anchoredRede;
    @SerializedName("pilotOnBoardforDeparture")
    @Expose
    private String pilotOnBoardDeparture;
    @SerializedName("anchorforDeparture")
    @Expose
    private String anchorDeparture;
    @SerializedName("actualTimeDeparture")
    @Expose
    private String actualTimeDeparture;
    @SerializedName("delivery")
    @Expose
    private String delivery;
    @SerializedName("reDelivery")
    @Expose
    private String redelivery;
    @SerializedName("onHire")
    @Expose
    private String onHire;
    @SerializedName("offHire")
    @Expose
    private String offHire;
    @SerializedName("timeOFFtoON")
    @Expose
    private String offToOn;
    @SerializedName("remarksActivity")
    @Expose
    private String remarksActivity;

    public TankerMovement(String allFast, String channelConnection, String dryCertifIssued1, String completedCargoCalculation1, String labTestReleased1, String commenceDisLoad, String completedDisLoad, String completedHoseDis, String dryCertifIssued2, String completedCargoCalculation2, String labTestReleased2, String cargoDocument, String portClearence, String bookingPilotUnberthing, String pilotOnBoardUnberthing, String castOff, String anchoredRede, String pilotOnBoardDeparture, String anchorDeparture, String actualTimeDeparture, String delivery, String redelivery, String onHire, String offHire, String offToOn, String remarksActivity) {
        this.allFast = allFast;
        this.channelConnection = channelConnection;
        this.dryCertifIssued1 = dryCertifIssued1;
        this.completedCargoCalculation1 = completedCargoCalculation1;
        this.labTestReleased1 = labTestReleased1;
        this.commenceDisLoad = commenceDisLoad;
        this.completedDisLoad = completedDisLoad;
        this.completedHoseDis = completedHoseDis;
        this.dryCertifIssued2 = dryCertifIssued2;
        this.completedCargoCalculation2 = completedCargoCalculation2;
        this.labTestReleased2 = labTestReleased2;
        this.cargoDocument = cargoDocument;
        this.portClearence = portClearence;
        this.bookingPilotUnberthing = bookingPilotUnberthing;
        this.pilotOnBoardUnberthing = pilotOnBoardUnberthing;
        this.castOff = castOff;
        this.anchoredRede = anchoredRede;
        this.pilotOnBoardDeparture = pilotOnBoardDeparture;
        this.anchorDeparture = anchorDeparture;
        this.actualTimeDeparture = actualTimeDeparture;
        this.delivery = delivery;
        this.redelivery = redelivery;
        this.onHire = onHire;
        this.offHire = offHire;
        this.offToOn = offToOn;
        this.remarksActivity = remarksActivity;
    }

    public String getId() {
        return id;
    }

    public String getBulan() {
        return bulan;
    }

    public String getCall_number() {
        return call_number;
    }

    public String getPeriode() {
        return periode;
    }

    public String getKapal() {
        return kapal;
    }

    public String getAllFast() {
        return allFast;
    }

    public void setAllFast(String allFast) {
        this.allFast = allFast;
    }

    public String getChannelConnection() {
        return channelConnection;
    }

    public void setChannelConnection(String channelConnection) {
        this.channelConnection = channelConnection;
    }

    public String getDryCertifIssued1() {
        return dryCertifIssued1;
    }

    public void setDryCertifIssued1(String dryCertifIssued1) {
        this.dryCertifIssued1 = dryCertifIssued1;
    }

    public String getCompletedCargoCalculation1() {
        return completedCargoCalculation1;
    }

    public void setCompletedCargoCalculation1(String completedCargoCalculation1) {
        this.completedCargoCalculation1 = completedCargoCalculation1;
    }

    public String getLabTestReleased1() {
        return labTestReleased1;
    }

    public void setLabTestReleased1(String labTestReleased1) {
        this.labTestReleased1 = labTestReleased1;
    }

    public String getCommenceDisLoad() {
        return commenceDisLoad;
    }

    public void setCommenceDisLoad(String commenceDisLoad) {
        this.commenceDisLoad = commenceDisLoad;
    }

    public String getCompletedDisLoad() {
        return completedDisLoad;
    }

    public void setCompletedDisLoad(String completedDisLoad) {
        this.completedDisLoad = completedDisLoad;
    }

    public String getCompletedHoseDis() {
        return completedHoseDis;
    }

    public void setCompletedHoseDis(String completedHoseDis) {
        this.completedHoseDis = completedHoseDis;
    }

    public String getDryCertifIssued2() {
        return dryCertifIssued2;
    }

    public void setDryCertifIssued2(String dryCertifIssued2) {
        this.dryCertifIssued2 = dryCertifIssued2;
    }

    public String getCompletedCargoCalculation2() {
        return completedCargoCalculation2;
    }

    public void setCompletedCargoCalculation2(String completedCargoCalculation2) {
        this.completedCargoCalculation2 = completedCargoCalculation2;
    }

    public String getLabTestReleased2() {
        return labTestReleased2;
    }

    public void setLabTestReleased2(String labTestReleased2) {
        this.labTestReleased2 = labTestReleased2;
    }

    public String getCargoDocument() {
        return cargoDocument;
    }

    public void setCargoDocument(String cargoDocument) {
        this.cargoDocument = cargoDocument;
    }

    public String getPortClearence() {
        return portClearence;
    }

    public void setPortClearence(String portClearence) {
        this.portClearence = portClearence;
    }

    public String getBookingPilotUnberthing() {
        return bookingPilotUnberthing;
    }

    public void setBookingPilotUnberthing(String bookingPilotUnberthing) {
        this.bookingPilotUnberthing = bookingPilotUnberthing;
    }

    public String getPilotOnBoardUnberthing() {
        return pilotOnBoardUnberthing;
    }

    public void setPilotOnBoardUnberthing(String pilotOnBoardUnberthing) {
        this.pilotOnBoardUnberthing = pilotOnBoardUnberthing;
    }

    public String getCastOff() {
        return castOff;
    }

    public void setCastOff(String castOff) {
        this.castOff = castOff;
    }

    public String getAnchoredRede() {
        return anchoredRede;
    }

    public void setAnchoredRede(String anchoredRede) {
        this.anchoredRede = anchoredRede;
    }

    public String getPilotOnBoardDeparture() {
        return pilotOnBoardDeparture;
    }

    public void setPilotOnBoardDeparture(String pilotOnBoardDeparture) {
        this.pilotOnBoardDeparture = pilotOnBoardDeparture;
    }

    public String getAnchorDeparture() {
        return anchorDeparture;
    }

    public void setAnchorDeparture(String anchorDeparture) {
        this.anchorDeparture = anchorDeparture;
    }

    public String getActualTimeDeparture() {
        return actualTimeDeparture;
    }

    public void setActualTimeDeparture(String actualTimeDeparture) {
        this.actualTimeDeparture = actualTimeDeparture;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getRedelivery() {
        return redelivery;
    }

    public void setRedelivery(String redelivery) {
        this.redelivery = redelivery;
    }

    public String getOnHire() {
        return onHire;
    }

    public void setOnHire(String onHire) {
        this.onHire = onHire;
    }

    public String getOffHire() {
        return offHire;
    }

    public void setOffHire(String offHire) {
        this.offHire = offHire;
    }

    public String getOffToOn() {
        return offToOn;
    }

    public void setOffToOn(String offToOn) {
        this.offToOn = offToOn;
    }

    public String getRemarksActivity() {
        return remarksActivity;
    }

    public void setRemarksActivity(String remarksActivity) {
        this.remarksActivity = remarksActivity;
    }
}
