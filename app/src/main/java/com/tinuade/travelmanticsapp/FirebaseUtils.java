package com.tinuade.travelmanticsapp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FirebaseUtils {
    public static FirebaseUtils firebaseUtils;
    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;
    public static ArrayList<TravelDeal> travelDeals;

    private FirebaseUtils() {
    }

    public static void openDatabaseReference(String ref) {
        if (firebaseUtils == null) {
            firebaseUtils = new FirebaseUtils();
            firebaseDatabase = FirebaseDatabase.getInstance();
            travelDeals = new ArrayList<>();
        }
        databaseReference = firebaseDatabase.getReference().child(ref);

    }
}
