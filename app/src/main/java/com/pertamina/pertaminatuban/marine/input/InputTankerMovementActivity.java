package com.pertamina.pertaminatuban.marine.input;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import com.pertamina.pertaminatuban.marine.models.TankerMovement;

import java.sql.Timestamp;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

    private TankerMovement movement;

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

        if (currentDataExist()) {
            getCurrentData();
            setInitialInput();
        }

        assignButtonListener();

        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInputData();
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
        Calendar cal = Calendar.getInstance();

        /*int year, month;
        year = getIntent().getIntExtra("yearPeriod", 2018);
        month = getIntent().getIntExtra("monthPeriod", 0);
        cal.set(year, month, 1);*/

        Date date = new Date(cal.getTimeInMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-dd");
        String bulan = format.format(date);

        /*String kapal = getIntent().getStringExtra("vesselName");
        String periode = getIntent().getStringExtra("periode");
        String callTanker = getIntent().getStringExtra("callTanker");
        kapal = kapal.toUpperCase();*/

        String kapal = "John Caine";
        kapal = kapal.toUpperCase();
        String periode = "A";
        String callTanker = "1";

        ArrayList<MarineInput> data = new ArrayList<>();

        data.add(new MarineInput(
                getTimeToString(allFastDate, allFastTime),
                getResources().getString(R.string.variable_tank_move_all_fast),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getTimeToString(channelConnectionDate, channelConnectionTime),
                getResources().getString(R.string.variable_tank_move_channel_connection),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getTimeToString(dryCertIssuedDate1, dryCertIssuedTime1),
                getResources().getString(R.string.variable_tank_move_dry_certif_1),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getTimeToString(compCargoCalcDate1, compCargoCalcTime1),
                getResources().getString(R.string.variable_tank_move_completed_cargo_calc_1),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getTimeToString(labTestReleaseDate1, labTestReleaseTime1),
                getResources().getString(R.string.variable_tank_move_lab_test_released_1),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getTimeToString(commenceDisLoadDate, commenceDisLoadTime),
                getResources().getString(R.string.variable_tank_move_commence_dis_load),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getTimeToString(compDisLoadDate, compDisLoadTime),
                getResources().getString(R.string.variable_tank_move_completed_dis_load),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getTimeToString(compHoseDiscDate, compHoseDiscTime),
                getResources().getString(R.string.variable_tank_move_completed_hose),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getTimeToString(dryCertIssuedDate2, dryCertIssuedTime2),
                getResources().getString(R.string.variable_tank_move_dry_certif_2),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getTimeToString(compCargoCalcDate2, compCargoCalcTime2),
                getResources().getString(R.string.variable_tank_move_completed_cargo_calc_2),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getTimeToString(labTestReleaseDate2, labTestReleaseTime2),
                getResources().getString(R.string.variable_tank_move_lab_test_released_2),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getTimeToString(cargoDocumentDate, cargoDocumentTime),
                getResources().getString(R.string.cargo_document_on_board),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getTimeToString(portClearanceDate, portClearanceTime),
                getResources().getString(R.string.variable_tank_move_port_clearence),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getTimeToString(bookingPilotDate, bookingPilotTime),
                getResources().getString(R.string.variable_tank_move_book_pilot_unberthing),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getTimeToString(pilotOnUnberDate, pilotOnUnberTime),
                getResources().getString(R.string.variable_tank_move_pilot_onboard_unberthing),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getTimeToString(castOffDate, castOffTime),
                getResources().getString(R.string.variable_tank_move_cast_off),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getTimeToString(anchoredDate, anchoredTime),
                getResources().getString(R.string.variable_tank_move_anchoredOnBoard),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getTimeToString(atdDate, atdTime),
                getResources().getString(R.string.variable_tank_move_actual_time_departure),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getTimeToString(deliveryDate, deliveryTime),
                getResources().getString(R.string.variable_tank_move_delivery),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getTimeToString(redeliveryDate, redeliveryTime),
                getResources().getString(R.string.variable_tank_move_redelivery),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getTimeToString(onHireDate, onHireTime),
                getResources().getString(R.string.variable_tank_move_on_hire),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getTimeToString(offHireDate, offHireTime),
                getResources().getString(R.string.variable_tank_move_off_hire),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getTimeOnlyToString(timeOffToOn),
                getResources().getString(R.string.variable_tank_move_time_off_on),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDataIfAvailable(inputRemarks),
                getResources().getString(R.string.variable_tank_move_remarks),
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

    private void getCurrentData() {
        Calendar cal = Calendar.getInstance();
        Timestamp time = new Timestamp(cal.getTimeInMillis());
        movement = new TankerMovement(
                time,
                time,
                time,
                time,
                time,
                time,
                time,
                time,
                time,
                null,
                time,
                time,
                time,
                time,
                time,
                time,
                time,
                time,
                time,
                time,
                time,
                time,
                time,
                time,
                time,
                "Remarks"
        );
    }

    private boolean currentDataExist() {
        return false;
    }

    private void setInitialInput() {
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

    private void setDateAndTimeButton(final Button dateButton, final Button timeButton, Timestamp time) {
        if (time != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(time.getTime());

            if (dateButton != null) {
//                DateFormatSymbols symbols = new DateFormatSymbols();
//                String text = symbols.getMonths()[cal.get(Calendar.MONTH)] + " " + String.valueOf(cal.get(Calendar.YEAR));
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String text = format.format(new Date(cal.getTimeInMillis()));
                dateButton.setText(text);
            }

            if (timeButton != null) {
                Date date = new Date(cal.getTimeInMillis());
                SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
                timeButton.setText(format.format(date));
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
}
