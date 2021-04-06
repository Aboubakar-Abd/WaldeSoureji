package com.bello.papa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bello.papa.Model.Prop;
import com.bello.papa.viewHolder.PropViewHolder;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import static com.bello.papa.Consts.COLLECTION_PROPRIETAIRE;

public class ListPropActivity extends AppCompatActivity {

    FirebaseFirestore db;
    RecyclerView.LayoutManager layoutManager;
    FirestoreRecyclerAdapter<Prop, PropViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_prop);
        db = FirebaseFirestore.getInstance();


        FloatingActionButton addBoeuf = findViewById(R.id.add_prop_fab);
        addBoeuf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListPropActivity.this, AddPropActivity.class);
                startActivity(intent);
            }
        });

        final Query query = db.collection(COLLECTION_PROPRIETAIRE)
                .orderBy("Id", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Prop> options = new FirestoreRecyclerOptions.Builder<Prop>()
                .setQuery(query, Prop.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<Prop, PropViewHolder>(options) {
            @Override
            protected void onBindViewHolder(final PropViewHolder propViewHolder,int i,final Prop prop) {

                propViewHolder.txtPropName.setText("Nom: " + prop.getNom());
                propViewHolder.txtPropPname.setText("Prenom:" + prop.getPrenom());
                propViewHolder.txtPropId.setText("Nombre des beoufs: " + prop.getNombre());
//                propViewHolder.txtPropNmber.setText("Nombre des beoufs: " + Integer.toString(prop.getNombre()));
                propViewHolder.txtDepense.setText("Depense: " + Integer.toString(prop.getDepense()));
                String profileProp = prop.getProfilePic();
                //String image = "https://firebasestorage.googleapis.com/v0/b/faromesure.appspot.com/o/Clients_Profile_Picture%2Fabak_abd_567435643?alt=media&token=37c67046-7d39-4aac-bc7d-ed73b7e0199a";
                Picasso.get().load(profileProp).into(propViewHolder.profile);

//                propViewHolder.addBoeuf.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        DocumentSnapshot snapshot = getSnapshots().getSnapshot(propViewHolder.getAdapterPosition());
//                        Intent intent = new Intent(ListPropActivity.this, ListBoeufActivity.class);
//                        intent.putExtra("id", snapshot.getId());
//                        startActivity(intent);
//                    }
//                });
                propViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        DocumentSnapshot snapshot = getSnapshots().getSnapshot(propViewHolder.getAdapterPosition());
                        Intent intent = new Intent(ListPropActivity.this, ListBoeufActivity.class);
                        intent.putExtra("id", snapshot.getId());
                        startActivity(intent);

                    }
                });
            }

            @NonNull
            @Override
            public PropViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prop_items_layout, parent, false);
                PropViewHolder holder = new PropViewHolder(view);
                return holder;
            }
        };
        RecyclerView recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    protected void onStart(){
        super.onStart();

        adapter.startListening();
    }

}
