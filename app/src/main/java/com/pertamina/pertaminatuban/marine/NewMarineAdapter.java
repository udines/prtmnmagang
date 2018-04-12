package com.pertamina.pertaminatuban.marine;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pertamina.pertaminatuban.R;

import java.util.ArrayList;


public class NewMarineAdapter extends RecyclerView.Adapter<NewMarineViewHolder> {

    private ArrayList<MarineTanker> tankers;

    public NewMarineAdapter(ArrayList<MarineTanker> tankers) {
        this.tankers = tankers;
    }

    @Override
    public NewMarineViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_new_marine_tanker,
                parent,
                false
        );
        final NewMarineViewHolder holder = new NewMarineViewHolder(view);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent marineIntent = new Intent(parent.getContext(), MarineActivity.class);
                marineIntent.putExtra("call", tankers.get(holder.getAdapterPosition()).getCall());
                marineIntent.putExtra("vesselName", tankers.get(holder.getAdapterPosition()).getVesselName());
                marineIntent.putStringArrayListExtra("noBill", tankers.get(holder.getAdapterPosition()).getNoBill());
                marineIntent.putExtra("berthingDate", tankers.get(holder.getAdapterPosition()).getBerthingDate());
                parent.getContext().startActivity(marineIntent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(NewMarineViewHolder holder, int position) {
        MarineTanker model = tankers.get(position);

        //set card call tanker dan name vessel
        String callTanker = String.valueOf("Call: " + model.getCall() + " " + model.getVesselName());
        holder.callTanker.setText(callTanker);

        //set card no bill list
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < model.getNoBill().size(); i++) {
            builder.append("- ")
                    .append(model.getNoBill().get(i))
                    .append("\n");
        }
        holder.noBill.setText(builder.toString());
    }

    @Override
    public int getItemCount() {
        return tankers.size();
    }
}
