package com.pertamina.pertaminatuban.finance.costperliter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;


public class CplViewHolder extends RecyclerView.ViewHolder {

    public TextView bulan, nilai;
    public LinearLayout row;

    public CplViewHolder(View itemView) {
        super(itemView);
        bulan = itemView.findViewById(R.id.item_cpl_bulan);
        nilai = itemView.findViewById(R.id.item_cpl_nilai);
        row = itemView.findViewById(R.id.item_cpl_row);
    }
}
