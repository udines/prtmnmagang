package com.pertamina.pertaminatuban.marine;

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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.MatbalActivity;
import com.pertamina.pertaminatuban.marine.input.InputInitialTankerActivity;
import com.pertamina.pertaminatuban.marine.models.InitialTanker;
import com.pertamina.pertaminatuban.service.UserClient;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewMarineActivity extends AppCompatActivity {

    private int month;
    private int year;
    private int day;
    private TextView periodeBulan;
    private RecyclerView recyclerView;
    private Spinner periodeSpinner;
    private Button addInitTanker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_marine);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        periodeBulan = findViewById(R.id.new_marine_bulan);
        periodeSpinner = findViewById(R.id.new_marine_periode_spinner);
        recyclerView = findViewById(R.id.new_marine_recyclerview);
        addInitTanker = findViewById(R.id.new_marine_tambah_init_tanker);

        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        setBulanButton(month, year);

        handlePeriode();
        displayDummy();
        handleAddInitTanker();
    }

    private void handleAddInitTanker() {
        SharedPreferences preferences = this.getSharedPreferences(
                "login",
                Context.MODE_PRIVATE
        );
        String role = preferences.getString("userRole", "none");
        if (role.equals("marine") || role.equals("admin")) {
            addInitTanker.setVisibility(View.VISIBLE);
        } else {
            addInitTanker.setVisibility(View.GONE);
        }

        addInitTanker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InputInitialTankerActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("month", month);
                intent.putExtra("year", year);
                startActivity(intent);
            }
        });
    }

    private void handlePeriode() {
        periodeBulan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar today = Calendar.getInstance();

                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(
                        NewMarineActivity.this,
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) {
                                month = selectedMonth;
                                year = selectedYear;
                                setBulanButton(month, year);
                                updateUi(month, year);
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
    }

    private void updateUi(int month, int year) {
        SharedPreferences preferences = NewMarineActivity.this.getSharedPreferences(
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
        UserClient userClient = retrofit.create(UserClient.class);

        Call<ArrayList<InitialTanker>> call = userClient.getInitialTanker(String.valueOf(month));
        call.enqueue(new Callback<ArrayList<InitialTanker>>() {
            @Override
            public void onResponse(Call<ArrayList<InitialTanker>> call, Response<ArrayList<InitialTanker>> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    populateData(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<InitialTanker>> call, Throwable t) {

            }
        });
    }

    private void populateData(ArrayList<InitialTanker> tankers) {
        Log.w("posisi", "populateData");
        if (tankers.size() > 0) {
            Log.w("tankers", "ada");
            ArrayList<String> listCall = new ArrayList<>();
            listCall.add(tankers.get(0).getCall());
            for (int k = 0; k < tankers.size(); k++) {
                boolean ada = false;
                for (int l = 0; l < listCall.size(); l++) {
                    if (tankers.get(k).getCall().equals(listCall.get(l))) {
                        ada = true;
                        break;
                    } else {
                        ada = false;
                    }
                }
                if (!ada) {
                    listCall.add(tankers.get(k).getCall());
                }
            }

            ArrayList<ArrayList<InitialTanker>> kumpulanTankers = new ArrayList<>();
            for (int i = 0; i < listCall.size(); i++) {
                ArrayList<InitialTanker> groupedTanker = new ArrayList<>();
                for (int j = 0; j < tankers.size(); j++) {
                    if (tankers.get(j).getCall().equals(listCall.get(i))) {
                        groupedTanker.add(tankers.get(j));
                    }
                }
                kumpulanTankers.add(groupedTanker);
            }

            ArrayList<MarineTanker> marineTankers= new ArrayList<>();
            for (int i = 0; i < kumpulanTankers.size(); i++) {
                ArrayList<String> noBill = new ArrayList<>();
                for (int j = 0; j < kumpulanTankers.get(i).size(); j++) {
                    noBill.add(kumpulanTankers.get(i).get(j).getNoBill());
                }
                marineTankers.add(new MarineTanker(
                        kumpulanTankers.get(i).get(0).getCall(),
                        kumpulanTankers.get(i).get(0).getKapal(),
                        noBill,
                        kumpulanTankers.get(i).get(0).getBerthingDate()
                ));
            }

            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.setAdapter(new NewMarineAdapter(marineTankers));
        }
    }

    private void displayDummy() {
        Log.w("tankers", "tidak ada");
        ArrayList<MarineTanker> hmm = new ArrayList<>();
        ArrayList<String> noBL = new ArrayList<>();
        noBL.add("ABCDEFGHIJ");
        noBL.add("KLMNOPQRST");
        noBL.add("UVWXYZABCD");

        hmm.add(new MarineTanker(
                "1",
                "Sindang",
                noBL,
                "2018-04-12"
        ));
        hmm.add(new MarineTanker(
                "2",
                "John Caine",
                noBL,
                "2018-04-11"
        ));
        hmm.add(new MarineTanker(
                "3",
                "Titanic",
                noBL,
                "2018-04-10"
        ));

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(new NewMarineAdapter(hmm));
    }

    private void setBulanButton(int month, int year) {
        SimpleDateFormat format = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        periodeBulan.setText(format.format(new Date(cal.getTimeInMillis())));
    }

}
