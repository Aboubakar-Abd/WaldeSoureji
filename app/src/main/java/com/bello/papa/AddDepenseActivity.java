package com.bello.papa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import static com.bello.papa.Consts.COLLECTION_DEPENSES;
import static com.bello.papa.Consts.COLLECTION_NOMBRES;
import static com.bello.papa.Consts.COLLECTION_NOMBRES_BOEUFS;
import static com.bello.papa.Consts.COLLECTION_PROPRIETAIRE;

public class AddDepenseActivity extends AppCompatActivity {

    public EditText description_dep, qte_dep, pu_dep;
    public Button save_btn_dep;

    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_depense);

        db = FirebaseFirestore.getInstance();

        description_dep = findViewById(R.id.description_dep);
        qte_dep = findViewById(R.id.qte_dep);
        pu_dep = findViewById(R.id.pu_dep);
        save_btn_dep = findViewById(R.id.save_dep);

        save_btn_dep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDepense();
            }
        });
    }

    private void addDepense(){

        Date date = new Date();

        HashMap<String, Object> depense = new HashMap<>();
        depense.put("Description", description_dep.getText().toString());
        depense.put("quantite", Integer.valueOf(qte_dep.getText().toString()));
        depense.put("prix_unitaire", Double.valueOf(pu_dep.getText().toString()));
        depense.put("Date", dateFormat.format(date));

        final double prixT = Double.valueOf(pu_dep.getText().toString()) * Double.valueOf(qte_dep.getText().toString());

       db.collection(COLLECTION_DEPENSES)
                .add(depense).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(final DocumentReference documentReference) {
//                Intent intent = new Intent(SaveClient.this, Client_View.class);
////                intent.putExtra("id", documentReference.getId());
////                startActivity(intent);
                db.collection(COLLECTION_NOMBRES).document(COLLECTION_NOMBRES_BOEUFS)
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {

//                                Intent intent = new Intent(AddDepenseActivity.this, ListDepActivity.class);
//                                 startActivity(intent);
                                updateDep(prixT/document.getDouble(COLLECTION_NOMBRES_BOEUFS));
                                Toast.makeText(AddDepenseActivity.this,"Depense Added Succesfully",Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                        }
                    }
                });

            }
        });

    }

    public void updateDep(final double depense) {

        final FirebaseFirestore db_prop = FirebaseFirestore.getInstance();
        db.collection(COLLECTION_PROPRIETAIRE)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
                                long nombre_prop = (long) document.get("Nombre");
                                double dep = depense*nombre_prop;
                                db_prop.collection(COLLECTION_PROPRIETAIRE).document(document.getId()).update(
                                        "Depense" , FieldValue.increment(dep)
                                );
                            }
                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
