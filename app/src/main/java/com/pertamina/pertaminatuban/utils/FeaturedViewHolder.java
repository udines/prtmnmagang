package com.pertamina.pertaminatuban.utils;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.models.Featured;

public class FeaturedViewHolder extends RecyclerView.ViewHolder {

    private final ImageView image;
    public final CardView card;

    public FeaturedViewHolder(View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.item_menu_featured_image);
        card = itemView.findViewById(R.id.item_menu_featured_card);
    }

    public void setView(Featured model) {
        Glide.with(itemView.getContext())
                .load(model.getImageUrl())
                .into(image);
    }
}
