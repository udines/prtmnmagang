package com.pertamina.pertaminatuban.distribusi.tables;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.models.Konsumen;

import java.util.ArrayList;

/**
 * Created by fata on 1/20/2018.
 */

public class KonsumenTableAdapter extends RecyclerView.Adapter<KonsumenTableViewHolder> {

    private ArrayList<Konsumen> konsumens;
    private Context context;

    public KonsumenTableAdapter(ArrayList<Konsumen> konsumens, Context context) {
        this.konsumens = konsumens;
        this.context = context;
    }

    @Override
    public KonsumenTableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_tabel_konsumen, parent, false);
        return new KonsumenTableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(KonsumenTableViewHolder holder, int position) {
        holder.setView(konsumens.get(position));
        if (position % 2 == 0) {
            holder.row.setBackgroundColor(ContextCompat.getColor(context, R.color.grey_200));
        }
    }

    @Override
    public int getItemCount() {
        return konsumens.size();
    }
}
