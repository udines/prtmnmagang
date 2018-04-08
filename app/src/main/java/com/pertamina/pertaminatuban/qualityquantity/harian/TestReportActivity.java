package com.pertamina.pertaminatuban.qualityquantity.harian;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.qualityquantity.UploadFileActivity;
import com.pertamina.pertaminatuban.qualityquantity.models.ItemTestReport;
import com.pertamina.pertaminatuban.qualityquantity.models.NewTestReportHeader;
import com.pertamina.pertaminatuban.qualityquantity.models.TestReport;
import com.pertamina.pertaminatuban.qualityquantity.utils.TestReportHeaderAdapter;
import com.pertamina.pertaminatuban.service.QqClient;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestReportActivity extends AppCompatActivity {

    private TextView tanggal;
    private RecyclerView recyclerView;
    private int year, month, day;
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

        tanggal = findViewById(R.id.test_report_bulan);
        recyclerView = findViewById(R.id.test_report_recyclerview);
        upload = findViewById(R.id.test_report_upload_button);
        progressBar = findViewById(R.id.test_report_progressbar);
        emptyText = findViewById(R.id.test_report_empty_text);

        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        setTanggalButton(day, month, year);

        handleBulanButton();
        updateUi(day, month, year);
        handleUploadButton();
    }

    private void handleUploadButton() {
        SharedPreferences preferences = this.getSharedPreferences(
                "login",
                Context.MODE_PRIVATE
        );
        String role = preferences.getString("userRole", "none");
        if (role.equals("qq") || role.equals("admin")) {
            upload.setVisibility(View.GONE);
        } else {
            upload.setVisibility(View.GONE);
        }
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent uploadIntent = new Intent(getApplicationContext(), UploadFileActivity.class);
                uploadIntent.putExtra("type", ItemTestReport.TYPE_TEST_REPORT);
                uploadIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(uploadIntent);
            }
        });
    }

    private void handleBulanButton() {
        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        year = i;
                        month = i1;
                        day = i2;
                        setTanggalButton(day, month, year);
                        updateUi(day, month, year);
                    }
                };

                DatePickerDialog dialog = new DatePickerDialog(
                        TestReportActivity.this,
                        listener,
                        year,
                        month,
                        day
                );

                dialog.show();
            }
        });
    }

    private void updateUi(int day, int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String tanggal = format.format(new Date(cal.getTimeInMillis()));

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

        Call<ArrayList<NewTestReportHeader>> call = qqClient.getTestReportHari(tanggal);
        call.enqueue(new Callback<ArrayList<NewTestReportHeader>>() {
            @Override
            public void onResponse(Call<ArrayList<NewTestReportHeader>> call, Response<ArrayList<NewTestReportHeader>> response) {
                Log.w("code", String.valueOf(response.code()));
                progressBar.setVisibility(View.GONE);
                if (response.code() == 200) {
                    ArrayList<NewTestReportHeader> testReports = response.body();

                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    TestReportHeaderAdapter adapter = new TestReportHeaderAdapter(testReports, getApplicationContext());
                    recyclerView.setAdapter(adapter);

                    if (adapter.getItemCount() <= 0) {
                        emptyText.setVisibility(View.VISIBLE);
                    } else {
                        emptyText.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<NewTestReportHeader>> call, Throwable t) {

            }
        });
    }

    private void setTanggalButton(int day, int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        String text = format.format(new Date(cal.getTimeInMillis()));
        tanggal.setText(text);
    }
}
