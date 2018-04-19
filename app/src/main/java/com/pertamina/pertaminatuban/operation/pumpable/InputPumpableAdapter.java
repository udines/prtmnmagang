package com.pertamina.pertaminatuban.operation.pumpable;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.operation.models.Pumpable;
import com.pertamina.pertaminatuban.service.OperationClient;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class InputPumpableAdapter extends RecyclerView.Adapter<InputPumpableViewHolder> {

    private ArrayList<Pumpable> pumpables;
    private Context context;
    private int index;

    public InputPumpableAdapter(ArrayList<Pumpable> pumpables, Context context) {
        this.pumpables = pumpables;
        this.context = context;
    }

    @Override
    public InputPumpableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_input_pumpable, parent, false);
        return new InputPumpableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final InputPumpableViewHolder holder, int position) {
        if (pumpables.get(position).getNoTank().length() > 0) {
            holder.inputNoTank.setText(pumpables.get(position).getNoTank());
        }
        if (pumpables.get(position).getValue() > 0) {
            holder.inputValue.setText(String.valueOf(pumpables.get(position).getValue()));
        }

        holder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pumpables.get(holder.getAdapterPosition()).getId() != null) {
                    index = holder.getAdapterPosition();
                    hapusPumpable(pumpables.get(holder.getAdapterPosition()).getId());
                } else {
                    pumpables.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());
                    notifyItemRangeChanged(holder.getAdapterPosition(), pumpables.size());
                }
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.bahan_bakar, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spinner.setAdapter(adapter);
        holder.spinner.setSelection(adapter.getPosition(pumpables.get(position).getFuel()));
        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pumpables.get(holder.getAdapterPosition()).setFuel(holder.spinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        holder.inputNoTank.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    pumpables.get(holder.getAdapterPosition()).setNoTank(charSequence.toString());
                } else {
                    pumpables.get(holder.getAdapterPosition()).setNoTank("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        holder.inputValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    pumpables.get(holder.getAdapterPosition()).setValue(Long.parseLong(charSequence.toString()));
                } else {
                    pumpables.get(holder.getAdapterPosition()).setValue(0);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return pumpables.size();
    }

    public ArrayList<Pumpable> getPumpables() {
        return pumpables;
    }

    private void hapusPumpable(String id) {
        Log.w("delete id", id);
        SharedPreferences preferences = context.getSharedPreferences(
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
        OperationClient operationClient = retrofit.create(OperationClient.class);
        Call<Object> call = operationClient.deletePumpable(id);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    Log.w("body", new Gson().toJson(response.body()));
                    pumpables.remove(index);
                    notifyItemRemoved(index);
                    notifyItemRangeChanged(index, pumpables.size());
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }
}
