package com.pertamina.pertaminatuban.finance.perjalanan_dinas;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.finance.models.TarifAntarkota;
import com.pertamina.pertaminatuban.finance.models.UraianPerjalanan;
import com.pertamina.pertaminatuban.service.FinanceClient;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TambahRincianActivity extends AppCompatActivity {

    private Spinner spinnerJenis;
    private Button buttonTambahkan, buttonTanggal;
    private TextView textTotal;
    private LinearLayout containerAntarKota, containerTiketPesawat, containerUangHarian, containerUangMakan,
    containerTransport, containerAkomodasiLaundry;

    private long total = 0;
    private int year, month, day;
    private String noPerjalanan;
    private SimpleDateFormat dateFormat;
    private Spinner spinnerProvinsi, spinnerKota, spinnerDarike;

    private HashMap<String, String> jenisClaimValueKey, jenisClaimKeyValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_rincian);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        containerUangHarian = findViewById(R.id.tambah_rincian_container_uang_harian);
        containerUangMakan = findViewById(R.id.tambah_rincian_container_uang_makan_harian);
        containerAntarKota = findViewById(R.id.tambah_rincian_container_antar_kota);
        containerTiketPesawat = findViewById(R.id.tambah_rincian_container_tiket_pesawat);
        containerTransport = findViewById(R.id.tambah_rincian_container_transport);
        containerAkomodasiLaundry = findViewById(R.id.tambah_rincian_container_akomodasi_laundry);

        buttonTambahkan = findViewById(R.id.tambah_rincian_button_tambahkan);
        buttonTanggal = findViewById(R.id.tambah_rincian_button_tanggal);
        textTotal = findViewById(R.id.tambah_rincian_total);

        noPerjalanan = getIntent().getStringExtra("noPerjalanan");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        setHashMaps();
        handleSpinnerJenis();
        handleButtonTanggal();
        handleButtonTambahkan();
    }

    private void setHashMaps() {
        jenisClaimValueKey = new HashMap<>();
        jenisClaimValueKey.put("Transport antar kota", "kota");
        jenisClaimValueKey.put("Transport Bandara/stasiun/pelabuhan/Terminal", "umum");
        jenisClaimValueKey.put("Akomodasi dan Laundry", "akomodasi");
        jenisClaimValueKey.put("Uang Harian", "hari");
        jenisClaimValueKey.put("Transport Lokal", "lokal");
        jenisClaimValueKey.put("Tiket Pesawat", "tiket");
        jenisClaimValueKey.put("Uang Makan Harian", "makan");

        jenisClaimKeyValue = new HashMap<>();
        jenisClaimKeyValue.put("kota", "Transport antar kota");
        jenisClaimKeyValue.put("umum", "Transport Bandara/stasiun/pelabuhan/Terminal");
        jenisClaimKeyValue.put("akomodasi", "Akomodasi dan Laundry");
        jenisClaimKeyValue.put("hari", "Uang Harian");
        jenisClaimKeyValue.put("lokal", "Transport Lokal");
        jenisClaimKeyValue.put("tiket", "Tiket Pesawat");
        jenisClaimKeyValue.put("makan", "Uang Makan Harian");
    }

    private void handleButtonTambahkan() {
        buttonTambahkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (spinnerJenis.getSelectedItemPosition()) {
                    case 0:
                        getDataAntarKota();
                        break;
                    case 1:
                        getDataTransport();
                        break;
                    case 2:
                        getDataAkomodasi();
                        break;
                    case 3:
                        getDataMakanHarian();
                        break;
                    case 4:
                        break;
                    case 5:
                        getDataUangHarian();
                        break;
                    case 6:
                        getDataTiketPesawat();
                        break;
                    case 7:
                        break;
                }
            }
        });
    }

    private void getDataTransport() {
        EditText inputKeterangan = findViewById(R.id.tambah_rincian_transport_keterangan);
        String keterangan = "";
        if (inputKeterangan.getText().length() > 0) {
            keterangan = inputKeterangan.getText().toString();
        }
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        UraianPerjalanan uraian = new UraianPerjalanan(
                noPerjalanan,
                dateFormat.format(new Date(cal.getTimeInMillis())),
                String.valueOf(total),
                keterangan,
                jenisClaimValueKey.get(spinnerJenis.getSelectedItem().toString())
        );
        sendPostRequest(uraian);
    }

    private void getDataTiketPesawat() {
        EditText inputKeterangan = findViewById(R.id.tambah_rincian_tiket_pesawat_keterangan);
        String keterangan = "";
        if (inputKeterangan.getText().length() > 0) {
            keterangan = inputKeterangan.getText().toString();
        }
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        UraianPerjalanan uraian = new UraianPerjalanan(
                noPerjalanan,
                dateFormat.format(new Date(cal.getTimeInMillis())),
                String.valueOf(total),
                keterangan,
                jenisClaimValueKey.get(spinnerJenis.getSelectedItem().toString())
        );
        sendPostRequest(uraian);
    }

    private void getDataUangHarian() {
        EditText inputKeterangan = findViewById(R.id.tambah_rincian_uang_harian_keterangan);
        String keterangan = "";
        if (inputKeterangan.getText().length() > 0) {
            keterangan = inputKeterangan.getText().toString();
        }
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        UraianPerjalanan uraian = new UraianPerjalanan(
                noPerjalanan,
                dateFormat.format(new Date(cal.getTimeInMillis())),
                String.valueOf(total),
                keterangan,
                jenisClaimValueKey.get(spinnerJenis.getSelectedItem().toString())
        );
        sendPostRequest(uraian);
    }

    private void getDataMakanHarian() {
        CheckBox pagi, siang, malam;
        pagi = findViewById(R.id.tambah_rincian_uang_makan_checkbox_pagi);
        siang = findViewById(R.id.tambah_rincian_uang_makan_checkbox_siang);
        malam = findViewById(R.id.tambah_rincian_uang_makan_checkbox_malam);

        String keterangan = "";
        if (pagi.isChecked()) {
            keterangan = keterangan + "pagi ";
        }
        if (siang.isChecked()) {
            keterangan = keterangan + "siang ";
        }
        if (malam.isChecked()) {
            keterangan = keterangan + "malam ";
        }

        Log.w("keterangan", keterangan);

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        UraianPerjalanan uraian = new UraianPerjalanan(
                noPerjalanan,
                dateFormat.format(new Date(cal.getTimeInMillis())),
                String.valueOf(total),
                keterangan,
                jenisClaimValueKey.get(spinnerJenis.getSelectedItem().toString())
        );
        sendPostRequest(uraian);
    }

    private void getDataAkomodasi() {
        EditText inputKeterangan = findViewById(R.id.tambah_rincian_akomodasi_laundry_keterangan);
        String keterangan = "";
        if (inputKeterangan.getText().length() > 0) {
            keterangan = inputKeterangan.getText().toString();
        }
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        UraianPerjalanan uraian = new UraianPerjalanan(
                noPerjalanan,
                dateFormat.format(new Date(cal.getTimeInMillis())),
                String.valueOf(total),
                keterangan,
                jenisClaimValueKey.get(spinnerJenis.getSelectedItem().toString())
        );

        sendPostRequest(uraian);
    }

    private void getDataAntarKota() {
        EditText inputKeterangan = findViewById(R.id.tambah_rincian_antar_kota_keterangan);
        String keterangan = "";
        if (inputKeterangan.getText().length() > 0) {
            keterangan = inputKeterangan.getText().toString();
        }
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        UraianPerjalanan uraian = new UraianPerjalanan(
                noPerjalanan,
                dateFormat.format(new Date(cal.getTimeInMillis())),
                String.valueOf(total),
                keterangan,
                jenisClaimValueKey.get(spinnerJenis.getSelectedItem().toString())
        );

        sendPostRequest(uraian);
    }

    private void sendPostRequest(UraianPerjalanan uraian) {
        Log.w("uraian json", new Gson().toJson(uraian));

        SharedPreferences preferences = TambahRincianActivity.this.getSharedPreferences(
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
        FinanceClient financeClient = retrofit.create(FinanceClient.class);
        Call<Object> call = financeClient.postUraianPerjalanan(uraian);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    Log.w("response", response.body().toString());
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(TambahRincianActivity.this, "Gagal mengirim claim", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleButtonTanggal() {
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        setDateButton(year, month, day);

        buttonTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        year = i;
                        month = i1;
                        day = i2;
                        setDateButton(year, month, day);
                    }
                };

                DatePickerDialog dialog = new DatePickerDialog(
                        TambahRincianActivity.this,
                        listener,
                        year,
                        month,
                        day
                );
                dialog.show();
            }
        });
    }

    private void setDateButton(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        buttonTanggal.setText(format.format(new Date(cal.getTimeInMillis())));
    }

    private void clearContainer() {
        total = 0;
        updateTotal();

        containerUangHarian.setVisibility(View.GONE);
        containerUangMakan.setVisibility(View.GONE);
        containerAntarKota.setVisibility(View.GONE);
        containerTiketPesawat.setVisibility(View.GONE);
        containerTransport.setVisibility(View.GONE);
        containerAkomodasiLaundry.setVisibility(View.GONE);
    }

    private void updateTotal() {
        DecimalFormat formatter = new DecimalFormat("#,###");
        textTotal.setText(String.valueOf("Total: Rp" + formatter.format(total)));
    }

    private void handleSpinnerJenis() {
        spinnerJenis = findViewById(R.id.tambah_rincian_jenis);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.jenis_claim_trip, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJenis.setAdapter(adapter);
        spinnerJenis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        clearContainer();
                        containerAntarKota.setVisibility(View.VISIBLE);
                        handleAntarKota();
                        break;
                    case 1:
                        clearContainer();
                        containerTransport.setVisibility(View.VISIBLE);
                        handleTransport();
                        break;
                    case 2:
                        clearContainer();
                        containerAkomodasiLaundry.setVisibility(View.VISIBLE);
                        handleAkomodasiLaundry();
                        break;
                    case 3:
                        clearContainer();
                        containerUangMakan.setVisibility(View.VISIBLE);
                        handleUangMakan();
                        break;
                    case 4:
                        clearContainer();
                        break;
                    case 5:
                        clearContainer();
                        containerUangHarian.setVisibility(View.VISIBLE);
                        handleUangHarian();
                        break;
                    case 6:
                        clearContainer();
                        containerTiketPesawat.setVisibility(View.VISIBLE);
                        handleTiketPesawat();
                        break;
                    case 7:
                        clearContainer();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void handleTransport() {
        spinnerProvinsi = findViewById(R.id.tambah_rincian_transport_spinner_provinsi);
        spinnerKota = findViewById(R.id.tambah_rincian_transport_spinner_kota);
        spinnerDarike = findViewById(R.id.tambah_rincian_transport_spinner_darike);

        SharedPreferences preferences = TambahRincianActivity.this.getSharedPreferences(
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
        FinanceClient financeClient = retrofit.create(FinanceClient.class);
        Call<ArrayList<TarifAntarkota>> call = financeClient.getTarif();
        call.enqueue(new Callback<ArrayList<TarifAntarkota>>() {
            @Override
            public void onResponse(Call<ArrayList<TarifAntarkota>> call, Response<ArrayList<TarifAntarkota>> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    Log.w("body", new Gson().toJson(response.body()));
                    handleSpinnerProvinsi(response.body(), spinnerProvinsi);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<TarifAntarkota>> call, Throwable t) {

            }
        });
    }

    private void handleSpinnerProvinsi(final ArrayList<TarifAntarkota> tarifList, final Spinner spinner) {

        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < tarifList.size(); i++) {
            list.add(tarifList.get(i).getProvinsi());
        }
        Set<String> uniqueList = new HashSet<String>(list);
        list = new ArrayList<>();
        list.addAll(uniqueList);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, list
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String provinsi = spinner.getSelectedItem().toString();
                ArrayList<TarifAntarkota> tarifListKota = new ArrayList<>();
                for (int j = 0; j < tarifList.size(); j++) {
                    if (tarifList.get(j).getProvinsi().equals(provinsi)) {
                        tarifListKota.add(tarifList.get(j));
                    }
                }
                handleSpinnerKota(tarifListKota, spinnerKota);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void handleSpinnerKota(final ArrayList<TarifAntarkota> tarifList, final Spinner spinner) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < tarifList.size(); i++) {
            list.add(tarifList.get(i).getKota());
        }
        Set<String> uniqueList = new HashSet<String>(list);
        list = new ArrayList<>();
        list.addAll(uniqueList);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, list
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String kota = spinner.getSelectedItem().toString();
                ArrayList<TarifAntarkota> tarifListNode = new ArrayList<>();
                for (int j = 0; j < tarifList.size(); j++) {
                    if (tarifList.get(j).getKota().equals(kota)) {
                        tarifListNode.add(tarifList.get(j));
                    }
                }
                handleSpinnerNode(tarifListNode, spinnerDarike);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void handleSpinnerNode(final ArrayList<TarifAntarkota> tarifList, Spinner spinner) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < tarifList.size(); i++) {
            list.add(tarifList.get(i).getDarike());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, list
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                total = tarifList.get(i).getTarif();
                updateTotal();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void handleTiketPesawat() {
        final EditText inputJumlahTiket, inputHargaTiket;
        inputJumlahTiket = findViewById(R.id.tambah_rincian_tiket_pesawat_jumlah);
        inputHargaTiket = findViewById(R.id.tambah_rincian_tiket_pesawat_harga);
        inputJumlahTiket.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int jumlahTiket;
                if (charSequence.length() > 0) {
                    jumlahTiket = Integer.parseInt(charSequence.toString());
                    if (inputHargaTiket.getText().length() > 0) {
                        total = jumlahTiket * Long.parseLong(inputHargaTiket.getText().toString());
                    }
                }
                updateTotal();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        inputHargaTiket.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int hargaTiket;
                if (charSequence.length() > 0) {
                    hargaTiket = Integer.parseInt(charSequence.toString());
                    if (inputJumlahTiket.getText().length() > 0) {
                        total = hargaTiket * Long.parseLong(inputJumlahTiket.getText().toString());
                    }
                }
                updateTotal();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void handleUangHarian() {
        final EditText inputJumlahHari, inputNominal;
        inputJumlahHari = findViewById(R.id.tambah_rincian_uang_harian_jml_hari);
        inputNominal = findViewById(R.id.tambah_rincian_uang_harian_nominal);
        inputJumlahHari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int jumlahHari;
                if (charSequence.length() > 0) {
                    jumlahHari = Integer.parseInt(charSequence.toString());
                    if (inputNominal.getText().length() > 0) {
                        total = jumlahHari * Long.parseLong(inputNominal.getText().toString());
                    }
                }
                updateTotal();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputNominal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int nominal;
                if (charSequence.length() > 0) {
                    nominal = Integer.parseInt(charSequence.toString());
                    if (inputJumlahHari.getText().length() > 0) {
                        total = nominal * Long.parseLong(inputJumlahHari.getText().toString());
                    }
                }
                updateTotal();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void handleUangMakan() {
        CheckBox pagi, siang, malam;
        pagi = findViewById(R.id.tambah_rincian_uang_makan_checkbox_pagi);
        siang = findViewById(R.id.tambah_rincian_uang_makan_checkbox_siang);
        malam = findViewById(R.id.tambah_rincian_uang_makan_checkbox_malam);
        final long biayaMakan = 75000;

        pagi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    total = total + biayaMakan;
                } else {
                    total = total - biayaMakan;
                }
                updateTotal();
            }
        });
        siang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    total = total + biayaMakan;
                } else {
                    total = total - biayaMakan;
                }
                updateTotal();
            }
        });
        malam.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    total = total + biayaMakan;
                } else {
                    total = total - biayaMakan;
                }
                updateTotal();
            }
        });
    }

    private void handleAkomodasiLaundry() {
        final EditText inputAkomodasi = findViewById(R.id.tambah_rincian_akomodasi);
        final EditText inputLaundry = findViewById(R.id.tambah_rincian_laundry);
        inputAkomodasi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                long totalLaundry = 0;
                if (inputLaundry.getText().length() > 0) {{
                    totalLaundry = Long.parseLong(inputLaundry.getText().toString());
                }}
                if (charSequence.length() > 0) {
                    total = Long.parseLong(charSequence.toString()) + totalLaundry;
                } else {
                    total = totalLaundry;
                }
                updateTotal();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputLaundry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                long totalAkomodasi = 0;
                if (inputAkomodasi.getText().length() > 0) {{
                    totalAkomodasi = Long.parseLong(inputAkomodasi.getText().toString());
                }}
                if (charSequence.length() > 0) {
                    total = Long.parseLong(charSequence.toString()) + totalAkomodasi;
                } else {
                    total = totalAkomodasi;
                }
                updateTotal();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void handleAntarKota() {
        final EditText inputJarak = findViewById(R.id.tambah_rincian_antar_kota_jarak);
        inputJarak.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    total = Long.parseLong(charSequence.toString()) * 2000;
                    if (total > 750000) {
                        total = 750000;
                    }
                } else {
                    total = 0;
                }
                updateTotal();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

}
