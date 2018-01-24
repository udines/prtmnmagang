package com.pertamina.pertaminatuban.distribusi;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.models.Matbal;
import com.pertamina.pertaminatuban.distribusi.page.MatbalPage;
import com.pertamina.pertaminatuban.service.UserClient;
import com.pertamina.pertaminatuban.utils.ViewPagerAdapter;

import java.io.IOException;
import java.sql.Date;
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

public class MatbalActivity extends AppCompatActivity {

    private TextView tanggal;
    private LinearLayout tanggalButton;
    private int month;
    private int year;
    private int day;
    private TextView pertamax, pertalite, premium, solar, biosolar, bioflame, grandTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matbal);
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
        tanggal = findViewById(R.id.matbal_tanggal);
        tanggalButton = findViewById(R.id.matbal_tanggal_button);
        pertamax = findViewById(R.id.matbal_nilai_pertamax);
        pertalite = findViewById(R.id.matbal_nilai_pertalite);
        premium = findViewById(R.id.matbal_nilai_premium);
        solar = findViewById(R.id.matbal_nilai_solar);
        biosolar = findViewById(R.id.matbal_nilai_biosolar);
        bioflame = findViewById(R.id.matbal_nilai_bioflame);
        grandTotal = findViewById(R.id.matbal_nilai_total);

        /*inisialisasi tanggal jika data tidak ada agar tidak error*/
        Calendar calendar = Calendar.getInstance();
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        setDateButton(day, month, year);

        /*get data. jika data tidak ada maka tampilkan tulisan jika kosong. jika ada
        * maka tampilkan tab beserta isinya*/
        getMatbal(month);

        /*handle tanggal untuk mengubah data berdasarkan bulan*/
        handleDate();
    }

    private void cekData(ArrayList<Matbal> matbals) {

        /*cek apakah data ada atau tidak*/
        if (matbals != null && matbals.size() > 0) {

            Log.d("data", "data matbal ada");
            /*data ada maka tampilkan tab dan isinya*/
            populateData(matbals);
        } else {

            Log.d("data", "data matbal tidak ada");
            /*data tidak ada maka hilangkan tab dan tampilkan pesan peringatan
            * untuk tanggal gunakan month dan year yang sudah diinisialisasi*/

        }
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
                        getMatbal(month);
                    }
                };

                DatePickerDialog dialog = new DatePickerDialog(
                        MatbalActivity.this,
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

    private void populateData(ArrayList<Matbal> matbals) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        java.util.Date date = new Date(calendar.getTimeInMillis());
        Log.w("date", date.toString());
        String today = date.toString();

        ArrayList<Matbal> matbalToday = new ArrayList<>();
        float total = 0;
        for (int i = 0; i < matbals.size(); i++) {
            if (matbals.get(i).getDate().equals(today)) {
                matbalToday.add(matbals.get(i));
                total = total + matbals.get(i).getNilai();
            }
        }
        grandTotal.setText(String.valueOf(total + " KL"));

        for (int i = 0; i < matbalToday.size(); i++) {
            switch (matbalToday.get(i).getFuel()) {
                case Matbal.PERTAMAX:
                    pertamax.setText(String.valueOf(matbalToday.get(i).getNilai() + " KL"));
                    break;
                case Matbal.PERTALITE:
                    pertalite.setText(String.valueOf(matbalToday.get(i).getNilai() + " KL"));
                    break;
                case Matbal.PREMIUM:
                    premium.setText(String.valueOf(matbalToday.get(i).getNilai() + " KL"));
                    break;
                case Matbal.SOLAR:
                    solar.setText(String.valueOf(matbalToday.get(i).getNilai() + " KL"));
                    break;
                case Matbal.BIOSOLAR:
                    biosolar.setText(String.valueOf(matbalToday.get(i).getNilai() + " KL"));
                    break;
                case Matbal.BIOFLAME:
                    bioflame.setText(String.valueOf(matbalToday.get(i).getNilai() + " KL"));
                    break;
            }
        }
    }

    private void getMatbal(int bulan) {

        SharedPreferences preferences = MatbalActivity.this.getSharedPreferences(
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
        Call<ArrayList<Matbal>> call = userClient.getMatbal(bulan + 1);

        /*call.enqueue(new Callback<ArrayList<Matbal>>() {
            @Override
            public void onResponse(Call<ArrayList<Matbal>> call, Response<ArrayList<Matbal>> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200 && response.body() != null) {
                    Log.w("size", String.valueOf(response.body().size()));
                    cekData(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Matbal>> call, Throwable t) {
                Log.e("Call", " failed " + t.getMessage());
            }
        });*/
        /*SAMPLE DATA*/
        ArrayList<Matbal> matbalFeb = new ArrayList<>();
/*pertamax*/
        matbalFeb.add(new Matbal("2017-02-01", Matbal.PERTAMAX, 344));
        matbalFeb.add(new Matbal("2017-02-02", Matbal.PERTAMAX, 392));
        matbalFeb.add(new Matbal("2017-02-03", Matbal.PERTAMAX, 240));
        matbalFeb.add(new Matbal("2017-02-04", Matbal.PERTAMAX, 344));
        matbalFeb.add(new Matbal("2017-02-05", Matbal.PERTAMAX, 256));
        matbalFeb.add(new Matbal("2017-02-06", Matbal.PERTAMAX, 268));
        matbalFeb.add(new Matbal("2017-02-07", Matbal.PERTAMAX, 516));
        matbalFeb.add(new Matbal("2017-02-08", Matbal.PERTAMAX, 316));
        matbalFeb.add(new Matbal("2017-02-09", Matbal.PERTAMAX, 376));
        matbalFeb.add(new Matbal("2017-02-10", Matbal.PERTAMAX, 440));
        matbalFeb.add(new Matbal("2017-02-11", Matbal.PERTAMAX, 324));
        matbalFeb.add(new Matbal("2017-02-12", Matbal.PERTAMAX, 284));
        matbalFeb.add(new Matbal("2017-02-13", Matbal.PERTAMAX, 264));
        matbalFeb.add(new Matbal("2017-02-14", Matbal.PERTAMAX, 424));
        matbalFeb.add(new Matbal("2017-02-15", Matbal.PERTAMAX, 400));
/*pertalite*/
        matbalFeb.add(new Matbal("2017-02-01", Matbal.PERTALITE, 360));
        matbalFeb.add(new Matbal("2017-02-02", Matbal.PERTALITE, 272));
        matbalFeb.add(new Matbal("2017-02-03", Matbal.PERTALITE, 264));
        matbalFeb.add(new Matbal("2017-02-04", Matbal.PERTALITE, 232));
        matbalFeb.add(new Matbal("2017-02-05", Matbal.PERTALITE, 208));
        matbalFeb.add(new Matbal("2017-02-06", Matbal.PERTALITE, 232));
        matbalFeb.add(new Matbal("2017-02-07", Matbal.PERTALITE, 416));
        matbalFeb.add(new Matbal("2017-02-08", Matbal.PERTALITE, 272));
        matbalFeb.add(new Matbal("2017-02-09", Matbal.PERTALITE, 280));
        matbalFeb.add(new Matbal("2017-02-10", Matbal.PERTALITE, 232));
        matbalFeb.add(new Matbal("2017-02-11", Matbal.PERTALITE, 368));
        matbalFeb.add(new Matbal("2017-02-12", Matbal.PERTALITE, 216));
        matbalFeb.add(new Matbal("2017-02-13", Matbal.PERTALITE, 192));
        matbalFeb.add(new Matbal("2017-02-14", Matbal.PERTALITE, 368));
        matbalFeb.add(new Matbal("2017-02-15", Matbal.PERTALITE, 296));
/*biosolar*/
        matbalFeb.add(new Matbal("2017-02-01", Matbal.BIOSOLAR, 904));
        matbalFeb.add(new Matbal("2017-02-02", Matbal.BIOSOLAR, 928));
        matbalFeb.add(new Matbal("2017-02-03", Matbal.BIOSOLAR, 936));
        matbalFeb.add(new Matbal("2017-02-04", Matbal.BIOSOLAR, 992));
        matbalFeb.add(new Matbal("2017-02-05", Matbal.BIOSOLAR, 600));
        matbalFeb.add(new Matbal("2017-02-06", Matbal.BIOSOLAR, 632));
        matbalFeb.add(new Matbal("2017-02-07", Matbal.BIOSOLAR, 904));
        matbalFeb.add(new Matbal("2017-02-08", Matbal.BIOSOLAR, 1008));
        matbalFeb.add(new Matbal("2017-02-09", Matbal.BIOSOLAR, 800));
        matbalFeb.add(new Matbal("2017-02-10", Matbal.BIOSOLAR, 840));
        matbalFeb.add(new Matbal("2017-02-11", Matbal.BIOSOLAR, 912));
        matbalFeb.add(new Matbal("2017-02-12", Matbal.BIOSOLAR, 672));
        matbalFeb.add(new Matbal("2017-02-13", Matbal.BIOSOLAR, 752));
        matbalFeb.add(new Matbal("2017-02-14", Matbal.BIOSOLAR, 1096));
        matbalFeb.add(new Matbal("2017-02-15", Matbal.BIOSOLAR, 1032));
/*solar*/
        matbalFeb.add(new Matbal("2017-02-01", Matbal.SOLAR, 723));
        matbalFeb.add(new Matbal("2017-02-02", Matbal.SOLAR, 742));
        matbalFeb.add(new Matbal("2017-02-03", Matbal.SOLAR, 756));
        matbalFeb.add(new Matbal("2017-02-04", Matbal.SOLAR, 809));
        matbalFeb.add(new Matbal("2017-02-05", Matbal.SOLAR, 480));
        matbalFeb.add(new Matbal("2017-02-06", Matbal.SOLAR, 513));
        matbalFeb.add(new Matbal("2017-02-07", Matbal.SOLAR, 835));
        matbalFeb.add(new Matbal("2017-02-08", Matbal.SOLAR, 814));
        matbalFeb.add(new Matbal("2017-02-09", Matbal.SOLAR, 648));
        matbalFeb.add(new Matbal("2017-02-10", Matbal.SOLAR, 672));
        matbalFeb.add(new Matbal("2017-02-11", Matbal.SOLAR, 737));
        matbalFeb.add(new Matbal("2017-02-12", Matbal.SOLAR, 537));
        matbalFeb.add(new Matbal("2017-02-13", Matbal.SOLAR, 609));
        matbalFeb.add(new Matbal("2017-02-14", Matbal.SOLAR, 972));
        matbalFeb.add(new Matbal("2017-02-15", Matbal.SOLAR, 849));
        cekData(matbalFeb);
    }
}
