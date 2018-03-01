package com.pertamina.pertaminatuban.finance.perjalanan_dinas;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.finance.models.UraianPerjalanan;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;


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
        DecimalFormat formatter = new DecimalFormat("#,###");
        holder.jumlah.setText(String.valueOf("Rp" + formatter.format(Double.parseDouble(model.getJumlah()))));
        holder.uraian.setText(model.getClaimType());
        holder.keterangan.setText(model.getKeterangan());

        SimpleDateFormat format = new SimpleDateFormat("dd - MM - yyyy", Locale.getDefault());
        SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            holder.tanggal.setText(format.format(parseFormat.parse(model.getDate())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return uraians.size();
    }
}
