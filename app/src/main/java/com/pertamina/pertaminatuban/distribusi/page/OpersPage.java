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
import com.pertamina.pertaminatuban.distribusi.models.Opers;
import com.pertamina.pertaminatuban.distribusi.tables.OpersTableAdapter;

import java.util.ArrayList;

public class OpersPage extends Fragment {
    private int type;
    private ArrayList<Opers> opers;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_opers, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.opers_table_recyclerview);

        TextView text = view.findViewById(R.id.opers_text);
        text.setText(Opers.getTitle(type));

        OpersTableAdapter adapter = new OpersTableAdapter(opers, getContext(), type);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ArrayList<Opers> getOpers() {
        return opers;
    }

    public void setOpers(ArrayList<Opers> opers) {
        this.opers = opers;
    }
}
