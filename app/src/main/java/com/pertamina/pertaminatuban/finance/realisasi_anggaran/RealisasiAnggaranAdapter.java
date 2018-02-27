package com.pertamina.pertaminatuban.finance.realisasi_anggaran;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.finance.models.RealisasiAnggaran;

import java.util.ArrayList;
import java.util.List;


public class RealisasiAnggaranAdapter extends RecyclerView.Adapter<RealisasiAnggaranViewHolder> {

    private ArrayList<RealisasiAnggaran> anggarans;
    private Context context;

    public RealisasiAnggaranAdapter(ArrayList<RealisasiAnggaran> anggarans, Context context) {
        this.anggarans = anggarans;
        this.context = context;
    }

    @Override
    public RealisasiAnggaranViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_realisasi_anggaran, parent, false);
        return new RealisasiAnggaranViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RealisasiAnggaranViewHolder holder, int position) {
        RealisasiAnggaran model = anggarans.get(position);
        holder.title.setText(model.getCost_elements());

        List<BarEntry> actual = new ArrayList<>();
        List<BarEntry> commitment = new ArrayList<>();
        List<BarEntry> allotted = new ArrayList<>();
        List<BarEntry> plan = new ArrayList<>();
        List<BarEntry> available = new ArrayList<>();

        actual.add(new BarEntry(0, (float)model.getActual()));
        commitment.add(new BarEntry(1, (float)model.getCommitment()));
        allotted.add(new BarEntry(2, (float)model.getAllotted()));
        plan.add(new BarEntry(3, (float)model.getPlan()));
        available.add(new BarEntry(4, (float)model.getAvailable()));

        BarDataSet set1 = new BarDataSet(actual, "Actual");
        BarDataSet set2 = new BarDataSet(commitment, "Commitment");
        BarDataSet set3 = new BarDataSet(allotted, "Allotted");
        BarDataSet set4 = new BarDataSet(plan, "Plan");
        BarDataSet set5 = new BarDataSet(available, "Available");

        set1.setValueTextSize(11);
        set2.setValueTextSize(11);
        set3.setValueTextSize(11);
        set4.setValueTextSize(11);
        set5.setValueTextSize(11);

        set1.setColor(ContextCompat.getColor(context, R.color.black));
        set2.setColor(ContextCompat.getColor(context, R.color.red_pertamina));
        set3.setColor(ContextCompat.getColor(context, R.color.grey_400));
        set4.setColor(ContextCompat.getColor(context, R.color.green_pertamina));
        set5.setColor(ContextCompat.getColor(context, R.color.blue_pertamina));

        BarData data = new BarData(set1, set2, set3, set4, set5);
        holder.chart.setData(data);
        holder.chart.getDescription().setEnabled(false);
        holder.chart.getAxisRight().setDrawLabels(false);
        holder.chart.getXAxis().setDrawLabels(false);
        holder.chart.setHighlightPerTapEnabled(false);
        holder.chart.setFitBars(true);
        holder.chart.invalidate();
    }

    @Override
    public int getItemCount() {
        return anggarans.size();
    }
}
