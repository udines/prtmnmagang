package com.pertamina.pertaminatuban.marine;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.marine.models.MarineTable;

import java.util.ArrayList;

public class MarineCardAdapter extends RecyclerView.Adapter<MarineCardViewHolder> {

    private ArrayList<MarineTable> vessels;
    private ArrayList<ArrayList<MarineTable>> tables;

    public MarineCardAdapter(ArrayList<MarineTable> vessels, ArrayList<ArrayList<MarineTable>> tables) {
        this.vessels = vessels;
        this.tables = tables;
    }

    @Override
    public MarineCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_marine_data, parent, false);
        return new MarineCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MarineCardViewHolder holder, int position) {
        holder.setView(
                vessels.get(position).getVariable(),
                vessels.get(position).getValue(),
                tables.get(position)
        );
    }

    @Override
    public int getItemCount() {
        return vessels.size();
    }
}
