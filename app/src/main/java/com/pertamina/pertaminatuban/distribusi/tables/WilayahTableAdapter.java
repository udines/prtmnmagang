package com.pertamina.pertaminatuban.distribusi.tables;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.models.Wilayah;

import java.util.ArrayList;

public class WilayahTableAdapter extends RecyclerView.Adapter<WilayahTableViewHolder> {

    private ArrayList<Wilayah> wilayahs;
    private Context context;

    public WilayahTableAdapter(ArrayList<Wilayah> wilayahs, Context context) {
        this.wilayahs = wilayahs;
        this.context = context;
    }

    @Override
    public WilayahTableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tabel_wilayah, parent, false);
        return new WilayahTableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WilayahTableViewHolder holder, int position) {
        holder.setView(wilayahs.get(position));
        if (position % 2 == 0) {
            holder.row.setBackgroundColor(ContextCompat.getColor(context, R.color.grey_200));
        }
    }

    @Override
    public int getItemCount() {
        return wilayahs.size();
    }
}
