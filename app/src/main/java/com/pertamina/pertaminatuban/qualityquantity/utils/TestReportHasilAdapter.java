package com.pertamina.pertaminatuban.qualityquantity.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.qualityquantity.models.NewTestReportItem;

import java.util.ArrayList;

public class TestReportHasilAdapter extends RecyclerView.Adapter<TestReportHasilViewHolder> {

    private ArrayList<NewTestReportItem> reportItems;
    private Context context;

    public TestReportHasilAdapter(ArrayList<NewTestReportItem> reportItems, Context context) {
        this.reportItems = reportItems;
        this.context = context;
    }

    @Override
    public TestReportHasilViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_test_report_hasil, parent, false);
        return new TestReportHasilViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TestReportHasilViewHolder holder, int position) {
        holder.value.setText(String.valueOf(reportItems.get(position).getHasilUji() + " " + reportItems.get(position).getUnit()));
        holder.parameter.setText(reportItems.get(position).getParameterUji());
        if (position % 2 == 0) {
            holder.row.setBackgroundColor(ContextCompat.getColor(context, R.color.grey_200));
        } else {
            holder.row.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }
    }

    @Override
    public int getItemCount() {
        return reportItems.size();
    }
}
