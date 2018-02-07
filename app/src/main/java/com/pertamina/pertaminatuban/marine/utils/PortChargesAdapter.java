package com.pertamina.pertaminatuban.marine.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.marine.models.PortCharges;

import java.util.ArrayList;

public class PortChargesAdapter extends RecyclerView.Adapter<PortChargesViewHolder> {

    private ArrayList<PortCharges> portCharges;

    public PortChargesAdapter(ArrayList<PortCharges> portCharges) {
        this.portCharges = portCharges;
    }

    @Override
    public PortChargesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_marine_port_charges, parent, false);
        return new PortChargesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PortChargesViewHolder holder, int position) {
        PortCharges model = portCharges.get(position);
        if (model.getLightDues() > 0) holder.lightDues.setText(String.valueOf(model.getLightDues()));
        if (model.getHarborDues() > 0) holder.harborDues.setText(String.valueOf(model.getHarborDues()));
        if (model.getQuayDues() > 0) holder.quayDues.setText(String.valueOf(model.getQuayDues()));
        if (model.getPilotage() > 0) holder.pilotages.setText(String.valueOf(model.getPilotage()));
        if (model.getTowage() > 0) holder.towage.setText(String.valueOf(model.getTowage()));
        if (model.getPup9a2() > 0) holder.pup.setText(String.valueOf(model.getPup9a2()));
    }

    @Override
    public int getItemCount() {
        return portCharges.size();
    }
}
