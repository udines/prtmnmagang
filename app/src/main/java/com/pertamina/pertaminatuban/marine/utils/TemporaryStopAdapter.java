package com.pertamina.pertaminatuban.marine.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.marine.models.TemporaryStop;

import java.util.ArrayList;


public class TemporaryStopAdapter extends RecyclerView.Adapter<TemporaryStopViewHolder> {

    private ArrayList<TemporaryStop> temporaryStops;

    public TemporaryStopAdapter(ArrayList<TemporaryStop> temporaryStops) {
        this.temporaryStops = temporaryStops;
    }

    @Override
    public TemporaryStopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_marine_temporary_stop, parent, false);
        return new TemporaryStopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TemporaryStopViewHolder holder, int position) {
        TemporaryStop model = temporaryStops.get(position);

        setTextData(holder.reason1, model.getReason1());
        setTextData(holder.reason2, model.getReason2());
        setTextData(holder.reason3, model.getReason3());
        setTextData(holder.reason4, model.getReason4());
        setTextData(holder.reason5, model.getReason5());

        setTextData(holder.stop1, model.getStopTime1());
        setTextData(holder.stop2, model.getStopTime2());
        setTextData(holder.stop3, model.getStopTime3());
        setTextData(holder.stop4, model.getStopTime4());
        setTextData(holder.stop5, model.getStopTime5());

        setTextData(holder.resume1, model.getResumeTime1());
        setTextData(holder.resume2, model.getResumeTime2());
        setTextData(holder.resume3, model.getResumeTime3());
        setTextData(holder.resume4, model.getResumeTime4());
        setTextData(holder.resume5, model.getResumeTime5());

        if (model.getCall_number() != null && model.getKapal() != null && model.getPeriode() != null) {
            holder.vessel.setText(String.valueOf(
                    "Call: " + model.getCall_number() + " " + model.getKapal() + " " + model.getPeriode()
            ));
        }
    }

    private void setTextData(TextView text, String data) {
        if (data != null) {
            text.setText(data);
        }
    }

    @Override
    public int getItemCount() {
        return temporaryStops.size();
    }
}
