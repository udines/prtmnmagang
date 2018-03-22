package com.pertamina.pertaminatuban.operation.tfpipeline;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.operation.models.TransferPipeline;

import java.util.ArrayList;


public class TotalTransferPipelineAdapter extends RecyclerView.Adapter<TotalTransferPipelineViewHolder> {

    private ArrayList<TransferPipeline> pipelines;

    public TotalTransferPipelineAdapter(ArrayList<TransferPipeline> pipelines) {
        this.pipelines = pipelines;
    }

    @Override
    public TotalTransferPipelineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_total_transfer_pipeline, parent, false);
        return new TotalTransferPipelineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TotalTransferPipelineViewHolder holder, int position) {
//        holder.
    }

    @Override
    public int getItemCount() {
        return pipelines.size();
    }
}
