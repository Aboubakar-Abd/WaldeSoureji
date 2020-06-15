package com.bello.betaille.Model;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;

public class FirebaseQueries {

     public static void firebaseIncrement(DocumentReference documentReference,String s){

         documentReference.update(s, FieldValue.increment(1));

    }

    public static void firebaseDecrementt(DocumentReference documentReference,String s){

        documentReference.update(s, FieldValue.increment(-1));

    }


}
