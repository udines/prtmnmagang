package com.pertamina.pertaminatuban.qualityquantity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.qualityquantity.bulanan.TruckingLossActivity;
import com.pertamina.pertaminatuban.qualityquantity.harian.FotoSampleActivity;
import com.pertamina.pertaminatuban.qualityquantity.harian.InputFotoSampleActivity;
import com.pertamina.pertaminatuban.qualityquantity.harian.TestReportActivity;
import com.pertamina.pertaminatuban.qualityquantity.models.ItemTestReport;
import com.pertamina.pertaminatuban.service.QqClient;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UploadFileActivity extends AppCompatActivity {

    private TextView fileName;
    private Button chooseFile, kirim;
    private ArrayList<String> filePaths;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_file);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fileName = findViewById(R.id.upload_file_name);
        chooseFile = findViewById(R.id.upload_file_choose);
        kirim = findViewById(R.id.upload_file_kirim);
        progressBar = findViewById(R.id.upload_file_progress);
        kirim.setVisibility(View.GONE);

        handleChooseFile();
        handleKirim();
    }

    private void handleChooseFile() {
        chooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Log.v("storage","Permission is granted");
                        //File write logic here
                        FilePickerBuilder.getInstance().setMaxCount(1)
                                .setSelectedFiles(filePaths)
                                .pickFile(UploadFileActivity.this);
                    } else {
                        ActivityCompat.requestPermissions(UploadFileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
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
                    .pickFile(UploadFileActivity.this);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode)
        {
            case FilePickerConst.REQUEST_CODE_DOC:
                if(resultCode== Activity.RESULT_OK && data!=null)
                {
                    filePaths = new ArrayList<>();
                    filePaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS));
                }
                break;
        }
        addThemToView(filePaths);
    }

    private void addThemToView(ArrayList<String> path) {
        if (path != null) {
            String name = Uri.fromFile(new File(path.get(0))).getLastPathSegment();
            fileName.setText(name);
        } else {
            fileName.setText("");
        }
        handleKirim();
    }

    private void handleKirim() {
        kirim.setVisibility(View.GONE);
        if (fileName.getText().length() > 0) {
            kirim.setVisibility(View.VISIBLE);
        }
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kirim.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                String type = getIntent().getStringExtra("type");
                String path = filePaths.get(0);
                sendFileRequest(path, type);
            }
        });
    }

    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        Log.w("file type", type);
        return type;
    }

    private void sendFileRequest(final String path, String type) {
        final File file = new File(path);
        Log.w("file path", path);

        RequestBody reqFile = RequestBody.create(
                MediaType.parse("pdf"),
                file
        );
        Log.w("reqFile", reqFile.toString());

        MultipartBody.Part body = MultipartBody.Part.createFormData("pdf", file.getName(), reqFile);
        Log.w("MultipartBody", body.toString());

        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), type);
        Log.w("RequestBody", name.toString());

        RequestBody deskripsi = RequestBody.create(MediaType.parse("text/plain"), type);
        Log.w("RequestBody", deskripsi.toString());

        SharedPreferences preferences = UploadFileActivity.this.getSharedPreferences(
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
                        .header("Content-Type", "application/octet-stream")
                        .header("Upload-Content-Type", getMimeType(path))
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
        QqClient qqClient = retrofit.create(QqClient.class);

        Call<Object> call = qqClient.postFilePdf(
                body,
                name,
                deskripsi
        );
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.w("response code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    kirim.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                    Intent intent = new Intent();
                    switch (getIntent().getStringExtra("type")) {
                        case ItemTestReport.TYPE_TEST_REPORT:
                            intent.setClass(getApplicationContext(), TestReportActivity.class);
                            break;
                        case ItemTestReport.TYPE_TRUCKING_LOSS:
                            intent.setClass(getApplicationContext(), TruckingLossActivity.class);
                            break;
                    }
                    NavUtils.navigateUpTo(UploadFileActivity.this, intent);
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(UploadFileActivity.this, "Gagal upload file", Toast.LENGTH_SHORT).show();
                kirim.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

}
