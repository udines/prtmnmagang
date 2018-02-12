package com.pertamina.pertaminatuban.marine.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.marine.models.InitialTanker;

import java.sql.Date;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class InitialTankerAdapter extends RecyclerView.Adapter<InitialTankerViewHolder> {

    private ArrayList<InitialTanker> tankers;

    public InitialTankerAdapter(ArrayList<InitialTanker> tankers) {
        this.tankers = tankers;
    }

    @Override
    public InitialTankerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_marine_initial_tanker, parent, false);
        return new InitialTankerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InitialTankerViewHolder holder, int position) {
        InitialTanker model = tankers.get(position);
        if (model.getCall() !=null) holder.callTanker.setText(String.valueOf(model.getCall()));
        if (model.getPeriod() != null) holder.periode.setText(model.getPeriod());
        if (model.getVoyage() != null) holder.voyage.setText(model.getVoyage());
        if (model.getNoBill() != null) holder.noBill.setText(model.getNoBill());
        if (model.getDateOfBill() != null) holder.doBill.setText(model.getDateOfBill());
        if (model.getStatus() != null) holder.statusTanker.setText(model.getStatus());
        if (model.getStatusOps() != null) holder.statusOps.setText(model.getStatusOps());
        if (model.getGrades() != null) holder.grades.setText(model.getGrades());
        if (model.getHandlingAgent() != null) holder.handlingAgent.setText(model.getHandlingAgent());
        if (model.getGeneralAgent() != null) holder.generalAgent.setText(model.getGeneralAgent());
        if (model.getCargoStatus() != null) holder.cargoStatus.setText(model.getCargoStatus());
        if (model.getTankerActivity() != null) holder.tankerActivity.setText(model.getTankerActivity());
        if (model.getPumpMethod() != null) holder.pumpMethod.setText(model.getPumpMethod());
        if (model.getBarthing() != null) holder.barthing.setText(model.getBarthing());
        if (model.getPortOfCall() != null) holder.portCall.setText(model.getPortOfCall());
        if (model.getPortOfCallReport() != null) holder.portCallReport.setText(model.getPortOfCallReport());
        if (model.getLastPort() != null) holder.lastPort.setText(model.getLastPort());
        if (model.getNextPort() != null) holder.nextPort.setText(model.getNextPort());
    }

    @Override
    public int getItemCount() {
        return tankers.size();
    }

    private void setMonthTextView(Date date, TextView dateText) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        DateFormatSymbols symbols = new DateFormatSymbols();
        String text = symbols.getMonths()[cal.get(Calendar.MONTH)] + " " + String.valueOf(cal.get(Calendar.YEAR));
        dateText.setText(text);
    }

    private void setDateTextView(Date date, TextView dateText) {
        java.util.Date date1 = new Date(date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        dateText.setText(format.format(date1));
    }
}
