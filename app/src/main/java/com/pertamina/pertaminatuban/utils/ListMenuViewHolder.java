package com.pertamina.pertaminatuban.utils;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pertamina.pertaminatuban.R;

/*class ini digunakan untuk mengubah obyek dari MenuViewModel menjadi
* sebuah tampilan menu dalam bentuk list menurun*/
public class ListMenuViewHolder extends RecyclerView.ViewHolder {

    public final TextView title;
    public final CardView card;
    private final Context context;

    public ListMenuViewHolder(View itemView) {
        super(itemView);

        /*membuat dan instansiasi context untuk keperluan lain*/
        context = itemView.getContext();

        /*menghubungkan variable view dengan layout sesuai dengan id
        * pada layout item_menu_grid.xml*/
        title = itemView.findViewById(R.id.item_menu_grid_title);

        /*menambahkan click listener. jika pengguna melakukan klik pada
        * salah satu menu, maka pindah ke halaman sesuai dengan pilihan
        * pengguna berdasarkan title menu.*/
        card = itemView.findViewById(R.id.item_menu_grid_card);
    }
}
