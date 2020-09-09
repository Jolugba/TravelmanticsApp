package com.tinuade.travelmanticsapp;

import android.content.Intent;
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

public class DealActivity extends AppCompatActivity {
    EditText title;
    EditText price;
    EditText description;
    TravelDeal travelDeal;
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
        Intent intent = getIntent();
        TravelDeal deal = (TravelDeal) intent.getSerializableExtra("Deals");
        if (deal == null) {
            deal = new TravelDeal();
        }
        this.travelDeal = deal;
        title.setText(travelDeal.getTitle());
        price.setText(travelDeal.getPrice());
        description.setText(travelDeal.getDescription());

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
            case R.id.delete_deal:
                deleteDeal();
                Toast.makeText(this, "Deal deleted", Toast.LENGTH_SHORT).show();
                refresh();
                backToList();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    // save the travels on the database
    private void saveDeal() {
        travelDeal.setTitle(title.getText().toString());
        travelDeal.setPrice(price.getText().toString());
        travelDeal.setDescription(description.getText().toString());
        if (travelDeal.getId() == null) {
            dataBaseReference.push().setValue(travelDeal);
        } else
            dataBaseReference.child(travelDeal.getId()).setValue(travelDeal);
    }

    private void deleteDeal() {
        if (travelDeal == null) {
            Toast.makeText(this, "Please save the deal before deleting", Toast.LENGTH_LONG).show();
            return;
        }
        dataBaseReference.child(travelDeal.getId()).removeValue();
    }

    private void backToList() {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    // clean up the editText
    private void refresh() {
        title.setText("");
        price.setText("");
        description.setText("");
        title.requestFocus();
    }
}