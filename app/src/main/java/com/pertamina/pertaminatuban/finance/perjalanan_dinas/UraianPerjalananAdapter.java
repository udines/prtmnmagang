package com.pertamina.pertaminatuban.finance.perjalanan_dinas;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.finance.models.UraianPerjalanan;

import java.util.ArrayList;


public class UraianPerjalananAdapter extends RecyclerView.Adapter<UraianPerjalananViewHolder> {

    private ArrayList<UraianPerjalanan> uraians;

    public UraianPerjalananAdapter(ArrayList<UraianPerjalanan> uraians) {
        this.uraians = uraians;
    }

    @Override
    public UraianPerjalananViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_uraian_perjalanan, parent, false);
        return new UraianPerjalananViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UraianPerjalananViewHolder holder, int position) {
        UraianPerjalanan model = uraians.get(position);
        holder.jumlah.setText(String.valueOf("Jumlah: Rp" + model.getJumlah()));
        holder.keterangan.setText(model.getKeterangan());
    }

    @Override
    public int getItemCount() {
        return uraians.size();
    }
}
