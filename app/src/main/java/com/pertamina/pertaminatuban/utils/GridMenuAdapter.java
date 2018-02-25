package com.pertamina.pertaminatuban.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.DistribusiActivity;
import com.pertamina.pertaminatuban.finance.perjalanan_dinas.PerjalananDinasActivity;
import com.pertamina.pertaminatuban.marine.NewMarineActivity;
import com.pertamina.pertaminatuban.models.MenuViewModel;
import com.pertamina.pertaminatuban.qualityquantity.QQActivity;

import java.util.ArrayList;

public class GridMenuAdapter extends RecyclerView.Adapter<GridMenuViewHolder> {

    private ArrayList<MenuViewModel> menuList;
    private Context context;

    public GridMenuAdapter(ArrayList<MenuViewModel> menuList, Context context) {
        this.menuList = menuList;
        this.context = context;
    }

    @Override
    public GridMenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        /*membuat inflater untuk menghubungkan viewholder dengan layout item_menu_grid*/
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new GridMenuViewHolder(inflater.inflate(R.layout.item_menu_grid, parent, false));
    }

    @Override
    public void onBindViewHolder(final GridMenuViewHolder holder, int position) {

        /*set text pada menu sesuai dengan teks yang sudah ditentukan dan
         sesuai dengan urutan dari daftar menu*/
        holder.title.setText(menuList.get(position).getTitle());

        /*set gambar menu sesuai dengan id gambar yang sudah ditentukan*/
        holder.image.setImageDrawable(ContextCompat.getDrawable(context,
                menuList.get(position).getIdDrawable()));

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (holder.getAdapterPosition()) {
                    case 0 :
                        Intent distribusiIntent = new Intent(holder.context, DistribusiActivity.class);
                        distribusiIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        holder.context.startActivity(distribusiIntent);
                        break;
                    case 1 :
                        Intent marineIntent = new Intent(holder.context, NewMarineActivity.class);
                        marineIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        holder.context.startActivity(marineIntent);
                        break;
                    case 2:
                        Intent qqIntent = new Intent(holder.context, QQActivity.class);
                        qqIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        holder.context.startActivity(qqIntent);
                        break;
                    case 3:
                        Intent keuanganIntent = new Intent(holder.context, PerjalananDinasActivity.class);
                        keuanganIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        holder.context.startActivity(keuanganIntent);
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        /*jika list kosong maka return nilai 0 agar tidak terjadi error*/
        return (menuList != null ? menuList.size() : 0);
    }


}
