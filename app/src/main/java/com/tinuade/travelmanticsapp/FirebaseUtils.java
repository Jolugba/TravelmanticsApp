package com.tinuade.travelmanticsapp;

import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FirebaseUtils {
    public static FirebaseUtils firebaseUtils;
    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;
    public static final int RC_SIGN_IN = 127;
    public static FirebaseAuth firebaseAuth;
    public static ArrayList<TravelDeal> travelDeals;
    public static FirebaseAuth.AuthStateListener authStateListener;
    public static Activity caller;

    private FirebaseUtils() {
    }

    public static void openDatabaseReference(String ref, final Activity callerActivity) {
        if (firebaseUtils == null) {
            firebaseUtils = new FirebaseUtils();
            firebaseDatabase = FirebaseDatabase.getInstance();
            firebaseAuth = FirebaseAuth.getInstance();
            caller = callerActivity;

            authStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    if (firebaseAuth.getCurrentUser() == null) {
                        FirebaseUtils.SIGNIN();
                    }
                    Toast.makeText(callerActivity.getApplicationContext(), "Welcome back", Toast.LENGTH_SHORT).show();
                }
            };
        }
        travelDeals = new ArrayList<>();
        databaseReference = firebaseDatabase.getReference().child(ref);

    }

    public static void attachListener() {
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    public static void detachListener() {
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

    public static void SIGNIN() {
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());


        // Create and launch sign-in intent
        caller.startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(), RC_SIGN_IN);
    }
}
