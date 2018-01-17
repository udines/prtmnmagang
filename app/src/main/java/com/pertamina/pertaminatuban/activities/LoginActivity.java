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

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

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
        sendPost(username, password);
    }

    /**/
    private void sendPost(final String username, final String password) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    /*api url untuk login*/
                    URL url = new URL("http://www.api.odc-abcd.com/login");

                    /*membuat koneksi dan set metode POST*/
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setDoInput(true);
                    connection.setDoOutput(true);

                    /*membuat obyek json yang berisi data login*/
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("username", username);
                    jsonObject.put("password", password);
                    Log.d("json", jsonObject.toString());

                    /*mengubah json menjadi output stream*/
                    DataOutputStream os = new DataOutputStream(connection.getOutputStream());
                    os.writeBytes(jsonObject.toString());
                    os.flush();
                    os.close();

                    Log.d("connection", String.valueOf(connection.getResponseCode()));
                    Log.d("connection", String.valueOf(connection.getResponseMessage()));

                    /*Log hasil request body*/
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Log.d("connection body", br.lines().collect(Collectors.joining()));
                    }

                    connection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
