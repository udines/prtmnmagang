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

    private ArrayList<Tanker> tankers;
    private ArrayList<WaitingTime> waitingTimes;

    public WaitingTimeAdapter(ArrayList<Tanker> tankers, ArrayList<WaitingTime> waitingTimes) {
        this.tankers = tankers;
        this.waitingTimes = waitingTimes;
    }

    @Override
    public WaitingTimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_marine_waiting_time, parent, false);
        return new WaitingTimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WaitingTimeViewHolder holder, int position) {
        Tanker tanker = tankers.get(position);
        WaitingTime waitingTime = waitingTimes.get(position);

        setTextTime(holder.pilot, waitingTime.getPilot());
        setTextTime(holder.lab, waitingTime.getLabAnalysis());
        setTextTime(holder.tug, waitingTime.getTugBoat());
        setTextTime(holder.jetty, waitingTime.getJetty());
        setTextTime(holder.daylight, waitingTime.getDaylight());
        setTextTime(holder.tide, waitingTime.getTide());
        setTextTime(holder.ballast, waitingTime.getBallast());
        setTextTime(holder.cleaning, waitingTime.getTankCleaning());
        setTextTime(holder.nomination, waitingTime.getNomination());
        setTextTime(holder.manPower, waitingTime.getManPower());
        setTextTime(holder.badWeather, waitingTime.getBadWeater());
        setTextTime(holder.line, waitingTime.getLine());
        setTextTime(holder.cargo, waitingTime.getCargo());
        setTextTime(holder.ullage, waitingTime.getUllage());
        setTextTime(holder.bunker, waitingTime.getSupplyBunker());
        setTextTime(holder.fresh, waitingTime.getSupplyFreshWater());
        setTextTime(holder.actLoad, waitingTime.getActLoadDate());
        setTextTime(holder.preparation, waitingTime.getPreparation());
        setTextTime(holder.shoreOrder, waitingTime.getShoreOrder());
        setTextTime(holder.clearence, waitingTime.getShipClearence());
        setTextTime(holder.cargoDoc, waitingTime.getCargoDocument());
        setTextTime(holder.slowVessel, waitingTime.getSlowPumpVessel());
        setTextTime(holder.slowShore, waitingTime.getSlowPumpShore());
        setTextTime(holder.cargoCalc, waitingTime.getCargoCalculation());
        setTextTime(holder.steaming, waitingTime.getSteamingInOut());
        setTextTime(holder.unready, waitingTime.getShipUnready());

        holder.vessel.setText(String.valueOf(tanker.getCall() + " " + tanker.getVesselName()));
    }

    private void setTextTime(TextView textTime, int minutes) {
        int hour, mins;
        hour = minutes / 60;
        mins = minutes % 60;

        textTime.setText(String.valueOf(hour + ":" + mins));
    }

    @Override
    public int getItemCount() {
        return tankers.size();
    }
}
