package com.pertamina.pertaminatuban.operation.pumpable;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.operation.models.Pumpable;

import java.util.ArrayList;


public class InputPumpableAdapter extends RecyclerView.Adapter<InputPumpableViewHolder> {

    private ArrayList<Pumpable> pumpables;
    private Context context;

    public InputPumpableAdapter(ArrayList<Pumpable> pumpables, Context context) {
        this.pumpables = pumpables;
        this.context = context;
    }

    @Override
    public InputPumpableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_input_pumpable, parent, false);
        return new InputPumpableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final InputPumpableViewHolder holder, int position) {
        if (pumpables.get(position).getNoTank().length() > 0) {
            holder.inputNoTank.setText(pumpables.get(position).getNoTank());
        }
        if (pumpables.get(position).getValue() > 0) {
            holder.inputValue.setText(String.valueOf(pumpables.get(position).getValue()));
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.bahan_bakar, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spinner.setAdapter(adapter);
        holder.spinner.setSelection(adapter.getPosition(pumpables.get(position).getFuel()));
        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pumpables.get(holder.getAdapterPosition()).setFuel(holder.spinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        holder.inputNoTank.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    pumpables.get(holder.getAdapterPosition()).setNoTank(charSequence.toString());
                } else {
                    pumpables.get(holder.getAdapterPosition()).setNoTank("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        holder.inputValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    pumpables.get(holder.getAdapterPosition()).setValue(Long.parseLong(charSequence.toString()));
                } else {
                    pumpables.get(holder.getAdapterPosition()).setValue(0);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return pumpables.size();
    }

    public ArrayList<Pumpable> getPumpables() {
        return pumpables;
    }
}
