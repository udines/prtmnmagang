package com.pertamina.pertaminatuban.finance.perjalanan_dinas;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.finance.models.LaporanPerjalananDinas;
import com.pertamina.pertaminatuban.finance.models.UraianPerjalanan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class LaporanPerjalananAdapter extends RecyclerView.Adapter<LaporanPerjalananViewHolder> {

    private ArrayList<LaporanPerjalananDinas> laporans;

    public LaporanPerjalananAdapter(ArrayList<LaporanPerjalananDinas> laporans) {
        this.laporans = laporans;
    }

    @Override
    public LaporanPerjalananViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_laporan_perjalanan_dinas, parent, false);
        final LaporanPerjalananViewHolder holder = new LaporanPerjalananViewHolder(view);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LaporanPerjalananDinas laporan = laporans.get(holder.getAdapterPosition());
                Intent intent = new Intent(parent.getContext(), InputRincianActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("noPekerja", laporan.getNoPekerja());
                intent.putExtra("namaPekerja", laporan.getNama());
                intent.putExtra("noPerjalanan", laporan.getNoPerjalanan());
                intent.putExtra("rangka", laporan.getRangka());
                intent.putExtra("tujuan", laporan.getTujuan());
                intent.putExtra("waktuMulai", laporan.getWaktuMulai());
                intent.putExtra("waktuSelesai", laporan.getWaktuSelesai());
                parent.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(LaporanPerjalananViewHolder holder, int position) {
        LaporanPerjalananDinas laporan = laporans.get(position);
        SimpleDateFormat format = new SimpleDateFormat("dd - MM - yyyy", Locale.getDefault());
        SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        holder.nama.setText(laporan.getNama());
        holder.noPekerja.setText(laporan.getNoPekerja());
        holder.noPerjalanan.setText(laporan.getNoPerjalanan());
        holder.rangka.setText(laporan.getRangka());
        holder.ke.setText(laporan.getTujuan());
//        holder.waktuMulai.setText(laporan.getWaktuMulai());
//        holder.waktuSelesai.setText(laporan.getWaktuSelesai());
        try {
            holder.waktuMulai.setText(format.format(parseFormat.parse(laporan.getWaktuMulai())));
            holder.waktuSelesai.setText(format.format(parseFormat.parse(laporan.getWaktuSelesai())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return laporans.size();
    }
}
