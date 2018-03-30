package com.pertamina.pertaminatuban.saranafasilitas.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;


public class SaranaViewHolder extends RecyclerView.ViewHolder {

    public final TextView tanggal, nomor, nama, fungsi, lokasi, tipe, spesifikasi, merk, keterangan;

    public SaranaViewHolder(View itemView) {
        super(itemView);
        tanggal = itemView.findViewById(R.id.item_sarana_tanggal);
        nomor = itemView.findViewById(R.id.item_sarana_nomor);
        nama = itemView.findViewById(R.id.item_sarana_nama);
        fungsi = itemView.findViewById(R.id.item_sarana_fungsi);
        lokasi = itemView.findViewById(R.id.item_sarana_lokasi);
        tipe = itemView.findViewById(R.id.item_sarana_tipe);
        spesifikasi = itemView.findViewById(R.id.item_sarana_spesifikasi);
        merk = itemView.findViewById(R.id.item_sarana_merk);
        keterangan = itemView.findViewById(R.id.item_sarana_keterangan);
    }
}
