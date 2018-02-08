package com.pertamina.pertaminatuban.marine.utils;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;

public class ShipConditionViewHolder extends RecyclerView.ViewHolder {

    public final TextView description, ata, atd, grades, robLastPort, robAta, robAtd, repl, comRepl,
    compRepl, location, bunkerPort, bunkerSeatime, slopTankAta, slopTankAtd, vessel;
    public final CardView card;

    public ShipConditionViewHolder(View itemView) {
        super(itemView);
        description = itemView.findViewById(R.id.item_ship_condition_description_draft);
        ata = itemView.findViewById(R.id.item_ship_condition_ata);
        atd = itemView.findViewById(R.id.item_ship_condition_atd);
        grades = itemView.findViewById(R.id.item_ship_condition_grades_bunnker);
        robLastPort = itemView.findViewById(R.id.item_ship_condition_rob_last_port);
        robAta = itemView.findViewById(R.id.item_ship_condition_rob_ata);
        robAtd = itemView.findViewById(R.id.item_ship_condition_rob_atd);
        repl = itemView.findViewById(R.id.item_ship_condition_repl);
        comRepl = itemView.findViewById(R.id.item_ship_condition_com_repl);
        compRepl = itemView.findViewById(R.id.item_ship_condition_comp_repl);
        location = itemView.findViewById(R.id.item_ship_condition_location);
        bunkerPort = itemView.findViewById(R.id.item_ship_condition_bunker_cons_port);
        bunkerSeatime = itemView.findViewById(R.id.item_ship_condition_bunker_cons_seatime);
        slopTankAtd = itemView.findViewById(R.id.item_ship_condition_slop_tank_atd);
        slopTankAta = itemView.findViewById(R.id.item_ship_condition_slop_tank_ata);
        vessel = itemView.findViewById(R.id.item_ship_condition_vessel);
        card = itemView.findViewById(R.id.item_ship_condition_card);
    }
}
