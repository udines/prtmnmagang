package com.pertamina.pertaminatuban.marine.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.marine.models.Tanker;
import com.pertamina.pertaminatuban.marine.models.WaitingTime;

import java.util.ArrayList;

public class WaitingTimeAdapter extends RecyclerView.Adapter<WaitingTimeViewHolder> {

    private ArrayList<WaitingTime> waitingTimes;

    public WaitingTimeAdapter(ArrayList<WaitingTime> waitingTimes) {
        this.waitingTimes = waitingTimes;
    }

    @Override
    public WaitingTimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_marine_waiting_time, parent, false);
        return new WaitingTimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WaitingTimeViewHolder holder, int position) {
        WaitingTime model = waitingTimes.get(position);

        setTextTime(holder.pilot, model.getPilot());
        setTextTime(holder.lab, model.getLabAnalysis());
        setTextTime(holder.tug, model.getTugBoat());
        setTextTime(holder.jetty, model.getJetty());
        setTextTime(holder.daylight, model.getDaylight());
        setTextTime(holder.tide, model.getTide());
        setTextTime(holder.ballast, model.getBallast());
        setTextTime(holder.cleaning, model.getTankCleaning());
        setTextTime(holder.nomination, model.getNomination());
        setTextTime(holder.manPower, model.getManPower());
        setTextTime(holder.badWeather, model.getBadWeater());
        setTextTime(holder.line, model.getLine());
        setTextTime(holder.cargo, model.getCargo());
        setTextTime(holder.ullage, model.getUllage());
        setTextTime(holder.bunker, model.getSupplyBunker());
        setTextTime(holder.fresh, model.getSupplyFreshWater());
        setTextTime(holder.actLoad, model.getActLoadDate());
        setTextTime(holder.preparation, model.getPreparation());
        setTextTime(holder.shoreOrder, model.getShoreOrder());
        setTextTime(holder.clearence, model.getShipClearence());
        setTextTime(holder.cargoDoc, model.getCargoDocument());
        setTextTime(holder.slowVessel, model.getSlowPumpVessel());
        setTextTime(holder.slowShore, model.getSlowPumpShore());
        setTextTime(holder.cargoCalc, model.getCargoCalculation());
        setTextTime(holder.steaming, model.getSteamingInOut());
        setTextTime(holder.unready, model.getShipUnready());

        if (model.getCall_number() != null && model.getKapal() != null && model.getPeriode() != null) {
            holder.vessel.setText(String.valueOf(
                    "Call: " + model.getCall_number() + " " + model.getKapal() + " " + model.getPeriode()
            ));
        }
    }

    private void setTextTime(TextView textTime, int minutes) {
        int hour, mins;
        hour = minutes / 60;
        mins = minutes % 60;

        textTime.setText(String.valueOf(hour + ":" + mins));
    }

    @Override
    public int getItemCount() {
        return waitingTimes.size();
    }
}
