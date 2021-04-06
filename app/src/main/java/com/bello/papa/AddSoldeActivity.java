package com.bello.papa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bello.papa.Model.Prop;
import com.bello.papa.viewHolder.PropSoldeViewHolder;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import static com.bello.papa.Consts.COLLECTION_PROPRIETAIRE;


public class AddSoldeActivity extends AppCompatActivity {


    FirebaseFirestore db;

    RecyclerView.LayoutManager layoutManager;
    FirestoreRecyclerAdapter<Prop, PropSoldeViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_solde);

        db = FirebaseFirestore.getInstance();



        final Query query = db.collection(COLLECTION_PROPRIETAIRE)
                .orderBy("Nom", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Prop> options = new FirestoreRecyclerOptions.Builder<Prop>()
                .setQuery(query, Prop.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<Prop, PropSoldeViewHolder>(options) {
            @Override
            protected void onBindViewHolder(final PropSoldeViewHolder propViewHolder, int i, final Prop prop) {

                propViewHolder.txtPropName.setText("Nom: " + prop.getNom() + " " + prop.getPrenom());
//                propViewHolder.txtPropPname.setText("Prenom:" + prop.getPrenom());
                propViewHolder.txtPropId.setText("Numero Identification: " + prop.getId());
                propViewHolder.txtDepense.setText("Depense: " + Integer.toString(prop.getDepense()));
                String profileProp = prop.getProfilePic();
                Picasso.get().load(profileProp).into(propViewHolder.profile);

//                propViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        DocumentSnapshot snapshot = getSnapshots().getSnapshot(propViewHolder.getAdapterPosition());
//                        Intent intent = new Intent(ListPropActivity.this, ListBoeufActivity.class);
//                        intent.putExtra("id", snapshot.getId());
//                        startActivity(intent);
//
//                    }
//                });
            }

            @NonNull
            @Override
            public PropSoldeViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prop_solde_items_layout, parent, false);
                PropSoldeViewHolder holder = new PropSoldeViewHolder(view);
                return holder;
            }
        };
        RecyclerView recyclerView = findViewById(R.id.recycler_menu_solde);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    protected void onStart(){
        super.onStart();

        adapter.startListening();
    }

//        CollectionReference prop_ref = db.collection(COLLECTION_PROPRIETAIRE);
//
//        slct_prop = findViewById(R.id.slct_prop);
//        final List<String> proprietaires = new ArrayList<>();
//        final ArrayAdapter<String> prop_adpt = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, proprietaires);
//        prop_adpt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        slct_prop.setAdapter(prop_adpt);
//        prop_ref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot document : task.getResult()){
//                        String prop_name = document.getString(getString(R.string.nom_prop)) + " " + document.getString(getString(R.string.prenom_prop));
//                        proprietaires.add(prop_name);
//                    }
//                    prop_adpt.notifyDataSetChanged();
//                }
//            }
//        });

}

