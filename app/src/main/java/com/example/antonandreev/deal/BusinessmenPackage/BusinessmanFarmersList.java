package com.example.antonandreev.deal.BusinessmenPackage;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.antonandreev.deal.FarmerPackage.FarmerProducts;
import com.example.antonandreev.deal.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BusinessmanFarmersList extends AppCompatActivity implements View.OnClickListener{
    Integer i = 0;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    FirebaseListAdapter mAdapter;
    ArrayAdapter<String> adapter;
    ArrayList<String> farmers = new ArrayList();
    ArrayList<String> farmers_fisrtnames = new ArrayList();
    ArrayList<String> farmers_secondnames = new ArrayList();
    ArrayList<String> farmers_fullnames = new ArrayList();
    private ListView ListFarmers;

    FirebaseUser user = mAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_businessman_farmers_list);

        ListFarmers = (ListView) findViewById(R.id.list_farmers);
        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("users").child("Farmers").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d("sss", "onChildAdded:" + dataSnapshot.getKey());
                String farmer = dataSnapshot.getKey();
                farmers.add(farmer);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        });

        findViewById(R.id.btn_show_farmers).setOnClickListener(this);

        Button button = findViewById(R.id.btn_menu);
        button.setOnClickListener(viewClickListener);

    }
    View.OnClickListener viewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showPopupMenu(v);
        }
    };

    private void showPopupMenu(View v) {
        PopupMenu menu = new PopupMenu(this, v);
        menu.inflate(R.menu.menu_businessman_list_farmer);


        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.main:
                        Intent hoba = new Intent(BusinessmanFarmersList.this, BusinessmanMain.class);
                        startActivity(hoba);
                        return true;
                    case R.id.updateprofile:
                        Intent intent = new Intent(BusinessmanFarmersList.this, BusinessmanUpdateProfile.class);
                        startActivity(intent);
                        return true;
                    default:
                        return false;
                }
            }
        });
        menu.show();
    }

    public void onClick(View v){
        if(v.getId() == R.id.btn_show_farmers){
            try{
                while (i == 0){
                    for (int i = 0; i < farmers.size(); i++) {
                        FindFarmerName(farmers.get(i));
                        FindFarmerSecondName(farmers.get(i));
                    }
                    i += 1;
                }
                ListFarmers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(BusinessmanFarmersList.this, BusinessmanFarmerActivity.class);
                        intent.putExtra("FarmerUI", farmers.get(position));
                        intent.putExtra("FarmerName", ListFarmers.getItemAtPosition(position).toString());
                        startActivity(intent);
                    }
                });
                FarmerDataView();
            }catch (Exception e){
                Toast.makeText(BusinessmanFarmersList.this, "List of farmers is empty", Toast.LENGTH_SHORT).show();
            }

        }
    }
    public void FarmerDataView(){
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, farmers_secondnames);
        ListFarmers.setAdapter(adapter);
    }
    private void FindFarmerName(String FarmerUI){
        DatabaseReference Ref = myRef.child("users").child("Farmers").child(FarmerUI).child("FirstName").child("firstname");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    String firstname = dataSnapshot.getValue().toString();
                    farmers_fisrtnames.add(firstname);
                } else {
                    Toast.makeText(BusinessmanFarmersList.this, "List of farmers is empty", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        Ref.addListenerForSingleValueEvent(eventListener);
    }
    private void FindFarmerSecondName(String FarmerUI){
        DatabaseReference Ref = myRef.child("users").child("Farmers").child(FarmerUI).child("SecondName").child("secondname");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    String secondname = dataSnapshot.getValue().toString();
                    farmers_secondnames.add(secondname);
                } else {
                    Toast.makeText(BusinessmanFarmersList.this, "List of farmers is empty", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        Ref.addListenerForSingleValueEvent(eventListener);
    }

}

