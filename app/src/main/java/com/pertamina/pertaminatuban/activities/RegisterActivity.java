package com.pertamina.pertaminatuban.activities;

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
import android.widget.Toast;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.models.LoginResponse;
import com.pertamina.pertaminatuban.models.RegisterData;
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
                if (dataTerisi() && emailSesuai() && passwordSama()) {

                    RegisterData data = new RegisterData(
                            inputUsername.getText().toString(),
                            inputEmail.getText().toString(),
                            inputPassword.getText().toString()
                    );

                    sendAuthRequest(data);
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
        Call<LoginResponse> call = client.register(data);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.code() == 200) {

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("register", "error " + t.getMessage());
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

    private boolean emailSesuai() {
        if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail.getText().toString()).matches()) {
            Toast.makeText(this, "Masukkan email dengan benar", Toast.LENGTH_SHORT).show();
        }
        return Patterns.EMAIL_ADDRESS.matcher(inputEmail.getText().toString()).matches();
    }
}