package com.pertamina.pertaminatuban.marine.utils;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;

public class PortChargesViewHolder extends RecyclerView.ViewHolder {

    public final TextView lightDues, harborDues, quayDues, pilotages, towage, pup, vessel;
    public final CardView card;

    public PortChargesViewHolder(View itemView) {
        super(itemView);
        lightDues = itemView.findViewById(R.id.item_port_charges_light_dues);
        harborDues = itemView.findViewById(R.id.item_port_charges_harbor_dues);
        quayDues = itemView.findViewById(R.id.item_port_charges_quay_dues);
        pilotages = itemView.findViewById(R.id.item_port_charges_pilotages);
        towage = itemView.findViewById(R.id.item_port_charges_towage);
        pup = itemView.findViewById(R.id.item_port_charges_pup);

        card = itemView.findViewById(R.id.item_port_charges_card);
        vessel = itemView.findViewById(R.id.item_port_charges_vessel);
    }
}
