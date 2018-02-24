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

    private WorkingLoss workingLoss;

    private double stockAwal = 0, actReceipt = 0, blendingIn = 0, totalTersedia = 0,
    salesPso = 0, salesNpso = 0, konsinyasi = 0, ownUseTbbm = 0, ownUseKapal = 0, totalPenyaluran = 0, blendingOut = 0,
    quantity = 0, percentage = 0, stockAkhir = 0;

    private EditText stockAwalInput, actReceiptInput, blendingInInput,
            salesPsoInput, salesNpsoInput, konsinyasiInput, ownUseTbbmInput,
            ownUseKapalInput, blendingOutInput, stockAkhirInput;

    private TextView lokasiInput, totalTersediaInput, totalPenyaluranInput, percentageInput, quantityInput;

    private Spinner produkInput;
    private Button kirim;
    private ArrayList<String> fuelList;
    private int fuelIndex;

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
                Date date = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
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
                ArrayList<WorkingLoss> workingLosses = new ArrayList<>();
                workingLosses.add(workingLoss);

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
                    Log.w("response size", response.body().toString());
                    Toast.makeText(InputWorkingLossActivity.this, "Data berhasil ditambahkan", Toast.LENGTH_LONG).show();
                    NavUtils.navigateUpTo(InputWorkingLossActivity.this, new Intent(getApplicationContext(), WorkingLossActivity.class));
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.w("error", t.getMessage());
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
    }

}
