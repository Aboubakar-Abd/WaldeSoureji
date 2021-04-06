package com.bello.papa.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bello.papa.Interface.ItemClickListener;
import com.bello.papa.R;


public class PropSoldeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtPropName, txtPropPname, txtPropId, txtDepense;
    public ImageView profile;
    public ItemClickListener listner;
    private View view;

    public PropSoldeViewHolder(@NonNull View itemView) {
        super(itemView);

        view = itemView;

        txtPropName = itemView.findViewById(R.id.nom_prop_solde_holder);
        txtPropPname = itemView.findViewById(R.id.prenom_prop_solde_holder);
        txtPropId = itemView.findViewById(R.id.id_prop_solde_holder);
        txtDepense = itemView.findViewById(R.id.dep_prop_solde_holder);
        profile = itemView.findViewById(R.id.profile_prop_solde_holder);
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