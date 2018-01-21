package com.pertamina.pertaminatuban.distribusi.tables;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.models.Opers;

import java.util.ArrayList;

/**
 * Created by fata on 1/21/2018.
 */

public class OpersTableAdapter extends RecyclerView.Adapter<OpersTableViewHolder> {

    private ArrayList<Opers> opers;
    private Context context;
    private int type;

    public OpersTableAdapter(ArrayList<Opers> opers, Context context, int type) {
        this.opers = opers;
        this.context = context;
        this.type = type;
    }

    @Override
    public OpersTableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tabel_opers, parent, false);
        return new OpersTableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OpersTableViewHolder holder, int position) {
        holder.setView(opers.get(position), type);
        if (position % 2 == 0) {
            holder.row.setBackgroundColor(ContextCompat.getColor(context, R.color.grey_200));
        } else {
            holder.row.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }
    }

    @Override
    public int getItemCount() {
        return opers.size();
    }
}
