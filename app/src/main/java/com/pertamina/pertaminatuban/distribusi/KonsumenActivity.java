package com.pertamina.pertaminatuban.distribusi;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.models.Konsumen;
import com.pertamina.pertaminatuban.distribusi.models.Matbal;
import com.pertamina.pertaminatuban.distribusi.tables.KonsumenContainerAdapter;
import com.pertamina.pertaminatuban.service.UserClient;
import com.whiteelephant.monthpicker.MonthPickerDialog;

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

public class KonsumenActivity extends AppCompatActivity {

    private TextView tanggal, bulan, tahun, emptyText;
    private LinearLayout tanggalButton, bulanButton, tahunButton;
    private RecyclerView recyclerView;
    private int month;
    private int year;
    private int day;

    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konsumen);
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
        tanggal = findViewById(R.id.konsumen_tanggal);
        bulan = findViewById(R.id.konsumen_bulan);
        tahun = findViewById(R.id.konsumen_tahun);
        tanggalButton = findViewById(R.id.konsumen_tanggal_button);
        bulanButton = findViewById(R.id.konsumen_bulan_button);
        tahunButton = findViewById(R.id.konsumen_tahun_button);
        recyclerView = findViewById(R.id.konsumen_recyclerview);
        emptyText = findViewById(R.id.konsumen_empty_text);

        /*inisialisasi tanggal jika data tidak ada agar tidak error*/
        Calendar calendar = Calendar.getInstance();
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        setDateButton(day, month, year);
        setMonthButton(month, year);
        setYearButton(year);

        /*get data. jika data tidak ada maka tampilkan tulisan jika kosong. jika ada
        * maka tampilkan tab beserta isinya*/
