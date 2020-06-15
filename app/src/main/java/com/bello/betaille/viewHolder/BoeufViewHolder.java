package com.bello.betaille.viewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bello.betaille.Interface.ItemClickListener;
import com.bello.betaille.R;

public class BoeufViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView tof_boeuf_holder;
    public TextView nom_boeuf_holder, id_boeuf_holder, nom_prop_boeuf_holder, age_boeuf_holder, sexe_boeuf_holder;

    public ItemClickListener listner;
    private View view;

    public BoeufViewHolder(@NonNull View itemView) {
        super(itemView);
        tof_boeuf_holder = itemView.findViewById(R.id.tof_boeuf_holder);
        nom_boeuf_holder = itemView.findViewById(R.id.nom_boeuf_holder);
        id_boeuf_holder = itemView.findViewById(R.id.num_boeuf_holder);
        age_boeuf_holder = itemView.findViewById(R.id.age_boeuf_holder);
        sexe_boeuf_holder = itemView.findViewById(R.id.sexe_boeuf_holder);
        nom_prop_boeuf_holder = itemView.findViewById(R.id.nom_prop_boeuf_holder);
        view = itemView;


    }

    public void setItemClickListener(ItemClickListener listner){

        this.listner = listner;
    }

    @Override
    public void onClick(View v) {

        listner.onClick(v, getAdapterPosition(), false);
    }

}