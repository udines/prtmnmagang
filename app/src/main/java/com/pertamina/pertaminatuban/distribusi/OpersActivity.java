package com.pertamina.pertaminatuban.distribusi;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.models.Opers;
import com.pertamina.pertaminatuban.distribusi.page.OpersPage;
import com.pertamina.pertaminatuban.service.UserClient;
import com.pertamina.pertaminatuban.utils.ViewPagerAdapter;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OpersActivity extends AppCompatActivity {

    private int month, year, day;
    private TextView tanggal;
    private LinearLayout tanggalButton;
    private TextView sum, minJam, maxJam, jamOps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*inisialisasi view yang digunakan lebih dari satu fungsi*/
        tanggal = findViewById(R.id.opers_tanggal);
        tanggalButton = findViewById(R.id.opers_tanggal_button);
        sum = findViewById(R.id.opers_jumlah_keluar);
        minJam = findViewById(R.id.opers_jam_keluar_min);
        maxJam = findViewById(R.id.opers_jam_keluar_max);
        jamOps = findViewById(R.id.opers_jam_operasional);

        /*inisialisasi tanggal jika data tidak ada agar tidak error*/
        Calendar calendar = Calendar.getInstance();
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        setDateButton(day, month, year);

        getOpers(month);

        /*handle tanggal untuk mengubah data berdasarkan bulan*/
        handleDate();
    }

    private void handleDate() {
        tanggalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        year = i;
                        month = i1;
                        day = i2;
                        setDateButton(day, month, year);
                        getOpers(month);
                    }
                };
                DatePickerDialog dialog = new DatePickerDialog(
                        OpersActivity.this,
                        listener,
                        year,
                        month,
                        day
                );
                dialog.show();
            }
        });
    }

    private void setDateButton(int day, int month, int year) {
        DateFormatSymbols symbols = new DateFormatSymbols();
        String text = String.valueOf(day) + " " + symbols.getMonths()[month] + " " + String.valueOf(year);
        tanggal.setText(text);
    }

    private void getOpers(int bulan) {

        SharedPreferences preferences = OpersActivity.this.getSharedPreferences(
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

        Log.w("GET ", "start getting matbal bulan " + bulan);
        String baseUrl = "http://www.api.clicktuban.com/";
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client);

        Retrofit retrofit = builder.build();
        UserClient userClient = retrofit.create(UserClient.class);
        Call<ArrayList<Opers>> call = userClient.getOpers(bulan);

        /*call.enqueue(new Callback<ArrayList<Opers>>() {
            @Override
            public void onResponse(Call<ArrayList<Opers>> call, Response<ArrayList<Opers>> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200 && response.body() != null) {
                    Log.w("size", String.valueOf(response.body().size()));
                    cekData(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Opers>> call, Throwable t) {
                Log.e("Call", " failed " + t.getMessage());
            }
        });*/

        Calendar calendar = Calendar.getInstance();
        Calendar calendar1 = Calendar.getInstance();
        calendar.set(2018, 0, 24, 4, 49);
        calendar1.set(2018, 0, 24, 16, 44);
        Opers opers = new Opers(
                new Date(calendar.getTimeInMillis()).toString(),
                1608,
                new Time(calendar.getTimeInMillis()),
                new Time(calendar1.getTimeInMillis()),
                new Time(calendar1.getTimeInMillis() - calendar.getTimeInMillis())
        );
        ArrayList<Opers> opers1 = new ArrayList<>();
        opers1.add(opers);
        cekData(opers1);
    }

    private void cekData(ArrayList<Opers> opers) {

        /*cek apakah data ada atau tidak*/
        if (opers != null && opers.size() > 0) {

            Log.d("data", "data matbal ada");
            /*data ada maka tampilkan tab dan isinya*/
            populateData(opers);
        } else {

            Log.d("data", "data matbal tidak ada");
            /*data tidak ada maka hilangkan tab dan tampilkan pesan peringatan
            * untuk tanggal gunakan month dan year yang sudah diinisialisasi*/
        }
    }

    private void populateData(ArrayList<Opers> opers) {
        Calendar calendar = Calendar.getInstance();
        Log.w("tanggal", String.valueOf(day));
        calendar.set(year, month, day);
        String today = new Date(calendar.getTimeInMillis()).toString();
        for (int i = 0; i < opers.size(); i++) {
            if (opers.get(i).getDate().equals(today)) {
                sum.setText(String.valueOf(opers.get(i).getJumlahKeluar()));
                minJam.setText(opers.get(i).getMinJamKeluar().toString());
                maxJam.setText(opers.get(i).getMaxJamKeluar().toString());
                jamOps.setText(opers.get(i).getJamOperasional().toString());
                break;
            } else {
                sum.setText(String.valueOf(0));
                minJam.setText(String.valueOf("00:00"));
                maxJam.setText(String.valueOf("00:00"));
                jamOps.setText(String.valueOf("00:00"));
            }
        }
    }
}
