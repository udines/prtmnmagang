package com.pertamina.pertaminatuban.qualityquantity.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.qualityquantity.harian.TableTestReportActivity;
import com.pertamina.pertaminatuban.qualityquantity.models.NewTestReportHeader;

import java.util.ArrayList;

public class TestReportHeaderAdapter extends RecyclerView.Adapter<TestReportHeaderViewHolder> {

    private ArrayList<NewTestReportHeader> reportHeaders;
    private Context context;

    public TestReportHeaderAdapter(ArrayList<NewTestReportHeader> reportHeaders, Context context) {
        this.reportHeaders = reportHeaders;
        this.context = context;
    }

    @Override
    public TestReportHeaderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_test_report_header, parent, false);
        final TestReportHeaderViewHolder holder = new TestReportHeaderViewHolder(view);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TableTestReportActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("tanggal", reportHeaders.get(holder.getAdapterPosition()).getSamplingDate());
                intent.putExtra("fuel", reportHeaders.get(holder.getAdapterPosition()).getProduk());
                context.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(TestReportHeaderViewHolder holder, int position) {
        NewTestReportHeader object = reportHeaders.get(position);
        holder.no.setText(object.getNo());
        holder.product.setText(object.getProduk());
        holder.date.setText(object.getSamplingDate());
        holder.vessel.setText(object.getVessel());
        holder.shipNo.setText(object.getShipTankNo());
        holder.destination.setText(object.getPortDestination());
        holder.shore.setText(object.getShoreTank());
        holder.refinery.setText(object.getExRef());
        holder.sample.setText(object.getSample());
        holder.exSample.setText(object.getExSample());
    }

    @Override
    public int getItemCount() {
        return reportHeaders.size();
    }
}
