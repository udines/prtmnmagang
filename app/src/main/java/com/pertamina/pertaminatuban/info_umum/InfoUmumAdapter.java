package com.pertamina.pertaminatuban.info_umum;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pertamina.pertaminatuban.R;

import java.util.ArrayList;

/**
 * Created by laptop on 1/29/2018.
 */

public class InfoUmumAdapter extends RecyclerView.Adapter<InfoUmumViewHolder> {

    private ArrayList<InfoUmum> infoUmums;

    public InfoUmumAdapter(ArrayList<InfoUmum> infoUmums) {
        this.infoUmums = infoUmums;
    }

    @Override
    public InfoUmumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info_umum, parent, false);
        return new InfoUmumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InfoUmumViewHolder holder, int position) {
        holder.setView(infoUmums.get(position));
    }

    @Override
    public int getItemCount() {
        return infoUmums.size();
    }
}
