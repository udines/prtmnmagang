package com.pertamina.pertaminatuban.distribusi;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.models.Matbal;
import com.pertamina.pertaminatuban.service.UserClient;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InputMatbalActivity extends AppCompatActivity {

    private int year, month, day;
    private Button dateButton, kirim;
    private boolean tanggalSet, isUpdate;

    private EditText inputPertamax, inputPertalite, inputPremium, inputSolar, inputBiosolar, inputBioflame,
            inputLain, inputLainNilai;
    private boolean pertamaxUpdate, pertaliteUpdate, premiumUpdate, solarUpdate, biosolarUpdate, biofameUpdate;
    private ProgressBar progressBar;
    private boolean postSuccess, putSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_matbal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*inisialisasi view yang digunakan lebih dari satu fungsi*/
        dateButton = findViewById(R.id.input_matbal_tanggal);
        progressBar = findViewById(R.id.input_matbal_progress);
        kirim = findViewById(R.id.input_matbal_kirim);

        inputPertamax = findViewById(R.id.input_matbal_pertamax);
        inputPertalite = findViewById(R.id.input_matbal_pertalite);
        inputPremium = findViewById(R.id.input_matbal_premium);
        inputSolar = findViewById(R.id.input_matbal_solar);
        inputBiosolar = findViewById(R.id.input_matbal_biosolar);
        inputBioflame = findViewById(R.id.input_matbal_bioflame);
        inputLain = findViewById(R.id.input_matbal_lain);
        inputLainNilai = findViewById(R.id.input_matbal_lain_nilai);

        /*inisialisasi tanggal sesuai dengan tanggal hari ini*/
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        handleDatePicker();

        getData();
    }

    private void setBooleanUpdates() {
        pertamaxUpdate = isTerisi(inputPertamax);
        pertaliteUpdate = isTerisi(inputPertalite);
        premiumUpdate = isTerisi(inputPremium);
        solarUpdate = isTerisi(inputSolar);
        biosolarUpdate = isTerisi(inputBiosolar);
        biofameUpdate = isTerisi(inputBioflame);
    }

    private boolean isTerisi(EditText editText) {
        return editText.getText().length() > 0;
    }

    private void getData() {

        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tanggalSet) {
                    ArrayList<Matbal> matbalPost = new ArrayList<>();
                    ArrayList<Matbal> matbalPut = new ArrayList<>();

                    if (!isNull(inputPertamax)) {
                        if (pertamaxUpdate) {
                            matbalPut.add(makeObject(inputPertamax, Matbal.PERTAMAX));
                        } else {
                            matbalPost.add(makeObject(inputPertamax, Matbal.PERTAMAX));
                        }
                    }
                    if (!isNull(inputPertalite)) {
                        if (pertaliteUpdate) {
                            matbalPut.add(makeObject(inputPertalite, Matbal.PERTALITE));
                        } else {
                            matbalPost.add(makeObject(inputPertalite, Matbal.PERTALITE));
                        }
                    }
                    if (!isNull(inputPremium)) {
                        if (premiumUpdate) {
                            matbalPut.add(makeObject(inputPremium, Matbal.PREMIUM));
                        } else {
                            matbalPost.add(makeObject(inputPremium, Matbal.PREMIUM));
                        }
                    }
                    if (!isNull(inputSolar)) {
                        if (solarUpdate) {
                            matbalPut.add(makeObject(inputSolar, Matbal.SOLAR));
                        } else {
                            matbalPost.add(makeObject(inputSolar, Matbal.SOLAR));
                        }
                    }
                    if (!isNull(inputBiosolar)) {
                        if (biosolarUpdate) {
                            matbalPut.add(makeObject(inputBiosolar, Matbal.BIOSOLAR));
                        } else {
                            matbalPost.add(makeObject(inputBiosolar, Matbal.BIOSOLAR));
                        }
                    }
                    if (!isNull(inputBioflame)) {
                        if (biofameUpdate) {
                            matbalPut.add(makeObject(inputBioflame, Matbal.BIOFAME));
                        } else {
                            matbalPost.add(makeObject(inputBioflame, Matbal.BIOFAME));
                        }
                    }
                    if (!isNull(inputLain) && !isNull(inputLainNilai)) {
                        matbalPost.add(makeObject(inputLainNilai, inputLain.getText().toString()));
                    }


                    sendUpdateRequest(matbalPost);
                    sendPostRequest(matbalPost);

                } else {
                    Toast.makeText(InputMatbalActivity.this, "Belum memilih tanggal", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendUpdateRequest(ArrayList<Matbal> matbals) {
        //update matbals

        kirim.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        SharedPreferences preferences = InputMatbalActivity.this.getSharedPreferences(
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

        String json = new Gson().toJson(matbals);
        Log.w("json", json);

        OkHttpClient client = httpClient.build();

        String baseUrl = "http://www.api.clicktuban.com/";
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client);

        Retrofit retrofit = builder.build();
        UserClient userClient = retrofit.create(UserClient.class);

        Call<Object> call = userClient.updateMatbalTanggal(matbals);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    kirim.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    putSuccess = true;

                    Log.w("response size", response.body().toString());

                    if (putSuccess && postSuccess) {
                        Toast.makeText(InputMatbalActivity.this, "Data berhasil ditambahkan", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.w("error", t.getMessage());
                kirim.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void getInitialInputData() {

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        Date date = new Date(cal.getTimeInMillis());
        String tanggalSekarang;
        tanggalSekarang = date.toString();
        Log.w("tanggal", tanggalSekarang);

        SharedPreferences preferences = InputMatbalActivity.this.getSharedPreferences(
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

        Call<ArrayList<Matbal>> call = userClient.getMatbalHari(tanggalSekarang);
        call.enqueue(new Callback<ArrayList<Matbal>>() {
            @Override
            public void onResponse(Call<ArrayList<Matbal>> call, Response<ArrayList<Matbal>> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200 && response.body().size() > 0) {
                    Log.w("data", new Gson().toJson(response.body()));
                    ArrayList<Matbal> matbals = new ArrayList<>();
                    ArrayList<Matbal> body = response.body();
                    for (int i = 0; i < body.size(); i++) {
                        if (body.get(i).getNilai() > 0) {
                            matbals.add(body.get(i));
                        }
                    }
                    setInitialInputData(matbals);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Matbal>> call, Throwable t) {

            }
        });
    }

    private void setInitialInputData(ArrayList<Matbal> matbals) {
        if (matbals.size() > 0) {
            isUpdate = true;
            for (int i = 0; i < matbals.size(); i++) {
                String nilai = String.valueOf(matbals.get(i).getNilai());
                switch (matbals.get(i).getFuel()) {
                    case Matbal.BIOFAME:
                        inputBioflame.setText(nilai);
                        break;
                    case Matbal.BIOSOLAR:
                        inputBiosolar.setText(nilai);
                        break;
                    case Matbal.PERTALITE:
                        inputPertalite.setText(nilai);
                        break;
                    case Matbal.PERTAMAX:
                        inputPertamax.setText(nilai);
                        break;
                    case Matbal.PREMIUM:
                        inputPremium.setText(nilai);
                        break;
                    case Matbal.SOLAR:
                        inputSolar.setText(nilai);
                        break;
                }
            }

            //mencek apakah input sudah terisi atau belum guna menentukan jenis request
            setBooleanUpdates();

        } else {
            isUpdate = false;
        }
        Log.w("is update", String.valueOf(isUpdate));
    }

    private void sendPostRequest(ArrayList<Matbal> matbals) {
        Log.w("input size", String.valueOf(matbals.size()));

        kirim.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        Log.w("post body", new Gson().toJson(matbals));

        SharedPreferences preferences = InputMatbalActivity.this.getSharedPreferences(
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

        String json = new Gson().toJson(matbals);
        Log.w("json", json);

        OkHttpClient client = httpClient.build();

        String baseUrl = "http://www.api.clicktuban.com/";
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client);

        Retrofit retrofit = builder.build();
        UserClient userClient = retrofit.create(UserClient.class);

        Call<Object> call = userClient.postMatbal(matbals);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    kirim.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    postSuccess = true;

                    Log.w("response size", response.body().toString());
                    if (postSuccess && putSuccess) {
                        Toast.makeText(InputMatbalActivity.this, "Data berhasil ditambahkan", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.w("error", t.getMessage());
                kirim.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private float getInputFloat(EditText editText) {
        return Float.parseFloat(editText.getText().toString());
    }

    private boolean isNull(EditText editText) {
        return editText.getText().toString().isEmpty();
    }

    private Matbal makeObject(EditText editText, String fuel) {
        float nilai;
        if (!isNull(editText)) {
            nilai = getInputFloat(editText);
        } else {
            nilai = 0;
        }
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String date = format.format(new Date(cal.getTimeInMillis()));
        return new Matbal(date, fuel, nilai);
    }

    private void setDateButton(int year, int month, int day) {
        dateButton.setText(String.valueOf(year + "-" + month + 1 + "-" + day));
    }

    private void handleDatePicker() {
        final DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                year = i;
                month = i1;
                day = i2;
                setDateButton(year, month, day);
                tanggalSet = true;
                clearInput();
                getInitialInputData();
            }
        };

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.w("button", "date button clicked");
                DatePickerDialog dialog = new DatePickerDialog(
                        InputMatbalActivity.this,
                        listener,
                        year,
                        month,
                        day
                );
                dialog.show();
            }
        });
    }

    private void clearInput() {
        inputPertamax.setText("");
        inputPertalite.setText("");
        inputPremium.setText("");
        inputSolar.setText("");
        inputBiosolar.setText("");
        inputBioflame.setText("");
        inputLain.setText("");
        inputLainNilai.setText("");
    }

}
