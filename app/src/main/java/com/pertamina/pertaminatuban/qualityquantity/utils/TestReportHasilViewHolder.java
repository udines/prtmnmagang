package com.pertamina.pertaminatuban.qualityquantity.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;

public class TestReportHasilViewHolder extends RecyclerView.ViewHolder {

    public final TextView parameter, value;
    public final LinearLayout row;

    public TestReportHasilViewHolder(View itemView) {
        super(itemView);
        parameter = itemView.findViewById(R.id.item_new_test_report_hasil_parameter);
        value = itemView.findViewById(R.id.item_new_test_report_hasil_value);
        row = itemView.findViewById(R.id.item_new_test_report_hasil_row);
    }
}
