package com.pertamina.pertaminatuban.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pertamina.pertaminatuban.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*mengurus pengambilan data username dan password lalu melakukan
        * otentikasi menggunakan data diatas*/
        handleLogin();
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

    private void otentikasi(String username, String password) {
        Toast.makeText(this, username + " " + password, Toast.LENGTH_SHORT).show();
        Intent menuIntent = new Intent(getApplicationContext(), MenuActivity.class);
        menuIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(menuIntent);
    }


}
