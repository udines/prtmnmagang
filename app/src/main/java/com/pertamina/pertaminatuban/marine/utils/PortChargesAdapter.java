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
        if (model.getLightDues() != null) holder.lightDues.setText(String.valueOf(model.getLightDues()));
        if (model.getHarborDues() != null) holder.harborDues.setText(String.valueOf(model.getHarborDues()));
        if (model.getQuayDues() != null) holder.quayDues.setText(String.valueOf(model.getQuayDues()));
        if (model.getPilotage() != null) holder.pilotages.setText(String.valueOf(model.getPilotage()));
        if (model.getTowage() != null) holder.towage.setText(String.valueOf(model.getTowage()));
        if (model.getPup9a2() != null) holder.pup.setText(String.valueOf(model.getPup9a2()));
    }

    @Override
    public int getItemCount() {
        return portCharges.size();
    }
}
