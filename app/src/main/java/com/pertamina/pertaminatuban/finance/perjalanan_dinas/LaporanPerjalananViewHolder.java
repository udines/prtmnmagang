package com.pertamina.pertaminatuban.finance.perjalanan_dinas;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;


public class LaporanPerjalananViewHolder extends RecyclerView.ViewHolder {

    public final TextView nama, noPekerja, noPerjalanan, rangka, ke, waktuMulai, waktuSelesai,
    totalBiaya;
    public final CardView card;

    public LaporanPerjalananViewHolder(View itemView) {
        super(itemView);
        nama = itemView.findViewById(R.id.item_laporan_perjalanan_nama);
        noPekerja = itemView.findViewById(R.id.item_laporan_perjalanan_no_pekerja);
        noPerjalanan = itemView.findViewById(R.id.item_laporan_perjalanan_no_perjalanan);
        rangka = itemView.findViewById(R.id.item_laporan_perjalanan_rangka);
        ke = itemView.findViewById(R.id.item_laporan_perjalanan_ke);
        waktuMulai = itemView.findViewById(R.id.item_laporan_perjalanan_waktu_mulai);
        waktuSelesai = itemView.findViewById(R.id.item_laporan_perjalanan_waktu_selesai);
        totalBiaya = itemView.findViewById(R.id.item_laporan_perjalanan_total_biaya);
        card = itemView.findViewById(R.id.item_laporan_perjalanan_card);
    }
}
