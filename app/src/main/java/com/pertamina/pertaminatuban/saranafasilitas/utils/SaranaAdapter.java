package com.pertamina.pertaminatuban.saranafasilitas.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.saranafasilitas.models.Sarfas;

import java.util.ArrayList;


public class SaranaAdapter extends RecyclerView.Adapter<SaranaViewHolder> {

    private ArrayList<Sarfas> sarfases;

    public SaranaAdapter(ArrayList<Sarfas> sarfases) {
        this.sarfases = sarfases;
    }

    @Override
    public SaranaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sarana_fasilitas, parent, false);
        return new SaranaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SaranaViewHolder holder, int position) {
        Sarfas sarfas = sarfases.get(position);
        holder.tanggal.setText(sarfas.getDate());
        holder.nomor.setText(sarfas.getNomor());
        holder.nama.setText(sarfas.getNama());
        holder.fungsi.setText(sarfas.getFungsi());
        holder.lokasi.setText(sarfas.getLokasi());
        holder.tipe.setText(sarfas.getTipe());
        holder.spesifikasi.setText(sarfas.getSpesifikasi());
        holder.merk.setText(sarfas.getMerk());
        holder.keterangan.setText(sarfas.getKeterangan());
    }

    @Override
    public int getItemCount() {
        return sarfases.size();
    }
}
