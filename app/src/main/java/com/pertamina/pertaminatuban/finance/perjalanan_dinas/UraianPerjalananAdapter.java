package com.pertamina.pertaminatuban.finance.perjalanan_dinas;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.finance.models.UraianPerjalanan;
import com.pertamina.pertaminatuban.service.FinanceClient;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class UraianPerjalananAdapter extends RecyclerView.Adapter<UraianPerjalananViewHolder> {

    private ArrayList<UraianPerjalanan> uraians;
    private AppCompatActivity activity;

    private HashMap<String, String> jenisClaimValueKey, jenisClaimKeyValue;

    public UraianPerjalananAdapter(ArrayList<UraianPerjalanan> uraians, AppCompatActivity activity) {
        this.uraians = uraians;
        this.activity = activity;
    }

    @Override
    public UraianPerjalananViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_uraian_perjalanan, parent, false);
        final UraianPerjalananViewHolder holder = new UraianPerjalananViewHolder(view);
        SharedPreferences preferences = parent.getContext().getSharedPreferences(
                "login",
                Context.MODE_PRIVATE
        );
        String role = preferences.getString("userRole", "none");
        if (role.equals("admin")) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builderSukses = new AlertDialog.Builder(activity)
                            .setTitle("Ubah Status")
                            .setMessage("Ubah status claim menjadi Success")
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    UraianPerjalanan uraian = uraians.get(holder.getAdapterPosition());
                                    uraian.setStatus("Success");
                                    sendUpdateRequest(uraian, dialogInterface);
                                }
                            })
                            .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });

                    AlertDialog.Builder builderGagal = new AlertDialog.Builder(activity)
                            .setTitle("Ubah Status")
                            .setMessage("Ubah status claim menjadi Failed")
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    UraianPerjalanan uraian = uraians.get(holder.getAdapterPosition());
                                    uraian.setStatus("Failed");
                                    sendUpdateRequest(uraian, dialogInterface);
                                }
                            })
                            .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });

                    if (!uraians.get(holder.getAdapterPosition()).getStatus().equals("Success")) {
                        AlertDialog dialog = builderSukses.create();
                        dialog.show();
                    } else {
                        AlertDialog dialog = builderGagal.create();
                        dialog.show();
                    }
                }
            });

        }
        return holder;
    }

    private void sendUpdateRequest(UraianPerjalanan uraian, final DialogInterface dialog) {
        SharedPreferences preferences = activity.getSharedPreferences(
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
        Call<Object> call = financeClient.updateUraianPerjalanan(uraian);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200) {
                    dialog.dismiss();
                    Toast.makeText(activity.getApplicationContext(), "Update status berhasil", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBindViewHolder(UraianPerjalananViewHolder holder, int position) {
        setHashMaps();

        UraianPerjalanan model = uraians.get(position);
        DecimalFormat formatter = new DecimalFormat("#,###");
        holder.jumlah.setText(String.valueOf("Rp" + formatter.format(Double.parseDouble(model.getJumlah()))));
        holder.uraian.setText(jenisClaimKeyValue.get(model.getClaimType()));
        holder.keterangan.setText(model.getKeterangan());
        holder.status.setText(model.getStatus());

        SimpleDateFormat format = new SimpleDateFormat("dd - MM - yyyy", Locale.getDefault());
        SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            holder.tanggal.setText(format.format(parseFormat.parse(model.getDate())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
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

    @Override
    public int getItemCount() {
        return uraians.size();
    }
}
