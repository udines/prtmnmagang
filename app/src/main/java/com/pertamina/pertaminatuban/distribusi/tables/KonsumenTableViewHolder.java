package com.pertamina.pertaminatuban.distribusi.tables;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.models.Konsumen;

public class KonsumenTableViewHolder extends RecyclerView.ViewHolder {

    private final TextView tanggal;
    private final TextView nilai;
    public final LinearLayout row;

    public KonsumenTableViewHolder(View itemView) {
        super(itemView);
        tanggal = itemView.findViewById(R.id.item_konsumen_tanggal);
        nilai = itemView.findViewById(R.id.item_konsumen_nilai);
        row = itemView.findViewById(R.id.item_konsumen_row);
    }

    public void setView(Konsumen model) {
        tanggal.setText(model.getFuel());
        nilai.setText(String.valueOf(model.getNilai() + " KL"));
    }
}
