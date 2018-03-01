package com.pertamina.pertaminatuban.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.info_umum.InfoUmumActivity;
import com.pertamina.pertaminatuban.models.Featured;
import com.pertamina.pertaminatuban.models.ImageUrl;

import java.util.ArrayList;
import java.util.List;

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedViewHolder> {

    private List<Featured> featureds;
    private Context context;
    private ArrayList<ImageUrl> imageUrl;

    public FeaturedAdapter(List<Featured> featureds, Context context, ArrayList<ImageUrl> imageUrl) {
        this.featureds = featureds;
        this.context = context;
        this.imageUrl = imageUrl;
    }

    @Override
    public FeaturedViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_featured, parent, false);
        FeaturedViewHolder holder = new FeaturedViewHolder(view);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parent.getContext(), InfoUmumActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                parent.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(FeaturedViewHolder holder, int position) {
        Featured model = featureds.get(position);
        holder.judul.setText(model.getJudul());
        holder.informasi.setText(model.getInformasi());
        holder.deskripsi.setText(model.getDeskripsi());

        switch (position) {
            case 0:
                Glide.with(context)
                        .load(imageUrl.get(0).getUrl())
                        .into(holder.image);
                break;
            case 1:
                Glide.with(context)
                        .load(imageUrl.get(1).getUrl())
                        .into(holder.image);
                break;
            case 2:
                Glide.with(context)
                        .load(imageUrl.get(2).getUrl())
                        .into(holder.image);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return featureds.size();
    }
}
