package com.pertamina.pertaminatuban.qualityquantity.harian;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.qualityquantity.models.ItemFotoSample;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;


public class FotoSampleAdapter extends RecyclerView.Adapter<FotoSampleViewHolder> {

    private ArrayList<ItemFotoSample> fotoSamples;
    private Context context;

    public FotoSampleAdapter(ArrayList<ItemFotoSample> fotoSamples, Context context) {
        this.fotoSamples = fotoSamples;
        this.context = context;
    }

    @Override
    public FotoSampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foto_sample, parent, false);
        return new FotoSampleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FotoSampleViewHolder holder, int position) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        holder.date.setText(format.format(fotoSamples.get(position).getDate()));
        holder.info.setText(fotoSamples.get(position).getInfo());
        Glide.with(context)
                .load(fotoSamples.get(position).getUrl())
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return fotoSamples.size();
    }
}
