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
import com.pertamina.pertaminatuban.marine.models.TankerMovement;
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

public class InputTankerMovementActivity extends AppCompatActivity {

    private Button allFastDate, allFastTime, channelConnectionDate, channelConnectionTime, dryCertIssuedDate1,
            dryCertIssuedTime1, compCargoCalcDate1, compCargoCalcTime1, labTestReleaseDate1,
            labTestReleaseTime1, commenceDisLoadDate, commenceDisLoadTime, compDisLoadDate,
            compDisLoadTime, compHoseDiscDate, compHoseDiscTime, dryCertIssuedDate2,
            dryCertIssuedTime2, compCargoCalcDate2, compCargoCalcTime2, labTestReleaseDate2,
            labTestReleaseTime2, cargoDocumentDate, cargoDocumentTime, portClearanceDate,
            portClearanceTime, bookingPilotDate, bookingPilotTime, pilotOnUnberDate, pilotOnUnberTime,
            castOffDate, castOffTime, anchoredDate, anchoredTime, pilotOnDepartDate, pilotOnDepartTime,
            anchorDepartDate, anchorDepartTime, atdDate, atdTime, deliveryDate, deliveryTime,
            redeliveryDate, redeliveryTime, onHireDate, onHireTime, offHireDate, offHireTime,
            timeOffToOn, kirim;
    private EditText inputRemarks;

