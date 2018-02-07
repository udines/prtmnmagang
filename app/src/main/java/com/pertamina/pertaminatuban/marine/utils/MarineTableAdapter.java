package com.pertamina.pertaminatuban.marine.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.marine.models.MarineTable;

import java.util.ArrayList;

public class MarineTableAdapter extends RecyclerView.Adapter<MarineTableViewHolder> {

    private ArrayList<MarineTable> tables;

    public MarineTableAdapter(ArrayList<MarineTable> tables) {
        this.tables = tables;
    }

    @Override
    public MarineTableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_marine_data, parent, false);
        return new MarineTableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MarineTableViewHolder holder, int position) {
        holder.setView(tables.get(position));
    }

    @Override
    public int getItemCount() {
        return tables.size();
    }
}
