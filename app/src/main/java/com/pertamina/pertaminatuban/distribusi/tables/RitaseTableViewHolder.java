package com.pertamina.pertaminatuban.distribusi.tables;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.models.Ritase;

public class RitaseTableViewHolder extends RecyclerView.ViewHolder {

    private final TextView tanggal, nilai;
    public final LinearLayout row;

    public RitaseTableViewHolder(View itemView) {
        super(itemView);
        tanggal = itemView.findViewById(R.id.item_ritase_tanggal);
        nilai = itemView.findViewById(R.id.item_ritase_nilai);
        row = itemView.findViewById(R.id.item_ritase_row);
    }

    public void setView(Ritase model) {

    }
}
