package com.pertamina.pertaminatuban.finance;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pertamina.pertaminatuban.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class LaporanPerjalananAdapter extends RecyclerView.Adapter<LaporanPerjalananViewHolder> {

    private ArrayList<LaporanPerjalananDinas> laporans;

    public LaporanPerjalananAdapter(ArrayList<LaporanPerjalananDinas> laporans) {
        this.laporans = laporans;
    }

    @Override
    public LaporanPerjalananViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_laporan_perjalanan_dinas, parent, false);
        final LaporanPerjalananViewHolder holder = new LaporanPerjalananViewHolder(view);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(LaporanPerjalananViewHolder holder, int position) {
        LaporanPerjalananDinas laporan = laporans.get(position);
        SimpleDateFormat format = new SimpleDateFormat("dd - MM - yyyy", Locale.getDefault());

        holder.nama.setText(laporan.getNama());
        holder.noPekerja.setText(laporan.getNoPekerja());
        holder.noPerjalanan.setText(laporan.getNoPerjalanan());
        holder.rangka.setText(laporan.getRangka());
        holder.ke.setText(laporan.getTujuan());
        holder.waktuMulai.setText(format.format(laporan.getWaktuMulai()));
        holder.waktuSelesai.setText(format.format(laporan.getWaktuSelesai()));
        holder.totalBiaya.setText(laporan.getTotalBiaya());
    }

    @Override
    public int getItemCount() {
        return laporans.size();
    }
}
