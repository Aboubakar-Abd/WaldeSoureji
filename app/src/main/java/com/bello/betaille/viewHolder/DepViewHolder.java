package com.bello.betaille.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bello.betaille.Interface.ItemClickListener;
import com.bello.betaille.R;

public class DepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView date_dep_holder, dsc_dep_holder, pu_dep_holder, ptotal_dep_holder;

    public ItemClickListener listner;
    private View view;

    public DepViewHolder(@NonNull View itemView) {
        super(itemView);
        date_dep_holder = itemView.findViewById(R.id.date_dep_hoder);
        dsc_dep_holder = itemView.findViewById(R.id.desc_dep_hoder);
        pu_dep_holder = itemView.findViewById(R.id.pu_dep_hoder);
        ptotal_dep_holder = itemView.findViewById(R.id.ptotal_dep_hoder);
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