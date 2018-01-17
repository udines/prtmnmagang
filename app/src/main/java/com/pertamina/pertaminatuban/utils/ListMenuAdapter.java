package com.pertamina.pertaminatuban.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.models.MenuViewModel;

import java.util.ArrayList;

public class ListMenuAdapter extends RecyclerView.Adapter<ListMenuViewHolder> {

    private ArrayList<MenuViewModel> menuList;
    private Context context;

    public ListMenuAdapter(ArrayList<MenuViewModel> menuList, Context context) {
        this.menuList = menuList;
        this.context = context;
    }

    @Override
    public ListMenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        /*membuat inflater untuk menghubungkan viewholder dengan layout item_menu_list*/
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ListMenuViewHolder(inflater.inflate(R.layout.item_menu_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ListMenuViewHolder holder, int position) {

        /*set text pada menu sesuai dengan teks yang sudah ditentukan dan
         sesuai dengan urutan dari daftar menu*/
        holder.title.setText(menuList.get(position).getTitle());

        /*set gambar menu sesuai dengan id gambar yang sudah ditentukan*/
        holder.image.setImageDrawable(ContextCompat.getDrawable(context,
                menuList.get(position).getIdDrawable()));
    }

    @Override
    public int getItemCount() {

        /*jika list kosong maka return nilai 0 agar tidak terjadi error*/
        return (menuList != null ? menuList.size() : 0);
    }
}
