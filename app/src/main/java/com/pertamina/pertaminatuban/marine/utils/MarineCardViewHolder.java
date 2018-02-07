package com.pertamina.pertaminatuban.marine.utils;

import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.marine.models.MarineTable;

import java.util.ArrayList;

public class MarineCardViewHolder extends RecyclerView.ViewHolder {

    public final CardView card;
    private final TextView vessel;
    private final RecyclerView recyclerView;

    public MarineCardViewHolder(View itemView) {
        super(itemView);
        card = itemView.findViewById(R.id.item_marine_card);
        vessel = itemView.findViewById(R.id.item_marine_call_vessel);
        recyclerView = itemView.findViewById(R.id.item_marine_table);
        recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
    }

    public void setView(String call, String vessel, ArrayList<MarineTable> tables) {
        this.vessel.setText(String.valueOf(call + " " + vessel));
        MarineTableAdapter adapter = new MarineTableAdapter(tables);
        recyclerView.setAdapter(adapter);
    }
}
