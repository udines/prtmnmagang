package com.pertamina.pertaminatuban.marine.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.marine.models.ShipParticular;
import com.pertamina.pertaminatuban.marine.models.Tanker;

import java.util.ArrayList;


public class ShipParticularAdapter extends RecyclerView.Adapter<ShipPartcularViewHolder> {

    private ArrayList<Tanker> tankers;
    private ArrayList<ShipParticular> shipParticulars;

    public ShipParticularAdapter(ArrayList<Tanker> tankers, ArrayList<ShipParticular> shipParticulars) {
        this.tankers = tankers;
        this.shipParticulars = shipParticulars;
    }

    @Override
    public ShipPartcularViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_marine_ship_particular, parent, false);
        return new ShipPartcularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShipPartcularViewHolder holder, int position) {
        Tanker tanker = tankers.get(position);
        ShipParticular model = shipParticulars.get(position);

        holder.vessel.setText(String.valueOf(tanker.getCall() + " " + tanker.getVesselName()));

        setStringText(holder.flag, model.getFlag());
        setStringText(holder.typeCall, model.getTypeCall());
        setStringText(holder.typeActivity, model.getTypeActivity());
        setStringText(holder.master, model.getMaster());

        setStringText(holder.dwt, model.getDwt());
        setStringText(holder.grt, model.getGrt());
        setStringText(holder.loa, model.getLoa());
        setStringText(holder.hireRate, model.getHireRate());
    }

    private void setFloatText(TextView text, float value) {
        if (value > 0) {
            text.setText(String.valueOf(value));
        }
    }

    private void setStringText(TextView text, String value) {
        if (value != null) {
            text.setText(value);
        }
    }
    @Override
    public int getItemCount() {
        return tankers.size();
    }
}
