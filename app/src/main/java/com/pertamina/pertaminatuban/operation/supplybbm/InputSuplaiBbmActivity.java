package com.pertamina.pertaminatuban.operation.supplybbm;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.pertamina.pertaminatuban.R;

public class InputSuplaiBbmActivity extends AppCompatActivity {

    private EditText inputExtankerImportPremium, inputExtankerImportPertamax, inputExtankerImportSolar,
    inputExtankerDomestikPertamax, inputExtankerDomestikPremium, inputExtankerDomestikSolar,
    inputExtppiPertamax, inputExtppiPremium, inputExtppiSolar, inputExtwuPertamax, inputExtwuPremium,
    inputExtwuSolar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_suplai_bbm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
