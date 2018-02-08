package com.pertamina.pertaminatuban.marine.utils;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;

public class TankerMovementViewHolder extends RecyclerView.ViewHolder {

    public final TextView allFastDate, channelConnectionDate, dryCertIssuedDate1, compCargoCalcDate1,
            labTestReleaseDate1, commenceDisLoadDate, compDisLoadDate, compHoseDiscDate, dryCertIssuedDate2,
            compCargoCalcDate2, labTestReleaseDate2, cargoDocumentDate, portClearanceDate,
            bookingPilotDate, pilotOnUnberDate, castOffDate, anchoredDate, pilotOnDepartDate,
            anchorDepartDate, atdDate, deliveryDate, redeliveryDate, onHireDate, offHireDate, timeOffToOn,
            remarks, vessel;
    public final CardView card;

    public TankerMovementViewHolder(View itemView) {
        super(itemView);
        allFastDate = itemView.findViewById(R.id.item_tanker_movement_all_fast);
        channelConnectionDate = itemView.findViewById(R.id.item_tanker_movement_ch_conn);
        dryCertIssuedDate1 = itemView.findViewById(R.id.item_tanker_movement_dry_certif_1);
        compCargoCalcDate1 = itemView.findViewById(R.id.item_tanker_movement_comp_cargo_calc_1);
        labTestReleaseDate1 = itemView.findViewById(R.id.item_tanker_movement_lab_test_1);
        commenceDisLoadDate = itemView.findViewById(R.id.item_tanker_movement_comm_disload);
        compDisLoadDate = itemView.findViewById(R.id.item_tanker_movement_comp_disload);
        compHoseDiscDate = itemView.findViewById(R.id.item_tanker_movement_hose);
        dryCertIssuedDate2 = itemView.findViewById(R.id.item_tanker_movement_dry_certif_2);
        compCargoCalcDate2 = itemView.findViewById(R.id.item_tanker_movement_comp_cargo_calc_2);
        labTestReleaseDate2 = itemView.findViewById(R.id.item_tanker_movement_lab_test_2);
        cargoDocumentDate = itemView.findViewById(R.id.item_tanker_movement_cargo_document);
        portClearanceDate = itemView.findViewById(R.id.item_tanker_movement_port_clearence);
        bookingPilotDate = itemView.findViewById(R.id.item_tanker_movement_book_pilot_unberthing);
        pilotOnUnberDate = itemView.findViewById(R.id.item_tanker_movement_pilot_onboard_unberthing);
        castOffDate = itemView.findViewById(R.id.item_tanker_movement_cast_off);
        anchoredDate = itemView.findViewById(R.id.item_tanker_movement_anchored_rede);
        pilotOnDepartDate = itemView.findViewById(R.id.item_tanker_movement_pilot_onboard_departure);
        anchorDepartDate = itemView.findViewById(R.id.item_tanker_movement_anchor_departure);
        atdDate = itemView.findViewById(R.id.item_tanker_movement_actual_time_departure);
        deliveryDate = itemView.findViewById(R.id.item_tanker_movement_delivery);
        redeliveryDate = itemView.findViewById(R.id.item_tanker_movement_redelivery);
        onHireDate = itemView.findViewById(R.id.item_tanker_movement_on_hire);
        offHireDate = itemView.findViewById(R.id.item_tanker_movement_off_hire);
        timeOffToOn = itemView.findViewById(R.id.item_tanker_movement_off_to_on);
        remarks = itemView.findViewById(R.id.item_tanker_movement_remarks);
        vessel = itemView.findViewById(R.id.item_tanker_movement_vessel);
        card = itemView.findViewById(R.id.item_tanker_movement_card);
    }
}
