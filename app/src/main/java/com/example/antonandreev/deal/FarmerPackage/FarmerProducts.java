package com.example.antonandreev.deal.FarmerPackage;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.antonandreev.deal.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class FarmerProducts extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private TextView selection;
    private Button btn_show_products;
    private Button button;
    private ListView ListFarmerProduct;

    ArrayAdapter<String> adapter;
    ArrayList<String> products = new ArrayList();
    ArrayList<String> selectedProducts = new ArrayList();
    ArrayList<String> selectedNameProducts = new ArrayList();
    FirebaseUser user = mAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_farmer_products);
        ListFarmerProduct = (ListView) findViewById(R.id.list_products);
        myRef = FirebaseDatabase.getInstance().getReference().child("users").child("Farmers").child(user.getUid()).child("Products");
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
        findViewById(R.id.btn_show_products).setOnClickListener(this);
        findViewById(R.id.btn_del_product).setOnClickListener(this);
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
        menu.inflate(R.menu.menu_farmer_show_product);


        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.main:
                        Intent hoba = new Intent(FarmerProducts.this, FarmerMain.class);
                        startActivity(hoba);
                        return true;
                    case R.id.updateprofile:
                        Intent intent = new Intent(FarmerProducts.this, FarmerUpdateProfile.class);
                        startActivity(intent);
                        return true;
                    case R.id.add_product:
                        Intent add_product = new Intent(FarmerProducts.this, FarmerAddProducts.class);
                        startActivity(add_product);
                        return true;
                    default:
                        return false;
                }
            }
        });


        menu.show();
    }

    public void onClick(View v) {
        if (v.getId() == R.id.btn_show_products){
            try{
                DataView();
                ListFarmerProduct.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id)
                    {
                        final String product = adapter.getItem(position);
                        final String NameProduct = products.get(position);
                        if(ListFarmerProduct.isItemChecked(position)==true){
                            selectedProducts.add(product);
                            selectedNameProducts.add(NameProduct);
                        }else{
                            selectedProducts.remove(product);
                            selectedNameProducts.remove(NameProduct);
                        }

                    }
                });
            }catch (Exception e){
                Toast.makeText(FarmerProducts.this, "Your list of products is empty", Toast.LENGTH_SHORT).show();
            }
        }
        else if(v.getId() == R.id.btn_del_product){
            remove();
            for (int i = 0; i < selectedNameProducts.size(); i++){
                DeleteProduct(selectedNameProducts.get(i));
            }
        }

    }
    private void DeleteProduct(String ProductName){
        DatabaseReference DelProduct = FirebaseDatabase.getInstance().getReference().child("users").child("Farmers").child(user.getUid()).child("Products").child(ProductName);
        DelProduct.removeValue();
    }
    public void DataView(){
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, products);
        ListFarmerProduct.setAdapter(adapter);
    }
    public void remove(){
        for(int i=0; i< selectedProducts.size();i++){
            adapter.remove(selectedProducts.get(i));
        }
        ListFarmerProduct.clearChoices();
        selectedProducts.clear();
        adapter.notifyDataSetChanged();
    }

}

