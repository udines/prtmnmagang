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
import com.pertamina.pertaminatuban.marine.models.TemporaryStop;
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

public class InputTemporaryStopActivity extends AppCompatActivity {

    private EditText inputReason1, inputReason2, inputReason3, inputReason4, inputReason5;
    private Button kirim, inputStopDate1, inputStopTime1, inputResumeDate1, inputResumeTime1,
            inputStopDate2, inputStopTime2, inputResumeDate2, inputResumeTime2,
            inputStopDate3, inputStopTime3, inputResumeDate3, inputResumeTime3,
            inputStopDate4, inputStopTime4, inputResumeDate4, inputResumeTime4,
            inputStopDate5, inputStopTime5, inputResumeDate5, inputResumeTime5;
    private String bulan, kapal, periode, callTanker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_temporary_stop);
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

        inputReason1 = findViewById(R.id.input_temporary_stop_reason1);
        inputStopDate1 = findViewById(R.id.input_temporary_stop_date_stop1);
        inputStopTime1 = findViewById(R.id.input_temporary_stop_time_stop1);
        inputResumeDate1 = findViewById(R.id.input_temporary_stop_date_resume1);
        inputResumeTime1 = findViewById(R.id.input_temporary_stop_time_resume1);

        inputReason2 = findViewById(R.id.input_temporary_stop_reason2);
        inputStopDate2 = findViewById(R.id.input_temporary_stop_date_stop2);
        inputStopTime2 = findViewById(R.id.input_temporary_stop_time_stop2);
        inputResumeDate2 = findViewById(R.id.input_temporary_stop_date_resume2);
        inputResumeTime2 = findViewById(R.id.input_temporary_stop_time_resume2);

        inputReason3 = findViewById(R.id.input_temporary_stop_reason3);
        inputStopDate3 = findViewById(R.id.input_temporary_stop_date_stop3);
        inputStopTime3 = findViewById(R.id.input_temporary_stop_time_stop3);
        inputResumeDate3 = findViewById(R.id.input_temporary_stop_date_resume3);
        inputResumeTime3 = findViewById(R.id.input_temporary_stop_time_resume3);

        inputReason4 = findViewById(R.id.input_temporary_stop_reason4);
        inputStopDate4 = findViewById(R.id.input_temporary_stop_date_stop4);
        inputStopTime4 = findViewById(R.id.input_temporary_stop_time_stop4);
        inputResumeDate4 = findViewById(R.id.input_temporary_stop_date_resume4);
        inputResumeTime4 = findViewById(R.id.input_temporary_stop_time_resume4);

        inputReason5 = findViewById(R.id.input_temporary_stop_reason5);
        inputStopDate5 = findViewById(R.id.input_temporary_stop_date_stop5);
        inputStopTime5 = findViewById(R.id.input_temporary_stop_time_stop5);
        inputResumeDate5 = findViewById(R.id.input_temporary_stop_date_resume5);
        inputResumeTime5 = findViewById(R.id.input_temporary_stop_time_resume5);
        kirim = findViewById(R.id.input_temporary_stop_kirim);

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

        Call<TemporaryStop> call = userClient.getInitTemporaryStop(identifier);
        call.enqueue(new Callback<TemporaryStop>() {
            @Override
            public void onResponse(Call<TemporaryStop> call, Response<TemporaryStop> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    setInitialData(response.body());
                }
            }

            @Override
            public void onFailure(Call<TemporaryStop> call, Throwable t) {
                Log.w("error", t.getMessage());
            }
        });
    }

    private void setInitialData(TemporaryStop object) {
        if (object != null) {
            setEditText(inputReason1, object.getReason1());
            setEditText(inputReason2, object.getReason2());
            setEditText(inputReason3, object.getReason3());
            setEditText(inputReason4, object.getReason4());
            setEditText(inputReason5, object.getReason5());

            setButtonTime(inputStopDate1, inputStopTime1, object.getStopTime1());
            setButtonTime(inputStopDate2, inputStopTime2, object.getStopTime2());
            setButtonTime(inputStopDate3, inputStopTime3, object.getStopTime3());
            setButtonTime(inputStopDate4, inputStopTime4, object.getStopTime4());
            setButtonTime(inputStopDate5, inputStopTime5, object.getStopTime5());

            setButtonTime(inputResumeDate1, inputResumeTime1, object.getResumeTime1());
            setButtonTime(inputResumeDate2, inputResumeTime2, object.getResumeTime2());
            setButtonTime(inputResumeDate3, inputResumeTime3, object.getResumeTime3());
            setButtonTime(inputResumeDate4, inputResumeTime4, object.getResumeTime4());
            setButtonTime(inputResumeDate5, inputResumeTime5, object.getResumeTime5());
        }
    }

    private void setEditText(EditText editText, String value) {
        if (value != null) {
            editText.setText(value);
        }
    }

    private void setButtonTime(Button dateButton, Button timeButton, String value) {
        if (value != null) {
            String[] dateTime = value.split(" ");

            if (dateTime[0] != null) {
                dateButton.setText(dateTime[0]);
            }

            if (dateTime[1] != null) {
                timeButton.setText(dateTime[1]);
            }
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
                InputTemporaryStopActivity.this,
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
                InputTemporaryStopActivity.this,
                listener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
        );
        dialog.show();
    }

    private void assignButtonListener() {
        addButtonListener(inputStopDate1, inputStopTime1);
        addButtonListener(inputResumeDate1, inputResumeTime1);
        addButtonListener(inputStopDate2, inputStopTime2);
        addButtonListener(inputResumeDate2, inputResumeTime2);
        addButtonListener(inputStopDate3, inputStopTime3);
        addButtonListener(inputResumeDate3, inputResumeTime3);
        addButtonListener(inputStopDate4, inputStopTime4);
        addButtonListener(inputResumeDate4, inputResumeTime4);
        addButtonListener(inputStopDate5, inputStopTime5);
        addButtonListener(inputResumeDate5, inputResumeTime5);
    }

    private String getDataIfAvailable(EditText inputField) {
        if (!inputField.getText().toString().isEmpty()) {
            return inputField.getText().toString();
        } else {
            return "";
        }
    }

    private void getInputData() {

        String nomorBl = getIntent().getStringExtra("nomorBl");
        String namaKapal = getIntent().getStringExtra("namaKapal");
        String berthingDate = getIntent().getStringExtra("berthingDate");

        ArrayList<NewMarineInput> data = new ArrayList<>();

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_temp_stop_reason_1),
                getDataIfAvailable(inputReason1),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_temp_stop_start_1),
                getTimeToString(inputStopDate1, inputStopTime1),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_temp_stop_resume_1),
                getTimeToString(inputResumeDate1, inputResumeTime1),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_temp_stop_reason_2),
                getDataIfAvailable(inputReason2),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_temp_stop_start_2),
                getTimeToString(inputStopDate2, inputStopTime2),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_temp_stop_resume_2),
                getTimeToString(inputResumeDate2, inputResumeTime2),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_temp_stop_reason_3),
                getDataIfAvailable(inputReason3),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_temp_stop_start_3),
                getTimeToString(inputStopDate3, inputStopTime3),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_temp_stop_resume_3),
                getTimeToString(inputResumeDate3, inputResumeTime3),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_temp_stop_reason_4),
                getDataIfAvailable(inputReason4),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_temp_stop_start_4),
                getTimeToString(inputStopDate4, inputStopTime4),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_temp_stop_resume_4),
                getTimeToString(inputResumeDate4, inputResumeTime4),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_temp_stop_reason_5),
                getDataIfAvailable(inputReason5),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_temp_stop_start_5),
                getTimeToString(inputStopDate5, inputStopTime5),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_temp_stop_resume_5),
                getTimeToString(inputResumeDate5, inputResumeTime5),
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

    private void sendPostRequest(ArrayList<NewMarineInput> marine) {
        SharedPreferences preferences = InputTemporaryStopActivity.this.getSharedPreferences(
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

        Call<Object> call = userClient.postTemporaryStop(marine);
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
}
