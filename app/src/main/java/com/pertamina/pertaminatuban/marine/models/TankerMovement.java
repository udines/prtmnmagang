package com.pertamina.pertaminatuban.marine.models;

import java.sql.Timestamp;

public class TankerMovement {
    private Timestamp allFast;
    private Timestamp channelConnection;
    private Timestamp dryCertifIssued1;
    private Timestamp completedCargoCalculation1;
    private Timestamp labTestReleased1;
    private Timestamp commenceDisLoad;
    private Timestamp completedDisLoad;
    private Timestamp completedHoseDis;
    private Timestamp dryCertifIssued2;
    private Timestamp completedCargoCalculation2;
    private Timestamp labTestReleased2;
    private Timestamp cargoDocument;
    private Timestamp portClearence;
    private Timestamp bookingPilotUnberthing;
    private Timestamp pilotOnBoardUnberthing;
    private Timestamp castOff;
    private Timestamp anchoredRede;
    private Timestamp pilotOnBoardDeparture;
    private Timestamp anchorDeparture;
    private Timestamp actualTimeDeparture;
    private Timestamp delivery;
    private Timestamp redelivery;
    private Timestamp onHire;
    private Timestamp offHire;
    private Timestamp offToOn;
    private String remarksActivity;

    public TankerMovement(Timestamp allFast,
                          Timestamp channelConnection,
                          Timestamp dryCertifIssued1,
                          Timestamp completedCargoCalculation1,
                          Timestamp labTestReleased1,
                          Timestamp commenceDisLoad,
                          Timestamp completedDisLoad,
                          Timestamp completedHoseDis,
                          Timestamp dryCertifIssued2,
                          Timestamp completedCargoCalculation2,
                          Timestamp labTestReleased2,
                          Timestamp cargoDocument,
                          Timestamp portClearence,
                          Timestamp bookingPilotUnberthing,
                          Timestamp pilotOnBoardUnberthing,
                          Timestamp castOff,
                          Timestamp anchoredRede,
                          Timestamp pilotOnBoardDeparture,
                          Timestamp anchorDeparture,
                          Timestamp actualTimeDeparture,
                          Timestamp delivery,
                          Timestamp redelivery,
                          Timestamp onHire,
                          Timestamp offHire,
                          Timestamp offToOn,
                          String remarksActivity) {
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

    public Timestamp getAllFast() {
        return allFast;
    }

    public Timestamp getChannelConnection() {
        return channelConnection;
    }

    public Timestamp getDryCertifIssued1() {
        return dryCertifIssued1;
    }

    public Timestamp getCompletedCargoCalculation1() {
        return completedCargoCalculation1;
    }

    public Timestamp getLabTestReleased1() {
        return labTestReleased1;
    }

    public Timestamp getCommenceDisLoad() {
        return commenceDisLoad;
    }

    public Timestamp getCompletedDisLoad() {
        return completedDisLoad;
    }

    public Timestamp getCompletedHoseDis() {
        return completedHoseDis;
    }

    public Timestamp getDryCertifIssued2() {
        return dryCertifIssued2;
    }

    public Timestamp getCompletedCargoCalculation2() {
        return completedCargoCalculation2;
    }

    public Timestamp getLabTestReleased2() {
        return labTestReleased2;
    }

    public Timestamp getCargoDocument() {
        return cargoDocument;
    }

    public Timestamp getPortClearence() {
        return portClearence;
    }

    public Timestamp getBookingPilotUnberthing() {
        return bookingPilotUnberthing;
    }

    public Timestamp getPilotOnBoardUnberthing() {
        return pilotOnBoardUnberthing;
    }

    public Timestamp getCastOff() {
        return castOff;
    }

    public Timestamp getAnchoredRede() {
        return anchoredRede;
    }

    public Timestamp getPilotOnBoardDeparture() {
        return pilotOnBoardDeparture;
    }

    public Timestamp getAnchorDeparture() {
        return anchorDeparture;
    }

    public Timestamp getActualTimeDeparture() {
        return actualTimeDeparture;
    }

    public Timestamp getDelivery() {
        return delivery;
    }

    public Timestamp getRedelivery() {
        return redelivery;
    }

    public Timestamp getOnHire() {
        return onHire;
    }

    public Timestamp getOffHire() {
        return offHire;
    }

    public Timestamp getOffToOn() {
        return offToOn;
    }

    public String getRemarksActivity() {
        return remarksActivity;
    }
}
