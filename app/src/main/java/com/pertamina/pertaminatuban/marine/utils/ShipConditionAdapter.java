package com.pertamina.pertaminatuban.marine.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.marine.models.ShipCondition;
import com.pertamina.pertaminatuban.marine.models.Tanker;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ShipConditionAdapter extends RecyclerView.Adapter<ShipConditionViewHolder> {

    private ArrayList<Tanker> tankers;
    private ArrayList<ShipCondition> conditions;

    public ShipConditionAdapter(ArrayList<Tanker> tankers, ArrayList<ShipCondition> conditions) {
        this.tankers = tankers;
        this.conditions = conditions;
    }

    @Override
    public ShipConditionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_marine_ship_condition, parent, false);
        return new ShipConditionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShipConditionViewHolder holder, int position) {
        Tanker tanker = tankers.get(position);
        ShipCondition model = conditions.get(position);

        setDateText(holder.ata, model.getActualTimeArrival());
        setDateText(holder.atd, model.getActualTimeDeparture());
        setDateText(holder.comRepl, model.getComRepl());
        setDateText(holder.compRepl, model.getCompRepl());

        setText(holder.description, model.getDescriptionDraft());
        setText(holder.grades, model.getGradeBunker());
        setText(holder.location, model.getReplLocation());

        setFloatText(holder.robLastPort, model.getRobLastPort());
        setFloatText(holder.robAta, model.getRobAta());
        setFloatText(holder.robAtd, model.getRobAtd());
        setFloatText(holder.repl, model.getRepl());
        setFloatText(holder.bunkerPort, model.getBunkerConsumptionPort());
        setFloatText(holder.bunkerSeatime, model.getBunkerConsumptionSeatime());
        setFloatText(holder.slopTankAta, model.getSlopTankAta());
        setFloatText(holder.slopTankAtd, model.getSlopTankAtd());

        if (model.getCall_number() != null && model.getKapal() != null && model.getPeriode() != null) {
            holder.vessel.setText(String.valueOf(
                    "Call: " + model.getCall_number() + " " + model.getKapal() + " " + model.getPeriode()
            ));
        }
    }

    @Override
    public int getItemCount() {
        return tankers.size();
    }

    private void setFloatText(TextView text, String value) {
        if (value != null) {
            text.setText(String.valueOf(value));
        }
    }

    private void setText(TextView text, String value) {
        if (value != null) {
            text.setText(value);
        }
    }

    private void setDateText(TextView text, String time) {
        /*if (time != null) {
            java.util.Date date = new java.util.Date(time.getTime());
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
            text.setText(format.format(date));
        }*/
        text.setText(time);
    }
}
