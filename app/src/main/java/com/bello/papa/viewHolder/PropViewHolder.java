package com.bello.papa.viewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bello.papa.Interface.ItemClickListener;
import com.bello.papa.R;


public class PropViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtPropName, txtPropPname, txtPropNmber, txtPropId, txtDepense;
    public ImageView profile;
    public Button addBoeuf;
    public ItemClickListener listner;
    private View view;

    public PropViewHolder(@NonNull View itemView) {
        super(itemView);

        view = itemView;

        txtPropName = itemView.findViewById(R.id.nom_prop_holder);
        txtPropPname = itemView.findViewById(R.id.prenom_prop_holder);
        txtPropId = itemView.findViewById(R.id.id_prop_holder);
//        txtPropNmber = itemView.findViewById(R.id.nmbre_boeuf_holder);
        txtDepense = itemView.findViewById(R.id.depene_prop);
        profile = itemView.findViewById(R.id.profile_image_holder);
    }

//    public void setClientName(String propName){
//        txtPropPname = view.findViewById(R.id.nom_prop);
//        txtPropName.setText(propName);
//    }

    public void setItemClickListener(ItemClickListener listner){

        this.listner = listner;
    }

    @Override
    public void onClick(View v) {

        listner.onClick(v, getAdapterPosition(), false);
    }

}