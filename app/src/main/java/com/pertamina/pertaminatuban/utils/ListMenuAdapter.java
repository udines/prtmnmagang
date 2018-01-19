package com.pertamina.pertaminatuban.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.models.MenuViewModel;

import java.util.ArrayList;

public class ListMenuAdapter extends RecyclerView.Adapter<ListMenuViewHolder> {

    private ArrayList<MenuViewModel> menuList;
    private ArrayList<Class> classes;
    private Context context;

    public ListMenuAdapter(ArrayList<MenuViewModel> menuList, Context context) {
        this.menuList = menuList;
        this.context = context;
    }

    public void setClasses(ArrayList<Class> classes) {
        this.classes = classes;
    }

    @Override
    public ListMenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        /*membuat inflater untuk menghubungkan viewholder dengan layout item_menu_list*/
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ListMenuViewHolder(inflater.inflate(R.layout.item_menu_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ListMenuViewHolder holder, final int position) {

        /*set text pada menu sesuai dengan teks yang sudah ditentukan dan
         sesuai dengan urutan dari daftar menu*/
        holder.title.setText(menuList.get(position).getTitle());

        /*handle user klik list untuk menuju ke halaman yang dipilih*/
        if (classes != null) {
            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent activityIntent = new Intent(context, classes.get(position));
                    activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(activityIntent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {

        /*jika list kosong maka return nilai 0 agar tidak terjadi error*/
        return (menuList != null ? menuList.size() : 0);
    }
}
