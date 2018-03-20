package com.pertamina.pertaminatuban.qualityquantity.harian;

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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.qualityquantity.UploadFileActivity;
import com.pertamina.pertaminatuban.qualityquantity.bulanan.TruckingLossActivity;
import com.pertamina.pertaminatuban.qualityquantity.models.ItemTestReport;
import com.pertamina.pertaminatuban.service.QqClient;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.io.IOException;
import java.text.DateFormatSymbols;
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

public class TestReportActivity extends AppCompatActivity {

    private TextView bulan;
    private RecyclerView recyclerView;
    private int year, month;
    private Button upload;
    private TextView emptyText;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bulan = findViewById(R.id.test_report_bulan);
        recyclerView = findViewById(R.id.test_report_recyclerview);
        upload = findViewById(R.id.test_report_upload_button);
        progressBar = findViewById(R.id.test_report_progressbar);
        emptyText = findViewById(R.id.test_report_empty_text);

        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        setMonthButton(month, year);

        handleBulanButton();
        updateUi(month, year);
        handleUploadButton();
    }

    private void handleUploadButton() {
        SharedPreferences preferences = this.getSharedPreferences(
                "login",
                Context.MODE_PRIVATE
        );
        String role = preferences.getString("userRole", "none");
        if (role.equals("qq") || role.equals("admin")) {
            upload.setVisibility(View.VISIBLE);
        } else {
            upload.setVisibility(View.GONE);
        }
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent uploadIntent = new Intent(getApplicationContext(), InputTestReportActivity.class);
                uploadIntent.putExtra("type", ItemTestReport.TYPE_TEST_REPORT);
                uploadIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(uploadIntent);
            }
        });
    }

    private void handleBulanButton() {
        bulan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar today = Calendar.getInstance();

                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(
                        TestReportActivity.this,
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) {
                                month = selectedMonth;
                                year = selectedYear;
                                setMonthButton(month, year);
                                updateUi(month, year);
                            }
                        },
                        today.get(Calendar.YEAR),
                        today.get(Calendar.MONTH)
                );

                builder.setMinYear(1970)
                        .setMaxYear(today.get(Calendar.YEAR))
                        .setTitle("Pilih bulan dan tahun")
                        .setActivatedMonth(month)
                        .setActivatedYear(year)
                        .build()
                        .show();
            }
        });
    }

    private void updateUi(int month, int year) {
        progressBar.setVisibility(View.VISIBLE);

        SharedPreferences preferences = TestReportActivity.this.getSharedPreferences(
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

        Call<ArrayList<ItemTestReport>> call = qqClient.getTestReportBulan(
                String.valueOf(year),
                String.valueOf(month + 1));
        call.enqueue(new Callback<ArrayList<ItemTestReport>>() {
            @Override
            public void onResponse(Call<ArrayList<ItemTestReport>> call, Response<ArrayList<ItemTestReport>> response) {
                Log.w("code", String.valueOf(response.code()));
                progressBar.setVisibility(View.GONE);
                if (response.code() == 200) {
                    ArrayList<ItemTestReport> testReports = new ArrayList<>();
                    for (int i = 0; i < response.body().size(); i++) {
                        if (response.body().get(i).getType().equals(ItemTestReport.TYPE_TEST_REPORT)) {
                            testReports.add(response.body().get(i));
                        }
                    }
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    TestReportAdapter adapter = new TestReportAdapter(testReports);
                    recyclerView.setAdapter(adapter);

                    if (adapter.getItemCount() <= 0) {
                        emptyText.setVisibility(View.VISIBLE);
                    } else {
                        emptyText.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ItemTestReport>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void setMonthButton(int month, int year) {
        DateFormatSymbols symbols = new DateFormatSymbols();
        String text = symbols.getMonths()[month] + " " + String.valueOf(year);
        bulan.setText(text);
    }
}
