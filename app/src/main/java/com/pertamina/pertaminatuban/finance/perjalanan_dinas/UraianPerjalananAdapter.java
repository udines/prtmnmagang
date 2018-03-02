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
import java.util.HashMap;
import java.util.Locale;


public class UraianPerjalananAdapter extends RecyclerView.Adapter<UraianPerjalananViewHolder> {

    private ArrayList<UraianPerjalanan> uraians;
    private HashMap<String, String> jenisClaimValueKey, jenisClaimKeyValue;

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
        setHashMaps();

        UraianPerjalanan model = uraians.get(position);
        DecimalFormat formatter = new DecimalFormat("#,###");
        holder.jumlah.setText(String.valueOf("Rp" + formatter.format(Double.parseDouble(model.getJumlah()))));
        holder.uraian.setText(jenisClaimKeyValue.get(model.getClaimType()));
        holder.keterangan.setText(model.getKeterangan());

        SimpleDateFormat format = new SimpleDateFormat("dd - MM - yyyy", Locale.getDefault());
        SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            holder.tanggal.setText(format.format(parseFormat.parse(model.getDate())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void setHashMaps() {
        jenisClaimValueKey = new HashMap<>();
        jenisClaimValueKey.put("Transport antar kota", "kota");
        jenisClaimValueKey.put("Transport Bandara/stasiun/pelabuhan/Terminal", "umum");
        jenisClaimValueKey.put("Akomodasi dan Laundry", "akomodasi");
        jenisClaimValueKey.put("Uang Harian", "hari");
        jenisClaimValueKey.put("Transport Lokal", "lokal");
        jenisClaimValueKey.put("Tiket Pesawat", "tiket");
        jenisClaimValueKey.put("Uang Makan Harian", "makan");

        jenisClaimKeyValue = new HashMap<>();
        jenisClaimKeyValue.put("kota", "Transport antar kota");
        jenisClaimKeyValue.put("umum", "Transport Bandara/stasiun/pelabuhan/Terminal");
        jenisClaimKeyValue.put("akomodasi", "Akomodasi dan Laundry");
        jenisClaimKeyValue.put("hari", "Uang Harian");
        jenisClaimKeyValue.put("lokal", "Transport Lokal");
        jenisClaimKeyValue.put("tiket", "Tiket Pesawat");
        jenisClaimKeyValue.put("makan", "Uang Makan Harian");
    }

    @Override
    public int getItemCount() {
        return uraians.size();
    }
}
