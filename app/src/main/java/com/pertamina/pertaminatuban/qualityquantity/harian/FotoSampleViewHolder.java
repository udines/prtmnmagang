package com.pertamina.pertaminatuban.qualityquantity.harian;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;

public class FotoSampleViewHolder extends RecyclerView.ViewHolder {

    public final TextView date, info;
    public final ImageView image;

    public FotoSampleViewHolder(View itemView) {
        super(itemView);
        date = itemView.findViewById(R.id.item_foto_sample_date);
        info = itemView.findViewById(R.id.item_foto_sample_info);
        image = itemView.findViewById(R.id.item_foto_sample_image);
    }
}
