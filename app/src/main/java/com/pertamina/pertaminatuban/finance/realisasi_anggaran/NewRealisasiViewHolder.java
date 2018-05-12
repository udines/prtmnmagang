package com.pertamina.pertaminatuban.finance.realisasi_anggaran;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;

public class NewRealisasiViewHolder extends RecyclerView.ViewHolder {

    public final TextView bulan, plan, realisasi, persentase;

    public NewRealisasiViewHolder(View itemView) {
        super(itemView);
        bulan = itemView.findViewById(R.id.item_new_realisasi_anggaran_bulan);
        plan = itemView.findViewById(R.id.item_new_realisasi_anggaran_plan);
        realisasi = itemView.findViewById(R.id.item_new_realisasi_anggaran_realisasi);
        persentase = itemView.findViewById(R.id.item_new_realisasi_anggaran_persentase);
    }
}
