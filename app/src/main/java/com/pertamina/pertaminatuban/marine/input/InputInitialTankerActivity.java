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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.marine.models.InitialTanker;
import com.pertamina.pertaminatuban.marine.models.MarineInput;
import com.pertamina.pertaminatuban.service.UserClient;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InputInitialTankerActivity extends AppCompatActivity {

    private InitialTanker initialTanker;

    private EditText inputCallTanker, inputVoyage, inputNoBill, inputHandlingAgent, inputGeneralAgent,
            inputCargoStatus;
    private Button inputPeriode, kirim;
    private RadioGroup groupStatusTanker, groupStatusOperasional, groupGrades, groupTankerActivity,
            groupPumpingMethod, groupBarthing, groupPortCall, groupPortCallReport, groupLastPort,
            groupNextPort;
    private List<String> portChoice, barthingChoice, statusChoice, activityChoice, methodChoice, gradeChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_initial_tanker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initChoices();
        getData();
    }

    private void initChoices() {
        portChoice = new ArrayList<>();
        portChoice.add(getResources().getString(R.string.radio_loading_port));
        portChoice.add(getResources().getString(R.string.radio_discharge_port));
        Log.w("port choice size", String.valueOf(portChoice.size()));

        barthingChoice = new ArrayList<>();
        barthingChoice.add(getResources().getString(R.string.radio_spm_35));
        barthingChoice.add(getResources().getString(R.string.radio_spm_150));
        barthingChoice.add(getResources().getString(R.string.radio_jetty_tppi_2));
        barthingChoice.add(getResources().getString(R.string.radio_jetty_tppi_3));
        Log.w("barthing choice size", String.valueOf(barthingChoice.size()));

        statusChoice = new ArrayList<>();
        statusChoice.add(getResources().getString(R.string.radio_own_tanker));
        statusChoice.add(getResources().getString(R.string.radio_charter_tanker));

        activityChoice = new ArrayList<>();
        activityChoice.add(getResources().getString(R.string.radio_discharge));
        activityChoice.add(getResources().getString(R.string.radio_loading));

        methodChoice = new ArrayList<>();
        methodChoice.add(getResources().getString(R.string.radio_grade_by_grade));
        methodChoice.add(getResources().getString(R.string.radio_simultan));

        gradeChoice = new ArrayList<>();
        gradeChoice.add(getResources().getString(R.string.text_pertamax));
        gradeChoice.add(getResources().getString(R.string.text_premium));
        gradeChoice.add(getResources().getString(R.string.text_solar));
    }

    private void getData() {

        inputCallTanker = findViewById(R.id.input_initial_tanker_call_tanker);
        inputVoyage = findViewById(R.id.input_initial_tanker_voyage_tanker);
        inputNoBill = findViewById(R.id.input_initial_tanker_no_bill_of_landing);
        inputHandlingAgent = findViewById(R.id.input_initial_tanker_handling_agent);
        inputGeneralAgent = findViewById(R.id.input_initial_tanker_general_agent);
        inputCargoStatus = findViewById(R.id.input_initial_tanker_cargo_status);
        groupPortCall = findViewById(R.id.input_initial_tanker_group_port_of_call);
        groupPortCallReport = findViewById(R.id.input_initial_tanker_group_port_of_call_report);
        groupLastPort = findViewById(R.id.input_initial_tanker_group_last_port);
        groupNextPort = findViewById(R.id.input_initial_tanker_group_next_port);

        inputPeriode = findViewById(R.id.input_initial_tanker_periode);
        kirim = findViewById(R.id.input_initial_tanker_kirim);

        groupStatusOperasional = findViewById(R.id.input_initial_tanker_group_status_operasional);
        groupStatusTanker = findViewById(R.id.input_initial_tanker_group_status_tanker);
        groupGrades = findViewById(R.id.input_initial_tanker_group_grades);
        groupTankerActivity = findViewById(R.id.input_initial_tanker_group_tanker_activity);
        groupPumpingMethod = findViewById(R.id.input_initial_tanker_group_pumpung_method);
        groupBarthing = findViewById(R.id.input_initial_tanker_group_barthing);

//        if (currentDataExist()) {
//            getInitialData();
//            setInitialData();
//        }

        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInputData();
            }
        });
    }

    private void getInputData() {

        String bulan = getIntent().getStringExtra("bulan");

        String kapal = getIntent().getStringExtra("kapal");
        String periode = getIntent().getStringExtra("periode");
        String callTanker = getIntent().getStringExtra("call");

        ArrayList<MarineInput> data = new ArrayList<>();

        data.add(new MarineInput(
                getDataIfAvailable(inputCallTanker),
                getResources().getString(R.string.variable_init_tanker_call_tanker),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        /*data.add(new MarineInput(
                getDataIfAvailable(inputCallTanker),
                getResources().getString(R.string.variable_init_tanker_call_tanker),
                kapal,
                periode,
                bulan,
                callTanker
        ));*/

        data.add(new MarineInput(
                getDataIfAvailable(inputVoyage),
                getResources().getString(R.string.variable_init_tanker_voyage),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDataIfAvailable(inputNoBill),
                getResources().getString(R.string.variable_init_tanker_no_bill),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getRadioGroupData(groupStatusTanker),
                getResources().getString(R.string.variable_init_tanker_status_tanker),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getRadioGroupData(groupStatusOperasional),
                getResources().getString(R.string.variable_init_tanker_status_ops),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getRadioGroupData(groupGrades),
                getResources().getString(R.string.variable_init_tanker_grades),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDataIfAvailable(inputHandlingAgent),
                getResources().getString(R.string.variable_init_tanker_handling_agent),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDataIfAvailable(inputGeneralAgent),
                getResources().getString(R.string.variable_init_tanker_general_agent),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDataIfAvailable(inputCargoStatus),
                getResources().getString(R.string.variable_init_tanker_cargo_status),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getRadioGroupData(groupTankerActivity),
                getResources().getString(R.string.variable_init_tanker_tanker_activity),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getRadioGroupData(groupPumpingMethod),
                getResources().getString(R.string.variable_init_tanker_pump_method),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getRadioGroupData(groupBarthing),
                getResources().getString(R.string.variable_init_tanker_barthing_spm),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getRadioGroupData(groupPortCall),
                getResources().getString(R.string.variable_init_tanker_port_call),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getRadioGroupData(groupPortCallReport),
                getResources().getString(R.string.variable_init_tanker_port_call_report),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getRadioGroupData(groupLastPort),
                getResources().getString(R.string.variable_init_tanker_last_port),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getRadioGroupData(groupNextPort),
                getResources().getString(R.string.variable_init_tanker_next_port),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        uploadData(data);
    }

    private void uploadData(ArrayList<MarineInput> data) {
        String json = new Gson().toJson(data);
        Log.w("json", json);
        sendPostRequest(data);
    }

    private String getRadioGroupData(RadioGroup radioGroup) {
        if (!radioGroup.isSelected()) {
            return "";
        } else {
            int id= radioGroup.getCheckedRadioButtonId();
            View radioButton = radioGroup.findViewById(id);
            int radioId = radioGroup.indexOfChild(radioButton);
            RadioButton btn = (RadioButton) radioGroup.getChildAt(radioId);
            return  (String) btn.getText();
        }
    }

    private String getDataIfAvailable(EditText inputField) {
        if (!inputField.getText().toString().isEmpty()) {
            return inputField.getText().toString();
        } else {
            return "";
        }
    }

    /*private void setInitialData() {

        setEditText(inputCallTanker, String.valueOf(initialTanker.getCall()));
        setEditText(inputVoyage, initialTanker.getVoyage());
        setEditText(inputNoBill, initialTanker.getNoBill());
        setEditText(inputHandlingAgent, initialTanker.getHandlingAgent());
        setEditText(inputGeneralAgent, initialTanker.getGeneralAgent());
        setEditText(inputCargoStatus, initialTanker.getCargoStatus());

        setDateButtonText(inputPeriode, initialTanker.getPeriod());

        setRadioSelected(groupPortCall, portChoice, initialTanker.getPortOfCall());
        setRadioSelected(groupPortCallReport, portChoice, initialTanker.getPortOfCallReport());
        setRadioSelected(groupLastPort, portChoice, initialTanker.getLastPort());
        setRadioSelected(groupNextPort, portChoice, initialTanker.getNextPort());

        setRadioSelected(groupBarthing, barthingChoice, initialTanker.getBarthing());

        setRadioSelected(groupStatusTanker, statusChoice, initialTanker.getStatus());
        setRadioSelected(groupStatusOperasional, statusChoice, initialTanker.getStatusOps());

        setRadioSelected(groupTankerActivity, activityChoice, initialTanker.getTankerActivity());
        setRadioSelected(groupPumpingMethod, methodChoice, initialTanker.getPumpMethod());
        setRadioSelected(groupGrades, gradeChoice, initialTanker.getGrades());
    }*/

    private void setRadioSelected(RadioGroup group, List<String> choices, String chosen) {
        if (chosen != null && !chosen.isEmpty()) {
            Log.w("chosen", chosen);
            for (int i = 0; i < choices.size(); i++) {
                if (choices.get(i).equals(chosen)) {
                    Log.w("index", String.valueOf(i));
                    int id = group.getChildAt(i).getId();
                    group.check(id);
                    break;
                }
            }
        }
    }

    private void setEditText(EditText editText, String content) {
        if (content != null && !content.isEmpty()) {
            editText.setText(content);
        }
    }

    private void setDateButtonText(Button dateButton, Date date) {
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(date.getTime());
            DateFormatSymbols symbols = new DateFormatSymbols();
            String text = symbols.getMonths()[cal.get(Calendar.MONTH)] + " " + String.valueOf(cal.get(Calendar.YEAR));
            dateButton.setText(text);
        }
    }

    private boolean currentDataExist() {
        return true;
    }

    private void getInitialData() {

        Calendar cal = Calendar.getInstance();

        initialTanker = new InitialTanker(
                "",
                "",
                "15/D1/P.3010/VII/2017",
                "12",
                "",
                "Own Tanker",
                "Own Tanker",
                "",
                "PTM",
                "PTM",
                "Domestic",
                "",
                "Simultan",
                getResources().getString(R.string.radio_spm_35),
                "Discharge Port",
                "Discharge Port",
                "Loading Port",
                "Discharge Port"
        );
    }

    private void sendPostRequest(ArrayList<MarineInput> marine) {
        SharedPreferences preferences = InputInitialTankerActivity.this.getSharedPreferences(
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

        Call<Object> call = userClient.postInitialTanker(marine);
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

            }
        });
    }
}
