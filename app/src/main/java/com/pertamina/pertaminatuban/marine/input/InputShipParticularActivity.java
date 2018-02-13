package com.pertamina.pertaminatuban.marine.input;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.marine.models.MarineIdentifier;
import com.pertamina.pertaminatuban.marine.models.MarineInput;
import com.pertamina.pertaminatuban.marine.models.ShipParticular;
import com.pertamina.pertaminatuban.service.UserClient;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InputShipParticularActivity extends AppCompatActivity {

    private EditText inputFlag, inputDwt, inputGrt, inputLoa, inputTypeCall, inputTypeActivity, inputRate,
            inputMaster;

    private String bulan, periode, kapal, callTanker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_ship_particular);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getData();
    }

    private void getData() {
        Button kirim;
        inputFlag = findViewById(R.id.input_ship_particular_flag);
        inputDwt = findViewById(R.id.input_ship_particular_dwt);
        inputGrt = findViewById(R.id.input_ship_particular_grt);
        inputLoa = findViewById(R.id.input_ship_particular_loa);
        inputTypeCall = findViewById(R.id.input_ship_particular_type_call);
        inputTypeActivity = findViewById(R.id.input_ship_particular_type_activity);
        inputRate = findViewById(R.id.input_ship_particular_rate);
        inputMaster = findViewById(R.id.input_ship_particular_master);
        kirim = findViewById(R.id.input_ship_particular_kirim);

        getInitialData();

        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInputData();
            }
        });
    }

    private void getInitialData() {
        bulan = getIntent().getStringExtra("bulan");
        kapal = getIntent().getStringExtra("kapal");
        periode = getIntent().getStringExtra("periode");
        callTanker = getIntent().getStringExtra("call");

        MarineIdentifier identifier = new MarineIdentifier(
                bulan, callTanker, kapal, periode
        );

        SharedPreferences preferences = getSharedPreferences(
                "login",
                Context.MODE_PRIVATE
        );
        final String key = preferences.getString("userKey", "none");

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("Authorization", key)
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build();

        String baseUrl = "http://www.api.clicktuban.com/";
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client);

        Retrofit retrofit = builder.build();
        UserClient userClient = retrofit.create(UserClient.class);

        Call<ShipParticular> call = userClient.getInitShipParticular(identifier);
        call.enqueue(new Callback<ShipParticular>() {
            @Override
            public void onResponse(Call<ShipParticular> call, Response<ShipParticular> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    setInitialData(response.body());
                }
            }

            @Override
            public void onFailure(Call<ShipParticular> call, Throwable t) {
                Log.w("error", t.getMessage());
            }
        });
    }

    private void getInputData() {

        ArrayList<MarineInput> data = new ArrayList<>();

        data.add(new MarineInput(
                getDataIfAvailable(inputFlag),
                getResources().getString(R.string.variable_ship_particular_flag),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDataIfAvailable(inputDwt),
                getResources().getString(R.string.variable_ship_particular_dwt),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDataIfAvailable(inputGrt),
                getResources().getString(R.string.variable_ship_particular_grt),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDataIfAvailable(inputLoa),
                getResources().getString(R.string.variable_ship_particular_loa),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDataIfAvailable(inputTypeCall),
                getResources().getString(R.string.variable_ship_particular_type_call),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDataIfAvailable(inputTypeActivity),
                getResources().getString(R.string.variable_ship_particular_type_activity),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDataIfAvailable(inputRate),
                getResources().getString(R.string.variable_ship_particular_hire_rate),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDataIfAvailable(inputMaster),
                getResources().getString(R.string.variable_ship_particular_master),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        uploadData(data);
    }

    private void uploadData(ArrayList<MarineInput> data) {
        Log.w("json", new Gson().toJson(data));
    }

    private String getDataIfAvailable(EditText inputField) {
        if (!inputField.getText().toString().isEmpty()) {
            return inputField.getText().toString();
        } else {
            return "";
        }
    }

    private void setInitialData(ShipParticular shipParticular) {
        if (shipParticular != null) {
            setEditText(inputFlag, shipParticular.getFlag());
            setEditText(inputDwt, shipParticular.getDwt());
            setEditText(inputGrt, shipParticular.getGrt());
            setEditText(inputLoa, shipParticular.getLoa());
            setEditText(inputTypeCall, shipParticular.getTypeCall());
            setEditText(inputTypeActivity, shipParticular.getTypeActivity());
            setEditText(inputRate, shipParticular.getHireRate());
            setEditText(inputMaster, shipParticular.getMaster());
        }
    }

    private void setEditText(EditText editText, Object value) {
        if (value != null) {
            if (value instanceof Float) {
                if ((Float) value != 0) {
                    editText.setText(String.valueOf(value));
                }
            } else if (value instanceof String) {
                editText.setText((String) value);
            }
        }
    }
}
