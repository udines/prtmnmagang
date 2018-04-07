package com.pertamina.pertaminatuban.qualityquantity.harian;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.qualityquantity.models.NewTestReportHeader;
import com.pertamina.pertaminatuban.qualityquantity.models.NewTestReportItem;
import com.pertamina.pertaminatuban.qualityquantity.utils.TestReportHasilAdapter;
import com.pertamina.pertaminatuban.qualityquantity.utils.TestReportHeaderAdapter;
import com.pertamina.pertaminatuban.service.QqClient;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TableTestReportActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String tanggal;
    private String fuel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_test_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.table_test_report_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        Intent intent = getIntent();
        tanggal = intent.getStringExtra("tanggal");
        fuel = intent.getStringExtra("fuel");

        getTable(tanggal, fuel);
    }

    private void getTable(String tanggal, String fuel) {
        SharedPreferences preferences = TableTestReportActivity.this.getSharedPreferences(
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

        Call<NewTestReportHeader> call = qqClient.getTestReportFuel(tanggal, fuel);
        call.enqueue(new Callback<NewTestReportHeader>() {
            @Override
            public void onResponse(Call<NewTestReportHeader> call, Response<NewTestReportHeader> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    NewTestReportHeader testReport = response.body();
                    ArrayList<NewTestReportItem> tables = testReport.getTable();
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    TestReportHasilAdapter adapter = new TestReportHasilAdapter(tables, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<NewTestReportHeader> call, Throwable t) {

            }
        });
    }

}
