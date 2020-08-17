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
import com.example.antonandreev.deal.FarmerPackage.Product;
import com.example.antonandreev.deal.Order;
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

public class BusinessmanFarmerActivity extends AppCompatActivity implements View.OnClickListener {

    TextView FarmerName;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private DatabaseReference mDatabase;
    FirebaseListAdapter mAdapter;
    private ListView ListFarmerProduct;
    ArrayAdapter<String> adapter;
    ArrayList<String> products = new ArrayList();
    ArrayList<String> selectedNameProducts = new ArrayList();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_businessman_farmer_info);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FarmerName = (TextView) findViewById(R.id.FarmerSecondname);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            FarmerName.setText(bundle.getString("FarmerName"));
            FarmerInfo(bundle.getString("FarmerUI"));
        }

        ListFarmerProduct = (ListView) findViewById(R.id.list_products);
        findViewById(R.id.btn_show_products).setOnClickListener(this);
        findViewById(R.id.btn_order_products).setOnClickListener(this);
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
        menu.inflate(R.menu.menu_businessman_farmer_info);


        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.main:
                        Intent hoba = new Intent(BusinessmanFarmerActivity.this, BusinessmanMain.class);
                        startActivity(hoba);
                        return true;
                    case R.id.updateprofile:
                        Intent intent = new Intent(BusinessmanFarmerActivity.this, BusinessmanUpdateProfile.class);
                        startActivity(intent);
                        return true;
                    case R.id.farmer_list:
                        Intent farmers = new Intent(BusinessmanFarmerActivity.this, BusinessmanFarmersList.class);
                        startActivity(farmers);
                    default:
                        return false;
                }
            }
        });
        menu.show();
    }

    public void onClick(View v) {
        if (v.getId() == R.id.btn_show_products) {
            try {

                DataView();
                if (products.size() == 0){
                    Toast.makeText(BusinessmanFarmerActivity.this, "List of products is empty", Toast.LENGTH_SHORT).show();
                }else{
                    ListFarmerProduct.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                        @Override
                        public void onItemClick(AdapterView<?> parent, View v, int position, long id)
                        {
                            final String product = adapter.getItem(position);
                            final String NameProduct = products.get(position);
                            if(ListFarmerProduct.isItemChecked(position)==true){
                                selectedNameProducts.add(NameProduct);
                            }else{
                                selectedNameProducts.remove(NameProduct);
                            }

                        }
                    });
                }
            } catch (Exception e) {
                Toast.makeText(BusinessmanFarmerActivity.this, "List of products is empty", Toast.LENGTH_SHORT).show();
            }
        }else if(v.getId() == R.id.btn_order_products){
            Bundle bundle = getIntent().getExtras();
            String FarmerID = bundle.getString("FarmerUI");
            try {
                AddNewOrder(FarmerID, selectedNameProducts);
                Toast.makeText(BusinessmanFarmerActivity.this, "Заказ сделан. Ждите звонка", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(BusinessmanFarmerActivity.this, "Failed to place an order", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void FarmerInfo(String FarmerUI){
        myRef = FirebaseDatabase.getInstance().getReference().child("users").child("Farmers").child(FarmerUI).child("Products");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d("sss", "onChildAdded:" + dataSnapshot.getKey());
                String product = dataSnapshot.getKey();
                products.add(product);
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
    }
    public void DataView(){
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, products);
        ListFarmerProduct.setAdapter(adapter);
    }
    private void AddNewOrder(String FarmerID,ArrayList <String> ProductNames) throws Exception{
        onAuthSuccess(FarmerID, mAuth.getCurrentUser(), ProductNames);
    }
    private void onAuthSuccess(String FarmerID, FirebaseUser user,ArrayList <String> ProductNames) {
        CreateNewOrder(FarmerID, user.getUid(), ProductNames);
    }
    private void CreateNewOrder(String FarmerID, String CustomerId,ArrayList <String> ProductNames){
        Order order = new Order(ProductNames);
        mDatabase.child("users").child("Farmers").child(FarmerID).child("Orders").child(CustomerId).setValue(order);
    }

}
