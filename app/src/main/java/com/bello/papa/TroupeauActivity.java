package com.bello.papa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.bello.papa.Model.Boeufs;
import com.bello.papa.Model.Troupeaux;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.bello.papa.Consts.COLLECTION_BOEUF;
import static com.bello.papa.Consts.COLLECTION_NOMBRES;
import static com.bello.papa.Consts.COLLECTION_NOMBRES_BOEUFS;
import static com.bello.papa.Consts.SEXE_FEMELE;
import static com.bello.papa.Consts.SEXE_MALE;

public class TroupeauActivity extends AppCompatActivity {

    FirebaseFirestore db;
    DocumentReference nbre;
    TextView nbtotal, nbmal, nbfemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_troupeau);

        db = FirebaseFirestore.getInstance();
        nbre = db.collection(COLLECTION_NOMBRES).document(COLLECTION_NOMBRES_BOEUFS);

        nbtotal = findViewById(R.id.nbre_total_tr);
        nbmal = findViewById(R.id.nbre_male_tr);
        nbfemale = findViewById(R.id.nbre_female_tr);

        nbre.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
              Troupeaux troupeaux = documentSnapshot.toObject(Troupeaux.class);
             initValue(troupeaux);
            }
        });


    }

    public void initValue(Troupeaux tr){
        nbtotal.setText("Total: " + tr.getNombres_Boeufs());
        nbmal.setText("Male: " + tr.getMale());
        nbfemale.setText("Femelle: " + tr.getFemelle());
    }
}