package com.example.onlineshopp.Database;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ConnectFirebase {
    public static FirebaseFirestore db;

    public  static FirebaseAuth mAuth;

    public  static void setDb(){
        db=FirebaseFirestore.getInstance();
    }

    public  static void setmAuth(){
        mAuth=FirebaseAuth.getInstance();
    }
}
