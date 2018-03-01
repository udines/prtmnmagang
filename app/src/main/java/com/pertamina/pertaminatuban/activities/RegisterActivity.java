package com.pertamina.pertaminatuban.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.models.LoginResponse;
import com.pertamina.pertaminatuban.models.RegisterData;
import com.pertamina.pertaminatuban.models.RegisterResponse;
import com.pertamina.pertaminatuban.service.UserClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    private EditText inputUsername, inputEmail, inputPassword, inputUlangPassword;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        /*inisialisasi view yang digunakan di lebih dari satu fungsi*/
        inputUsername = findViewById(R.id.register_input_username);
        inputEmail = findViewById(R.id.register_input_email);
        inputPassword = findViewById(R.id.register_input_password);
        inputUlangPassword = findViewById(R.id.register_ulangi_password);
        registerButton = findViewById(R.id.register_button);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        handleLogin();
    }

    private void handleLogin() {
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //tampilkan progressbar dan hilangkan button
                toggleProgressbar();

                if (dataTerisi() && usernameCukup() && emailSesuai() && passwordCukup() && passwordSama()) {

                    RegisterData data = new RegisterData(
                            inputUsername.getText().toString(),
                            inputEmail.getText().toString(),
                            inputPassword.getText().toString(),
                            inputUlangPassword.getText().toString()
                    );

                    sendAuthRequest(data);
                } else {
                    toggleProgressbar();
                }
            }
        });
    }

    private void sendAuthRequest(RegisterData data) {
        String baseUrl = "http://www.api.clicktuban.com/";
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        UserClient client = retrofit.create(UserClient.class);
        Call<RegisterResponse> call = client.register(data);

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                Log.w("code", String.valueOf(response.code()));

                //hilangkan progressbar dan tampilkan button
                toggleProgressbar();

                if (response.code() == 200) {
                    Log.w("msg", response.body().getMsg());
                    Log.w("success", String.valueOf(response.body().isSuccess()));
                    if (!response.body().isSuccess()) {
                        Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                        loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Toast.makeText(RegisterActivity.this, "Pendaftaran BERHASIL, silakan login", Toast.LENGTH_SHORT).show();
                        startActivity(loginIntent);
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Log.e("register", "error " + t.getMessage());
                //hilangkan progressbar dan tampilkan button
                toggleProgressbar();
            }
        });
    }

    private boolean dataTerisi() {
        boolean isi = true;
        if (inputUsername.getText().length() < 1) {
            isi = false;
        }
        if (inputEmail.getText().length() < 1) {
            isi = false;
        }
        if (inputPassword.getText().length() < 1) {
            isi = false;
        }
        if (inputUlangPassword.getText().length() < 1) {
            isi = false;
        }
        if (!isi) {
            Toast.makeText(this, "Data harus lengkap", Toast.LENGTH_SHORT).show();
        }
        return isi;
    }

    private boolean passwordSama() {
        boolean sama;
        sama = inputPassword.getText().toString().equals(inputUlangPassword.getText().toString());
        if (!sama) {
            Toast.makeText(this, "Password tidak sama", Toast.LENGTH_SHORT).show();
        }
        return sama;
    }

    private void toggleProgressbar() {
        ProgressBar progressBar = findViewById(R.id.register_progressbar);
        Button button = findViewById(R.id.register_button);

        if (progressBar.getVisibility() == View.GONE) {
            progressBar.setVisibility(View.VISIBLE);
            button.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            button.setVisibility(View.VISIBLE);
        }
    }

    private boolean passwordCukup() {
        boolean cukup;
        cukup = inputPassword.getText().length() >= 8;
        if (!cukup) {
            Toast.makeText(this, "Password minimal 8 karakter", Toast.LENGTH_SHORT).show();
        }
        return cukup;
    }

    private boolean usernameCukup() {
        boolean cukup;
        cukup = inputUsername.getText().length() >= 6;
        if (!cukup) {
            Toast.makeText(this, "Username minimal 6 karakter", Toast.LENGTH_SHORT).show();
        }
        return cukup;
    }

    private boolean emailSesuai() {
        if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail.getText().toString()).matches()) {
            Toast.makeText(this, "Masukkan email dengan benar", Toast.LENGTH_SHORT).show();
        }
        return Patterns.EMAIL_ADDRESS.matcher(inputEmail.getText().toString()).matches();
    }
}