package com.pertamina.pertaminatuban.distribusi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.models.MenuViewModel;
import com.pertamina.pertaminatuban.utils.ListMenuAdapter;

import java.util.ArrayList;

public class DistribusiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distribusi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        populateMenu();
    }

    private void populateMenu() {
        RecyclerView recyclerView = findViewById(R.id.distribusi_menu_recycler);
        ArrayList<MenuViewModel> menus = new ArrayList<>();
        menus.add(new MenuViewModel("Matbal", 0));
        menus.add(new MenuViewModel("Konsumen", 0));
        menus.add(new MenuViewModel("Wilayah", 0));
        menus.add(new MenuViewModel("Opers", 0));
        menus.add(new MenuViewModel("Ritase", 0));

        ListMenuAdapter adapter = new ListMenuAdapter(menus, getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
    }
}
