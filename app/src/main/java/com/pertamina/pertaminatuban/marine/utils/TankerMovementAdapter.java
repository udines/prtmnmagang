package com.pertamina.pertaminatuban.marine.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.marine.models.Tanker;
import com.pertamina.pertaminatuban.marine.models.TankerMovement;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class TankerMovementAdapter extends RecyclerView.Adapter<TankerMovementViewHolder> {

    private ArrayList<Tanker> tankers;
    private ArrayList<TankerMovement> movements;

    public TankerMovementAdapter(ArrayList<Tanker> tankers, ArrayList<TankerMovement> movements) {
        this.tankers = tankers;
        this.movements = movements;
    }

    @Override
    public TankerMovementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_marine_tanker_movement, parent, false);
        return new TankerMovementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TankerMovementViewHolder holder, int position) {
        TankerMovement movement = movements.get(position);
        Tanker tanker = tankers.get(position);

        setDateText(holder.allFastDate, movement.getAllFast());
        setDateText(holder.channelConnectionDate, movement.getChannelConnection());
        setDateText(holder.dryCertIssuedDate1, movement.getDryCertifIssued1());
        setDateText(holder.compCargoCalcDate1, movement.getCompletedCargoCalculation1());
        setDateText(holder.labTestReleaseDate1, movement.getLabTestReleased1());
        setDateText(holder.commenceDisLoadDate, movement.getCommenceDisLoad());
        setDateText(holder.compDisLoadDate, movement.getCompletedDisLoad());
        setDateText(holder.compHoseDiscDate, movement.getCompletedHoseDis());
        setDateText(holder.dryCertIssuedDate2, movement.getDryCertifIssued2());
        setDateText(holder.compCargoCalcDate2, movement.getCompletedCargoCalculation2());
        setDateText(holder.labTestReleaseDate2, movement.getLabTestReleased2());
        setDateText(holder.cargoDocumentDate, movement.getCargoDocument());
        setDateText(holder.portClearanceDate, movement.getPortClearence());
        setDateText(holder.bookingPilotDate, movement.getBookingPilotUnberthing());
        setDateText(holder.pilotOnUnberDate, movement.getPilotOnBoardUnberthing());
        setDateText(holder.castOffDate, movement.getCastOff());
        setDateText(holder.anchoredDate, movement.getAnchoredRede());
        setDateText(holder.pilotOnDepartDate, movement.getPilotOnBoardDeparture());
        setDateText(holder.anchorDepartDate, movement.getAnchorDeparture());
        setDateText(holder.atdDate, movement.getActualTimeDeparture());
        setDateText(holder.deliveryDate, movement.getDelivery());
        setDateText(holder.redeliveryDate, movement.getRedelivery());
        setDateText(holder.onHireDate, movement.getOnHire());
        setDateText(holder.offHireDate, movement.getOffHire());
        setTimeText(holder.timeOffToOn, movement.getOffToOn());
        holder.remarks.setText(movement.getRemarksActivity());
        holder.vessel.setText(String.valueOf(tanker.getCall() + " " + tanker.getVesselName()));
    }

    @Override
    public int getItemCount() {
        return tankers.size();
    }

    private void setDateText(TextView text, Timestamp time) {
        java.util.Date date = new java.util.Date(time.getTime());
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
        text.setText(format.format(date));
    }

    private void setTimeText(TextView text, Timestamp time) {
        java.util.Date date = new java.util.Date(time.getTime());
        SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
        text.setText(format.format(date));
    }
}
