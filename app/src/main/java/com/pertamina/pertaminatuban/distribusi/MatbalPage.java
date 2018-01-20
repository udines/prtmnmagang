package com.pertamina.pertaminatuban.distribusi;

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
import com.pertamina.pertaminatuban.distribusi.models.Matbal;
import com.pertamina.pertaminatuban.distribusi.tables.MatbalTableAdapter;

import java.util.ArrayList;

/**
 * Created by fata on 1/20/2018.
 */

public class MatbalPage extends Fragment {

    private String fuel;
    private ArrayList<Matbal> matbals;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_matbal, container,false);
        TextView textView = view.findViewById(R.id.matbal_text);
        textView.setText(fuel);
        TextView total = view.findViewById(R.id.matbal_total);
        RecyclerView recyclerView = view.findViewById(R.id.matbal_table_recyclerview);
        if (matbals.size() == 0) {
            total.setText("Tidak ada data");
        } else {
            MatbalTableAdapter adapter = new MatbalTableAdapter(matbals, getContext());
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);

            float grandTotal = 0;
            for (int i = 0; i < matbals.size(); i++) {
                grandTotal = grandTotal + matbals.get(i).getNilai();
            }
            total.setText(String.valueOf("Grand total : " + grandTotal));
        }
        return view;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public ArrayList<Matbal> getMatbals() {
        return matbals;
    }

    public void setMatbals(ArrayList<Matbal> matbals) {
        this.matbals = matbals;
    }
}
