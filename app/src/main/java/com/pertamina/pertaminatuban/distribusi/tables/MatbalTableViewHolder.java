package com.pertamina.pertaminatuban.distribusi.tables;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.models.Matbal;

public class MatbalTableViewHolder extends RecyclerView.ViewHolder {

    private final TextView tanggal;
    private final TextView nilai;
    public final LinearLayout row;

    public MatbalTableViewHolder(View itemView) {
        super(itemView);
        tanggal = itemView.findViewById(R.id.item_matbal_tanggal);
        nilai = itemView.findViewById(R.id.item_matbal_nilai);
        row = itemView.findViewById(R.id.item_matbal_row);
    }

    public void setView(Matbal model) {
        tanggal.setText(model.getDate());
        nilai.setText(String.valueOf(model.getNilai()));

        nilai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("table", "klik nilai");
            }
        });
    }
}
