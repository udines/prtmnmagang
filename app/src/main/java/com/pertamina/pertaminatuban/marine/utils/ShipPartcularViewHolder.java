package com.pertamina.pertaminatuban.marine.utils;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;

public class ShipPartcularViewHolder extends RecyclerView.ViewHolder {

    public final TextView flag, dwt, grt, loa, typeCall, typeActivity, hireRate, master, vessel;
    public final CardView card;

    public ShipPartcularViewHolder(View itemView) {
        super(itemView);
        flag = itemView.findViewById(R.id.item_ship_particular_flag);
        dwt = itemView.findViewById(R.id.item_ship_particular_dwt);
        grt = itemView.findViewById(R.id.item_ship_particular_grt);
        loa = itemView.findViewById(R.id.item_ship_particular_loa);
        typeCall = itemView.findViewById(R.id.item_ship_particular_type_call);
        typeActivity = itemView.findViewById(R.id.item_ship_particular_type_activity);
        hireRate = itemView.findViewById(R.id.item_ship_particular_rate);
        master = itemView.findViewById(R.id.item_ship_particular_master);
        vessel = itemView.findViewById(R.id.item_ship_particular_vessel);
        card = itemView.findViewById(R.id.item_ship_particular_card);
    }
}
