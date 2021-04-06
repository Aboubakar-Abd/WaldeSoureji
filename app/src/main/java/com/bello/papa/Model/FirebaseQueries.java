package com.bello.papa.Model;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;

public class FirebaseQueries {

     public static void firebaseIncrement(DocumentReference documentReference,String s){

         documentReference.update(s, FieldValue.increment(1));

    }

    public static void firebaseDecrementt(DocumentReference documentReference,String s){

        documentReference.update(s, FieldValue.increment(-1));

    }


}
