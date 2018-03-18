package com.pertamina.pertaminatuban.qualityquantity.harian;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.EditText;
import android.widget.ImageView;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.qualityquantity.bulanan.WorkingLossActivity;
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

public class InputFotoSampleActivity extends AppCompatActivity {

    private ImageView imagePreview;
    private ArrayList<String> photoPaths;
    private EditText inputDeskripsi;
    private Button kirim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_foto_sample);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imagePreview = findViewById(R.id.input_foto_sampel_image);
        inputDeskripsi = findViewById(R.id.input_foto_sampel_deskripsi);
        kirim = findViewById(R.id.input_foto_sampel_kirim);

        handleUplodFoto();
        handleKirimFoto();
    }

    private void handleKirimFoto() {
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (photoPaths != null && inputDeskripsi.getText().length() > 0) {
                    String deskripsi = inputDeskripsi.getText().toString();
                    String path = photoPaths.get(0);
                    sendFileRequest(path, deskripsi);
                }
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

    private void sendFileRequest(final String filePath, String deskripsi) {

        final File file = new File(filePath);
        Log.w("file path", filePath);

        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        Log.w("reqFile", reqFile.toString());
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), reqFile);
        Log.w("MultipartBody", body.toString());
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), deskripsi);
        Log.w("RequestBody", name.toString());

        SharedPreferences preferences = InputFotoSampleActivity.this.getSharedPreferences(
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
                        .header("Upload-Content-Type", getMimeType(filePath))
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

        Call<Object> call = qqClient.postFotoSample(body, name);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.w("response code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    NavUtils.navigateUpTo(InputFotoSampleActivity.this, new Intent(
                            getApplicationContext(),
                            FotoSampleActivity.class
                    ));
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
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
        if (photoPaths != null) {
            if (photoPaths.get(0) != null) {
                if (Uri.parse(photoPaths.get(0)) != null) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inScaled = true;
                    Bitmap bitmap = BitmapFactory.decodeFile(photoPaths.get(0), options);
                    imagePreview.setImageBitmap(bitmap);
//                    imagePreview.setImageURI(Uri.parse(photoPaths.get(0)));
                }
            }
        }
    }

}
