package com.pertamina.pertaminatuban.qualityquantity.harian;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.pertamina.pertaminatuban.R;

import java.util.ArrayList;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;

public class InputFotoSampleActivity extends AppCompatActivity {

    private ImageView imagePreview;
    private ArrayList<String> photoPaths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_foto_sample);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imagePreview = findViewById(R.id.input_foto_sampel_image);

        handleUplodFoto();
    }

    private void handleUplodFoto() {
        Button button = findViewById(R.id.input_foto_sampel_pilih_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Log.v("storage","Permission is granted");
                        //File write logic here
                        FilePickerBuilder.getInstance().setMaxCount(1)
                                .setSelectedFiles(photoPaths)
                                .pickPhoto(InputFotoSampleActivity.this);
                    } else {
                        ActivityCompat.requestPermissions(InputFotoSampleActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    }
                }

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.v("Storage","Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
            FilePickerBuilder.getInstance().setMaxCount(1)
                    .setSelectedFiles(new ArrayList<String>())
                    .pickPhoto(InputFotoSampleActivity.this);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode)
        {
            case FilePickerConst.REQUEST_CODE_PHOTO:
                if(resultCode== Activity.RESULT_OK && data!=null)
                {
                    photoPaths = new ArrayList<>();
                    photoPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA));
                }
                break;
        }
        addThemToView(photoPaths);
    }

    private void addThemToView(ArrayList<String> photoPaths) {
        imagePreview.setImageURI(Uri.parse(photoPaths.get(0)));
    }

}
