package com.example.antonandreev.deal.FarmerPackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.antonandreev.deal.BusinessmenPackage.BusinessmanFarmerActivity;
import com.example.antonandreev.deal.BusinessmenPackage.BusinessmanFarmersList;
import com.example.antonandreev.deal.BusinessmenPackage.BusinessmanMain;
import com.example.antonandreev.deal.BusinessmenPackage.BusinessmanUpdateProfile;
import com.example.antonandreev.deal.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FarmerOrders extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    FirebaseListAdapter mAdapter;
    ArrayAdapter<String> adapter;
    ArrayList<String> customers = new ArrayList();
    ArrayList<String> customers_firstnames = new ArrayList();
    private ListView ListCustomers;

    FirebaseUser user = mAuth.getInstance().getCurrentUser();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_farmer_orders);
        ListCustomers = (ListView) findViewById(R.id.list_customers);
        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("users").child("Farmers").child(user.getUid()).child("Orders").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d("sss", "onChildAdded:" + dataSnapshot.getKey());
                String customer = dataSnapshot.getKey();
                customers.add(customer);
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

        findViewById(R.id.btn_show_customers).setOnClickListener(this);

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
        menu.inflate(R.menu.menu_farmer_orders);


        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.main:
                        Intent hoba = new Intent(FarmerOrders.this, FarmerMain.class);
                        startActivity(hoba);
                        return true;
                    case R.id.updateprofile:
                        Intent intent = new Intent(FarmerOrders.this, FarmerUpdateProfile.class);
                        startActivity(intent);
                        return true;
                    case R.id.show_product:
                        Intent farmers = new Intent(FarmerOrders.this, FarmerProducts.class);
                        startActivity(farmers);
                    default:
                        return false;
                }
            }
        });
        menu.show();
    }

    public void onClick(View v){
        if(v.getId() == R.id.btn_show_customers){
            if (customers_firstnames.size() != customers.size()) {
                for (int i = 0; i < customers.size(); i++) {
                    FindBusinessmenName(customers.get(i));
                    Log.d("ЗАКАЗЧИКИ", customers_firstnames.toString());
                }
            }
            try{
                ListCustomers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent customerInfo = new Intent(FarmerOrders.this, FarmerBusinessmanActivity.class);
                        customerInfo.putExtra("CustomerName", ListCustomers.getItemAtPosition(position).toString());
                        customerInfo.putExtra("CustomerID", customers.get(position));
                        startActivity(customerInfo);
                    }
                });
                DataView();
            }catch (Exception e){
                Toast.makeText(FarmerOrders.this, "List of customers is empty", Toast.LENGTH_SHORT).show();
            }

        }
    }
    private void FindBusinessmenName(String CustomerUI){
        DatabaseReference Ref = myRef.child("users").child("Businessmen").child(CustomerUI).child("FirstName").child("firstname");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    String firstname = dataSnapshot.getValue().toString();
                    customers_firstnames.add(firstname);
                } else {
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        Ref.addListenerForSingleValueEvent(eventListener);
    }
    public void DataView(){
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, customers_firstnames);
        ListCustomers.setAdapter(adapter);
    }
}
