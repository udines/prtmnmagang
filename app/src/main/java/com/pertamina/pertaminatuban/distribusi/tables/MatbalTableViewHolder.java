package com.pertamina.pertaminatuban.distribusi.tables;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.models.Matbal;

public class MatbalTableViewHolder extends RecyclerView.ViewHolder {

    public final TextView fuel;
    public final TextView nilai, textNow;
    public final TextView lastNilai, textLast;
    public final TextView difference;
    public final LinearLayout row;

    public MatbalTableViewHolder(View itemView) {
        super(itemView);
        fuel = itemView.findViewById(R.id.item_matbal_tanggal);

        nilai = itemView.findViewById(R.id.item_matbal_nilai);
        textNow = itemView.findViewById(R.id.item_matbal_text_now);

        lastNilai = itemView.findViewById(R.id.item_matbal_nilai_last);
        textLast = itemView.findViewById(R.id.item_matbal_text_last);

        difference = itemView.findViewById(R.id.item_matbal_difference);

        row = itemView.findViewById(R.id.item_matbal_row);
    }

    public void setView(Matbal model) {
        fuel.setText(model.getFuel());
        nilai.setText(String.valueOf(model.getNilai() + " KL"));

        nilai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("table", "klik nilai");
            }
        });
    }
}
