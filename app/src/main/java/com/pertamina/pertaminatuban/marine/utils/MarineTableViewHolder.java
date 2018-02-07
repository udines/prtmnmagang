package com.pertamina.pertaminatuban.marine.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.marine.models.MarineTable;

public class MarineTableViewHolder extends RecyclerView.ViewHolder {

    private final TextView variable, value;

    public MarineTableViewHolder(View itemView) {
        super(itemView);
        variable = itemView.findViewById(R.id.item_marine_variable);
        value = itemView.findViewById(R.id.item_marine_value);
    }

    public void setView(MarineTable model) {
        variable.setText(model.getVariable());
        value.setText(model.getValue());
    }
}
