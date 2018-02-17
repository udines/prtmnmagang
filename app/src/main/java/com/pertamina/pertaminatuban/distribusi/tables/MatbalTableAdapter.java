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
    private String periodeType;
    private Context context;

    public MatbalTableAdapter(ArrayList<Matbal> matbals, ArrayList<Matbal> lastMatbals, String periodeType, Context context) {
        this.matbals = matbals;
        this.lastMatbals = lastMatbals;
        this.periodeType = periodeType;
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

        //set nilai sebelum dan sekarang
        setNilai(holder.fuel,
                matbals.get(position).getFuel(),
                holder.nilai,
                matbals.get(position).getNilai(),
                holder.lastNilai,
                lastMatbals,
                holder.difference);

        //set tulisan last dan this sesuai dengan tipe periode
        //misalnya this month dan last month
        holder.textLast.setText(String.valueOf("Last " + periodeType));
        holder.textNow.setText(String.valueOf("This " + periodeType));

        //set row menjadi belang
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
        now.setText(String.valueOf(nilaiNow + " KL"));

        for (int i = 0; i < matbals.size(); i++) {
            if (matbals.get(i).getFuel().equals(fuel)) {
                nilaiLast = matbals.get(i).getNilai();
                last.setText(String.valueOf(nilaiLast + " KL"));
                break;
            }
        }

        nilaiDiff = nilaiNow - nilaiLast;
        float percentage = (nilaiDiff / nilaiNow) * 100;
        difference.setText(String.valueOf(nilaiDiff + "KL " + "(" + percentage + "%)"));
        if (percentage < 0) {
            difference.setTextColor(ContextCompat.getColor(context, R.color.red_800));
        } else {
            difference.setTextColor(ContextCompat.getColor(context, R.color.green_800));
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
