package com.pertamina.pertaminatuban.distribusi.tables;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.models.Matbal;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MatbalTableAdapter extends RecyclerView.Adapter<MatbalTableViewHolder> {

    private ArrayList<Matbal> matbals;
    private ArrayList<Matbal> lastMatbals;
    private String periodeType;
    private Context context;
    private int year, month, day;

    public MatbalTableAdapter(ArrayList<Matbal> matbals, ArrayList<Matbal> lastMatbals, String periodeType, Context context, int year, int month, int day) {
        this.matbals = matbals;
        this.lastMatbals = lastMatbals;
        this.periodeType = periodeType;
        this.context = context;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    @Override
    public MatbalTableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_tabel_matbal, parent, false);
        return new MatbalTableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MatbalTableViewHolder holder, int position) {

        //set nilai sebelum dan sekarang
        setNilai(holder.fuel,
                matbals.get(position).getFuel(),
                holder.nilai,
                matbals.get(position).getNilai(),
                holder.lastNilai,
                lastMatbals,
                holder.difference);

        //set tulisan last dan this sesuai dengan tipe periode
        //misalnya this month dan last month
//        holder.textLast.setText(String.valueOf("Last " + periodeType));
//        holder.textNow.setText(String.valueOf("This " + periodeType));

        SimpleDateFormat dayFormat = new SimpleDateFormat("dd MMM", Locale.getDefault());
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMM", Locale.getDefault());
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());

        Calendar cal = Calendar.getInstance();

        switch (periodeType) {
            case "day":
                cal.set(year, month, day);
                Date date = new Date(cal.getTimeInMillis());
                holder.textNow.setText(dayFormat.format(date));

                cal.add(Calendar.DAY_OF_MONTH, -1);
                date = new Date(cal.getTimeInMillis());
                holder.textLast.setText(dayFormat.format(date));
                break;
            case "month":
                cal.set(year, month, day);
                Date dateMonth = new Date(cal.getTimeInMillis());
                holder.textNow.setText(monthFormat.format(dateMonth));

                cal.add(Calendar.MONTH, -1);
                dateMonth = new Date(cal.getTimeInMillis());
                holder.textLast.setText(monthFormat.format(dateMonth));
                break;
            case "year":
                cal.set(year, month, day);
                Date dateYear = new Date(cal.getTimeInMillis());
                holder.textNow.setText(yearFormat.format(dateYear));

                cal.add(Calendar.YEAR, -1);
                dateYear = new Date(cal.getTimeInMillis());
                holder.textLast.setText(yearFormat.format(dateYear));
                break;
        }

        //set row menjadi belang
        if (position % 2 == 0) {
            holder.row.setBackgroundColor(ContextCompat.getColor(context, R.color.grey_200));
        } else {
            holder.row.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }
    }

    private void setNilai(TextView textFuel, String fuel,
                          TextView now, float nilaiNow,
                          TextView last, ArrayList<Matbal> matbals,
                          TextView difference) {
        float nilaiLast = 0, nilaiDiff;

        textFuel.setText(fuel);
        now.setText(String.valueOf(nilaiNow + " KL"));

        for (int i = 0; i < matbals.size(); i++) {
            if (matbals.get(i).getFuel().equals(fuel)) {
                nilaiLast = matbals.get(i).getNilai();
                last.setText(String.valueOf(nilaiLast + " KL"));
                break;
            }
        }

        nilaiDiff = nilaiNow - nilaiLast;
        float percentage = (nilaiDiff / nilaiLast) * 100;

        if (nilaiLast == 0 && nilaiNow > 0) {
            percentage = 100;
        } else if (nilaiLast == 0 && nilaiNow == 0) {
            percentage = 0;
        }

        difference.setText(String.valueOf(nilaiDiff + " KL " + "(" + (int)percentage + "%)"));
        if (percentage < 0) {
            difference.setTextColor(ContextCompat.getColor(context, R.color.red_800));
        } else {
            difference.setTextColor(ContextCompat.getColor(context, R.color.green_800));
        }
    }

    @Override
    public int getItemCount() {
        if (matbals != null) {
            return matbals.size();
        } else {
            return 0;
        }
    }
}
