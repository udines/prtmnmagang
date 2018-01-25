package com.pertamina.pertaminatuban.distribusi.page;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.models.Wilayah;
import com.pertamina.pertaminatuban.distribusi.tables.WilayahTableAdapter;

import java.util.ArrayList;

/**
 * Created by fata on 1/21/2018.
 */

public class WilayahPage extends Fragment {
    private String fuel;
    private ArrayList<Wilayah> wilayahs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_wilayah, container, false);

        TextView textView = view.findViewById(R.id.wilayah_text);
        textView.setText(fuel);
        TextView total = view.findViewById(R.id.wilayah_total);
        RecyclerView recyclerView = view.findViewById(R.id.wilayah_table_recyclerview);

        if (wilayahs.size() > 0) {
            float grandTotal = 0;
            for (int i = 0; i < wilayahs.size(); i++) {
                grandTotal = grandTotal + wilayahs.get(i).getNilai();
            }
            total.setText(String.valueOf("Grand total: " + grandTotal + " KL"));

            WilayahTableAdapter adapter = new WilayahTableAdapter(wilayahs, getContext());
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);
        }

        return view;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public ArrayList<Wilayah> getWilayahs() {
        return wilayahs;
    }

    public void setWilayahs(ArrayList<Wilayah> wilayahs) {
        this.wilayahs = wilayahs;
    }
}
