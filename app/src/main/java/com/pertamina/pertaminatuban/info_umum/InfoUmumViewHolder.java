package com.pertamina.pertaminatuban.info_umum;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;

public class InfoUmumViewHolder extends RecyclerView.ViewHolder {

    private final TextView judul, info, detail;

    public InfoUmumViewHolder(View itemView) {
        super(itemView);
        judul = itemView.findViewById(R.id.item_info_judul);
        info = itemView.findViewById(R.id.item_info_informasi);
        detail = itemView.findViewById(R.id.item_info_detail);
    }

    public void setView(InfoUmum model) {
        judul.setText(model.getJudul());
        info.setText(model.getInformasi());
        detail.setText(model.getDeskripsi());
    }
}
