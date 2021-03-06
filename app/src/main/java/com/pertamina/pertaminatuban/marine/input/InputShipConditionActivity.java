package com.pertamina.pertaminatuban.marine.input;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.gson.Gson;
import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.marine.models.MarineIdentifier;
import com.pertamina.pertaminatuban.marine.models.MarineInput;
import com.pertamina.pertaminatuban.marine.models.NewMarineInput;
import com.pertamina.pertaminatuban.marine.models.ShipCondition;
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

public class InputShipConditionActivity extends AppCompatActivity {

    private EditText inputDescription, inputGradesBunker, inputRobLastPort, inputRobAta, inputRobAtd,
            inputRepl, inputLocationRepl, inputBunkerConsPort, inputBunkerConsSeatime,
            inputSlopTankAta, inputSlopTankAtd;
    private Button ataTime, atdTime, comReplDate, comReplTime, compReplDate, compReplTime, kirim;

    private String bulan, periode, kapal, callTanker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_ship_condition);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initData();
    }

    private void initData() {

        inputDescription = findViewById(R.id.input_ship_condition_description_draft);
        inputGradesBunker = findViewById(R.id.input_ship_condition_grades_bunker);
        inputRobLastPort = findViewById(R.id.input_ship_condition_rob_last_port);
        inputRobAta = findViewById(R.id.input_ship_condition_rob_ata);
        inputRobAtd = findViewById(R.id.input_ship_condition_rob_atd);
        inputRepl = findViewById(R.id.input_ship_condition_repl);
        inputLocationRepl = findViewById(R.id.input_ship_condition_location_repl);
        inputBunkerConsPort = findViewById(R.id.input_ship_condition_bunker_consumption_port);
        inputBunkerConsSeatime = findViewById(R.id.input_ship_condition_bunker_consumption_seatime);
        inputSlopTankAta = findViewById(R.id.input_ship_condition_slop_tank_ata);
        inputSlopTankAtd = findViewById(R.id.input_ship_condition_slop_tank_atd);

        ataTime = findViewById(R.id.input_ship_condition_ata_time);
        atdTime = findViewById(R.id.input_ship_condition_atd_time);
        comReplDate = findViewById(R.id.input_ship_condition_com_repl_date);
        comReplTime = findViewById(R.id.input_ship_condition_com_repl_time);
        compReplDate = findViewById(R.id.input_ship_condition_comp_repl_date);
        compReplTime = findViewById(R.id.input_ship_condition_comp_repl_time);
        kirim = findViewById(R.id.input_ship_condition_kirim);

        getInitialData();

        assignButtonListener();

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

        Call<ShipCondition> call = userClient.getInitShipCondition(identifier);
        call.enqueue(new Callback<ShipCondition>() {
            @Override
            public void onResponse(Call<ShipCondition> call, Response<ShipCondition> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    setInitialData(response.body());
                }
            }

            @Override
            public void onFailure(Call<ShipCondition> call, Throwable t) {
                Log.w("error", t.getMessage());
            }
        });
    }

    private void assignButtonListener() {
        addButtonListener(null, ataTime);
        addButtonListener(null, atdTime);
        addButtonListener(compReplDate, compReplTime);
        addButtonListener(comReplDate, comReplTime);
    }

    private void addButtonListener(final Button dateButton, final Button timeButton) {
        if (dateButton != null) {
            dateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.w("date text", dateButton.getText().toString());
                    showDatePicker(dateButton);
                }
            });
        }
        if (timeButton != null) {
            timeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.w("time text", timeButton.getText().toString());
                    showTimePicker(timeButton);
                }
            });
        }
    }

    private void getInputData() {

        String nomorBl = getIntent().getStringExtra("nomorBl");
        String namaKapal = getIntent().getStringExtra("namaKapal");
        String berthingDate = getIntent().getStringExtra("berthingDate");

        ArrayList<NewMarineInput> data = new ArrayList<>();

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_ship_condition_description),
                getDataIfAvailable(inputDescription),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_ship_condition_draft_ata),
                getTimeOnlyToString(ataTime),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_ship_condition_draft_atd),
                getTimeOnlyToString(atdTime),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_ship_condition_grade_bunker),
                getDataIfAvailable(inputGradesBunker),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_ship_condition_rob_last_port),
                getDataIfAvailable(inputRobLastPort),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_ship_condition_rob_ata),
                getDataIfAvailable(inputRobAta),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_ship_condition_rob_atd),
                getDataIfAvailable(inputRobAtd),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_ship_condition_repl),
                getDataIfAvailable(inputRepl),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_ship_condition_com_repl),
                getTimeToString(comReplDate, comReplTime),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_ship_condition_comp_repl),
                getTimeToString(compReplDate, compReplTime),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_ship_condition_location),
                getDataIfAvailable(inputLocationRepl),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_ship_condition_bunker_consumption_port),
                getDataIfAvailable(inputBunkerConsPort),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_ship_condition_bunker_consumption_seatime),
                getDataIfAvailable(inputBunkerConsSeatime),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_ship_condition_slop_tank_ata),
                getDataIfAvailable(inputSlopTankAta),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_ship_condition_slop_tank_atd),
                getDataIfAvailable(inputSlopTankAtd),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        uploadData(data);
    }

    private void uploadData(ArrayList<NewMarineInput> data) {
        Log.w("json", new Gson().toJson(data));
        sendPostRequest(data);
    }

    private void setInitialData(ShipCondition condition) {
        if (condition != null) {
            setDateAndTimeButton(null, ataTime, condition.getActualTimeArrival());
            setDateAndTimeButton(null, atdTime, condition.getActualTimeDeparture());
            setDateAndTimeButton(compReplDate, compReplTime, condition.getCompRepl());
            setDateAndTimeButton(comReplDate, comReplTime, condition.getComRepl());

            setEditText(inputDescription, condition.getDescriptionDraft());
            setEditText(inputGradesBunker, condition.getGradeBunker());
            setEditText(inputRobAta, condition.getRobAta());
            setEditText(inputRobAtd, condition.getRobAtd());
            setEditText(inputRobLastPort, condition.getRobLastPort());
            setEditText(inputRepl, condition.getRepl());
            setEditText(inputLocationRepl, condition.getReplLocation());
            setEditText(inputBunkerConsPort, condition.getBunkerConsumptionPort());
            setEditText(inputBunkerConsSeatime, condition.getBunkerConsumptionSeatime());
            setEditText(inputSlopTankAta, condition.getSlopTankAta());
            setEditText(inputSlopTankAtd, condition.getSlopTankAtd());
        }
    }

    private void sendPostRequest(ArrayList<NewMarineInput> marine) {
        SharedPreferences preferences = InputShipConditionActivity.this.getSharedPreferences(
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

        Log.w("json", String.valueOf(marine.size()));

        OkHttpClient client = httpClient.build();

        String baseUrl = "http://www.api.clicktuban.com/";
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client);

        Retrofit retrofit = builder.build();
        UserClient userClient = retrofit.create(UserClient.class);

        Call<Object> call = userClient.postShipCondition(marine);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200) {
                    Log.w("message", response.message());
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.w("error", t.getMessage());
            }
        });
    }

    private void setEditText(EditText editText, Object content) {
        if (content != null) {
            if (content instanceof Float && (Float)content == 0) {
                Log.w("content", String.valueOf(content));
            } else {
                editText.setText(String.valueOf(content));
            }
        }
    }

    private String getDataIfAvailable(EditText inputField) {
        if (!inputField.getText().toString().isEmpty()) {
            return inputField.getText().toString();
        } else {
            return "";
        }
    }

    private String getTimeToString(Button dateButton, Button timeButton) {
        String timestamp = "";
        if (!dateButton.getText().equals("date")) {
            timestamp = timestamp + dateButton.getText().toString() + " ";
        }
        if (!timeButton.getText().equals("time")) {
            timestamp = timestamp + timeButton.getText().toString();
        }
        return timestamp;
    }

    private String getTimeOnlyToString(Button timeButton) {
        String timestamp = "";
        if (!timeButton.getText().equals("time")) {
            timestamp = timestamp + timeButton.getText().toString();
        }
        return timestamp;
    }

    private void setDateAndTimeButton(final Button dateButton, final Button timeButton, String time) {
        if (time != null) {
            String[] hourMin = time.split(" ");
            if (hourMin[0] != null) {
                dateButton.setText(hourMin[0]);
            }
            if (hourMin[1] != null) {
                timeButton.setText(hourMin[1]);
            }
        }
    }

    private void showTimePicker(final Button timeButton) {
        final Calendar cal = Calendar.getInstance();
        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                cal.set(
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.HOUR_OF_DAY),
                        i,
                        i1
                );
                SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
                timeButton.setText(format.format(new Date(cal.getTimeInMillis())));
            }
        };
        TimePickerDialog dialog = new TimePickerDialog(
                InputShipConditionActivity.this,
                listener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
        );
        dialog.show();
    }

    private void showDatePicker(final Button dateButton) {
        final Calendar cal = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                cal.set(i, i1, i2);
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                dateButton.setText(format.format(new Date(cal.getTimeInMillis())));
            }
        };
        DatePickerDialog dialog = new DatePickerDialog(
                InputShipConditionActivity.this,
                listener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
        );
        dialog.show();
    }
}
