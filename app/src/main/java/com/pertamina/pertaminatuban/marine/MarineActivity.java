package com.pertamina.pertaminatuban.marine;

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

public class MarineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marine);
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
        RecyclerView recyclerView = findViewById(R.id.marine_recyclerview);
        ArrayList<MenuViewModel> menus = new ArrayList<>();
        menus.add(new MenuViewModel("Ship's Movement", 0));
        menus.add(new MenuViewModel("In/Out Operation", 0));
        menus.add(new MenuViewModel("Cargo Quantity", 0));
        menus.add(new MenuViewModel("Performance Record", 0));
        menus.add(new MenuViewModel("Ship's Condition", 0));
        menus.add(new MenuViewModel("Waiting/Excess Time", 0));
        menus.add(new MenuViewModel("Temporary Stop Activity", 0));
        menus.add(new MenuViewModel("Integrated Port Time", 0));
        menus.add(new MenuViewModel("Port Charges", 0));
        menus.add(new MenuViewModel("Ship's Particular", 0));
        menus.add(new MenuViewModel("Claim Slow Speed", 0));
        menus.add(new MenuViewModel("Ship's Charted Party", 0));
        menus.add(new MenuViewModel("Shore Request", 0));
        menus.add(new MenuViewModel("Ship Request", 0));
        menus.add(new MenuViewModel("Connection", 0));
        menus.add(new MenuViewModel("Agreement by Request", 0));
        menus.add(new MenuViewModel("Agreement", 0));
        menus.add(new MenuViewModel("Actual", 0));
        menus.add(new MenuViewModel("Claim Slow Pumping", 0));
        menus.add(new MenuViewModel("Claim Transport Loss (R2)", 0));
        menus.add(new MenuViewModel("Key In MMHM", 0));
        menus.add(new MenuViewModel("Standard Time Movement", 0));

        ArrayList<Class> classes = new ArrayList<>();

        ListMenuAdapter adapter = new ListMenuAdapter(menus, getApplicationContext());
        adapter.setClasses(classes);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
    }
}
