package com.bello.betaille;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bello.betaille.Model.Depenses;
import com.bello.betaille.Model.Prop;
import com.bello.betaille.viewHolder.DepViewHolder;
import com.bello.betaille.viewHolder.PropViewHolder;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import static com.bello.betaille.Consts.COLLECTION_DEPENSES;
import static com.bello.betaille.Consts.COLLECTION_PROPRIETAIRE;

public class ListDepActivity extends AppCompatActivity {

    FirebaseFirestore db;
    RecyclerView.LayoutManager layoutManager;
    FirestoreRecyclerAdapter<Depenses, DepViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_dep);
        db = FirebaseFirestore.getInstance();


        FloatingActionButton addDep = findViewById(R.id.add_dep_fab);
        addDep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListDepActivity.this, AddDepenseActivity.class);
                startActivity(intent);
            }
        });

        final Query query = db.collection(COLLECTION_DEPENSES)
                .orderBy("Date", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Depenses> options = new FirestoreRecyclerOptions.Builder<Depenses>()
                .setQuery(query, Depenses.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<Depenses, DepViewHolder>(options) {
            @Override
            protected void onBindViewHolder(final DepViewHolder depViewHolder,int i,final Depenses depenses) {

                depViewHolder.date_dep_holder.setText(depenses.getDate());
                depViewHolder.dsc_dep_holder.setText(depenses.getDescription());
                depViewHolder.pu_dep_holder.setText(Double.toString(depenses.getPrix_unitaire()));
                depViewHolder.ptotal_dep_holder.setText(Integer.toString(depenses.getQuantite()));

//
//                propViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        DocumentSnapshot snapshot = getSnapshots().getSnapshot(propViewHolder.getAdapterPosition());
//                        Intent intent = new Intent(ListDepActivity.this, ListBoeufActivity.class);
//                        intent.putExtra("id", snapshot.getId());
//                        startActivity(intent);
//
//                    }
//                });
            }

            @NonNull
            @Override
            public DepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dep_items_layout, parent, false);
                DepViewHolder holder = new DepViewHolder(view);
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
