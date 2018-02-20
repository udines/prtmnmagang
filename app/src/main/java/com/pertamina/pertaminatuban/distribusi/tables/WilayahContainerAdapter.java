package com.pertamina.pertaminatuban.distribusi.tables;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.models.Wilayah;

import java.util.ArrayList;


public class WilayahContainerAdapter extends RecyclerView.Adapter<WilayahContainerViewHolder> {

    private ArrayList<ArrayList<Wilayah>> kumpulanWilayahs;
    private Context context;

    public WilayahContainerAdapter(ArrayList<ArrayList<Wilayah>> kumpulanWilayahs, Context context) {
        this.kumpulanWilayahs = kumpulanWilayahs;
        this.context = context;
    }

    @Override
    public WilayahContainerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.item_container_tabel_wilayah,
                parent,
                false
        );
        return new WilayahContainerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WilayahContainerViewHolder holder, int position) {
        ArrayList<Wilayah> wilayahs = kumpulanWilayahs.get(position);
        if (wilayahs.size() > 0) {
            holder.konsumen.setText(wilayahs.get(0).getKonsumen());
            float grandTotal = 0;
            for (int i = 0; i < wilayahs.size(); i++) {
                grandTotal = grandTotal + wilayahs.get(i).getNilai();
            }
            holder.total.setText(String.valueOf(grandTotal + " KL"));

            WilayahTableAdapter adapter = new WilayahTableAdapter(
                    wilayahs,
                    context
            );

            holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
            holder.recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public int getItemCount() {
        return kumpulanWilayahs.size();
    }
}
