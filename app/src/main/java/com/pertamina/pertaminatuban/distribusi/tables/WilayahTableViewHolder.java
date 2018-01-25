package com.pertamina.pertaminatuban.distribusi.tables;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.models.Wilayah;

public class WilayahTableViewHolder extends RecyclerView.ViewHolder {

    private final TextView lokasi, konsumen, nilai;
    public final LinearLayout row;

    public WilayahTableViewHolder(View itemView) {
        super(itemView);
        lokasi = itemView.findViewById(R.id.item_wilayah_lokasi);
        konsumen = itemView.findViewById(R.id.item_wilayah_konsumen);
        nilai = itemView.findViewById(R.id.item_wilayah_nilai);
        row = itemView.findViewById(R.id.item_wilayah_row);
    }

    public void setView(Wilayah model) {
        lokasi.setText(model.getWilayah());
        konsumen.setText(model.getKonsumen());
        nilai.setText(String.valueOf(model.getNilai() + " KL"));
    }
}
