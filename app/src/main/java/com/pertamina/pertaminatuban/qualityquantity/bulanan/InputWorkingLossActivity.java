package com.pertamina.pertaminatuban.qualityquantity.bulanan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.InputMatbalActivity;
import com.pertamina.pertaminatuban.distribusi.WilayahActivity;
import com.pertamina.pertaminatuban.models.MasterData;
import com.pertamina.pertaminatuban.qualityquantity.models.WorkingLoss;
import com.pertamina.pertaminatuban.service.QqClient;
import com.pertamina.pertaminatuban.service.UserClient;

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

public class InputWorkingLossActivity extends AppCompatActivity {

    private WorkingLoss workingLoss, workingLoss60f, workingLossObs;

    private double stockAwal = 0, actReceipt = 0, blendingIn = 0, totalTersedia = 0,
    salesPso = 0, salesNpso = 0, konsinyasi = 0, ownUseTbbm = 0, ownUseKapal = 0, totalPenyaluran = 0, blendingOut = 0,
    quantity = 0, percentage = 0, stockAkhir = 0;

    private double stockAwal60f = 0, actReceipt60f = 0, blendingIn60f = 0, totalTersedia60f = 0,
            salesPso60f = 0, salesNpso60f = 0, konsinyasi60f = 0, ownUseTbbm60f = 0, ownUseKapal60f = 0, totalPenyaluran60f = 0, blendingOut60f = 0,
            quantity60f = 0, percentage60f = 0, stockAkhir60f = 0;

    private double stockAwalObs = 0, actReceiptObs = 0, blendingInObs = 0, totalTersediaObs = 0,
            salesPsoObs = 0, salesNpsoObs = 0, konsinyasiObs = 0, ownUseTbbmObs = 0, ownUseKapalObs = 0, totalPenyaluranObs = 0, blendingOutObs = 0,
            quantityObs = 0, percentageObs = 0, stockAkhirObs = 0;

    private EditText stockAwalInput, actReceiptInput, blendingInInput,
            salesPsoInput, salesNpsoInput, konsinyasiInput, ownUseTbbmInput,
            ownUseKapalInput, blendingOutInput, stockAkhirInput;

    private EditText stockAwalInput60f, actReceiptInput60f, blendingInInput60f,
            salesPsoInput60f, salesNpsoInput60f, konsinyasiInput60f, ownUseTbbmInput60f,
            ownUseKapalInput60f, blendingOutInput60f, stockAkhirInput60f;

    private EditText stockAwalInputObs, actReceiptInputObs, blendingInInputObs,
            salesPsoInputObs, salesNpsoInputObs, konsinyasiInputObs, ownUseTbbmInputObs,
            ownUseKapalInputObs, blendingOutInputObs, stockAkhirInputObs;

    private TextView lokasiInput60f, totalTersediaInput60f, totalPenyaluranInput60f, percentageInput60f, quantityInput60f;
    private TextView lokasiInputObs, totalTersediaInputObs, totalPenyaluranInputObs, percentageInputObs, quantityInputObs;
    private TextView lokasiInput, totalTersediaInput, totalPenyaluranInput, percentageInput, quantityInput;

