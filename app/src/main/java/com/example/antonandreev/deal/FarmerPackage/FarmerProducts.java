package com.example.antonandreev.deal.FarmerPackage;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.antonandreev.deal.Products;
import com.example.antonandreev.deal.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class FarmerProducts extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private List<String> ListProducts;



    ListView ListUserProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_products);

        ListUserProducts = (ListView) findViewById(R.id.list_products);

        myRef = FirebaseDatabase.getInstance().getReference();

        FirebaseUser user = mAuth.getInstance().getCurrentUser();

        Log.d("APP_DEV", user.getUid());

        myRef.child("Farmer").child(user.getUid()).addValueEventListener(new ValueEventListener()  {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)  {
                GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {};
                ListProducts = dataSnapshot.getValue(Products.class).getProducts();

                updateUI();
            }




            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(FarmerProducts.this, "You don't have products", Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void updateUI() {
        ArrayAdapter <String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1,ListProducts);
        ListUserProducts.setAdapter(adapter);
    }

}

