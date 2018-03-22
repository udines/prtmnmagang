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
import android.widget.ProgressBar;
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

    private TextView lokasi60f, produk60f, stockAwal60f, actReceipt60f, blendingIn60f, totalTersedia60f,
            salesPso60f, salesNpso60f, konsinyasi60f, ownUseTbbm60f, ownUseKapal60f, totalPenyaluran60f, blendingOut60f,
            quantity60f, percentage60f, stockAkhir60f;

    private TextView lokasiObs, produkObs, stockAwalObs, actReceiptObs, blendingInObs, totalTersediaObs,
            salesPsoObs, salesNpsoObs, konsinyasiObs, ownUseTbbmObs, ownUseKapalObs, totalPenyaluranObs, blendingOutObs,
            quantityObs, percentageObs, stockAkhirObs;

    private Spinner spinner;
    private TextView bulan;
    private ArrayList<String> fuelList;
    private int fuelIndex = 0;
    private int month, year;
    private LinearLayout container, container60f, containerObs;

    private ProgressBar progressBar;
    private TextView emptyText;

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

        progressBar = findViewById(R.id.working_loss_progressbar);
        emptyText = findViewById(R.id.working_loss_empty_text);

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
        progressBar.setVisibility(View.VISIBLE);

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
                progressBar.setVisibility(View.GONE);
                if (response.code() == 200) {
                    Log.w("size", String.valueOf(response.body().size()));
                    ArrayList<WorkingLoss> workingLosses = response.body();
                    ArrayList<WorkingLoss> wLforFuel = new ArrayList<>();
                    for (int i = 0; i < workingLosses.size(); i++) {
                        if (workingLosses.get(i).getProduk().equals(produk)) {
                            Log.w("fuel data", "ada");
                            container.setVisibility(View.VISIBLE);
                            container60f.setVisibility(View.VISIBLE);
                            containerObs.setVisibility(View.VISIBLE);
                            wLforFuel.add(workingLosses.get(i));
                            setWorkingLoss(wLforFuel);
                            emptyText.setVisibility(View.GONE);
                        } else {
                            emptyText.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<WorkingLoss>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
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

        container60f = findViewById(R.id.working_loss_container_60f);
        lokasi60f = findViewById(R.id.working_loss_lokasi_60f);
        produk60f = findViewById(R.id.working_loss_produk_60f);
        stockAwal60f = findViewById(R.id.working_loss_stock_awal_60f);
        actReceipt60f = findViewById(R.id.working_loss_act_receipt_60f);
        blendingIn60f = findViewById(R.id.working_loss_blending_in_60f);
        totalTersedia60f = findViewById(R.id.working_loss_total_tersedia_60f);
        salesPso60f = findViewById(R.id.working_loss_sales_pso_60f);
        salesNpso60f = findViewById(R.id.working_loss_sales_npso_60f);
        konsinyasi60f = findViewById(R.id.working_loss_konsinyasi_60f);
        ownUseTbbm60f = findViewById(R.id.working_loss_ownuse_tbbm_60f);
        ownUseKapal60f = findViewById(R.id.working_loss_ownuse_kapal_60f);
        totalPenyaluran60f = findViewById(R.id.working_loss_total_penyaluran_60f);
        blendingOut60f = findViewById(R.id.working_loss_blending_out_60f);
        quantity60f = findViewById(R.id.working_loss_quantity_60f);
        percentage60f = findViewById(R.id.working_loss_percentage_60f);
        stockAkhir60f = findViewById(R.id.working_loss_stock_akhir_60f);

        containerObs = findViewById(R.id.working_loss_container_obs);
        lokasiObs = findViewById(R.id.working_loss_lokasi_obs);
        produkObs = findViewById(R.id.working_loss_produk_obs);
        stockAwalObs = findViewById(R.id.working_loss_stock_awal_obs);
        actReceiptObs = findViewById(R.id.working_loss_act_receipt_obs);
        blendingInObs = findViewById(R.id.working_loss_blending_in_obs);
        totalTersediaObs = findViewById(R.id.working_loss_total_tersedia_obs);
        salesPsoObs = findViewById(R.id.working_loss_sales_pso_obs);
        salesNpsoObs = findViewById(R.id.working_loss_sales_npso_obs);
        konsinyasiObs = findViewById(R.id.working_loss_konsinyasi_obs);
        ownUseTbbmObs = findViewById(R.id.working_loss_ownuse_tbbm_obs);
        ownUseKapalObs = findViewById(R.id.working_loss_ownuse_kapal_obs);
        totalPenyaluranObs = findViewById(R.id.working_loss_total_penyaluran_obs);
        blendingOutObs = findViewById(R.id.working_loss_blending_out_obs);
        quantityObs = findViewById(R.id.working_loss_quantity_obs);
        percentageObs = findViewById(R.id.working_loss_percentage_obs);
        stockAkhirObs = findViewById(R.id.working_loss_stock_akhir_obs);

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
        if (role.equals("qq") || role.equals("admin")) {
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

    private void setWorkingLoss(ArrayList<WorkingLoss> workingLosses) {
        WorkingLoss model, model60f, modelObs;
        for (int i = 0; i < workingLosses.size(); i++) {
            switch (workingLosses.get(i).getSatuan()) {
                case "Liter 15 C":
                    model = workingLosses.get(i);
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
                    break;
                case "Bbls 60F":
                    model60f = workingLosses.get(i);
                    lokasi60f.setText(model60f.getLokasi());
                    produk60f.setText(model60f.getProduk());
                    stockAwal60f.setText(model60f.getStokAwal());
                    actReceipt60f.setText(model60f.getActReceipt());
                    blendingIn60f.setText(model60f.getBlendingIn());
                    totalTersedia60f.setText(model60f.getTotalTersedia());
                    salesPso60f.setText(model60f.getSalesPso());
                    salesNpso60f.setText(model60f.getSalesNpso());
                    konsinyasi60f.setText(model60f.getKonsinyasi());
                    ownUseTbbm60f.setText(model60f.getOwnUseTbbm());
                    ownUseKapal60f.setText(model60f.getOwnUseKapal());
                    totalPenyaluran60f.setText(model60f.getPenyaluran());
                    blendingOut60f.setText(model60f.getBlendingOut());
                    quantity60f.setText(model60f.getWlQuantity());
                    percentage60f.setText(String.valueOf(Double.parseDouble(model60f.getWlPercentage()) * 100 + " %"));
                    stockAkhir60f.setText(model60f.getStokAkhir());
                    break;
                case "Liter Obs":
                    modelObs = workingLosses.get(i);
                    lokasiObs.setText(modelObs.getLokasi());
                    produkObs.setText(modelObs.getProduk());
                    stockAwalObs.setText(modelObs.getStokAwal());
                    actReceiptObs.setText(modelObs.getActReceipt());
                    blendingInObs.setText(modelObs.getBlendingIn());
                    totalTersediaObs.setText(modelObs.getTotalTersedia());
                    salesPsoObs.setText(modelObs.getSalesPso());
                    salesNpsoObs.setText(modelObs.getSalesNpso());
                    konsinyasiObs.setText(modelObs.getKonsinyasi());
                    ownUseTbbmObs.setText(modelObs.getOwnUseTbbm());
                    ownUseKapalObs.setText(modelObs.getOwnUseKapal());
                    totalPenyaluranObs.setText(modelObs.getPenyaluran());
                    blendingOutObs.setText(modelObs.getBlendingOut());
                    quantityObs.setText(modelObs.getWlQuantity());
                    percentageObs.setText(String.valueOf(Double.parseDouble(modelObs.getWlPercentage()) * 100 + " %"));
                    stockAkhirObs.setText(modelObs.getStokAkhir());
                    break;
            }
        }
    }

    private void clearData() {
        container.setVisibility(View.GONE);
        container60f.setVisibility(View.GONE);
        containerObs.setVisibility(View.GONE);
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
