package com.pertamina.pertaminatuban.finance.costperliter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.finance.models.CostPerLiter;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class CplAdapter extends RecyclerView.Adapter<CplViewHolder> {

    private ArrayList<CostPerLiter> costs;
    private Context context;

    public CplAdapter(ArrayList<CostPerLiter> costs, Context context) {
        this.costs = costs;
        this.context = context;
    }

    @Override
    public CplViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cost_per_liter, parent, false);
        return new CplViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CplViewHolder holder, int position) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        holder.nilai.setText(String.valueOf("$" + formatter.format(costs.get(position).getCostPerLiter())));

        Calendar cal = Calendar.getInstance();
        cal.set(
                cal.get(Calendar.YEAR),
                position,
                cal.get(Calendar.DAY_OF_MONTH)
        );
        SimpleDateFormat format = new SimpleDateFormat("MMMM", Locale.getDefault());
        holder.bulan.setText(format.format(new Date(cal.getTimeInMillis())));

        if (position % 2 == 0) {
            holder.row.setBackgroundColor(ContextCompat.getColor(context, R.color.grey_200));
        } else {
            holder.row.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }
    }

    @Override
    public int getItemCount() {
        return costs.size();
    }
}
