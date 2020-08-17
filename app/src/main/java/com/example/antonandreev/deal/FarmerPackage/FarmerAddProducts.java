package com.example.antonandreev.deal.FarmerPackage;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.example.antonandreev.deal.FarmerPackage.Product;
import com.example.antonandreev.deal.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;



public class FarmerAddProducts extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private EditText EditTextPrice;
    private EditText ProductName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_farmer_add_products);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        ProductName = (EditText) findViewById(R.id.product_name);
        EditTextPrice = (EditText) findViewById(R.id.product_price);

        findViewById(R.id.btn_add_new_product).setOnClickListener(this);


        Button button = findViewById(R.id.btn_menu);
        button.setOnClickListener(viewClickListener);
    };
    View.OnClickListener viewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showPopupMenu(v);
        }
    };

    private void showPopupMenu(View v) {
        PopupMenu menu = new PopupMenu(this, v);
        menu.inflate(R.menu.menu_farmer_add_new_product);


        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.main:
                        Intent hoba = new Intent(FarmerAddProducts.this, FarmerMain.class);
                        startActivity(hoba);
                        return true;
                    case R.id.show_product:
                        Intent show_product = new Intent(FarmerAddProducts.this, FarmerProducts.class);
                        startActivity(show_product);
                        return true;
                    case R.id.updateprofile:
                        Intent intent = new Intent(FarmerAddProducts.this, FarmerUpdateProfile.class);
                        startActivity(intent);
                        return true;
                    case R.id.orders:
                        Intent orders = new Intent(FarmerAddProducts.this, FarmerOrders.class);
                        startActivity(orders);
                        return true;
                    default:
                        return false;
                }
            }
        });


        menu.show();
    }
    public void onClick(View v) {
        if (v.getId() == R.id.btn_add_new_product) {
            String Name = ProductName.getText().toString();
            String Price = EditTextPrice.getText().toString();
            try {
                if (TextUtils.isGraphic(Name) && TextUtils.isGraphic(Price)) {
                    AddNewProduct(Name, Price);
                }
                Toast.makeText(FarmerAddProducts.this, "Success", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(FarmerAddProducts.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void AddNewProduct(String Name, String Price) throws Exception{
            onAuthSuccess(mAuth.getCurrentUser(), Name, Price);
        }
    private void onAuthSuccess(FirebaseUser user, String ProductName, String ProductPrice) {
        CreateNewProduct(user.getUid(), ProductName, ProductPrice);
    }
    private void CreateNewProduct(String userId, String ProductName,String ProductPrice){
        Product product = new Product(ProductPrice);
        mDatabase.child("users").child("Farmers").child(userId).child("Products").child(ProductName).setValue(product);
    }
}
