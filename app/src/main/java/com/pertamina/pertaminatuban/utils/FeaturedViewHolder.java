package com.pertamina.pertaminatuban.utils;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.models.Featured;

public class FeaturedViewHolder extends RecyclerView.ViewHolder {

    public final ImageView image;
    public final CardView card;
    public final TextView judul, informasi, deskripsi;
    public final TextView index;

    public FeaturedViewHolder(View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.item_menu_featured_image);
        card = itemView.findViewById(R.id.item_menu_featured_card);
        judul = itemView.findViewById(R.id.item_menu_featured_judul);
        informasi = itemView.findViewById(R.id.item_menu_featured_informasi);
        deskripsi = itemView.findViewById(R.id.item_menu_featured_deskripsi);
        index = itemView.findViewById(R.id.item_menu_featured_index);
    }
}
