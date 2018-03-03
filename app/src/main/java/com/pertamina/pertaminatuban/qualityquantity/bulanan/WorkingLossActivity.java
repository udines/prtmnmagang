package com.pertamina.pertaminatuban.qualityquantity.bulanan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.WilayahActivity;
import com.pertamina.pertaminatuban.models.MasterData;
import com.pertamina.pertaminatuban.qualityquantity.models.WorkingLoss;
import com.pertamina.pertaminatuban.service.QqClient;
import com.pertamina.pertaminatuban.service.UserClient;
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

public class WorkingLossActivity extends AppCompatActivity {

    private TextView lokasi, produk, stockAwal, actReceipt, blendingIn, totalTersedia,
    salesPso, salesNpso, konsinyasi, ownUseTbbm, ownUseKapal, totalPenyaluran, blendingOut,
    quantity, percentage, stockAkhir;
    private Spinner spinner;
    private TextView bulan;
    private ArrayList<String> fuelList;
    private int fuelIndex = 0;
    private int month, year;
    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_working_loss);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Calendar calendar = Calendar.getInstance();
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        initView();
        setMonthButton(month, year);
        getMasterUntuk("fuel");
        handlePeriod();
        handleInputButton();
    }

    private void handlePeriod() {
        bulan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar today = Calendar.getInstance();

                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(
                        WorkingLossActivity.this,
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) {
                                month = selectedMonth;
                                year = selectedYear;
                                setMonthButton(month, year);
                                String tuban = "Tuban";
                                if (fuelList != null) {
                                    updateUi(month, year, tuban, fuelList.get(fuelIndex));
                                }
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

    private void setMonthButton(int month, int year) {
        DateFormatSymbols symbols = new DateFormatSymbols();
        String text = symbols.getMonths()[month] + " " + String.valueOf(year);
        bulan.setText(text);
    }

    private void getMasterUntuk(final String jenis) {

        SharedPreferences preferences = WorkingLossActivity.this.getSharedPreferences(
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

        final ArrayList<String> kumpulanData = new ArrayList<>();

        call.enqueue(new Callback<ArrayList<MasterData>>() {
            @Override
            public void onResponse(Call<ArrayList<MasterData>> call, Response<ArrayList<MasterData>> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    if (response.body().size() > 0) {
                        Log.w("size", String.valueOf(response.body().size()));
                        for (int i = 0; i < response.body().size(); i++) {
                            if (response.body().get(i).getJenis().equals(jenis)) {
                                kumpulanData.add(response.body().get(i).getVariable());
                            }
                        }
                        handleSpinner(kumpulanData);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<MasterData>> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }

    private void handleSpinner(ArrayList<String> list) {

        fuelList = list;

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, list
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int index = adapterView.getSelectedItemPosition();
                ((TextView) spinner.getSelectedView()).setTextColor(getResources().getColor(R.color.white));

                //set wilayah terpilih sesuai dengan index
                fuelIndex = index;

                String tuban = "Tuban";
                //update ui karena ada perubahan di jenis periode
                updateUi(month, year, tuban, fuelList.get(fuelIndex));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void updateUi(int month, int year, String wilayah, final String produk) {
        clearData();
        SharedPreferences preferences = WorkingLossActivity.this.getSharedPreferences(
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
        QqClient qqClient = retrofit.create(QqClient.class);

        Call<ArrayList<WorkingLoss>> call = qqClient.getWorkingLossBulan(
                wilayah,
                String.valueOf(year),
                String.valueOf(month + 1)
        );

        call.enqueue(new Callback<ArrayList<WorkingLoss>>() {
            @Override
            public void onResponse(Call<ArrayList<WorkingLoss>> call, Response<ArrayList<WorkingLoss>> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    Log.w("size", String.valueOf(response.body().size()));
                    ArrayList<WorkingLoss> workingLosses = response.body();
                    for (int i = 0; i < workingLosses.size(); i++) {
                        if (workingLosses.get(i).getProduk().equals(produk)) {
                            Log.w("fuel data", "ada");
                            container.setVisibility(View.VISIBLE);
                            setWorkingLoss(workingLosses.get(i));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<WorkingLoss>> call, Throwable t) {

            }
        });
    }

    private void initView() {
        container = findViewById(R.id.working_loss_container);
        lokasi = findViewById(R.id.working_loss_lokasi);
        produk = findViewById(R.id.working_loss_produk);
        stockAwal = findViewById(R.id.working_loss_stock_awal);
        actReceipt = findViewById(R.id.working_loss_act_receipt);
        blendingIn = findViewById(R.id.working_loss_blending_in);
        totalTersedia = findViewById(R.id.working_loss_total_tersedia);
        salesPso = findViewById(R.id.working_loss_sales_pso);
        salesNpso = findViewById(R.id.working_loss_sales_npso);
        konsinyasi = findViewById(R.id.working_loss_konsinyasi);
        ownUseTbbm = findViewById(R.id.working_loss_ownuse_tbbm);
        ownUseKapal = findViewById(R.id.working_loss_ownuse_kapal);
        totalPenyaluran = findViewById(R.id.working_loss_total_penyaluran);
        blendingOut = findViewById(R.id.working_loss_blending_out);
        quantity = findViewById(R.id.working_loss_quantity);
        percentage = findViewById(R.id.working_loss_percentage);
        stockAkhir = findViewById(R.id.working_loss_stock_akhir);

        spinner = findViewById(R.id.working_loss_spinner);
        bulan = findViewById(R.id.working_loss_bulan);
    }

    private void handleInputButton() {
        Button button = findViewById(R.id.working_loss_input_button);
        SharedPreferences preferences = this.getSharedPreferences(
                "login",
                Context.MODE_PRIVATE
        );
        String role = preferences.getString("userRole", "none");
        if (role.equals("distribusi") || role.equals("admin")) {
            button.setVisibility(View.VISIBLE);
        } else {
            button.setVisibility(View.GONE);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InputWorkingLossActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void setWorkingLoss(WorkingLoss model) {
        lokasi.setText(model.getLokasi());
        produk.setText(model.getProduk());
        stockAwal.setText(model.getStokAwal());
        actReceipt.setText(model.getActReceipt());
        blendingIn.setText(model.getBlendingIn());
        totalTersedia.setText(model.getTotalTersedia());
        salesPso.setText(model.getSalesPso());
        salesNpso.setText(model.getSalesNpso());
        konsinyasi.setText(model.getKonsinyasi());
        ownUseTbbm.setText(model.getOwnUseTbbm());
        ownUseKapal.setText(model.getOwnUseKapal());
        totalPenyaluran.setText(model.getPenyaluran());
        blendingOut.setText(model.getBlendingOut());
        quantity.setText(model.getWlQuantity());
        percentage.setText(String.valueOf(Double.parseDouble(model.getWlPercentage()) * 100 + " %"));
        stockAkhir.setText(model.getStokAkhir());
    }

    private void clearData() {
        container.setVisibility(View.GONE);
        WorkingLoss model = new WorkingLoss(
                "", "Tuban", "Produk", "", "", "",
                "","","","","","",
                "","","","",""
        );

        lokasi.setText(model.getLokasi());
        produk.setText(model.getProduk());
        stockAwal.setText(model.getStokAwal());
        actReceipt.setText(model.getActReceipt());
        blendingIn.setText(model.getBlendingIn());
        totalTersedia.setText(model.getTotalTersedia());
        salesPso.setText(model.getSalesPso());
        salesNpso.setText(model.getSalesNpso());
        konsinyasi.setText(model.getKonsinyasi());
        ownUseTbbm.setText(model.getOwnUseTbbm());
        ownUseKapal.setText(model.getOwnUseKapal());
        totalPenyaluran.setText(model.getPenyaluran());
        blendingOut.setText(model.getBlendingOut());
        quantity.setText(model.getWlQuantity());
        percentage.setText(model.getWlPercentage());
        stockAkhir.setText(model.getStokAkhir());
    }
}
