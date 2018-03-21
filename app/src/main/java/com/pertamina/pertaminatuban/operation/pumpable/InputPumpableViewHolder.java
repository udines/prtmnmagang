package com.pertamina.pertaminatuban.operation.pumpable;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.pertamina.pertaminatuban.R;


public class InputPumpableViewHolder extends RecyclerView.ViewHolder {

    public final Spinner spinner;
    public final EditText inputNoTank, inputValue;

    public InputPumpableViewHolder(View itemView) {
        super(itemView);
        spinner = itemView.findViewById(R.id.item_input_pumpable_spinner_product);
        inputNoTank = itemView.findViewById(R.id.item_input_pumpable_no_tank);
        inputValue = itemView.findViewById(R.id.item_input_pumpable_value);
    }
}
