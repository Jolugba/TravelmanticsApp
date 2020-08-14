package com.tinuade.travelmanticsapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText title;
    EditText price;
    EditText description;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference dataBaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.titleEditText);
        price = findViewById(R.id.priceEditText);
        description = findViewById(R.id.txtDescription);

        firebaseDatabase = FirebaseDatabase.getInstance();
        dataBaseReference = firebaseDatabase.getReference().child(getString(R.string.traveldeals));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveItem:
                saveDeal();
                Toast.makeText(this, R.string.dealSaveToast, Toast.LENGTH_SHORT).show();
                refresh();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    // save the travels on the database
    private void saveDeal() {
        String dealTitle = title.getText().toString();
        String dealPrice = price.getText().toString();
        String dealDescription = description.getText().toString();
        TravelDeal deal = new TravelDeal(dealTitle, dealDescription, dealPrice, "");
        dataBaseReference.push().setValue(deal);

    }

    // clean up the editText
    private void refresh() {
        title.setText("");
        price.setText("");
        description.setText("");
        title.requestFocus();
    }
}