//        getKonsumen(month);

        /*handle tanggal untuk mengubah data berdasarkan bulan*/
        handlePeriod();
    }

    private void setDateButton(int day, int month, int year) {
        DateFormatSymbols symbols = new DateFormatSymbols();
        String text = String.valueOf(day) + " " + symbols.getMonths()[month] + " " + String.valueOf(year);
        tanggal.setText(text);
    }

    private void setYearButton(int year) {
        tahun.setText(String.valueOf(year));
    }

    private void setMonthButton(int month, int year) {
        DateFormatSymbols symbols = new DateFormatSymbols();
        String text = symbols.getMonths()[month] + " " + String.valueOf(year);
        bulan.setText(text);
    }

    private void cekData(ArrayList<Konsumen> konsumens) {

        /*cek apakah data ada atau tidak*/
        if (konsumens != null && konsumens.size() > 0) {

            Log.d("data", "data konsumen ada");
            /*data ada maka tampilkan tab dan isinya*/
//            populateData(konsumens);
            populateCard(konsumens);
        } else {

            Log.d("data", "data konsumen tidak ada");
            /*data tidak ada maka hilangkan tab dan tampilkan pesan peringatan
            * untuk tanggal gunakan month dan year yang sudah diinisialisasi*/

        }
    }

    private void populateCard(ArrayList<Konsumen> kons) {

        ArrayList<Konsumen> konsumens = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        String today = new Date(calendar.getTimeInMillis()).toString();
        for (int i = 0; i < kons.size(); i++) {
            if (kons.get(i).getDate().equals(today)) {
                konsumens.add(kons.get(i));
            }
        }

        if (konsumens.size() > 0) {
            ArrayList<String> titles = new ArrayList<>();
            ArrayList<ArrayList<Konsumen>> kumpulanKonsumens = new ArrayList<>();

            titles.add(konsumens.get(0).getKonsumen());
            for (int i = 0; i < konsumens.size(); i++) {
                boolean ada = false;
                for (int j = 0; j < titles.size(); j++) {
                    if (konsumens.get(i).getKonsumen().equals(titles.get(j))) {
                        ada = true;
                        break;
                    } else {
                        ada = false;
                    }
                }
                if (!ada) {
                    titles.add(konsumens.get(i).getKonsumen());
                }
            }

            Log.d("tabs ada", String.valueOf(titles.size()));

            for (int i = 0; i < titles.size(); i++) {
                kumpulanKonsumens.add(new ArrayList<Konsumen>());
                for (int j = 0; j < konsumens.size(); j++) {
                    if (titles.get(i).equals(konsumens.get(j).getKonsumen())) {
                        kumpulanKonsumens.get(i).add(konsumens.get(j));
                    }
                }
            }

            recyclerView.setVisibility(View.VISIBLE);
            emptyText.setVisibility(View.GONE);
            KonsumenContainerAdapter adapter = new KonsumenContainerAdapter(kumpulanKonsumens);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.setAdapter(adapter);
        } else {
            recyclerView.setVisibility(View.GONE);
            emptyText.setVisibility(View.VISIBLE);
        }
    }

    private void handlePeriod() {
        spinner = findViewById(R.id.konsumen_periode_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.pilihan_periode, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int index = adapterView.getSelectedItemPosition();
                ((TextView) spinner.getSelectedView()).setTextColor(getResources().getColor(R.color.white));
                switch (index) {
                    case 0 :
                        tanggalButton.setVisibility(View.VISIBLE);
                        bulanButton.setVisibility(View.GONE);
                        tahunButton.setVisibility(View.GONE);
                        break;
                    case 1:
                        bulanButton.setVisibility(View.VISIBLE);
                        tanggalButton.setVisibility(View.GONE);
                        tahunButton.setVisibility(View.GONE);
                        break;
                    default:
                        tahunButton.setVisibility(View.VISIBLE);
                        tanggalButton.setVisibility(View.GONE);
                        bulanButton.setVisibility(View.GONE);
                        break;
                }

                //update ui karena ada perubahan di jenis periode
                updateUi(day, month, year, index);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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
                        setMonthButton(month, year);
                        setYearButton(year);
                        updateUi(day, month, year, 0);
                    }
                };
                DatePickerDialog dialog = new DatePickerDialog(
                        KonsumenActivity.this,
                        listener,
                        year,
                        month,
                        day
                );
                dialog.show();
            }
        });

        //handle klik jika periode diset bulanan
        bulanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar today = Calendar.getInstance();

                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(
                        KonsumenActivity.this,
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) {
                                month = selectedMonth;
                                year = selectedYear;
                                setDateButton(day, month, year);
                                setMonthButton(month, year);
                                setYearButton(year);
                                updateUi(day, month, year, 1);
                            }
                        },
                        today.get(Calendar.YEAR),
                        today.get(Calendar.MONTH)
                );

                builder.setMinYear(1970)
                        .setMaxYear(today.get(Calendar.YEAR))
                        .setActivatedYear(year)
                        .setActivatedMonth(month)
                        .build()
                        .show();
            }
        });

        //handle klik jika periode diset tahunan
        tahunButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar today = Calendar.getInstance();

                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(
                        KonsumenActivity.this,
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) {
                                month = selectedMonth;
                                year = selectedYear;
                                setDateButton(day, month, year);
                                setMonthButton(month, year);
                                setYearButton(year);
                                updateUi(day, month, year, 2);
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

    private void updateUi(int day, int month, int year, int type) {
        //type adalah 0 untuk daily, 1 untuk monthly, 2 untuk yearly

        switch (type) {
            case 0:
                getKonsumenHari(day, month, year);
                break;
            case 1:
                getKonsumenBulan(month, year);
                break;
            case 2:
                getKonsumenTahun(year);
                break;
        }
    }

    private void getKonsumenTahun(int year) {

        SharedPreferences preferences = KonsumenActivity.this.getSharedPreferences(
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
        Call<ArrayList<Konsumen>> call = userClient.getKonsumenTahun(String.valueOf(year));

        call.enqueue(new Callback<ArrayList<Konsumen>>() {
            @Override
            public void onResponse(Call<ArrayList<Konsumen>> call, Response<ArrayList<Konsumen>> response) {
                Log.w("code", String.valueOf(response.code()));
                Log.w("data", new Gson().toJson(response.body()));
                if (response.code() == 200) {

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Konsumen>> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }

    private void getKonsumenBulan(int month, int year) {

        SharedPreferences preferences = KonsumenActivity.this.getSharedPreferences(
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

        Call<ArrayList<Konsumen>> call = userClient.getKonsumenBulan(
                String.valueOf(year),
                String.valueOf(month + 1)
        );
        call.enqueue(new Callback<ArrayList<Konsumen>>() {
            @Override
            public void onResponse(Call<ArrayList<Konsumen>> call, Response<ArrayList<Konsumen>> response) {
                Log.w("code", String.valueOf(response.code()));
                Log.w("data", new Gson().toJson(response.body()));
                if (response.code() == 200) {

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Konsumen>> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }

    private void getKonsumenHari(int day, int month, int year) {

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        String tanggal = new Date(cal.getTimeInMillis()).toString();

        SharedPreferences preferences = KonsumenActivity.this.getSharedPreferences(
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

        Call<ArrayList<Konsumen>> call = userClient.getKonsumenTanggal(tanggal);
        call.enqueue(new Callback<ArrayList<Konsumen>>() {
            @Override
            public void onResponse(Call<ArrayList<Konsumen>> call, Response<ArrayList<Konsumen>> response) {
                Log.w("code", String.valueOf(response.code()));
                Log.w("data", new Gson().toJson(response.body()));
                if (response.code() == 200) {

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Konsumen>> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }

    /*private void populateData(ArrayList<Konsumen> konsumens) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Date date = new Date(calendar.getTimeInMillis());
        String today;
        today = date.toString();

        float grandTotal = 0;
        ArrayList<Konsumen> konsumenToday = new ArrayList<>();
        for (int i = 0; i < konsumens.size(); i++) {
            if (konsumens.get(i).getDate().equals(today)) {
                grandTotal = grandTotal + konsumens.get(i).getNilai();
                konsumenToday.add(konsumens.get(i));
            }
        }
        grand_total.setText(String.valueOf(grandTotal + " KL"));

        ArrayList<Konsumen> konsumenPertamax, konsumenPertalite, konsumenPremium, konsumenBiosolar, konsumenSolar;
        konsumenPertamax = new ArrayList<>();
        konsumenPertalite = new ArrayList<>();
        konsumenPremium = new ArrayList<>();
        konsumenBiosolar = new ArrayList<>();
        konsumenSolar = new ArrayList<>();
        for (int i = 0; i < konsumenToday.size(); i++) {
            switch (konsumenToday.get(i).getFuel()) {
                case Matbal.PERTAMAX:
                    konsumenPertamax.add(konsumenToday.get(i));
                    break;
                case Matbal.PERTALITE:
                    konsumenPertalite.add(konsumenToday.get(i));
                    break;
                case Matbal.PREMIUM:
                    konsumenPremium.add(konsumenToday.get(i));
                    break;
                case Matbal.BIOSOLAR:
                    konsumenBiosolar.add(konsumenToday.get(i));
                    break;
                case Matbal.SOLAR:
                    konsumenSolar.add(konsumenToday.get(i));
                    break;
            }
        }

        float pertamaxTotal = 0;
        for (int i = 0; i < konsumenPertamax.size(); i++) {
            pertamaxTotal = pertamaxTotal + konsumenPertamax.get(i).getNilai();
            if (konsumenPertamax.get(i).getKonsumen().equals(Konsumen.SPBU)) {
                pertamax_spbu.setText(String.valueOf(konsumenPertamax.get(i).getNilai() + " KL"));
            } else
            if (konsumenPertamax.get(i).getKonsumen().equals(Konsumen.TNI)) {
                pertamax_tni.setText(String.valueOf(konsumenPertamax.get(i).getNilai() + " KL"));
            }
        }
        pertamax_total.setText(String.valueOf(pertamaxTotal + " KL"));
        if (pertamaxTotal == 0) {
            pertamax_spbu.setText(String.valueOf("0 KL"));
            pertamax_tni.setText(String.valueOf("0 KL"));
        }

        float pertaliteTotal = 0;
        for (int i = 0; i < konsumenPertalite.size(); i++) {
            pertaliteTotal = pertamaxTotal + konsumenPertalite.get(i).getNilai();
            if (konsumenPertalite.get(i).getKonsumen().equals(Konsumen.SPBU)) {
                pertalite_spbu.setText(String.valueOf(konsumenPertalite.get(i).getNilai() + " KL"));
            }
        }
        pertalite_total.setText(String.valueOf(pertaliteTotal + " KL"));
        if (pertaliteTotal == 0) {
            pertalite_spbu.setText(String.valueOf("0 KL"));
        }

        float premiumTotal = 0;
        for (int i = 0; i < konsumenPremium.size(); i++) {
            premiumTotal = premiumTotal + konsumenPremium.get(i).getNilai();
            if (konsumenPremium.get(i).getKonsumen().equals(Konsumen.SPBU)) {
                premium_spbu.setText(String.valueOf(konsumenPremium.get(i).getNilai() + " KL"));
            }
        }
        premium_total.setText(String.valueOf(premiumTotal + " KL"));
        if (premiumTotal == 0) {
            premium_spbu.setText(String.valueOf("0 KL"));
        }

        float biosolarTotal = 0;
        for (int i = 0; i < konsumenBiosolar.size(); i++) {
            biosolarTotal = biosolarTotal + konsumenBiosolar.get(i).getNilai();
            if (konsumenBiosolar.get(i).getKonsumen().equals(Konsumen.SPBU)) {
                biosolar_spbu.setText(String.valueOf(konsumenBiosolar.get(i).getNilai() + " KL"));
            } else
            if (konsumenBiosolar.get(i).getKonsumen().equals(Konsumen.SPDN)) {
                biosolar_spdn.setText(String.valueOf(konsumenBiosolar.get(i).getNilai() + " KL"));
            }
        }
        biosolar_total.setText(String.valueOf(biosolarTotal + " KL"));
        if (biosolarTotal == 0) {
            biosolar_spbu.setText(String.valueOf("0 KL"));
            biosolar_spdn.setText(String.valueOf("0 KL"));
        }

        float solarTotal = 0;
        for (int i = 0; i < konsumenSolar.size(); i++) {
            solarTotal = solarTotal + konsumenSolar.get(i).getNilai();
            switch (konsumenSolar.get(i).getKonsumen()) {
                case Konsumen.MT:
                    solar_mt.setText(String.valueOf(konsumenSolar.get(i).getNilai() + " KL"));
                    break;
                case Konsumen.PLN:
                    solar_pln.setText(String.valueOf(konsumenSolar.get(i).getNilai() + " KL"));
                    break;
                case Konsumen.SPBU:
                    solar_spbu.setText(String.valueOf(konsumenSolar.get(i).getNilai() + " KL"));
                    break;
                case Konsumen.SPDN:
                    solar_spdn.setText(String.valueOf(konsumenSolar.get(i).getNilai() + " KL"));
                    break;
                case Konsumen.TNI:
                    solar_tni.setText(String.valueOf(konsumenSolar.get(i).getNilai() + " KL"));
                    break;
            }
        }
        solar_total.setText(String.valueOf(solarTotal + " KL"));
        if (solarTotal == 0) {
            solar_mt.setText(String.valueOf("0 KL"));
            solar_pln.setText(String.valueOf("0 KL"));
            solar_spbu.setText(String.valueOf("0 KL"));
            solar_spdn.setText(String.valueOf("0 KL"));
            solar_tni.setText(String.valueOf("0 KL"));
        }
    }*/

    private void getKonsumen(int bulan) {

        SharedPreferences preferences = KonsumenActivity.this.getSharedPreferences(
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

        Log.w("GET ", "start getting konsumen bulan " + bulan);
        String baseUrl = "http://www.api.clicktuban.com/";
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client);

        Retrofit retrofit = builder.build();
        UserClient userClient = retrofit.create(UserClient.class);
        Call<ArrayList<Konsumen>> call = userClient.getKonsumen(bulan + 1);

        /*call.enqueue(new Callback<ArrayList<Konsumen>>() {
            @Override
            public void onResponse(Call<ArrayList<Konsumen>> call, Response<ArrayList<Konsumen>> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200 && response.body() != null) {
                    Log.w("size", String.valueOf(response.body().size()));
                    cekData(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Konsumen>> call, Throwable t) {
                Log.e("Call", " failed " + t.getMessage());
            }
        });*/

        ArrayList<Konsumen> konsumens = new ArrayList<>();
        konsumens.add(new Konsumen("2017-02-01", "SPBU", Matbal.PERTAMAX, 344));
        konsumens.add(new Konsumen("2017-02-02", "SPBU", Matbal.PERTAMAX, 384));
        konsumens.add(new Konsumen("2017-02-03", "SPBU", Matbal.PERTAMAX, 240));
        konsumens.add(new Konsumen("2017-02-04", "SPBU", Matbal.PERTAMAX, 336));
        konsumens.add(new Konsumen("2017-02-05", "SPBU", Matbal.PERTAMAX, 248));
        konsumens.add(new Konsumen("2017-02-06", "SPBU", Matbal.PERTAMAX, 268));
        konsumens.add(new Konsumen("2017-02-07", "SPBU", Matbal.PERTAMAX, 508));
        konsumens.add(new Konsumen("2017-02-08", "SPBU", Matbal.PERTAMAX, 308));
        konsumens.add(new Konsumen("2017-02-09", "SPBU", Matbal.PERTAMAX, 376));
        konsumens.add(new Konsumen("2017-02-10", "SPBU", Matbal.PERTAMAX, 424));
        konsumens.add(new Konsumen("2017-02-11", "SPBU", Matbal.PERTAMAX, 324));
        konsumens.add(new Konsumen("2017-02-12", "SPBU", Matbal.PERTAMAX, 284));
        konsumens.add(new Konsumen("2017-02-13", "SPBU", Matbal.PERTAMAX, 264));
        konsumens.add(new Konsumen("2017-02-14", "SPBU", Matbal.PERTAMAX, 420));
        konsumens.add(new Konsumen("2017-02-15", "SPBU", Matbal.PERTAMAX, 400));
        konsumens.add(new Konsumen("2017-02-16", "SPBU", Matbal.PERTAMAX, 188));
        konsumens.add(new Konsumen("2017-02-02", "TNI/Polri", Matbal.PERTAMAX, 8));
        konsumens.add(new Konsumen("2017-02-04", "TNI/Polri", Matbal.PERTAMAX, 8));
        konsumens.add(new Konsumen("2017-02-05", "TNI/Polri", Matbal.PERTAMAX, 8));
        konsumens.add(new Konsumen("2017-02-07", "TNI/Polri", Matbal.PERTAMAX, 8));
        konsumens.add(new Konsumen("2017-02-08", "TNI/Polri", Matbal.PERTAMAX, 8));
        konsumens.add(new Konsumen("2017-02-10", "TNI/Polri", Matbal.PERTAMAX, 16));
        konsumens.add(new Konsumen("2017-02-14", "TNI/Polri", Matbal.PERTAMAX, 4));
        konsumens.add(new Konsumen("2017-02-01","SPBU",  Matbal.PREMIUM, 360));
        konsumens.add(new Konsumen("2017-02-02","SPBU",  Matbal.PREMIUM, 272));
        konsumens.add(new Konsumen("2017-02-03","SPBU",  Matbal.PREMIUM, 264));
        konsumens.add(new Konsumen("2017-02-04","SPBU",  Matbal.PREMIUM, 232));
        konsumens.add(new Konsumen("2017-02-05","SPBU",  Matbal.PREMIUM, 208));
        konsumens.add(new Konsumen("2017-02-06","SPBU",  Matbal.PREMIUM, 232));
        konsumens.add(new Konsumen("2017-02-07","SPBU",  Matbal.PREMIUM, 416));
        konsumens.add(new Konsumen("2017-02-08","SPBU",  Matbal.PREMIUM, 272));
        konsumens.add(new Konsumen("2017-02-09","SPBU",  Matbal.PREMIUM, 280));
        konsumens.add(new Konsumen("2017-02-10","SPBU",  Matbal.PREMIUM, 232));
        konsumens.add(new Konsumen("2017-02-11","SPBU",  Matbal.PREMIUM, 368));
        konsumens.add(new Konsumen("2017-02-12","SPBU",  Matbal.PREMIUM, 216));
        konsumens.add(new Konsumen("2017-02-13","SPBU",  Matbal.PREMIUM, 192));
        konsumens.add(new Konsumen("2017-02-14","SPBU",  Matbal.PREMIUM, 368));
        konsumens.add(new Konsumen("2017-02-15","SPBU",  Matbal.PREMIUM, 296));
        konsumens.add(new Konsumen("2017-02-01","SPBU",  Matbal.BIOSOLAR, 904));
        konsumens.add(new Konsumen("2017-02-02","SPBU",  Matbal.BIOSOLAR, 872));
        konsumens.add(new Konsumen("2017-02-03","SPBU",  Matbal.BIOSOLAR, 864));
        konsumens.add(new Konsumen("2017-02-04","SPBU",  Matbal.BIOSOLAR, 928));
        konsumens.add(new Konsumen("2017-02-05","SPBU",  Matbal.BIOSOLAR, 592));
        konsumens.add(new Konsumen("2017-02-06","SPBU",  Matbal.BIOSOLAR, 632));
        konsumens.add(new Konsumen("2017-02-07","SPBU",  Matbal.BIOSOLAR, 896));
        konsumens.add(new Konsumen("2017-02-08","SPBU",  Matbal.BIOSOLAR, 1008));
        konsumens.add(new Konsumen("2017-02-09","SPBU",  Matbal.BIOSOLAR, 800));
        konsumens.add(new Konsumen("2017-02-10","SPBU",  Matbal.BIOSOLAR, 840));
        konsumens.add(new Konsumen("2017-02-11","SPBU",  Matbal.BIOSOLAR, 912));
        konsumens.add(new Konsumen("2017-02-12","SPBU",  Matbal.BIOSOLAR, 672));
        konsumens.add(new Konsumen("2017-02-13","SPBU",  Matbal.BIOSOLAR, 744));
        konsumens.add(new Konsumen("2017-02-14","SPBU",  Matbal.BIOSOLAR, 1008));
        konsumens.add(new Konsumen("2017-02-15","SPBU",  Matbal.BIOSOLAR, 960));
        konsumens.add(new Konsumen("2017-02-02","SPDN",  Matbal.BIOSOLAR, 56));
        konsumens.add(new Konsumen("2017-02-03","SPDN",  Matbal.BIOSOLAR, 72));
        konsumens.add(new Konsumen("2017-02-04","SPDN",  Matbal.BIOSOLAR, 64));
        konsumens.add(new Konsumen("2017-02-05","SPDN",  Matbal.BIOSOLAR, 8));
        konsumens.add(new Konsumen("2017-02-07","SPDN",  Matbal.BIOSOLAR, 8));
        konsumens.add(new Konsumen("2017-02-13","SPDN",  Matbal.BIOSOLAR, 8));
        konsumens.add(new Konsumen("2017-02-14","SPDN",  Matbal.BIOSOLAR, 88));
        konsumens.add(new Konsumen("2017-02-15","SPDN",  Matbal.BIOSOLAR, 72));
        konsumens.add(new Konsumen("2017-02-01","SPBU",  Matbal.SOLAR, 723));
        konsumens.add(new Konsumen("2017-02-02","SPBU",  Matbal.SOLAR, 697));
        konsumens.add(new Konsumen("2017-02-03","SPBU",  Matbal.SOLAR, 691));
        konsumens.add(new Konsumen("2017-02-04","SPBU",  Matbal.SOLAR, 742));
        konsumens.add(new Konsumen("2017-02-05","SPBU",  Matbal.SOLAR, 473));
        konsumens.add(new Konsumen("2017-02-06","SPBU",  Matbal.SOLAR, 505));
        konsumens.add(new Konsumen("2017-02-07","SPBU",  Matbal.SOLAR, 716));
        konsumens.add(new Konsumen("2017-02-08","SPBU",  Matbal.SOLAR, 806));
        konsumens.add(new Konsumen("2017-02-09","SPBU",  Matbal.SOLAR, 640));
        konsumens.add(new Konsumen("2017-02-10","SPBU",  Matbal.SOLAR, 672));
        konsumens.add(new Konsumen("2017-02-11","SPBU",  Matbal.SOLAR, 729));
        konsumens.add(new Konsumen("2017-02-12","SPBU",  Matbal.SOLAR, 537));
        konsumens.add(new Konsumen("2017-02-13","SPBU",  Matbal.SOLAR, 595));
        konsumens.add(new Konsumen("2017-02-14","SPBU",  Matbal.SOLAR, 806));
        konsumens.add(new Konsumen("2017-02-15","SPBU",  Matbal.SOLAR, 768));

        konsumens.add(new Konsumen("2017-02-03","Own Use MT",  Matbal.SOLAR, 8));
        konsumens.add(new Konsumen("2017-02-04","Own Use MT",  Matbal.SOLAR, 8));
        konsumens.add(new Konsumen("2017-02-06","Own Use MT",  Matbal.SOLAR, 8));
        konsumens.add(new Konsumen("2017-02-08","Own Use MT",  Matbal.SOLAR, 8));
        konsumens.add(new Konsumen("2017-02-09","Own Use MT",  Matbal.SOLAR, 8));
        konsumens.add(new Konsumen("2017-02-11","Own Use MT",  Matbal.SOLAR, 8));
        konsumens.add(new Konsumen("2017-02-13","Own Use MT",  Matbal.SOLAR, 8));
        konsumens.add(new Konsumen("2017-02-14","Own Use MT",  Matbal.SOLAR, 8));

        konsumens.add(new Konsumen("2017-02-07","PLN",  Matbal.SOLAR, 112));
        konsumens.add(new Konsumen("2017-02-14","PLN",  Matbal.SOLAR, 88));
        konsumens.add(new Konsumen("2017-02-15","PLN",  Matbal.SOLAR, 24));

        konsumens.add(new Konsumen("2017-02-02","SPDN",  Matbal.SOLAR, 44));
        konsumens.add(new Konsumen("2017-02-03","SPDN",  Matbal.SOLAR, 57));
        konsumens.add(new Konsumen("2017-02-04","SPDN",  Matbal.SOLAR, 51));
        konsumens.add(new Konsumen("2017-02-05","SPDN",  Matbal.SOLAR, 6));
        konsumens.add(new Konsumen("2017-02-07","SPDN",  Matbal.SOLAR, 6));
        konsumens.add(new Konsumen("2017-02-13","SPDN",  Matbal.SOLAR, 6));
        konsumens.add(new Konsumen("2017-02-14","SPDN",  Matbal.SOLAR, 70));
        konsumens.add(new Konsumen("2017-02-15","SPDN",  Matbal.SOLAR, 57));

        konsumens.add(new Konsumen("2017-02-04","TNI/Polri",  Matbal.SOLAR, 8));
        cekData(konsumens);
    }
}
