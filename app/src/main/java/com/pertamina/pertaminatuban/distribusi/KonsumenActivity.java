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
import com.pertamina.pertaminatuban.distribusi.models.Konsumen;
import com.pertamina.pertaminatuban.distribusi.models.Matbal;
import com.pertamina.pertaminatuban.distribusi.page.KonsumenPage;
import com.pertamina.pertaminatuban.service.UserClient;
import com.pertamina.pertaminatuban.utils.ViewPagerAdapter;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KonsumenActivity extends AppCompatActivity {

    private TextView tanggal;
    private LinearLayout tanggalButton;
    private int month;
    private int year;
    private int day;

    private TextView pertamax_spbu, pertamax_tni, pertamax_total;
    private TextView pertalite_spbu, pertalite_total;
    private TextView premium_spbu, premium_total;
    private TextView biosolar_spbu, biosolar_spdn, biosolar_total;
    private TextView solar_mt, solar_pln, solar_spbu, solar_spdn, solar_tni, solar_total;
    private TextView grand_total;

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
        tanggalButton = findViewById(R.id.konsumen_tanggal_button);

        /*init view by id*/
        initView();

        /*inisialisasi tanggal jika data tidak ada agar tidak error*/
        Calendar calendar = Calendar.getInstance();
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        setDateButton(day, month, year);

        /*get data. jika data tidak ada maka tampilkan tulisan jika kosong. jika ada
        * maka tampilkan tab beserta isinya*/
        getKonsumen(month);

        /*handle tanggal untuk mengubah data berdasarkan bulan*/
        handleDate();
    }

    private void initView() {
        pertamax_spbu = findViewById(R.id.konsumen_nilai_pertamax_spbu);
        pertamax_tni = findViewById(R.id.konsumen_nilai_pertamax_tni);
        pertamax_total = findViewById(R.id.konsumen_nilai_pertamax_total);
        pertalite_spbu = findViewById(R.id.konsumen_nilai_pertalite_spbu);
        pertalite_total = findViewById(R.id.konsumen_nilai_pertalite_total);
        premium_spbu = findViewById(R.id.konsumen_nilai_premium_spbu);
        premium_total = findViewById(R.id.konsumen_nilai_premium_total);
        biosolar_spbu = findViewById(R.id.konsumen_nilai_biosolar_spbu);
        biosolar_spdn = findViewById(R.id.konsumen_nilai_biosolar_spdn);
        biosolar_total = findViewById(R.id.konsumen_nilai_biosolar_total);
        solar_mt = findViewById(R.id.konsumen_nilai_solar_mt);
        solar_pln = findViewById(R.id.konsumen_nilai_solar_pln);
        solar_spbu = findViewById(R.id.konsumen_nilai_solar_spbu);
        solar_spdn = findViewById(R.id.konsumen_nilai_solar_spdn);
        solar_tni = findViewById(R.id.konsumen_nilai_solar_tni);
        solar_total = findViewById(R.id.konsumen_nilai_solar_total);
        grand_total = findViewById(R.id.konsumen_nilai_grand_total);
    }

    private void cekData(ArrayList<Konsumen> konsumens) {

        /*cek apakah data ada atau tidak*/
        if (konsumens != null && konsumens.size() > 0) {

            Log.d("data", "data konsumen ada");
            /*data ada maka tampilkan tab dan isinya*/
            populateData(konsumens);
        } else {

            Log.d("data", "data konsumen tidak ada");
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
                        getKonsumen(month);
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
    }

    private void setDateButton(int day, int month, int year) {
        DateFormatSymbols symbols = new DateFormatSymbols();
        String text = String.valueOf(day) + " " + symbols.getMonths()[month] + " " + String.valueOf(year);
        tanggal.setText(text);
    }

    private void populateData(ArrayList<Konsumen> konsumens) {
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
                pertamax_spbu.setText(String.valueOf(konsumenPertamax.get(i).getNilai() + " KL"));
            }
        }
        pertamax_total.setText(String.valueOf(pertamaxTotal + " KL"));

        float pertaliteTotal = 0;
        for (int i = 0; i < konsumenPertalite.size(); i++) {
            pertaliteTotal = pertamaxTotal + konsumenPertalite.get(i).getNilai();
            if (konsumenPertalite.get(i).getKonsumen().equals(Konsumen.SPBU)) {
                pertalite_spbu.setText(String.valueOf(konsumenPertalite.get(i).getNilai() + " KL"));
            }
        }
        pertalite_total.setText(String.valueOf(pertaliteTotal + " KL"));

        float premiumTotal = 0;
        for (int i = 0; i < konsumenPremium.size(); i++) {
            premiumTotal = premiumTotal + konsumenPremium.get(i).getNilai();
            if (konsumenPremium.get(i).getKonsumen().equals(Konsumen.SPBU)) {
                premium_spbu.setText(String.valueOf(konsumenPremium.get(i).getNilai() + " KL"));
            }
        }
        premium_total.setText(String.valueOf(premiumTotal + " KL"));

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
    }

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
/*pertamax*/
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
/*pertalite*/
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
/*biosolar*/
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
/*solar*/
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