    private String bulan, kapal, callTanker, periode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_tanker_movement);
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

        allFastDate = findViewById(R.id.input_tankmove_allfast_date);
        allFastTime = findViewById(R.id.input_tankmove_allfast_time);

        channelConnectionDate = findViewById(R.id.input_tankmove_ch_conn_date);
        channelConnectionTime = findViewById(R.id.input_tankmove_ch_conn_time);

        dryCertIssuedDate1 = findViewById(R.id.input_tankmove_dry_cert_date_1);
        dryCertIssuedTime1 = findViewById(R.id.input_tankmove_dry_cert_time_1);

        compCargoCalcDate1 = findViewById(R.id.input_tankmove_cargo_calc_date_1);
        compCargoCalcTime1 = findViewById(R.id.input_tankmove_cargo_calc_time_1);

        labTestReleaseDate1 = findViewById(R.id.input_tankmove_lab_release_date_1);
        labTestReleaseTime1 = findViewById(R.id.input_tankmove_lab_release_time_1);

        commenceDisLoadDate = findViewById(R.id.input_tankmove_comm_disload_date);
        commenceDisLoadTime = findViewById(R.id.input_tankmove_comm_disload_time);

        compDisLoadDate = findViewById(R.id.input_tankmove_comp_disload_date);
        compDisLoadTime = findViewById(R.id.input_tankmove_comp_disload_time);

        compHoseDiscDate = findViewById(R.id.input_tankmove_comp_hose_date);
        compHoseDiscTime = findViewById(R.id.input_tankmove_comp_hose_time);

        dryCertIssuedDate2 = findViewById(R.id.input_tankmove_dry_cert_date_2);
        dryCertIssuedTime2 = findViewById(R.id.input_tankmove_dry_cert_time_2);

        compCargoCalcDate2 = findViewById(R.id.input_tankmove_cargo_calc_date_2);
        compCargoCalcTime2 = findViewById(R.id.input_tankmove_cargo_calc_time_2);

        labTestReleaseDate2 = findViewById(R.id.input_tankmove_lab_release_date_2);
        labTestReleaseTime2 = findViewById(R.id.input_tankmove_lab_release_time_2);

        cargoDocumentDate = findViewById(R.id.input_tankmove_cargo_doc_date);
        cargoDocumentTime = findViewById(R.id.input_tankmove_cargo_doc_time);

        portClearanceDate = findViewById(R.id.input_tankmove_clearance_date);
        portClearanceTime = findViewById(R.id.input_tankmove_clearance_time);

        bookingPilotDate = findViewById(R.id.input_tankmove_book_pilot_date);
        bookingPilotTime = findViewById(R.id.input_tankmove_book_pilot_time);

        pilotOnUnberDate = findViewById(R.id.input_tankmove_pilot_on_unber_date);
        pilotOnUnberTime = findViewById(R.id.input_tankmove_pilot_on_unber_time);

        castOffDate = findViewById(R.id.input_tankmove_castoff_date);
        castOffTime = findViewById(R.id.input_tankmove_castoff_time);

        anchoredDate = findViewById(R.id.input_tankmove_anchored_date);
        anchoredTime = findViewById(R.id.input_tankmove_anchored_time);

        pilotOnDepartDate = findViewById(R.id.input_tankmove_pilot_on_depart_date);
        pilotOnDepartTime = findViewById(R.id.input_tankmove_pilot_on_depart_time);

        anchorDepartDate = findViewById(R.id.input_tankmove_anchor_depart_date);
        anchorDepartTime = findViewById(R.id.input_tankmove_anchor_depart_time);

        atdDate = findViewById(R.id.input_tankmove_atd_date);
        atdTime = findViewById(R.id.input_tankmove_atd_time);

        deliveryDate = findViewById(R.id.input_tankmove_deliv_date);
        deliveryTime = findViewById(R.id.input_tankmove_deliv_time);

        redeliveryDate = findViewById(R.id.input_tankmove_redeliv_date);
        redeliveryTime = findViewById(R.id.input_tankmove_redeliv_time);

        onHireDate = findViewById(R.id.input_tankmove_onhire_date);
        onHireTime = findViewById(R.id.input_tankmove_onhire_time);

        offHireDate = findViewById(R.id.input_tankmove_offhire_date);
        offHireTime = findViewById(R.id.input_tankmove_offhire_time);

        timeOffToOn = findViewById(R.id.input_tankmove_off_to_on_time);

        kirim = findViewById(R.id.input_tankmove_kirim);

        inputRemarks = findViewById(R.id.input_tankmove_input_remarks);

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

        Call<TankerMovement> call = userClient.getInitTankerMovement(identifier);
        call.enqueue(new Callback<TankerMovement>() {
            @Override
            public void onResponse(Call<TankerMovement> call, Response<TankerMovement> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    setInitialData(response.body());
                }
            }

            @Override
            public void onFailure(Call<TankerMovement> call, Throwable t) {
                Log.w("error", t.getMessage());
            }
        });
    }

    private void assignButtonListener() {
        addButtonListener(allFastDate, allFastTime);
        addButtonListener(channelConnectionDate, channelConnectionTime);
        addButtonListener(dryCertIssuedDate1, dryCertIssuedTime1);
        addButtonListener(compCargoCalcDate1, compCargoCalcTime1);
        addButtonListener(labTestReleaseDate1, labTestReleaseTime1);
        addButtonListener(commenceDisLoadDate, commenceDisLoadTime);
        addButtonListener(compDisLoadDate, compDisLoadTime);
        addButtonListener(compHoseDiscDate, compHoseDiscTime);
        addButtonListener(dryCertIssuedDate2, dryCertIssuedTime2);
        addButtonListener(compCargoCalcDate2, compCargoCalcTime2);
        addButtonListener(labTestReleaseDate2, labTestReleaseTime2);
        addButtonListener(cargoDocumentDate, cargoDocumentTime);
        addButtonListener(portClearanceDate, portClearanceTime);
        addButtonListener(bookingPilotDate, bookingPilotTime);
        addButtonListener(pilotOnUnberDate, pilotOnUnberTime);
        addButtonListener(castOffDate, castOffTime);
        addButtonListener(anchoredDate, anchoredTime);
        addButtonListener(pilotOnDepartDate, pilotOnDepartTime);
        addButtonListener(anchorDepartDate, anchorDepartTime);
        addButtonListener(atdDate, atdTime);
        addButtonListener(deliveryDate, deliveryTime);
        addButtonListener(redeliveryDate, redeliveryTime);
        addButtonListener(onHireDate, onHireTime);
        addButtonListener(offHireDate, offHireTime);
        addButtonListener(null, timeOffToOn);
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
                getResources().getString(R.string.variable_tank_move_all_fast),
                getTimeToString(allFastDate, allFastTime),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_tank_move_channel_connection),
                getTimeToString(channelConnectionDate, channelConnectionTime),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_tank_move_dry_certif_1),
                getTimeToString(dryCertIssuedDate1, dryCertIssuedTime1),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_tank_move_completed_cargo_calc_1),
                getTimeToString(compCargoCalcDate1, compCargoCalcTime1),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_tank_move_lab_test_released_1),
                getTimeToString(labTestReleaseDate1, labTestReleaseTime1),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_tank_move_commence_dis_load),
                getTimeToString(commenceDisLoadDate, commenceDisLoadTime),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_tank_move_completed_dis_load),
                getTimeToString(compDisLoadDate, compDisLoadTime),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_tank_move_completed_hose),
                getTimeToString(compHoseDiscDate, compHoseDiscTime),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_tank_move_dry_certif_2),
                getTimeToString(dryCertIssuedDate2, dryCertIssuedTime2),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_tank_move_completed_cargo_calc_2),
                getTimeToString(compCargoCalcDate2, compCargoCalcTime2),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_tank_move_lab_test_released_2),
                getTimeToString(labTestReleaseDate2, labTestReleaseTime2),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.cargo_document_on_board),
                getTimeToString(cargoDocumentDate, cargoDocumentTime),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_tank_move_port_clearence),
                getTimeToString(portClearanceDate, portClearanceTime),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_tank_move_book_pilot_unberthing),
                getTimeToString(bookingPilotDate, bookingPilotTime),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_tank_move_pilot_onboard_unberthing),
                getTimeToString(pilotOnUnberDate, pilotOnUnberTime),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_tank_move_cast_off),
                getTimeToString(castOffDate, castOffTime),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_tank_move_anchoredOnBoard),
                getTimeToString(anchoredDate, anchoredTime),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_tank_move_actual_time_departure),
                getTimeToString(atdDate, atdTime),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_tank_move_delivery),
                getTimeToString(deliveryDate, deliveryTime),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_tank_move_redelivery),
                getTimeToString(redeliveryDate, redeliveryTime),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_tank_move_on_hire),
                getTimeToString(onHireDate, onHireTime),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_tank_move_off_hire),
                getTimeToString(offHireDate, offHireTime),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_tank_move_time_off_on),
                getTimeOnlyToString(timeOffToOn),
                nomorBl,
                namaKapal,
                berthingDate
        ));

        data.add(new NewMarineInput(
                getResources().getString(R.string.variable_tank_move_remarks),
                getDataIfAvailable(inputRemarks),
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

    private void setInitialData(TankerMovement movement) {
        if (movement != null) {
            setDateAndTimeButton(allFastDate, allFastTime, movement.getAllFast());
            setDateAndTimeButton(channelConnectionDate, channelConnectionTime, movement.getChannelConnection());
            setDateAndTimeButton(dryCertIssuedDate1, dryCertIssuedTime1, movement.getDryCertifIssued1());
            setDateAndTimeButton(compCargoCalcDate1, compCargoCalcTime1, movement.getCompletedCargoCalculation1());
            setDateAndTimeButton(labTestReleaseDate1, labTestReleaseTime1, movement.getLabTestReleased1());
            setDateAndTimeButton(commenceDisLoadDate, commenceDisLoadTime, movement.getCommenceDisLoad());
            setDateAndTimeButton(compDisLoadDate, compDisLoadTime, movement.getCompletedDisLoad());
            setDateAndTimeButton(compHoseDiscDate, compHoseDiscTime, movement.getCompletedHoseDis());
            setDateAndTimeButton(dryCertIssuedDate2, dryCertIssuedTime2, movement.getDryCertifIssued2());
            setDateAndTimeButton(compCargoCalcDate2, compCargoCalcTime2, movement.getCompletedCargoCalculation2());
            setDateAndTimeButton(labTestReleaseDate2, labTestReleaseTime2, movement.getLabTestReleased2());
            setDateAndTimeButton(cargoDocumentDate, cargoDocumentTime, movement.getCargoDocument());
            setDateAndTimeButton(portClearanceDate, portClearanceTime, movement.getPortClearence());
            setDateAndTimeButton(bookingPilotDate, bookingPilotTime, movement.getBookingPilotUnberthing());
            setDateAndTimeButton(pilotOnUnberDate, pilotOnUnberTime, movement.getPilotOnBoardUnberthing());
            setDateAndTimeButton(castOffDate, castOffTime, movement.getCastOff());
            setDateAndTimeButton(anchoredDate, anchoredTime, movement.getAnchoredRede());
            setDateAndTimeButton(pilotOnDepartDate, pilotOnDepartTime, movement.getPilotOnBoardDeparture());
            setDateAndTimeButton(anchorDepartDate, anchorDepartTime, movement.getAnchorDeparture());
            setDateAndTimeButton(atdDate, atdTime, movement.getActualTimeDeparture());
            setDateAndTimeButton(deliveryDate, deliveryTime, movement.getDelivery());
            setDateAndTimeButton(redeliveryDate, redeliveryTime, movement.getRedelivery());
            setDateAndTimeButton(onHireDate, onHireTime, movement.getOnHire());
            setDateAndTimeButton(offHireDate, offHireTime, movement.getOffHire());
            setDateAndTimeButton(null, timeOffToOn, movement.getOffToOn());

            if (movement.getRemarksActivity() != null) {
                inputRemarks.setText(movement.getRemarksActivity());
            }
        }
    }

    private void setDateAndTimeButton(final Button dateButton, final Button timeButton, String time) {
        if (time != null) {
            String[] dateTime = time.split(" ");

            if (!dateTime[0].isEmpty()) {
                dateButton.setText(dateTime[0]);
            }

            if (!dateTime[1].isEmpty()) {
                dateButton.setText(dateTime[1]);
            }

        }
        if (dateButton != null) {
            dateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.w("date text", dateButton.getText().toString());
                    showDatePicker(dateButton);
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
                InputTankerMovementActivity.this,
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
                InputTankerMovementActivity.this,
                listener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
        );
        dialog.show();
    }

    private void sendPostRequest(ArrayList<NewMarineInput> marine) {
        SharedPreferences preferences = InputTankerMovementActivity.this.getSharedPreferences(
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

        Call<Object> call = userClient.postTankerMovement(marine);
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
