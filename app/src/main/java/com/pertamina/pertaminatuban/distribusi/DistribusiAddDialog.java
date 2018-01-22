package com.pertamina.pertaminatuban.distribusi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;

public class DistribusiAddDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_distribusi_add, null);

        TextView matbal, konsumen, wilayah, ritase, opers;
        matbal = view.findViewById(R.id.dialog_distribusi_matbal);
        konsumen = view.findViewById(R.id.dialog_distribusi_konsumen);
        wilayah = view.findViewById(R.id.dialog_distribusi_wilayah);
        ritase = view.findViewById(R.id.dialog_distribusi_ritase);
        opers = view.findViewById(R.id.dialog_distribusi_opers);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle("Tambah Data")
                .setView(view);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                switch (view.getId()) {
                    case R.id.dialog_distribusi_matbal:
                        intent.setClass(getContext(), InputMatbalActivity.class);
                        break;
                    case R.id.dialog_distribusi_konsumen:
                        break;
                    case R.id.dialog_distribusi_wilayah:
                        break;
                    case R.id.dialog_distribusi_ritase:
                        break;
                    case R.id.dialog_distribusi_opers:
                        break;
                }
                startActivity(intent);
            }
        };

        matbal.setOnClickListener(listener);
        konsumen.setOnClickListener(listener);
        wilayah.setOnClickListener(listener);
        opers.setOnClickListener(listener);
        ritase.setOnClickListener(listener);

        return builder.create();
    }
}