    private Spinner produkInput;
    private Button kirim;
    private ArrayList<String> fuelList;
    private int fuelIndex;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_working_loss);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();
        getMasterUntuk("fuel");
        handleInputChange();
        handleInputChange60f();
        handleInputChangeObs();
        handleKirim();
    }

    private void handleSpinner(ArrayList<String> list) {
        fuelList = list;

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, list
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        produkInput.setAdapter(adapter);
        produkInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //set wilayah terpilih sesuai dengan index
                fuelIndex = adapterView.getSelectedItemPosition();

                //update ui karena ada perubahan di jenis periode
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getMasterUntuk(final String jenis) {

        SharedPreferences preferences = InputWorkingLossActivity.this.getSharedPreferences(
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
//                        updateUi(month, year, 0, 0);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<MasterData>> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }

    private void handleKirim() {
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kirim.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);

                Date date = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
                String text = format.format(date);
                workingLoss = new WorkingLoss(
                        text,
                        "Tuban",
                        fuelList.get(fuelIndex),
                        String.valueOf(stockAwal),
                        String.valueOf(actReceipt),
                        String.valueOf(blendingIn),
                        String.valueOf(totalTersedia),
                        String.valueOf(salesPso),
                        String.valueOf(salesNpso),
                        String.valueOf(konsinyasi),
                        String.valueOf(ownUseTbbm),
                        String.valueOf(ownUseKapal),
                        String.valueOf(totalPenyaluran),
                        String.valueOf(blendingOut),
                        String.valueOf(stockAkhir),
                        String.valueOf(quantity),
                        String.valueOf(percentage)
                );
                workingLoss.setSatuan("Liter 15 C");

                workingLoss60f = new WorkingLoss(
                        text,
                        "Tuban",
                        fuelList.get(fuelIndex),
                        String.valueOf(stockAwal60f),
                        String.valueOf(actReceipt60f),
                        String.valueOf(blendingIn60f),
                        String.valueOf(totalTersedia60f),
                        String.valueOf(salesPso60f),
                        String.valueOf(salesNpso60f),
                        String.valueOf(konsinyasi60f),
                        String.valueOf(ownUseTbbm60f),
                        String.valueOf(ownUseKapal60f),
                        String.valueOf(totalPenyaluran60f),
                        String.valueOf(blendingOut60f),
                        String.valueOf(stockAkhir60f),
                        String.valueOf(quantity60f),
                        String.valueOf(percentage60f)
                );
                workingLoss60f.setSatuan("Bbls 60F");

                workingLossObs = new WorkingLoss(
                        text,
                        "Tuban",
                        fuelList.get(fuelIndex),
                        String.valueOf(stockAwalObs),
                        String.valueOf(actReceiptObs),
                        String.valueOf(blendingInObs),
                        String.valueOf(totalTersediaObs),
                        String.valueOf(salesPsoObs),
                        String.valueOf(salesNpsoObs),
                        String.valueOf(konsinyasiObs),
                        String.valueOf(ownUseTbbmObs),
                        String.valueOf(ownUseKapalObs),
                        String.valueOf(totalPenyaluranObs),
                        String.valueOf(blendingOutObs),
                        String.valueOf(stockAkhirObs),
                        String.valueOf(quantityObs),
                        String.valueOf(percentageObs)
                );
                workingLossObs.setSatuan("Liter Obs");
                ArrayList<WorkingLoss> workingLosses = new ArrayList<>();
                workingLosses.add(workingLoss);
                workingLosses.add(workingLoss60f);
                workingLosses.add(workingLossObs);

                sendPostRequest(workingLosses);
            }
        });
    }

    private void sendPostRequest(ArrayList<WorkingLoss> workingLosses) {
        Log.w("input size", String.valueOf(workingLosses.size()));

        SharedPreferences preferences = InputWorkingLossActivity.this.getSharedPreferences(
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

        String json = new Gson().toJson(workingLosses);
        Log.w("json", json);

        OkHttpClient client = httpClient.build();

        String baseUrl = "http://www.api.clicktuban.com/";
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client);

        Retrofit retrofit = builder.build();
        QqClient qqClient = retrofit.create(QqClient.class);

        Call<Object> call = qqClient.postWorkingLoss(workingLosses);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.w("post req code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    kirim.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    Log.w("response size", response.body().toString());
                    Toast.makeText(InputWorkingLossActivity.this, "Data berhasil ditambahkan", Toast.LENGTH_LONG).show();
                    NavUtils.navigateUpTo(InputWorkingLossActivity.this, new Intent(getApplicationContext(), WorkingLossActivity.class));
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.w("error", t.getMessage());
                kirim.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(InputWorkingLossActivity.this, "Gagal mengirim data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleInputChange() {
        stockAwalInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty())
                    stockAwal = Double.parseDouble(charSequence.toString());
                setTotalTersedia();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        actReceiptInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty())
                    actReceipt = Double.parseDouble(charSequence.toString());
                setTotalTersedia();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        blendingInInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty())
                    blendingIn = Double.parseDouble(charSequence.toString());
                setTotalTersedia();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        salesPsoInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty())
                    salesPso = Double.parseDouble(charSequence.toString());
                setTotalPenyaluran();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        salesNpsoInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty())
                    salesNpso = Double.parseDouble(charSequence.toString());
                setTotalPenyaluran();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        konsinyasiInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty())
                    konsinyasi = Double.parseDouble(charSequence.toString());
                setTotalPenyaluran();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ownUseTbbmInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty())
                    ownUseTbbm = Double.parseDouble(charSequence.toString());
                setTotalPenyaluran();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ownUseKapalInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty())
                    ownUseKapal = Double.parseDouble(charSequence.toString());
                setTotalPenyaluran();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        blendingOutInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty())
                    blendingOut = Double.parseDouble(charSequence.toString());
                setWorkingLoss();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        stockAkhirInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty())
                    stockAkhir = Double.parseDouble(charSequence.toString());
                setWorkingLoss();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void handleInputChange60f() {
        stockAwalInput60f.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty())
                    stockAwal60f = Double.parseDouble(charSequence.toString());
                setTotalTersedia60f();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        actReceiptInput60f.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty())
                    actReceipt60f = Double.parseDouble(charSequence.toString());
                setTotalTersedia60f();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        blendingInInput60f.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty())
                    blendingIn60f = Double.parseDouble(charSequence.toString());
                setTotalTersedia60f();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        salesPsoInput60f.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty())
                    salesPso60f = Double.parseDouble(charSequence.toString());
                setTotalPenyaluran60f();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        salesNpsoInput60f.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty())
                    salesNpso60f = Double.parseDouble(charSequence.toString());
                setTotalPenyaluran60f();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        konsinyasiInput60f.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty())
                    konsinyasi60f = Double.parseDouble(charSequence.toString());
                setTotalPenyaluran60f();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ownUseTbbmInput60f.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty())
                    ownUseTbbm60f = Double.parseDouble(charSequence.toString());
                setTotalPenyaluran60f();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ownUseKapalInput60f.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty())
                    ownUseKapal60f = Double.parseDouble(charSequence.toString());
                setTotalPenyaluran60f();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        blendingOutInput60f.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty())
                    blendingOut60f = Double.parseDouble(charSequence.toString());
                setWorkingLoss60f();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        stockAkhirInput60f.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty())
                    stockAkhir60f = Double.parseDouble(charSequence.toString());
                setWorkingLoss60f();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void handleInputChangeObs() {
        stockAwalInputObs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty())
                    stockAwalObs = Double.parseDouble(charSequence.toString());
                setTotalTersediaObs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        actReceiptInputObs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty())
                    actReceiptObs = Double.parseDouble(charSequence.toString());
                setTotalTersediaObs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        blendingInInputObs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty())
                    blendingInObs = Double.parseDouble(charSequence.toString());
                setTotalTersediaObs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        salesPsoInputObs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty())
                    salesPsoObs = Double.parseDouble(charSequence.toString());
                setTotalPenyaluranObs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        salesNpsoInputObs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty())
                    salesNpsoObs = Double.parseDouble(charSequence.toString());
                setTotalPenyaluranObs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        konsinyasiInputObs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty())
                    konsinyasiObs = Double.parseDouble(charSequence.toString());
                setTotalPenyaluranObs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ownUseTbbmInputObs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty())
                    ownUseTbbmObs = Double.parseDouble(charSequence.toString());
                setTotalPenyaluranObs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ownUseKapalInputObs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty())
                    ownUseKapalObs = Double.parseDouble(charSequence.toString());
                setTotalPenyaluranObs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        blendingOutInputObs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty())
                    blendingOutObs = Double.parseDouble(charSequence.toString());
                setWorkingLossObs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        stockAkhirInputObs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty())
                    stockAkhirObs = Double.parseDouble(charSequence.toString());
                setWorkingLossObs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void setTotalTersedia() {
        totalTersedia = stockAwal + actReceipt + blendingIn;
        totalTersediaInput.setText(String.valueOf(totalTersedia));
        setWorkingLoss();
    }

    private void setTotalPenyaluran() {
        totalPenyaluran = salesPso + salesNpso + konsinyasi + ownUseTbbm + ownUseKapal;
        totalPenyaluranInput.setText(String.valueOf(totalPenyaluran));
        setWorkingLoss();
    }

    private void setQuantity() {
        quantity = totalPenyaluran + blendingOut + stockAkhir - totalTersedia;
        quantityInput.setText(String.valueOf(quantity));
    }

    private void setPercentage() {
        percentage = quantity / totalTersedia;
        percentageInput.setText(String.valueOf(percentage * 100 + " %"));
    }

    private void setWorkingLoss() {
        setQuantity();
        setPercentage();
    }

    private void setTotalTersedia60f() {
        totalTersedia60f = stockAwal60f + actReceipt60f + blendingIn60f;
        totalTersediaInput60f.setText(String.valueOf(totalTersedia60f));
        setWorkingLoss60f();
    }

    private void setTotalPenyaluran60f() {
        totalPenyaluran60f = salesPso60f + salesNpso60f + konsinyasi60f + ownUseTbbm60f + ownUseKapal60f;
        totalPenyaluranInput60f.setText(String.valueOf(totalPenyaluran60f));
        setWorkingLoss60f();
    }

    private void setQuantity60f() {
        quantity60f = totalPenyaluran60f + blendingOut60f + stockAkhir60f - totalTersedia60f;
        quantityInput60f.setText(String.valueOf(quantity60f));
    }

    private void setPercentage60f() {
        percentage60f = quantity60f / totalTersedia60f;
        percentageInput60f.setText(String.valueOf(percentage60f * 100 + " %"));
    }

    private void setWorkingLoss60f() {
        setQuantity60f();
        setPercentage60f();
    }

    private void setTotalTersediaObs() {
        totalTersediaObs = stockAwalObs + actReceiptObs + blendingInObs;
        totalTersediaInputObs.setText(String.valueOf(totalTersediaObs));
        setWorkingLossObs();
    }

    private void setTotalPenyaluranObs() {
        totalPenyaluranObs = salesPsoObs + salesNpsoObs + konsinyasiObs + ownUseTbbmObs + ownUseKapalObs;
        totalPenyaluranInputObs.setText(String.valueOf(totalPenyaluranObs));
        setWorkingLossObs();
    }

    private void setQuantityObs() {
        quantityObs = totalPenyaluranObs + blendingOutObs + stockAkhirObs - totalTersediaObs;
        quantityInputObs.setText(String.valueOf(quantityObs));
    }

    private void setPercentageObs() {
        percentageObs = quantityObs / totalTersediaObs;
        percentageInputObs.setText(String.valueOf(percentageObs * 100 + " %"));
    }

    private void setWorkingLossObs() {
        setQuantityObs();
        setPercentageObs();
    }

    private void initView() {
        stockAwalInput = findViewById(R.id.input_working_loss_stock_awal);
        actReceiptInput = findViewById(R.id.input_working_loss_receipt);
        blendingInInput = findViewById(R.id.input_working_loss_blending_in);
        salesPsoInput = findViewById(R.id.input_working_loss_sales_pso);
        salesNpsoInput = findViewById(R.id.input_working_loss_sales_npso);
        konsinyasiInput = findViewById(R.id.input_working_loss_konsinyasi);
        ownUseTbbmInput = findViewById(R.id.input_working_loss_ownuse_tbbm);
        ownUseKapalInput = findViewById(R.id.input_working_loss_ownuse_kapal);
        blendingOutInput = findViewById(R.id.input_working_loss_blending_out);
        stockAkhirInput = findViewById(R.id.input_working_loss_stock_akhir);
        totalTersediaInput = findViewById(R.id.input_working_loss_total_tersedia);
        totalPenyaluranInput = findViewById(R.id.input_working_loss_total_penyaluran);
        percentageInput = findViewById(R.id.input_working_loss_percentage);
        quantityInput = findViewById(R.id.input_working_loss_quantity);
        produkInput = findViewById(R.id.input_working_loss_spinner);
        kirim = findViewById(R.id.input_working_loss_kirim);
        progressBar = findViewById(R.id.input_working_loss_progress);

        stockAwalInput60f = findViewById(R.id.input_working_loss_stock_awal_60f);
        actReceiptInput60f = findViewById(R.id.input_working_loss_receipt_60f);
        blendingInInput60f = findViewById(R.id.input_working_loss_blending_in_60f);
        salesPsoInput60f = findViewById(R.id.input_working_loss_sales_pso_60f);
        salesNpsoInput60f = findViewById(R.id.input_working_loss_sales_npso_60f);
        konsinyasiInput60f = findViewById(R.id.input_working_loss_konsinyasi_60f);
        ownUseTbbmInput60f = findViewById(R.id.input_working_loss_ownuse_tbbm_60f);
        ownUseKapalInput60f = findViewById(R.id.input_working_loss_ownuse_kapal_60f);
        blendingOutInput60f = findViewById(R.id.input_working_loss_blending_out_60f);
        stockAkhirInput60f = findViewById(R.id.input_working_loss_stock_akhir_60f);
        totalTersediaInput60f = findViewById(R.id.input_working_loss_total_tersedia_60f);
        totalPenyaluranInput60f = findViewById(R.id.input_working_loss_total_penyaluran_60f);
        percentageInput60f = findViewById(R.id.input_working_loss_percentage_60f);
        quantityInput60f = findViewById(R.id.input_working_loss_quantity_60f);

        stockAwalInputObs = findViewById(R.id.input_working_loss_stock_awal_obs);
        actReceiptInputObs = findViewById(R.id.input_working_loss_receipt_obs);
        blendingInInputObs = findViewById(R.id.input_working_loss_blending_in_obs);
        salesPsoInputObs = findViewById(R.id.input_working_loss_sales_pso_obs);
        salesNpsoInputObs = findViewById(R.id.input_working_loss_sales_npso_obs);
        konsinyasiInputObs = findViewById(R.id.input_working_loss_konsinyasi_obs);
        ownUseTbbmInputObs = findViewById(R.id.input_working_loss_ownuse_tbbm_obs);
        ownUseKapalInputObs = findViewById(R.id.input_working_loss_ownuse_kapal_obs);
        blendingOutInputObs = findViewById(R.id.input_working_loss_blending_out_obs);
        stockAkhirInputObs = findViewById(R.id.input_working_loss_stock_akhir_obs);
        totalTersediaInputObs = findViewById(R.id.input_working_loss_total_tersedia_obs);
        totalPenyaluranInputObs = findViewById(R.id.input_working_loss_total_penyaluran_obs);
        percentageInputObs = findViewById(R.id.input_working_loss_percentage_obs);
        quantityInputObs = findViewById(R.id.input_working_loss_quantity_obs);
    }

}