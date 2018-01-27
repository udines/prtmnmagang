package com.pertamina.pertaminatuban.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.models.Featured;

import java.util.List;

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedViewHolder> {

    private List<Featured> featureds;

    public FeaturedAdapter(List<Featured> featureds) {
        this.featureds = featureds;
    }

    @Override
    public FeaturedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_featured, parent, false);
        return new FeaturedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FeaturedViewHolder holder, int position) {
        holder.setView(featureds.get(position));
    }

    @Override
    public int getItemCount() {
        return featureds.size();
    }
}
