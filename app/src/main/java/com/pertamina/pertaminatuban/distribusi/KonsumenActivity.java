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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.models.Konsumen;
import com.pertamina.pertaminatuban.distribusi.models.Matbal;
import com.pertamina.pertaminatuban.distribusi.tables.KonsumenContainerAdapter;
import com.pertamina.pertaminatuban.models.MasterData;
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
    private ProgressBar progressBar;

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
        progressBar = findViewById(R.id.konsumen_progressbar);

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
//        updateUi(day, month, year, 0);

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

    private void cekData(ArrayList<ArrayList<Konsumen>> kumpulanKonsumens) {

        /*cek apakah data ada atau tidak*/
        if (kumpulanKonsumens != null && kumpulanKonsumens.size() > 0) {

            Log.d("data", "data konsumen ada");
            /*data ada maka tampilkan tab dan isinya*/
//            populateData(konsumens);
            populateKonsumens(kumpulanKonsumens);
        } else {

            Log.d("data", "data konsumen tidak ada");
            /*data tidak ada maka hilangkan tab dan tampilkan pesan peringatan
            * untuk tanggal gunakan month dan year yang sudah diinisialisasi*/

        }
    }

    private void populateKonsumens(ArrayList<ArrayList<Konsumen>> kumpulanKonsumens) {
        progressBar.setVisibility(View.GONE);

        KonsumenContainerAdapter adapter = new KonsumenContainerAdapter(kumpulanKonsumens);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);

        TextView emptyText = findViewById(R.id.konsumen_empty_text);
        if (adapter.getItemCount() <= 0) {
            emptyText.setVisibility(View.VISIBLE);
        } else {
            emptyText.setVisibility(View.GONE);
        }
    }

    /*private void populateCard(ArrayList<Konsumen> kons) {

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
    }*/

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

    private void updateUi(final int day, final int month, final int year, final int type) {
        progressBar.setVisibility(View.VISIBLE);
        //type adalah 0 untuk daily, 1 untuk monthly, 2 untuk yearly

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

        Call<ArrayList<MasterData>> call = userClient.getMaster();
        call.enqueue(new Callback<ArrayList<MasterData>>() {
            @Override
            public void onResponse(Call<ArrayList<MasterData>> call, Response<ArrayList<MasterData>> response) {

                if (response.code() == 200) {

                    switch (type) {
                        case 0:
                            getKonsumenHari(day, month, year, getMasterUntuk("konsumen", response.body()));
                            break;
                        case 1:
                            getKonsumenBulan(month, year, getMasterUntuk("konsumen", response.body()));
                            break;
                        case 2:
                            getKonsumenTahun(year, getMasterUntuk("konsumen", response.body()));
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<MasterData>> call, Throwable t) {

            }
        });

    }

    private ArrayList<String> getMasterUntuk(String jenis, ArrayList<MasterData> masterData) {
        ArrayList<String> kumpulanData = new ArrayList<>();
        for (int i = 0; i < masterData.size(); i++) {
            if (masterData.get(i).getJenis().equals(jenis)) {
                kumpulanData.add(masterData.get(i).getVariable());
            }
        }
        return kumpulanData;
    }

    private void getKonsumenTahun(int year, final ArrayList<String> masterData) {

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
                progressBar.setVisibility(View.GONE);
                if (response.code() == 200) {
                    populateKonsumens(getKonsumenTerindeks(masterData, response.body()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Konsumen>> call, Throwable t) {
                Log.e("error", t.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void getKonsumenBulan(int month, int year, final ArrayList<String> masterData) {

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
                progressBar.setVisibility(View.GONE);
                if (response.code() == 200) {
                    populateKonsumens(getKonsumenTerindeks(masterData, response.body()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Konsumen>> call, Throwable t) {
                Log.e("error", t.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void getKonsumenHari(int day, int month, int year, final ArrayList<String> masterData) {

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
                progressBar.setVisibility(View.GONE);
                Log.w("code", String.valueOf(response.code()));
                Log.w("data", new Gson().toJson(response.body()));
                if (response.code() == 200) {
                    populateKonsumens(getKonsumenTerindeks(masterData, response.body()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Konsumen>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private ArrayList<ArrayList<Konsumen>> getKonsumenTerindeks(
            ArrayList<String> masterData,
            ArrayList<Konsumen> konsumen
    ) {
        if (masterData != null) {
            Log.w("master data size", String.valueOf(masterData.size()));
        } else {
            Log.w("master data", "null");
        }
        if (konsumen != null) {
            Log.w("konsumen size", String.valueOf(konsumen.size()));
        } else {
            Log.w("konsumen", "null");
        }
        ArrayList<ArrayList<Konsumen>> kumpulanKonsumen = new ArrayList<>();
        for (int i = 0; i < masterData.size(); i++) {
            ArrayList<Konsumen> konsumens = new ArrayList<>();
            for (int j = 0; j < konsumen.size(); j++) {
                Log.w("konsumen ke", String.valueOf(j) + " = " + konsumen.get(j).getKonsumen());
                if (konsumen.get(j).getKonsumen().equals(masterData.get(i))) {
                    konsumens.add(konsumen.get(j));
                }
            }

            ArrayList<String> listFuel = new ArrayList<>();
            if (konsumens.size() > 0) {
                listFuel.add(konsumens.get(0).getFuel());
                for (int k = 0; k < konsumens.size(); k++) {
                    boolean ada = false;
                    for (int l = 0; l < listFuel.size(); l++) {
                        if (konsumens.get(k).getFuel().equals(listFuel.get(l))) {
                            ada = true;
                            break;
                        } else {
                            ada = false;
                        }
                    }
                    if (!ada) {
                        listFuel.add(konsumens.get(k).getFuel());
                    }
                }
            }

            ArrayList<Konsumen> konsumensMerged = new ArrayList<>();
            for (int m = 0; m < listFuel.size(); m++) {
                float total = 0;
                Konsumen kons = new Konsumen();
                for (int n = 0; n < konsumens.size(); n++) {
                    if (konsumens.get(n).getFuel().equals(listFuel.get(m))) {
                        total = total + konsumens.get(n).getNilai();
                        kons.setKonsumen(konsumens.get(n).getKonsumen());
                    }
                }
                kons.setFuel(listFuel.get(m));
                kons.setNilai(total);
                konsumensMerged.add(kons);
            }
            if (konsumensMerged.size() > 0) {
                kumpulanKonsumen.add(konsumensMerged);
            }
        }
        return kumpulanKonsumen;
    }
}
