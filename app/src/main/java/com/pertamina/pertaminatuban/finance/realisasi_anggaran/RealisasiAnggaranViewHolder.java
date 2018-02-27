package com.pertamina.pertaminatuban.finance.realisasi_anggaran;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.pertamina.pertaminatuban.R;


public class RealisasiAnggaranViewHolder extends RecyclerView.ViewHolder {

    public final TextView title;
    public final BarChart chart;

    public RealisasiAnggaranViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.item_realisasi_anggaran_title);
        chart = itemView.findViewById(R.id.item_realisasi_anggaran_chart);
    }
}
