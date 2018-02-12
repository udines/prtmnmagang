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

    private ArrayList<TankerMovement> movements;

    public TankerMovementAdapter(ArrayList<TankerMovement> movements) {
        this.movements = movements;
    }

    @Override
    public TankerMovementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_marine_tanker_movement, parent, false);
        return new TankerMovementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TankerMovementViewHolder holder, int position) {
        TankerMovement model = movements.get(position);

        setDateText(holder.allFastDate, model.getAllFast());
        setDateText(holder.channelConnectionDate, model.getChannelConnection());
        setDateText(holder.dryCertIssuedDate1, model.getDryCertifIssued1());
        setDateText(holder.compCargoCalcDate1, model.getCompletedCargoCalculation1());
        setDateText(holder.labTestReleaseDate1, model.getLabTestReleased1());
        setDateText(holder.commenceDisLoadDate, model.getCommenceDisLoad());
        setDateText(holder.compDisLoadDate, model.getCompletedDisLoad());
        setDateText(holder.compHoseDiscDate, model.getCompletedHoseDis());
        setDateText(holder.dryCertIssuedDate2, model.getDryCertifIssued2());
        setDateText(holder.compCargoCalcDate2, model.getCompletedCargoCalculation2());
        setDateText(holder.labTestReleaseDate2, model.getLabTestReleased2());
        setDateText(holder.cargoDocumentDate, model.getCargoDocument());
        setDateText(holder.portClearanceDate, model.getPortClearence());
        setDateText(holder.bookingPilotDate, model.getBookingPilotUnberthing());
        setDateText(holder.pilotOnUnberDate, model.getPilotOnBoardUnberthing());
        setDateText(holder.castOffDate, model.getCastOff());
        setDateText(holder.anchoredDate, model.getAnchoredRede());
        setDateText(holder.pilotOnDepartDate, model.getPilotOnBoardDeparture());
        setDateText(holder.anchorDepartDate, model.getAnchorDeparture());
        setDateText(holder.atdDate, model.getActualTimeDeparture());
        setDateText(holder.deliveryDate, model.getDelivery());
        setDateText(holder.redeliveryDate, model.getRedelivery());
        setDateText(holder.onHireDate, model.getOnHire());
        setDateText(holder.offHireDate, model.getOffHire());
        setTimeText(holder.timeOffToOn, model.getOffToOn());
        holder.remarks.setText(model.getRemarksActivity());

        if (model.getCall_number() != null && model.getKapal() != null && model.getPeriode() != null) {
            holder.vessel.setText(String.valueOf(
                    "Call: " + model.getCall_number() + " " + model.getKapal() + " " + model.getPeriode()
            ));
        }
    }

    @Override
    public int getItemCount() {
        return movements.size();
    }

    private void setDateText(TextView text, String time) {
        if (time != null) {
            /*java.util.Date date = new java.util.Date(time.getTime());
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
            text.setText(format.format(date));*/
            text.setText(time);
        }
    }

    private void setTimeText(TextView text, String time) {
        /*java.util.Date date = new java.util.Date(time.getTime());
        SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
        text.setText(format.format(date));*/
        text.setText(time);
    }
}
