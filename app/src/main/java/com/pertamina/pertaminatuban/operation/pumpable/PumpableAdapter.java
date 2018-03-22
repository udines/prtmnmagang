package com.pertamina.pertaminatuban.operation.pumpable;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.operation.models.Pumpable;

import java.util.ArrayList;


public class PumpableAdapter extends RecyclerView.Adapter<PumpableViewHolder> {

    private ArrayList<Pumpable> pumpables;

    public PumpableAdapter(ArrayList<Pumpable> pumpables) {
        this.pumpables = pumpables;
    }

    @Override
    public PumpableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pumpable, parent, false);
        return new PumpableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PumpableViewHolder holder, int position) {
        String noTank = "Tank: ";
        switch (pumpables.get(position).getNoTank()) {
            case "total":
                noTank = "Total: ";
                break;
            case "average":
                noTank = "Average: ";
                break;
            default:
                noTank = noTank + pumpables.get(position).getNoTank();
        }
        holder.noTank.setText(String.valueOf(noTank));
        holder.value.setText(String.valueOf(pumpables.get(position).getValue()));
    }

    @Override
    public int getItemCount() {
        return pumpables.size();
    }
}
