package com.bello.papa;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bello.papa.Model.Boeufs;
import com.bello.papa.viewHolder.BoeufViewHolder;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

import static com.bello.papa.Consts.COLLECTION_BOEUF;
import static com.bello.papa.Consts.COLLECTION_PROPRIETAIRE;

public class ListBoeufActivity extends AppCompatActivity {

    FirebaseFirestore db;
    RecyclerView.LayoutManager layoutManager;
    String propId;
    FirestoreRecyclerAdapter<Boeufs, BoeufViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_boeuf);
        db = FirebaseFirestore.getInstance();

        propId = getIntent().getStringExtra("id");

        FloatingActionButton addBoeuf = findViewById(R.id.add_boeuf_fab);
        addBoeuf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListBoeufActivity.this, AddBoeufActivity.class);
                intent.putExtra("id", propId);
                startActivity(intent);
            }
        });
        final Query query = db.collection(COLLECTION_BOEUF).whereEqualTo("Proprietaire", propId);
        FirestoreRecyclerOptions<Boeufs> options = new FirestoreRecyclerOptions.Builder<Boeufs>()
                .setQuery(query, Boeufs.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<Boeufs, BoeufViewHolder>(options) {
            @Override
            protected void onBindViewHolder(final BoeufViewHolder boeufViewHolder,int i,final Boeufs boeufs) {

                //Toast.makeText(ListBoeufActivity.this,"nom" + boeufs.getSexe_Boeufs(),Toast.LENGTH_SHORT).show();
                boeufViewHolder.nom_boeuf_holder.setText(boeufs.getDesignation());
                if (boeufs.getSexe_Boeufs()!=null)
                boeufViewHolder.sexe_boeuf_holder.setText(boeufs.getSexe_Boeufs());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    try {
                        boeufViewHolder.age_boeuf_holder.setText(calculateAge(boeufs.getNaissBoeuf()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

//                db.collection(COLLECTION_PROPRIETAIRE).document(boeufs.getProprietaire())
//                        .get()
//                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                if (task.isSuccessful()) {
//                                    DocumentSnapshot document = task.getResult();
//                                    if (document.exists()) {
//
//                                        boeufViewHolder.nom_prop_boeuf_holder.setText("Nom du Prorietaire: " + document.getString("Nom") + " " + document.getString("Prenom"));
//                                        boeufViewHolder.id_boeuf_holder.setText("Numero Boeuf: " + document.getString("Id"));
//
//                                    }
//                                }
//                            }
//                        });

                String profileProp = boeufs.getBoeuf_photos();
                if (profileProp==null) {
                    profileProp = String.valueOf(R.drawable.boeuf);
                }
                Picasso.get().load(profileProp).into(boeufViewHolder.tof_boeuf_holder);

                boeufViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DocumentSnapshot snapshot = getSnapshots().getSnapshot(boeufViewHolder.getAdapterPosition());
                        Intent intent = new Intent(ListBoeufActivity.this, EditBoeufActivity.class);
                        intent.putExtra("id", snapshot.getId());
                        startActivity(intent);
                    }
                });
//                propViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        DocumentSnapshot snapshot = getSnapshots().getSnapshot(propViewHolder.getAdapterPosition());
//                        Intent intent = new Intent(Client_List.this, Client_View.class);
//                        intent.putExtra("id", snapshot.getId());
//                        startActivity(intent);
//
//                    }
//                });
            }

            @NonNull
            @Override
            public BoeufViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.boeuf_item_layout, parent, false);
                BoeufViewHolder holder = new BoeufViewHolder(view);
                return holder;
            }
        };
        RecyclerView recyclerView = findViewById(R.id.boeuf_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String calculateAge(String s) throws ParseException {

        //using Calendar Object
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date d = sdf.parse(s);
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int date = c.get(Calendar.DATE);
        LocalDate l1 = LocalDate.of(year, month, date);
        LocalDate now1 = LocalDate.now();
        Period diff1 = null;
        diff1 = Period.between(l1, now1);
        return diff1.getYears() + "Ans " + diff1.getMonths() + "Mois";
    }

    protected void onStart(){
        super.onStart();

        adapter.startListening();
    }

}