package com.pertamina.pertaminatuban.distribusi.tables;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.models.Ritase;

import java.util.ArrayList;

public class RitaseTableAdapter extends RecyclerView.Adapter<RitaseTableViewHolder> {

    private ArrayList<Ritase> ritases;
    private Context context;

    public RitaseTableAdapter(ArrayList<Ritase> ritases, Context context) {
        this.ritases = ritases;
        this.context = context;
    }

    @Override
    public RitaseTableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tabel_ritase, parent, false);
        return new RitaseTableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RitaseTableViewHolder holder, int position) {
        holder.setView(ritases.get(position));
        if (position % 2 == 0) {
            holder.row.setBackgroundColor(ContextCompat.getColor(context, R.color.grey_200));
        }
    }

    @Override
    public int getItemCount() {
        return ritases.size();
    }
}
