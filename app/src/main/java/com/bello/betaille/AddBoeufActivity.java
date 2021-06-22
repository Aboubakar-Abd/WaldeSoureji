package com.bello.betaille;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.iceteck.silicompressorr.FileUtils;
import com.iceteck.silicompressorr.SiliCompressor;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

import static com.bello.betaille.Consts.COLLECTION_BOEUF;
import static com.bello.betaille.Consts.COLLECTION_NOMBRES;
import static com.bello.betaille.Consts.COLLECTION_NOMBRES_BOEUFS;
import static com.bello.betaille.Consts.COLLECTION_PROFIL_BOEUFS;
import static com.bello.betaille.Consts.COLLECTION_PROPRIETAIRE;
import static com.bello.betaille.Consts.PICK_IMAGE_REQUEST;
import static com.bello.betaille.Consts.SEXE_FEMELE;
import static com.bello.betaille.Consts.SEXE_MALE;
import static com.bello.betaille.Model.FirebaseQueries.firebaseIncrement;

public class AddBoeufActivity extends AppCompatActivity {

    public EditText nomBoeuf, naisBoeuf;
    public ImageView tofBoeuf;
    Button saveBoeuf, profileChangeBtn;
    ListView listProp;
    String sexe = "";
    public String propId;
    DatePickerDialog picker;

    private String checker = "";
    private Uri filePath;
    private StorageTask uploadTask;
    private FirebaseStorage storage;
    private StorageReference storageProfileRef;
    FirebaseFirestore db;
    DocumentReference propRef, nbBoeufRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_boeuf);


        propId = getIntent().getStringExtra("id");

        db = FirebaseFirestore.getInstance();
        propRef = db.collection(COLLECTION_PROPRIETAIRE).document(propId);
        nbBoeufRef = db.collection(COLLECTION_NOMBRES).document(COLLECTION_NOMBRES_BOEUFS);

        storage = FirebaseStorage.getInstance();
        storageProfileRef = FirebaseStorage.getInstance().getReference().child(COLLECTION_BOEUF);
        storageProfileRef = FirebaseStorage.getInstance().getReference();

        nomBoeuf = findViewById(R.id.nom_boeuf);
        naisBoeuf = findViewById(R.id.naiss_boeuf);
        tofBoeuf = findViewById(R.id.boeuf_pict);
        profileChangeBtn = findViewById(R.id.change_animal_picture_btn);
        saveBoeuf = findViewById(R.id.save_boeuf_btn);
        listProp = findViewById(R.id.choix_prop);

        naisBoeuf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(AddBoeufActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view,int year,int monthOfYear,int dayOfMonth) {
                                naisBoeuf.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        profileChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checker = "clicked";
                chooseImage();
            }
        });

        saveBoeuf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checker.equals("clicked"))
                    uploadImage();
                else
                    AddBoeuf(null);
            }
        });


    }

    public void onRadioClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_male:
                if (checked)
                   sexe = SEXE_MALE;
                    break;
            case R.id.radio_female:
                if (checked)
                    sexe = SEXE_FEMELE;
                    break;
        }
    }

    private void AddBoeuf(String imageUrl){

        HashMap<String, Object> boeuf = new HashMap<>();
        boeuf.put("Proprietaire", propId);
        boeuf.put("Designation", nomBoeuf.getText().toString());
        boeuf.put("Sexe_Boeufs", sexe);
        boeuf.put("NaissBoeuf", naisBoeuf.getText().toString());

        if (imageUrl!=null)
            boeuf.put("Boeuf_photos", imageUrl);

        db.collection(COLLECTION_BOEUF)
                .add(boeuf).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
              Intent intent = new Intent(AddBoeufActivity.this, ListBoeufActivity.class);
                //intent.putExtra("id", documentReference.getId());
                startActivity(intent);
                //Toast.makeText(AddBoeufActivity.this,"Proprietaire Added Successfully",Toast.LENGTH_SHORT).show();
                firebaseIncrement(propRef, "Nombre");
                firebaseIncrement(nbBoeufRef, COLLECTION_NOMBRES_BOEUFS);

            }
        });

    }

    private void chooseImage() {

        Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),  filePath);
                tofBoeuf.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {
        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("upoading...");
            progressDialog.setCancelable(false);
            progressDialog.show();

           final File file = new File(SiliCompressor.with(this)
                    .compress(FileUtils.getPath(this, filePath), new File(this.getCacheDir(), "temp")));
            Uri imageUi = Uri.fromFile(file);

            final StorageReference ref = storageProfileRef.child(COLLECTION_PROFIL_BOEUFS + "/" + UUID.randomUUID());
            uploadTask = ref.putFile(imageUi);

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        AddBoeuf(downloadUri.toString());

                    }
                    file.delete();
                    progressDialog.dismiss();
                }
            });

        }
    }
}
