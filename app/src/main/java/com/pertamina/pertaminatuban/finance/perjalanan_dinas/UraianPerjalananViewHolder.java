package com.pertamina.pertaminatuban.finance.perjalanan_dinas;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;


public class UraianPerjalananViewHolder extends RecyclerView.ViewHolder {

    public final TextView jumlah, uraian, keterangan, kodeAnggaran, costCenter, kodeBagian, tanggal;

    public UraianPerjalananViewHolder(View itemView) {
        super(itemView);
        jumlah = itemView.findViewById(R.id.item_uraian_perjalanan_jumlah);
        uraian = itemView.findViewById(R.id.item_uraian_perjalanan_uraian);
        keterangan = itemView.findViewById(R.id.item_uraian_perjalanan_keterangan);
        kodeAnggaran = itemView.findViewById(R.id.item_uraian_perjalanan_kode_anggaran);
        costCenter = itemView.findViewById(R.id.item_uraian_perjalanan_cost_center);
        kodeBagian = itemView.findViewById(R.id.item_uraian_perjalanan_kode_bagian);
        tanggal = itemView.findViewById(R.id.item_uraian_perjalanan_tanggal);
    }
}
