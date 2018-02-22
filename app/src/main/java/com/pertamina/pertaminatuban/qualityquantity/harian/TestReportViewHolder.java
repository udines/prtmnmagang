package com.pertamina.pertaminatuban.qualityquantity.harian;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;


public class TestReportViewHolder extends RecyclerView.ViewHolder {

    public final CardView card;
    public final TextView date;

    public TestReportViewHolder(View itemView) {
        super(itemView);
        card = itemView.findViewById(R.id.item_test_report_card);
        date = itemView.findViewById(R.id.item_test_report_date);
    }
}
