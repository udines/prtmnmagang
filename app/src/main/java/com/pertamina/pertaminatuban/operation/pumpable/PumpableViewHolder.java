package com.pertamina.pertaminatuban.operation.pumpable;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;


public class PumpableViewHolder extends RecyclerView.ViewHolder {

    public final TextView noTank, value;

    public PumpableViewHolder(View itemView) {
        super(itemView);
        noTank = itemView.findViewById(R.id.item_pumpable_no_tank);
        value = itemView.findViewById(R.id.item_pumpable_value);
    }
}
