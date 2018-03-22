package com.pertamina.pertaminatuban.operation.tfpipeline;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;


public class TotalTransferPipelineViewHolder extends RecyclerView.ViewHolder {

    public final TextView var, val;

    public TotalTransferPipelineViewHolder(View itemView) {
        super(itemView);
        var = itemView.findViewById(R.id.item_total_transfer_pipeline_var);
        val = itemView.findViewById(R.id.item_total_transfer_pipeline_val);
    }
}
