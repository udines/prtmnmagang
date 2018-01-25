package com.pertamina.pertaminatuban.distribusi.tables;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.models.Konsumen;

import java.util.ArrayList;

public class KonsumenContainerAdapter extends RecyclerView.Adapter<KonsumenContainerViewHolder> {

    private ArrayList<ArrayList<Konsumen>> kumpulanKonsumens;

    public KonsumenContainerAdapter(ArrayList<ArrayList<Konsumen>> kumpulanKonsumens) {
        this.kumpulanKonsumens = kumpulanKonsumens;
    }

    @Override
    public KonsumenContainerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_container_tabel_konsumen,
                parent,
                false
        );
        return new KonsumenContainerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(KonsumenContainerViewHolder holder, int position) {
        holder.setView(kumpulanKonsumens.get(position));
    }

    @Override
    public int getItemCount() {
        return kumpulanKonsumens.size();
    }
}
