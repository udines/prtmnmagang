package com.pertamina.pertaminatuban.marine;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;


public class NewMarineViewHolder extends RecyclerView.ViewHolder {

    public final TextView callTanker;
    public final TextView noBill;
    public final CardView card;

    public NewMarineViewHolder(View itemView) {
        super(itemView);
        callTanker = itemView.findViewById(R.id.item_new_marine_call_tanker);
        noBill = itemView.findViewById(R.id.item_new_marine_no_bill);
        card = itemView.findViewById(R.id.item_new_marine_card);
    }
}
