package com.pertamina.pertaminatuban.marine.utils;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;

public class InitialTankerViewHolder extends RecyclerView.ViewHolder {

    public final TextView vessel, callTanker, periode, voyage, noBill, doBill, statusTanker,
    statusOps, grades, handlingAgent, generalAgent, cargoStatus, tankerActivity, pumpMethod, barthing,
    portCall, portCallReport, lastPort, nextPort;
    public final CardView card;

    public InitialTankerViewHolder(View itemView) {
        super(itemView);
        vessel = itemView.findViewById(R.id.item_initial_tanker_call_vessel);
        callTanker = itemView.findViewById(R.id.item_initial_tanker_call_tanker);
        periode = itemView.findViewById(R.id.item_initial_tanker_periode);
        voyage = itemView.findViewById(R.id.item_initial_tanker_voyage);
        noBill = itemView.findViewById(R.id.item_initial_tanker_no_bill);
        doBill = itemView.findViewById(R.id.item_initial_tanker_date_of_bill);
        statusTanker = itemView.findViewById(R.id.item_initial_tanker_status_tanker);
        statusOps = itemView.findViewById(R.id.item_initial_tanker_status_ops_report);
        grades = itemView.findViewById(R.id.item_initial_tanker_grades);
        handlingAgent = itemView.findViewById(R.id.item_initial_tanker_handling_agent);
        generalAgent = itemView.findViewById(R.id.item_initial_tanker_general_agent);
        cargoStatus = itemView.findViewById(R.id.item_initial_tanker_cargo_status);
        tankerActivity = itemView.findViewById(R.id.item_initial_tanker_tanker_activity);
        pumpMethod = itemView.findViewById(R.id.item_initial_tanker_pumping_method);
        barthing = itemView.findViewById(R.id.item_initial_tanker_barthing_spm);
        portCall = itemView.findViewById(R.id.item_initial_tanker_port_of_call);
        portCallReport = itemView.findViewById(R.id.item_initial_tanker_port_of_call_report);
        lastPort = itemView.findViewById(R.id.item_initial_tanker_last_port);
        nextPort = itemView.findViewById(R.id.item_initial_tanker_next_port);

        card = itemView.findViewById(R.id.item_initial_tanker_card);
    }
}
