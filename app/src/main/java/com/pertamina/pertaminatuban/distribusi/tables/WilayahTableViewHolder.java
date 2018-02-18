package com.pertamina.pertaminatuban.distribusi.tables;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.models.Wilayah;

public class WilayahTableViewHolder extends RecyclerView.ViewHolder {

    public final TextView tanggal;
    public final TextView nilai;
    public final LinearLayout row;

    public WilayahTableViewHolder(View itemView) {
        super(itemView);
        tanggal = itemView.findViewById(R.id.item_wilayah_tanggal);
        nilai = itemView.findViewById(R.id.item_wilayah_nilai);
        row = itemView.findViewById(R.id.item_wilayah_row);
    }
}
