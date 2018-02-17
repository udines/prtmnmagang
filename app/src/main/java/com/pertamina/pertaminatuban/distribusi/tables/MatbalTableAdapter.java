package com.pertamina.pertaminatuban.distribusi.tables;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.models.Matbal;

import java.util.ArrayList;

public class MatbalTableAdapter extends RecyclerView.Adapter<MatbalTableViewHolder> {

    private ArrayList<Matbal> matbals;
    private ArrayList<Matbal> lastMatbals;
    private Context context;

    public MatbalTableAdapter(ArrayList<Matbal> matbals, ArrayList<Matbal> lastMatbals, Context context) {
        this.matbals = matbals;
        this.lastMatbals = lastMatbals;
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
//        holder.setView(matbals.get(position));
        setNilai(holder.fuel,
                matbals.get(position).getFuel(),
                holder.nilai,
                matbals.get(position).getNilai(),
                holder.lastNilai,
                lastMatbals,
                holder.difference);

        if (position % 2 == 0) {
            holder.row.setBackgroundColor(ContextCompat.getColor(context, R.color.grey_200));
        } else {
            holder.row.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }
    }

    private void setNilai(TextView textFuel, String fuel,
                          TextView now, float nilaiNow,
                          TextView last, ArrayList<Matbal> matbals,
                          TextView difference) {
        float nilaiLast = 0, nilaiDiff;

        textFuel.setText(fuel);
        now.setText(String.valueOf(nilaiNow));

        for (int i = 0; i < matbals.size(); i++) {
            if (matbals.get(i).getFuel().equals(fuel)) {
                nilaiLast = matbals.get(i).getNilai();
                last.setText(String.valueOf(nilaiLast));
                break;
            }
        }

        nilaiDiff = nilaiNow - nilaiLast;
        float percentage = (nilaiDiff / nilaiNow) * 100;
        difference.setText(String.valueOf(nilaiDiff + "(" + percentage + "%)"));
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
