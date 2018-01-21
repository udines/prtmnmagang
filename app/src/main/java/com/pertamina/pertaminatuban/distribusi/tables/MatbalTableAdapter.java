package com.pertamina.pertaminatuban.distribusi.tables;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.models.Matbal;

import java.util.ArrayList;

public class MatbalTableAdapter extends RecyclerView.Adapter<MatbalTableViewHolder> {

    private ArrayList<Matbal> matbals;
    private Context context;

    public MatbalTableAdapter(ArrayList<Matbal> matbals, Context context) {
        this.matbals = matbals;
        this.context = context;
    }

    @Override
    public MatbalTableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_tabel_matbal, parent, false);
        return new MatbalTableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MatbalTableViewHolder holder, int position) {
        holder.setView(matbals.get(position));
        if (position % 2 == 0) {
            holder.row.setBackgroundColor(ContextCompat.getColor(context, R.color.grey_200));
        } else {
            holder.row.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }
    }

    @Override
    public int getItemCount() {
        if (matbals != null) {
            return matbals.size();
        } else {
            return 0;
        }
    }
}
