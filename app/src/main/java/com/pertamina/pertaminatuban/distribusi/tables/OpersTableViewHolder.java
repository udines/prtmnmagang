package com.pertamina.pertaminatuban.distribusi.tables;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.models.Opers;

public class OpersTableViewHolder extends RecyclerView.ViewHolder {

    private final TextView tanggal;
    private final TextView nilai;
    public final LinearLayout row;

    public OpersTableViewHolder(View itemView) {
        super(itemView);
        tanggal = itemView.findViewById(R.id.item_opers_tanggal);
        nilai = itemView.findViewById(R.id.item_opers_nilai);
        row = itemView.findViewById(R.id.item_opers_row);
    }

    public void setView(Opers model, int type) {
        tanggal.setText(model.getDate());
        switch (type) {
            case Opers.TYPE_JUMLAH_KELUAR :
                nilai.setText(String.valueOf(model.getJumlahKeluar()));
                break;
            case Opers.TYPE_MIN_JAM_KELUAR:
                nilai.setText(String.valueOf(model.getMinJamKeluar()));
                break;
            case Opers.TYPE_MAX_JAM_KELUAR:
                nilai.setText(String.valueOf(model.getMaxJamKeluar()));
                break;
            case Opers.TYPE_JAM_OPERASIONAL:
                nilai.setText(String.valueOf(model.getJamOperasional()));
                break;
        }
    }
}
