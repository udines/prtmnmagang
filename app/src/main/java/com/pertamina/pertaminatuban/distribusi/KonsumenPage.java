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
import com.pertamina.pertaminatuban.distribusi.models.Konsumen;
import com.pertamina.pertaminatuban.distribusi.models.Matbal;
import com.pertamina.pertaminatuban.distribusi.tables.KonsumenTableAdapter;
import com.pertamina.pertaminatuban.distribusi.tables.MatbalTableAdapter;

import java.util.ArrayList;

/**
 * Created by fata on 1/20/2018.
 */

public class KonsumenPage extends Fragment {
    private String fuel;
    private ArrayList<Konsumen> konsumens;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_konsumen, container,false);
        TextView textView = view.findViewById(R.id.konsumen_text);
        textView.setText(fuel);
        TextView total = view.findViewById(R.id.konsumen_total);
        RecyclerView recyclerView = view.findViewById(R.id.konsumen_table_recyclerview);
        if (konsumens.size() == 0) {
            total.setText("Tidak ada data");
        } else {
            KonsumenTableAdapter adapter = new KonsumenTableAdapter(konsumens, getContext());
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);

            float grandTotal = 0;
            for (int i = 0; i < konsumens.size(); i++) {
                grandTotal = grandTotal + konsumens.get(i).getNilai();
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

    public ArrayList<Konsumen> getKonsumens() {
        return konsumens;
    }

    public void setKonsumens(ArrayList<Konsumen> konsumens) {
        this.konsumens = konsumens;
    }
}
