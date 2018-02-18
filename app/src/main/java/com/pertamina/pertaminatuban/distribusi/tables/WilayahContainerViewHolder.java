package com.pertamina.pertaminatuban.distribusi.tables;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;


public class WilayahContainerViewHolder extends RecyclerView.ViewHolder {

    public final TextView konsumen, total;
    public final RecyclerView recyclerView;

    public WilayahContainerViewHolder(View itemView) {
        super(itemView);
        konsumen = itemView.findViewById(R.id.item_container_wilayah_jenis);
        total = itemView.findViewById(R.id.item_container_wilayah_total);
        recyclerView = itemView.findViewById(R.id.item_container_wilayah_recycler);
    }
}
