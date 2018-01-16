package com.pertamina.pertaminatuban.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.models.MenuViewModel;
import com.pertamina.pertaminatuban.utils.GridMenuAdapter;

import java.util.ArrayList;

/*Tujuan activity ini adalah menampilkan menu kepada pengguna agar pengguna
* bisa memilih divisi mana yang ingin pengguna lihat atau edit atau tambah datanya*/
public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*membuat daftar menu dengan judul beserta id gambarnya*/
        populateMenu();
    }

    private void populateMenu() {
        /*membuat obyek dari ArrayList untuk menyimpan obyek dari MenuViewModel*/
        ArrayList<MenuViewModel> menuList = new ArrayList<>();

        /*menambahkan obyek dari MenuViewModel ke dalam daftar menuList
        * instansiasi memerlukan 2 parameter yaitu String untuk judul menu
        * dan int untuk id gambar menu*/
        menuList.add(new MenuViewModel("Divisi 1", R.drawable.ic_launcher_foreground));
        menuList.add(new MenuViewModel("Divisi 2", R.drawable.ic_launcher_foreground));
        menuList.add(new MenuViewModel("Divisi 3", R.drawable.ic_launcher_foreground));
        menuList.add(new MenuViewModel("Divisi 4", R.drawable.ic_launcher_foreground));
        menuList.add(new MenuViewModel("Divisi 5", R.drawable.ic_launcher_foreground));
        menuList.add(new MenuViewModel("Divisi 6", R.drawable.ic_launcher_foreground));

        /*membuat obyek dari GridMenuAdapter*/
        GridMenuAdapter adapter = new GridMenuAdapter(menuList, getApplicationContext());

        /*menghubungkan view dengan layout recyclerview di content_menu.xml*/
        RecyclerView recyclerView = findViewById(R.id.menu_recyclerview);

        /*membuat obyek layout manager tipe grid dengan jumlah kolom : 2
        * sekaligus menggabungkan dengan recyclerview dan adapter yang sudah dibuat*/
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
