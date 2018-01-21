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
import com.pertamina.pertaminatuban.distribusi.models.Ritase;
import com.pertamina.pertaminatuban.distribusi.tables.RitaseTableAdapter;

import java.util.ArrayList;

public class RitasePage extends Fragment {
    private ArrayList<Ritase> ritases;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_ritase, container, false);

        TextView noMobil, transportir, total;
        RecyclerView recyclerView = view.findViewById(R.id.ritase_table_recyclerview);
        noMobil = view.findViewById(R.id.ritase_no_mobil);
        transportir = view.findViewById(R.id.ritase_transportir);
        total = view.findViewById(R.id.ritase_total);

        if (ritases.size() > 0) {
            Ritase ritase = ritases.get(0);
            noMobil.setText(String.valueOf(ritase.getNomobil() + " ( " + ritase.getKapasitas() + " )"));
            transportir.setText(ritase.getTransportir());

            float grandTotal = 0;
            for (int i = 0; i < ritases.size(); i++) {
                grandTotal = grandTotal + ritases.get(i).getNilai();
                total.setText(String.valueOf("Grand total : " + grandTotal));
            }
        }

        RitaseTableAdapter adapter = new RitaseTableAdapter(ritases, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    public ArrayList<Ritase> getRitases() {
        return ritases;
    }

    public void setRitases(ArrayList<Ritase> ritases) {
        this.ritases = ritases;
    }
}
