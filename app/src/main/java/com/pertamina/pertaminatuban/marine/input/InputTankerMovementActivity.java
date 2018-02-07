package com.pertamina.pertaminatuban.marine.input;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.marine.models.TankerMovement;

import java.sql.Timestamp;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
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
                time,
                "Remarks"
        );
    }

    private boolean currentDataExist() {
        return true;
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

    private void setDateAndTimeButton(Button dateButton, Button timeButton, Timestamp time) {
        if (time != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(time.getTime());

            if (dateButton != null) {
                DateFormatSymbols symbols = new DateFormatSymbols();
                String text = symbols.getMonths()[cal.get(Calendar.MONTH)] + " " + String.valueOf(cal.get(Calendar.YEAR));
                dateButton.setText(text);
            }

            if (timeButton != null) {
                Date date = new Date(cal.getTimeInMillis());
                SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
                timeButton.setText(format.format(date));
            }

        }
    }
}
