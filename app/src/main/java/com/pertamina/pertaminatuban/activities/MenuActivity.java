package com.pertamina.pertaminatuban.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.info_umum.InfoUmum;
import com.pertamina.pertaminatuban.info_umum.InfoUmumActivity;
import com.pertamina.pertaminatuban.models.Featured;
import com.pertamina.pertaminatuban.models.ImageUrl;
import com.pertamina.pertaminatuban.models.MenuViewModel;
import com.pertamina.pertaminatuban.utils.FeaturedAdapter;
import com.pertamina.pertaminatuban.utils.GridMenuAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*Tujuan activity ini adalah menampilkan menu kepada pengguna agar pengguna
* bisa memilih divisi mana yang ingin pengguna lihat atau edit atau tambah datanya*/
public class MenuActivity extends AppCompatActivity {

    private NestedScrollView scrollView;
    private DatabaseReference infoRef;
    private ValueEventListener listener;
    private ValueEventListener urlListener;
    private com.google.firebase.database.Query query;
    private com.google.firebase.database.Query urlquery;
    private ArrayList<Featured> featureds;
    private ArrayList<ImageUrl> urls;
    private DatabaseReference urlRef;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        scrollView = findViewById(R.id.menu_nestedscrollview);

        /*menampilkan featured image*/
        populateFeatured();
        getImageUrl();

        /*membuat daftar menu dengan judul beserta id gambarnya*/
        populateMenu();

        /*handle info card*/
//        handleInfoCard();
    }

    private void handleInfoCard() {
        CardView card = findViewById(R.id.menu_info_card);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), InfoUmumActivity.class));
            }
        });

        final TextView informasi = findViewById(R.id.menu_info_informasi);
        final TextView detail = findViewById(R.id.menu_info_detail);
        final TextView judul = findViewById(R.id.menu_info_judul);

        infoRef = FirebaseDatabase.getInstance().getReference("messages");
        query = infoRef.orderByChild("date").limitToLast(1);
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    InfoUmum info = dataSnapshot1.getValue(InfoUmum.class);
                    if (info != null) {
                        judul.setText(info.getJudul());
                        informasi.setText(info.getInformasi());
                        detail.setText(info.getDeskripsi());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

    private void getImageUrl() {
        Log.w("menu", "getting image urls");
        urlRef = FirebaseDatabase.getInstance().getReference("sliderimages");
        urlquery = urlRef.limitToLast(3);
        urlListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                urls = new ArrayList<>();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    urls.add(dataSnapshot1.getValue(ImageUrl.class));
                }
                Log.w("url size", String.valueOf(urls.size()));
                if (featureds != null && urls != null) {
                    displayFeatured(featureds, urls);
                }
                for (int i = 0; i < urls.size(); i++) {
                    Log.w("url", urls.get(i).getUrl());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("menu", databaseError.getMessage());
            }
        };
    }

    private void displayFeatured(ArrayList<Featured> featureds, ArrayList<ImageUrl> urls) {
        recyclerView.setAdapter(new FeaturedAdapter(featureds, MenuActivity.this, urls));
    }

    private void populateFeatured() {
        recyclerView = findViewById(R.id.menu_recyclerview_featured);
        recyclerView.setOnFlingListener(null);
        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(recyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(
                getApplicationContext(),
                LinearLayoutManager.HORIZONTAL,
                false
        ));

        infoRef = FirebaseDatabase.getInstance().getReference("messages");
        query = infoRef.orderByChild("date").limitToLast(3);
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                featureds = new ArrayList<>();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    featureds.add(dataSnapshot1.getValue(Featured.class));
                }
                Collections.reverse(featureds);
                if (featureds != null && urls != null) {
                    displayFeatured(featureds, urls);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        /*FirebaseFirestore.getInstance()
                .collection("featured")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(5).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    List<Featured> featureds;
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (!documentSnapshots.isEmpty()) {
                            featureds = documentSnapshots.toObjects(Featured.class);
                            FeaturedAdapter adapter = new FeaturedAdapter(featureds);
                            progress.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            scrollView.scrollTo(0,0);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                });*/
    }

    private void populateMenu() {
        /*membuat obyek dari ArrayList untuk menyimpan obyek dari MenuViewModel*/
        ArrayList<MenuViewModel> menuList = new ArrayList<>();

        /*menambahkan obyek dari MenuViewModel ke dalam daftar menuList
        * instansiasi memerlukan 2 parameter yaitu String untuk judul menu
        * dan int untuk id gambar menu*/
        menuList.add(new MenuViewModel("Distribusi", R.drawable.ic_tank));
        menuList.add(new MenuViewModel("Marine", R.drawable.ic_cargo_ship));
        menuList.add(new MenuViewModel("Quality", R.drawable.ic_recommended));
        menuList.add(new MenuViewModel("Finance", R.drawable.ic_presentation));
//        menuList.add(new MenuViewModel("Divisi 5", R.drawable.ic_launcher_foreground));
//        menuList.add(new MenuViewModel("Divisi 6", R.drawable.ic_launcher_foreground));

        /*membuat obyek dari GridMenuAdapter untuk menampilkan menu bentuk gridLayoutManager 2 kolom*/
        GridMenuAdapter gridAdapter = new GridMenuAdapter(menuList, getApplicationContext());

        /*menghubungkan view dengan layout recyclerview di content_menu.xml*/
        RecyclerView recyclerView = findViewById(R.id.menu_recyclerview);

        /*membuat obyek layout manager tipe gridLayoutManager dengan jumlah kolom : 2*/
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);

        /*inisialisasi awal menu dalam bentuk grid*/
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(gridAdapter);
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
        if (id == R.id.action_menu_logout) {

            /*hapus key dan keluar ke login activity*/
            SharedPreferences sharedPreferences = MenuActivity.this.getSharedPreferences(
                    "login",
                    Context.MODE_PRIVATE
            );
            sharedPreferences.edit().putString("userKey", null).apply();
            sharedPreferences.edit().putString("userRole", null).apply();
            Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(loginIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    protected void onStart() {
        super.onStart();
        query.addValueEventListener(listener);
        urlquery.addListenerForSingleValueEvent(urlListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        query.removeEventListener(listener);
        urlquery.removeEventListener(urlListener);
    }
}
