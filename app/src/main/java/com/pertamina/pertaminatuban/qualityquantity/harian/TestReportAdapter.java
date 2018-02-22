package com.pertamina.pertaminatuban.qualityquantity.harian;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pertamina.pertaminatuban.R;

import java.util.ArrayList;


public class TestReportAdapter extends RecyclerView.Adapter<TestReportViewHolder> {

    private ArrayList<ItemTestReport> testReports;

    public TestReportAdapter(ArrayList<ItemTestReport> testReports) {
        this.testReports = testReports;
    }

    @Override
    public TestReportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test_report, parent, false);
        final TestReportViewHolder holder = new TestReportViewHolder(view);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemTestReport report = testReports.get(holder.getAdapterPosition());
                //masuk ke halaman display pdf yang dipilih
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(TestReportViewHolder holder, int position) {
        holder.date.setText(testReports.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return testReports.size();
    }
}
