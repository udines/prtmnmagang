package com.pertamina.pertaminatuban.marine.utils;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;

public class WaitingTimeViewHolder extends RecyclerView.ViewHolder {

    public final TextView pilot, lab, tug, jetty, daylight, tide, ballast, cleaning, nomination,
    manPower, badWeather, line, cargo, ullage, bunker, fresh, actLoad, preparation, shoreOrder,
    clearence, cargoDoc, slowVessel, slowShore, cargoCalc, steaming, unready, vessel;

    public final CardView card;
    public WaitingTimeViewHolder(View itemView) {
        super(itemView);
        pilot = itemView.findViewById(R.id.item_waiting_time_pilot);
        lab = itemView.findViewById(R.id.item_waiting_time_lab_analysis);
        tug = itemView.findViewById(R.id.item_waiting_time_tug_boat);
        jetty = itemView.findViewById(R.id.item_waiting_time_jetty);
        daylight = itemView.findViewById(R.id.item_waiting_time_daylight);
        tide = itemView.findViewById(R.id.item_waiting_time_tide);
        ballast = itemView.findViewById(R.id.item_waiting_time_ballast);
        cleaning = itemView.findViewById(R.id.item_waiting_time_tank_cleaning);
        nomination = itemView.findViewById(R.id.item_waiting_time_nomination);
        manPower = itemView.findViewById(R.id.item_waiting_time_man_power);
        badWeather = itemView.findViewById(R.id.item_waiting_time_bad_weather);
        line = itemView.findViewById(R.id.item_waiting_time_line);
        cargo = itemView.findViewById(R.id.item_waiting_time_cargo);
        ullage = itemView.findViewById(R.id.item_waiting_time_ullage);
        bunker = itemView.findViewById(R.id.item_waiting_time_supply_bunker);
        fresh = itemView.findViewById(R.id.item_waiting_time_fresh_water);
        actLoad = itemView.findViewById(R.id.item_waiting_time_act_load);
        preparation = itemView.findViewById(R.id.item_waiting_time_preparation);
        shoreOrder = itemView.findViewById(R.id.item_waiting_time_shore_order);
        clearence = itemView.findViewById(R.id.item_waiting_time_ship_clearance);
        cargoDoc = itemView.findViewById(R.id.item_waiting_time_cargo_document);
        slowVessel = itemView.findViewById(R.id.item_waiting_time_slow_vessel);
        slowShore = itemView.findViewById(R.id.item_waiting_time_slow_shore);
        cargoCalc = itemView.findViewById(R.id.item_waiting_time_cargo_calculation);
        steaming = itemView.findViewById(R.id.item_waiting_time_steaming_in_out);
        unready = itemView.findViewById(R.id.item_waiting_time_unready);
        vessel = itemView.findViewById(R.id.item_waiting_time_vessel_name);

        card = itemView.findViewById(R.id.item_waiting_time_card);
    }
}
