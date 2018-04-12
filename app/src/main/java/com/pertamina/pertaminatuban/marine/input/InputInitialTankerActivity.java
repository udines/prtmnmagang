package com.pertamina.pertaminatuban.marine.input;

import android.app.DatePickerDialog;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.WilayahActivity;
import com.pertamina.pertaminatuban.marine.InitialTankerActivity;
import com.pertamina.pertaminatuban.marine.models.InitialTanker;
import com.pertamina.pertaminatuban.marine.models.MarineIdentifier;
import com.pertamina.pertaminatuban.marine.models.MarineInput;
import com.pertamina.pertaminatuban.marine.models.NewMarineInput;
import com.pertamina.pertaminatuban.service.UserClient;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InputInitialTankerActivity extends AppCompatActivity {

    private EditText inputCallTanker, inputVesselName, inputVoyage, inputNoBill, inputDoBill, inputHandlingAgent, inputGeneralAgent,
            inputCargoStatus;
    private Button inputPeriode, inputBerthingDate, kirim;
    private RadioGroup groupStatusTanker, groupStatusOperasional, groupGrades, groupTankerActivity,
            groupPumpingMethod, groupBarthing, groupPortCall, groupPortCallReport, groupLastPort,
            groupNextPort;
    private List<String> portChoice, barthingChoice, statusChoice, activityChoice, methodChoice, gradeChoice;

    private String bulan, callTanker, kapal, periode;
    private int month, year;

    //menyimpan data untuk berthing date
    private int bDay, bMonth, bYear;
    private String berthingDate;

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

        getIntentExtras();
        initChoices();
        getData();
        handlePeriodeButton();
        handleBerthingDate();
    }

    private void handleBerthingDate() {
        Calendar cal = Calendar.getInstance();
        bDay = cal.get(Calendar.DAY_OF_MONTH);
        bMonth = cal.get(Calendar.MONTH);
        bYear = cal.get(Calendar.YEAR);

        final SimpleDateFormat displayFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        final SimpleDateFormat uploadFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        //ganti text pada button
        cal.set(bYear, bMonth, bDay);
        inputBerthingDate.setText(displayFormat.format(new java.util.Date(cal.getTimeInMillis())));
        berthingDate = uploadFormat.format(new java.util.Date(cal.getTimeInMillis()));

        inputBerthingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        bYear = i;
                        bMonth = i1;
                        bDay = i2;

                        //ganti text pada button
                        Calendar cal = Calendar.getInstance();
                        cal.set(bYear, bMonth, bDay);
                        inputBerthingDate.setText(displayFormat.format(new java.util.Date(cal.getTimeInMillis())));
                        berthingDate = uploadFormat.format(new java.util.Date(cal.getTimeInMillis()));
                    }
                };
            }
        });
    }

    private void getIntentExtras() {

        bulan = getIntent().getStringExtra("bulan");
        kapal = getIntent().getStringExtra("kapal");
        periode = getIntent().getStringExtra("periode");
        callTanker = getIntent().getStringExtra("call");
    }

    private void handlePeriodeButton() {
        month = getIntent().getIntExtra("month", 0);
        year = getIntent().getIntExtra("year", 2018);
        setMonthButton(month, year);

        inputPeriode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar today = Calendar.getInstance();

                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(
                        InputInitialTankerActivity.this,
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) {
                                month = selectedMonth;
                                year = selectedYear;
                                setMonthButton(month, year);
                            }
                        },
                        today.get(Calendar.YEAR),
                        today.get(Calendar.MONTH)
                );

                builder.setMinYear(1970)
                        .setMaxYear(today.get(Calendar.YEAR))
                        .setTitle("Pilih bulan dan tahun")
                        .setActivatedMonth(month)
                        .setActivatedYear(year)
                        .build()
                        .show();
            }
        });
    }

    private void setMonthButton(int month, int year) {
        DateFormatSymbols symbols = new DateFormatSymbols();
        String text = symbols.getMonths()[month] + " " + String.valueOf(year);
        inputPeriode.setText(text);
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
        inputVesselName = findViewById(R.id.input_initial_tanker_name_of_vessel);
        inputVoyage = findViewById(R.id.input_initial_tanker_voyage_tanker);
        inputNoBill = findViewById(R.id.input_initial_tanker_no_bill_of_landing);
        inputDoBill = findViewById(R.id.input_initial_tanker_date_of_bill);
        inputHandlingAgent = findViewById(R.id.input_initial_tanker_handling_agent);
        inputGeneralAgent = findViewById(R.id.input_initial_tanker_general_agent);
        inputCargoStatus = findViewById(R.id.input_initial_tanker_cargo_status);
        groupPortCall = findViewById(R.id.input_initial_tanker_group_port_of_call);
        groupPortCallReport = findViewById(R.id.input_initial_tanker_group_port_of_call_report);
        groupLastPort = findViewById(R.id.input_initial_tanker_group_last_port);
        groupNextPort = findViewById(R.id.input_initial_tanker_group_next_port);

        inputPeriode = findViewById(R.id.input_initial_tanker_periode);
        inputBerthingDate = findViewById(R.id.input_initial_tanker_berthing_date);
        kirim = findViewById(R.id.input_initial_tanker_kirim);

        groupStatusOperasional = findViewById(R.id.input_initial_tanker_group_status_operasional);
        groupStatusTanker = findViewById(R.id.input_initial_tanker_group_status_tanker);
        groupGrades = findViewById(R.id.input_initial_tanker_group_grades);
        groupTankerActivity = findViewById(R.id.input_initial_tanker_group_tanker_activity);
        groupPumpingMethod = findViewById(R.id.input_initial_tanker_group_pumpung_method);
        groupBarthing = findViewById(R.id.input_initial_tanker_group_barthing);

        getInitialData();

        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputNoBill.getText().length() > 0 && inputVesselName.getText().length() > 0) {
                    getInputData();
                } else {
                    Toast.makeText(InputInitialTankerActivity.this, "Isi data untuk No bill dan name of vessel", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void getInputData() {

        String nomorBl = inputNoBill.getText().toString();
        String namaKapal = inputVesselName.getText().toString();

        ArrayList<NewMarineInput> newData = new ArrayList<>();
        newData.add(new NewMarineInput(
                getResources().getString(R.string.variable_init_tanker_call_tanker),
                getDataIfAvailable(inputCallTanker),
                nomorBl,
                namaKapal,
                berthingDate
        ));
        newData.add(new NewMarineInput(
                getResources().getString(R.string.variable_init_tanker_period),
                inputPeriode.getText().toString(),
                nomorBl,
                namaKapal,
                berthingDate
        ));
        newData.add(new NewMarineInput(
                getResources().getString(R.string.variable_init_tanker_voyage),
                getDataIfAvailable(inputVoyage),
                nomorBl,
                namaKapal,
                berthingDate
        ));
        newData.add(new NewMarineInput(
                getResources().getString(R.string.variable_init_tanker_date_bill),
                getDataIfAvailable(inputDoBill),
                nomorBl,
                namaKapal,
                berthingDate
        ));
        newData.add(new NewMarineInput(
                getResources().getString(R.string.variable_init_tanker_status_tanker),
                getRadioGroupData(groupStatusTanker),
                nomorBl,
                namaKapal,
                berthingDate
        ));
        newData.add(new NewMarineInput(
                getResources().getString(R.string.variable_init_tanker_status_ops),
                getRadioGroupData(groupStatusOperasional),
                nomorBl,
                namaKapal,
                berthingDate
        ));
        newData.add(new NewMarineInput(
                getResources().getString(R.string.variable_init_tanker_grades),
                getRadioGroupData(groupGrades),
                nomorBl,
                namaKapal,
                berthingDate
        ));
        newData.add(new NewMarineInput(
                getResources().getString(R.string.variable_init_tanker_handling_agent),
                getDataIfAvailable(inputHandlingAgent),
                nomorBl,
                namaKapal,
                berthingDate
        ));
        newData.add(new NewMarineInput(
                getResources().getString(R.string.variable_init_tanker_general_agent),
                getDataIfAvailable(inputGeneralAgent),
                nomorBl,
                namaKapal,
                berthingDate
        ));
        newData.add(new NewMarineInput(
                getResources().getString(R.string.variable_init_tanker_cargo_status),
                getDataIfAvailable(inputCargoStatus),
                nomorBl,
                namaKapal,
                berthingDate
        ));
        newData.add(new NewMarineInput(
                getResources().getString(R.string.variable_init_tanker_tanker_activity),
                getRadioGroupData(groupTankerActivity),
                nomorBl,
                namaKapal,
                berthingDate
        ));
        newData.add(new NewMarineInput(
                getResources().getString(R.string.variable_init_tanker_pump_method),
                getRadioGroupData(groupPumpingMethod),
                nomorBl,
                namaKapal,
                berthingDate
        ));
        newData.add(new NewMarineInput(
                getResources().getString(R.string.variable_init_tanker_barthing_spm),
                getRadioGroupData(groupBarthing),
                nomorBl,
                namaKapal,
                berthingDate
        ));
        newData.add(new NewMarineInput(
                getResources().getString(R.string.variable_init_tanker_port_call),
                getRadioGroupData(groupPortCall),
                nomorBl,
                namaKapal,
                berthingDate
        ));
        newData.add(new NewMarineInput(
                getResources().getString(R.string.variable_init_tanker_port_call_report),
                getRadioGroupData(groupPortCallReport),
                nomorBl,
                namaKapal,
                berthingDate
        ));
        newData.add(new NewMarineInput(
                getResources().getString(R.string.variable_init_tanker_last_port),
                getRadioGroupData(groupLastPort),
                nomorBl,
                namaKapal,
                berthingDate
        ));
        newData.add(new NewMarineInput(
                getResources().getString(R.string.variable_init_tanker_next_port),
                getRadioGroupData(groupNextPort),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        uploadData(newData);
    }

    private void uploadData(ArrayList<NewMarineInput> data) {
        String json = new Gson().toJson(data);
        Log.w("post json", json);
        sendPostRequest(data);
    }

    private String getRadioGroupData(RadioGroup radioGroup) {
        Log.w(String.valueOf(radioGroup.getId()), String.valueOf(radioGroup.isSelected()));
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

    private void setInitialData(InitialTanker initialTanker) {

        if (initialTanker != null) {

            setEditText(inputVoyage, initialTanker.getVoyage());
            setEditText(inputNoBill, initialTanker.getNoBill());
            setEditText(inputHandlingAgent, initialTanker.getHandlingAgent());
            setEditText(inputGeneralAgent, initialTanker.getGeneralAgent());
            setEditText(inputCargoStatus, initialTanker.getCargoStatus());


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
        } else {
            Log.w("init data", "null");
        }
    }

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

    private void setDateButtonText(Button dateButton, String stringDate) {
        if (stringDate != null) {
            dateButton.setText(stringDate);
        }
    }

    private void getInitialData() {

        setEditText(inputCallTanker, callTanker);
        inputPeriode.setText(String.valueOf(bulan + " " + periode));

        MarineIdentifier identifier = new MarineIdentifier(
                bulan,
                callTanker,
                kapal,
                periode
        );

        String json = new Gson().toJson(identifier);
        Log.w("identifier json", json);

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

        Call<Object> call = userClient.getInitInitialTanker(identifier);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.w("get code", String.valueOf(response.code()));
                if (response.code() == 200) {
//                    setInitialData(response.body());
                    String json = new Gson().toJson(response.body());
                    Log.w("body json", json);
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.w("error", t.getMessage());
            }
        });
    }

    private void sendPostRequest(ArrayList<NewMarineInput> marine) {
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
                Log.w("post code", String.valueOf(response.code()));
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
}
