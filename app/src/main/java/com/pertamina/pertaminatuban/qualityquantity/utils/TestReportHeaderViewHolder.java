package com.pertamina.pertaminatuban.qualityquantity.utils;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;

public class TestReportHeaderViewHolder extends RecyclerView.ViewHolder {

    public final TextView no, product, date, vessel, shipNo, destination, shore, refinery, sample, exSample;
    public final CardView card;

    public TestReportHeaderViewHolder(View itemView) {
        super(itemView);
        no = itemView.findViewById(R.id.item_new_test_report_header_no);
        product = itemView.findViewById(R.id.item_new_test_report_header_product);
        date = itemView.findViewById(R.id.item_new_test_report_header_date);
        vessel = itemView.findViewById(R.id.item_new_test_report_header_vessel);
        shipNo = itemView.findViewById(R.id.item_new_test_report_header_ship_no);
        destination = itemView.findViewById(R.id.item_new_test_report_header_port_destination);
        shore = itemView.findViewById(R.id.item_new_test_report_header_shore_tank);
        refinery = itemView.findViewById(R.id.item_new_test_report_header_ex_refinery);
        sample = itemView.findViewById(R.id.item_new_test_report_header_sample);
        exSample = itemView.findViewById(R.id.item_new_test_report_header_ex_sample);
        card = itemView.findViewById(R.id.item_new_test_report_header_card);
    }
}
