package com.bello.papa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
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
import java.util.HashMap;

import static com.bello.papa.Consts.COLLECTION_NOMBRES;
import static com.bello.papa.Consts.COLLECTION_NOMBRES_PROPRIETAIRE;
import static com.bello.papa.Consts.COLLECTION_PROFIL_PROPRIETAIRE;
import static com.bello.papa.Consts.COLLECTION_PROPRIETAIRE;
import static com.bello.papa.Consts.PICK_IMAGE_REQUEST;
import static com.bello.papa.Model.FirebaseQueries.firebaseIncrement;

public class AddPropActivity extends AppCompatActivity {


    public ImageView profilProp;

    private Button profileChangeBtn, savePropBtn;

    private String checker = "";

    private Uri filePath;
    private StorageTask uploadTask;
    private FirebaseStorage storage;
    private StorageReference storageProfileRef;

    private FirebaseFirestore db;

    public EditText nomProp, prenomProp, idProp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prop);
        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();

        storage = FirebaseStorage.getInstance();
        storageProfileRef = FirebaseStorage.getInstance().getReference().child(COLLECTION_PROPRIETAIRE);

        nomProp = findViewById(R.id.nom_prop);
        prenomProp = findViewById(R.id.prenom_prop);
        idProp = findViewById(R.id.id_prop);
        profilProp = findViewById(R.id.profile_image_prop);
        profileChangeBtn = findViewById(R.id.change_picture_btn);
        savePropBtn = findViewById(R.id.save_prop_btn);

        storageProfileRef = FirebaseStorage.getInstance().getReference();

        profileChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checker = "clicked";
                chooseImage();
            }
        });

        savePropBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checker.equals("clicked"))
                    uploadImage();
                else
                    AddProp(null);



            }
        });

    }

    private void AddProp(String imageUrl){

        HashMap<String, Object> proprietaire = new HashMap<>();
        proprietaire.put(getString(R.string.nom_prop), nomProp.getText().toString());
        proprietaire.put(getString(R.string.prenom_prop), prenomProp.getText().toString());
        proprietaire.put(getString(R.string.id_prop), idProp.getText().toString());
        proprietaire.put(getString(R.string.nbre_prop), 0);

        if (imageUrl!=null)
            proprietaire.put("ProfilePic", imageUrl);

        db.collection(COLLECTION_PROPRIETAIRE)
                .add(proprietaire).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
//                Intent intent = new Intent(AddPropActivity.this, ListPropActivity.class);
//                //intent.putExtra("id", documentReference.getId());
//                startActivity(intent);
                Toast.makeText(AddPropActivity.this,"Proprietaire Added Successfully",Toast.LENGTH_SHORT).show();
                firebaseIncrement(db.collection(COLLECTION_NOMBRES).document(COLLECTION_NOMBRES_PROPRIETAIRE), COLLECTION_NOMBRES_PROPRIETAIRE);
                onBackPressed();
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
                profilProp.setImageBitmap(bitmap);
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


            final StorageReference ref = storageProfileRef.child(COLLECTION_PROFIL_PROPRIETAIRE+ "/" + idProp.getText().toString());
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
                        AddProp(downloadUri.toString());

                    }
                    file.delete();
                    progressDialog.dismiss();
                }
            });

        }
    }
}
