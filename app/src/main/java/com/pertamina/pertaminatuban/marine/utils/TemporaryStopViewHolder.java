package com.pertamina.pertaminatuban.marine.utils;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;

public class TemporaryStopViewHolder extends RecyclerView.ViewHolder {

    public final TextView vessel, reason1, stop1, resume1,
            reason2, stop2, resume2,
            reason3, stop3, resume3,
            reason4, stop4, resume4,
            reason5, stop5, resume5;
    public final CardView card;

    public TemporaryStopViewHolder(View itemView) {
        super(itemView);
        vessel = itemView.findViewById(R.id.item_temporary_stop_vessel);
        card = itemView.findViewById(R.id.item_temporary_stop_card);

        reason1 = itemView.findViewById(R.id.item_temporary_stop_reason1);
        stop1 = itemView.findViewById(R.id.item_temporary_stop_stop1);
        resume1 = itemView.findViewById(R.id.item_temporary_stop_resume1);

        reason2 = itemView.findViewById(R.id.item_temporary_stop_reason2);
        stop2 = itemView.findViewById(R.id.item_temporary_stop_stop2);
        resume2 = itemView.findViewById(R.id.item_temporary_stop_resume2);

        reason3 = itemView.findViewById(R.id.item_temporary_stop_reason3);
        stop3 = itemView.findViewById(R.id.item_temporary_stop_stop3);
        resume3 = itemView.findViewById(R.id.item_temporary_stop_resume3);

        reason4 = itemView.findViewById(R.id.item_temporary_stop_reason4);
        stop4 = itemView.findViewById(R.id.item_temporary_stop_stop4);
        resume4 = itemView.findViewById(R.id.item_temporary_stop_resume4);

        reason5 = itemView.findViewById(R.id.item_temporary_stop_reason5);
        stop5 = itemView.findViewById(R.id.item_temporary_stop_stop5);
        resume5 = itemView.findViewById(R.id.item_temporary_stop_resume5);
    }
}
