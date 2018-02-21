package com.pertamina.pertaminatuban.distribusi;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

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

        /*handle button tambah. jika user role adalah distribusi maka tampilkan
        * tombol untuk menambah data*/
        handleAddButton();
    }

    private void handleAddButton() {
        Button addButton = findViewById(R.id.distribusi_button_tambah);

        SharedPreferences preferences = this.getSharedPreferences(
                "login",
                Context.MODE_PRIVATE
        );
        String role = preferences.getString("userRole", "none");
        if (role.equals("distribusi")) {
            addButton.setVisibility(View.VISIBLE);
        } else {
            addButton.setVisibility(View.GONE);
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DistribusiAddDialog dialog = new DistribusiAddDialog();
                dialog.show(getSupportFragmentManager(), "add distribusi");
            }
        });
    }

    private void populateMenu() {
        RecyclerView recyclerView = findViewById(R.id.distribusi_menu_recycler);
        ArrayList<MenuViewModel> menus = new ArrayList<>();
        menus.add(new MenuViewModel("Matbal", 0));
        menus.add(new MenuViewModel("Konsumen", 0));
        menus.add(new MenuViewModel("Wilayah", 0));
        menus.add(new MenuViewModel("Operasional", 0));

        ArrayList<Class> classes = new ArrayList<>();
        classes.add(MatbalActivity.class);
        classes.add(KonsumenActivity.class);
        classes.add(WilayahActivity.class);
        classes.add(OpersActivity.class);

        ListMenuAdapter adapter = new ListMenuAdapter(menus, getApplicationContext());
        adapter.setClasses(classes);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
    }
}
