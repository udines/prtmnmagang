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

    private ArrayList<String> noBill;
    private String call, vesselName;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marine);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getIntentExtras();
        populateMenu();
    }

    private void getIntentExtras() {
        call = getIntent().getStringExtra("call");
        vesselName = getIntent().getStringExtra("vesselName");
        noBill = getIntent().getStringArrayListExtra("noBill");

        toolbar.setTitle(String.valueOf("Call: " + call + " " + vesselName));
    }

    private void populateMenu() {
        RecyclerView recyclerView = findViewById(R.id.marine_recyclerview);
        ArrayList<MenuViewModel> menus = new ArrayList<>();

        MenuViewModel initialTanker = new MenuViewModel("Initial Tanker", 0);
        initialTanker.setNoBill(noBill);
        MenuViewModel tankerMovement = new MenuViewModel("Tanker Movement", 0);
        tankerMovement.setNoBill(noBill);
        MenuViewModel shipCondition = new MenuViewModel("Ship's Condition", 0);
        shipCondition.setNoBill(noBill);
        MenuViewModel waitingTime = new MenuViewModel("Waiting/Excess Time", 0);
        waitingTime.setNoBill(noBill);
        MenuViewModel temporaryStop = new MenuViewModel("Temporary Stop Activity", 0);
        temporaryStop.setNoBill(noBill);
        MenuViewModel portCharges = new MenuViewModel("Port Charges", 0);
        portCharges.setNoBill(noBill);
        MenuViewModel shipParticular = new MenuViewModel("Ship's Particular", 0);
        shipParticular.setNoBill(noBill);

        menus.add(initialTanker);
        menus.add(tankerMovement);
        menus.add(shipCondition);
        menus.add(waitingTime);
        menus.add(temporaryStop);
        menus.add(portCharges);
        menus.add(shipParticular);

        ArrayList<Class> classes = new ArrayList<>();
        classes.add(InitialTankerActivity.class);
        classes.add(TankerMovementActivity.class);
        classes.add(ShipConditionActivity.class);
        classes.add(WaitingTimeActivity.class);
        classes.add(TemporaryStopActivity.class);
        classes.add(PortChargesActivity.class);
        classes.add(ShipParticularActivity.class);

        ListMenuAdapter adapter = new ListMenuAdapter(menus, getApplicationContext());
        adapter.setClasses(classes);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
    }
}
