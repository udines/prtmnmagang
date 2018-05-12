package com.pertamina.pertaminatuban.finance.realisasi_anggaran;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.finance.models.RealisasiAnggaran;
import com.pertamina.pertaminatuban.utils.MethodCollection;

import java.text.DateFormatSymbols;
import java.util.ArrayList;

public class NewRealisasiAdapter extends RecyclerView.Adapter<NewRealisasiViewHolder> {

    private ArrayList<RealisasiAnggaran> anggarans;
    private int year;

    public NewRealisasiAdapter(ArrayList<RealisasiAnggaran> anggarans, int year) {
        this.anggarans = anggarans;
        this.year = year;
    }

    @NonNull
    @Override
    public NewRealisasiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_realisasi_anggaran, parent, false);
        return new NewRealisasiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewRealisasiViewHolder holder, int position) {
        holder.bulan.setText(String.valueOf("Bulan: " + getMonth(position) + " " + year));
        holder.plan.setText(String.valueOf("Plan Rp" + MethodCollection.numberWithDot(anggarans.get(position).getPlan())));
        holder.realisasi.setText(String.valueOf("Realisasi Rp" + MethodCollection.numberWithDot(anggarans.get(position).getAllotted())));
        holder.persentase.setText(String.valueOf(
                 MethodCollection.numberWithComma((anggarans.get(position).getAllotted() / anggarans.get(position).getPlan() * 100)) + "%"
        ));
    }

    @Override
    public int getItemCount() {
        return anggarans.size();
    }

    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month];
    }
}
