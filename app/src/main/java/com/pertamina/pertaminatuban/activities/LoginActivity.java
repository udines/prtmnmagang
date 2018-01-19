package com.pertamina.pertaminatuban.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.models.InvalidResponse;
import com.pertamina.pertaminatuban.models.UserCredential;
import com.pertamina.pertaminatuban.models.ValidResponse;
import com.pertamina.pertaminatuban.service.UserClient;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        /*mengurus pengambilan data username dan password lalu melakukan
        * otentikasi menggunakan data diatas*/
        handleLogin();

        /*respons pengguna ketika klik daftar*/
        handleRegister();
    }

    private void handleRegister() {

        /*menghubungkan variabel view dengan layout*/
        TextView registerButton = findViewById(R.id.login_daftar);

        /*eksekusi instruksi di bawah ketika pengguna klik tombol daftar*/
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*masuk ke halaman pendaftaran*/
                Intent registerIntent = new Intent(getApplicationContext(), RegisterActivity.class);
                registerIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(registerIntent);
            }
        });
    }

    private void handleLogin() {

        /*menghubungkan view dengan layout berdasarkan id di content_login.xml*/
        final EditText inputUsername, inputPassword;
        Button buttonLogin;
        inputUsername = findViewById(R.id.login_input_username);
        inputPassword = findViewById(R.id.login_input_password);
        buttonLogin = findViewById(R.id.login_button);

        /*merespons pengguna ketika tombol login diklik*/
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*deklarasi variable untuk menyimpan data input username dan password*/
                String username, password;

                /*mengecek apakah input username dan input password sudah diisi
                * jika sudah diisi maka ambil dan simpan data ke dalam variabel*/
                if (inputUsername.getText().length() > 0 && inputPassword.getText().length() > 0) {

                    /*memasukkan data ke dalam variable yang sudah dideklarasikan*/
                    username = inputUsername.getText().toString();
                    password = inputPassword.getText().toString();

                    /*lakukan otentikasi dengan username dan password yang sudah terisi data*/
                    otentikasi(username, password);
                } else {

                    /*menampilkan peringatan jika salah satu input tidak diisi*/
                    if (inputUsername.getText().length() == 0) {
                        Toast.makeText(LoginActivity.this, "Masukkan username", Toast.LENGTH_SHORT).show();
                    } else if (inputPassword.getText().length() == 0) {
                        Toast.makeText(LoginActivity.this, "Masukkan password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void otentikasi(String username, String password)  {

        UserCredential credential = new UserCredential(
                username,
                password
        );

        sendAuthRequest(credential);

//        sendPost(username, password);
    }

    private void sendAuthRequest(UserCredential credential) {
        String baseUrl = "http://www.api.odc-abcd.com/";
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        UserClient client = retrofit.create(UserClient.class);
        Call<Object> call = client.UserClient(credential);

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.d("request", "Success");
                Log.d("response code", String.valueOf(response.code()) + " " + response.message());
                if (response.code() == 200 && response.body() != null) {
                    
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.d("request", "Failure");
            }
        });
    }
}
