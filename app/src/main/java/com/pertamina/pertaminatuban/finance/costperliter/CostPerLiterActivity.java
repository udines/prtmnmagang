package com.pertamina.pertaminatuban.finance.costperliter;

import android.content.Context;
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
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.MatbalActivity;
import com.pertamina.pertaminatuban.finance.models.CostPerLiter;
import com.pertamina.pertaminatuban.service.FinanceClient;
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

public class CostPerLiterActivity extends AppCompatActivity {

    private TextView tahun;
    private RecyclerView recyclerView;
    private int year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost_per_liter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tahun = findViewById(R.id.cost_per_liter_tahun);
        recyclerView = findViewById(R.id.cost_per_liter_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        setYearButton(year);

        handleTahunButton();
        getCplTahun(year);
    }

    private void getCplTahun(int year) {
        SharedPreferences preferences = CostPerLiterActivity.this.getSharedPreferences(
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
        FinanceClient financeClient = retrofit.create(FinanceClient.class);
        Call<ArrayList<CostPerLiter>> call = financeClient.getCpl(
                String.valueOf(year)
        );
        call.enqueue(new Callback<ArrayList<CostPerLiter>>() {
            @Override
            public void onResponse(Call<ArrayList<CostPerLiter>> call, Response<ArrayList<CostPerLiter>> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    recyclerView.setAdapter(new CplAdapter(response.body(), getApplicationContext()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CostPerLiter>> call, Throwable t) {

            }
        });
    }

    private void handleTahunButton() {
        tahun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar today = Calendar.getInstance();

                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(
                        CostPerLiterActivity.this,
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) {
                                year = selectedYear;
                                setYearButton(year);
                                getCplTahun(year);
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

    private void setYearButton(int year) {
        tahun.setText(String.valueOf(year));
    }

}
