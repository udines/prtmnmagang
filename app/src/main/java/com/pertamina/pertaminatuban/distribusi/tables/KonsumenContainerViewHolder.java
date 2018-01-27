package com.pertamina.pertaminatuban.distribusi.tables;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.models.Konsumen;

import java.util.ArrayList;

public class KonsumenContainerViewHolder extends RecyclerView.ViewHolder {

    private final TextView konsumen, total;
    private final RecyclerView recyclerView;

    public KonsumenContainerViewHolder(View itemView) {
        super(itemView);
        konsumen = itemView.findViewById(R.id.item_container_konsumen_jenis);
        total = itemView.findViewById(R.id.item_container_konsumen_total);
        recyclerView = itemView.findViewById(R.id.item_container_konsumen_recycler);
    }

    public void setView(ArrayList<Konsumen> konsumens) {
        if (konsumens.size() > 0) {
            konsumen.setText(konsumens.get(0).getKonsumen());
            float grandTotal = 0;
            for (int i = 0; i < konsumens.size(); i++) {
                grandTotal = grandTotal + konsumens.get(i).getNilai();
            }
            total.setText(String.valueOf(grandTotal + " KL"));

            KonsumenTableAdapter adapter = new KonsumenTableAdapter(
                    konsumens,
                    itemView.getContext()
            );
            recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            recyclerView.setAdapter(adapter);
        }
    }
}
