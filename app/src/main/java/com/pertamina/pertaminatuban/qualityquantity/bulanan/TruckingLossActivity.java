package com.pertamina.pertaminatuban.qualityquantity.bulanan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.qualityquantity.UploadFileActivity;
import com.pertamina.pertaminatuban.qualityquantity.harian.TestReportAdapter;
import com.pertamina.pertaminatuban.qualityquantity.models.ItemTestReport;
import com.pertamina.pertaminatuban.service.QqClient;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TruckingLossActivity extends AppCompatActivity {

    private TextView tahun;
    private RecyclerView recyclerView;
    private int year;
    private Button upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trucking_loss);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tahun = findViewById(R.id.trucking_loss_tahun);
        recyclerView = findViewById(R.id.trucking_loss_recyclerview);
        upload = findViewById(R.id.trucking_loss_upload_button);

        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        setYearButton(year);

        handleTahunButton();
        updateUi(year);
        handleUploadButton();
    }

    private void handleUploadButton() {
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent uploadIntent = new Intent(getApplicationContext(), UploadFileActivity.class);
                uploadIntent.putExtra("type", ItemTestReport.TYPE_TRUCKING_LOSS);
                uploadIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(uploadIntent);
            }
        });
    }

    private void handleTahunButton() {
        tahun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar today = Calendar.getInstance();

                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(
                        TruckingLossActivity.this,
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) {
                                year = selectedYear;
                                setYearButton(year);
                                updateUi(year);
                            }
                        },
                        today.get(Calendar.YEAR),
                        today.get(Calendar.MONTH)
                );

                builder.setMinYear(1970)
                        .setMaxYear(today.get(Calendar.YEAR))
                        .showYearOnly()
                        .setActivatedYear(year)
                        .build()
                        .show();
            }
        });
    }

    private void updateUi(int year) {
        SharedPreferences preferences = TruckingLossActivity.this.getSharedPreferences(
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
        QqClient qqClient = retrofit.create(QqClient.class);

        Call<ArrayList<ItemTestReport>> call = qqClient.getTruckingLossTahun(String.valueOf(year));
        call.enqueue(new Callback<ArrayList<ItemTestReport>>() {
            @Override
            public void onResponse(Call<ArrayList<ItemTestReport>> call, Response<ArrayList<ItemTestReport>> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    ArrayList<ItemTestReport> truckingLosses = new ArrayList<>();
                    for (int i = 0; i < response.body().size(); i++) {
                        if (response.body().get(i).getType().equals(ItemTestReport.TYPE_TRUCKING_LOSS)) {
                            truckingLosses.add(response.body().get(i));
                        }
                    }
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(new TestReportAdapter(truckingLosses));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ItemTestReport>> call, Throwable t) {

            }
        });
    }

    private void setYearButton(int year) {
        tahun.setText(String.valueOf(year));
    }
}
