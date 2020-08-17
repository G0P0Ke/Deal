package com.example.antonandreev.deal.FarmerPackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.antonandreev.deal.BusinessmenPackage.BusinessmanFarmersList;
import com.example.antonandreev.deal.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ValueEventListener;

public class FarmerBusinessmanActivity  extends AppCompatActivity implements View.OnClickListener{


    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    FirebaseUser user = mAuth.getInstance().getCurrentUser();
    FirebaseListAdapter mAdapter;
    TextView CustomerName;
    TextView PhoneNumber;
    ListView ListProducts;
    String CustomerID;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_farmer_customer_info);
        ListProducts = (ListView) findViewById(R.id.list_products);
        CustomerName = (TextView) findViewById(R.id.CustomerFirstname);
        PhoneNumber = (TextView) findViewById(R.id.phonenumber);
        myRef = FirebaseDatabase.getInstance().getReference();
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            CustomerName.setText(bundle.getString("CustomerName"));
            CustomerID = bundle.getString("CustomerID");
        }
        findViewById(R.id.btn_show_products).setOnClickListener(this);
        FindCustomerPhoneNumber(CustomerID);
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
                        Intent hoba = new Intent(FarmerBusinessmanActivity.this, FarmerMain.class);
                        startActivity(hoba);
                        return true;
                    case R.id.updateprofile:
                        Intent intent = new Intent(FarmerBusinessmanActivity.this, FarmerUpdateProfile.class);
                        startActivity(intent);
                        return true;
                    case R.id.add_product:
                        Intent add_product = new Intent(FarmerBusinessmanActivity.this, FarmerAddProducts.class);
                        startActivity(add_product);
                        return true;
                    case R.id.orders:
                        Intent Orders = new Intent(FarmerBusinessmanActivity.this, FarmerOrders.class);
                        startActivity(Orders);
                        return true;
                    case R.id.show_product:
                        Intent Show_Product = new Intent(FarmerBusinessmanActivity.this, FarmerProducts.class);
                        startActivity(Show_Product);
                        return true;
                    default:
                        return false;
                }
            }
        });


        menu.show();
    }
    public void onClick(View v) {
        if (v.getId() == R.id.btn_show_products) {
            Bundle bundle = getIntent().getExtras();
            CustomerID = bundle.getString("CustomerID");
            mAdapter = new FirebaseListAdapter <String> (this, String.class, android.R.layout.simple_list_item_1,myRef.child("users").child("Farmers").child(user.getUid()).child("Orders").child(CustomerID).child("Products")) {
                @Override
                protected void populateView(View v, String s, int position) {
                    TextView text = (TextView) v.findViewById(android.R.id.text1);
                    text.setText(s);
                }
            };
            ListProducts.setAdapter(mAdapter);
        }
    }
    private void FindCustomerPhoneNumber(String CustomerUI){
        DatabaseReference Ref = myRef.child("users").child("Businessmen").child(CustomerUI).child("Telephone").child("telephone");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    String telephone = dataSnapshot.getValue().toString();
                    PhoneNumber.setText(telephone);

                } else {
                    Toast.makeText(FarmerBusinessmanActivity.this, "У покупателя нет номера", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        Ref.addListenerForSingleValueEvent(eventListener);
    }
}
