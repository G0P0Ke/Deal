package com.example.antonandreev.deal.FarmerPackage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.antonandreev.deal.R;

import com.example.antonandreev.deal.Users.User1;
import com.example.antonandreev.deal.Users.User2;
import com.example.antonandreev.deal.Users.User3;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FarmerUpdateProfile extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;
    private EditText firstname;
    private EditText secondname;
    private EditText telephone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_farmer_update_profile);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        firstname = (EditText) findViewById(R.id.first_name);
        secondname = (EditText) findViewById(R.id.second_name);
        telephone = (EditText) findViewById(R.id.telephone);

        findViewById(R.id.btn_update_prof).setOnClickListener(this);

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
        menu.inflate(R.menu.menu_farmer_update_profile);

        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.main:
                        Intent hoba = new Intent(FarmerUpdateProfile.this, FarmerMain.class);
                        startActivity(hoba);
                        return true;
                    case R.id.products:
                        Intent intent = new Intent(FarmerUpdateProfile.this, FarmerProducts.class);
                        startActivity(intent);
                        return true;
                    case R.id.orders:
                        Intent orders = new Intent(FarmerUpdateProfile.this, FarmerOrders.class);
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
        if(v.getId() == R.id.btn_update_prof){
            String finame = firstname.getText().toString();
            String sename = secondname.getText().toString();
            String telep = telephone.getText().toString();
            try{
                if(TextUtils.isEmpty(finame) && TextUtils.isEmpty(sename) && TextUtils.isGraphic(telep)){
                    UpdateTelephone(telep);
                }
                else if(TextUtils.isEmpty(finame) && TextUtils.isGraphic(sename) && TextUtils.isEmpty(telep)){
                    UpdateSName(sename);
                }
                else if(TextUtils.isGraphic(finame) && TextUtils.isEmpty(sename) && TextUtils.isEmpty(telep)){
                    UpdateName(finame);
                }
                else if(TextUtils.isEmpty(finame) && TextUtils.isGraphic(sename) && TextUtils.isGraphic(telep)){
                    UpdateTelephone(telep);
                    UpdateSName(sename);
                }
                else if(TextUtils.isGraphic(finame) && TextUtils.isEmpty(sename) && TextUtils.isGraphic(telep)){
                    UpdateTelephone(telep);
                    UpdateName(finame);
                }
                else if(TextUtils.isGraphic(finame) && TextUtils.isGraphic(sename) && TextUtils.isEmpty(telep)){
                    UpdateSName(sename);
                    UpdateName(finame);
                }
                else if(TextUtils.isGraphic(finame) && TextUtils.isGraphic(sename) && TextUtils.isGraphic(telep)){
                    UpdateName(finame);
                    UpdateSName(sename);
                    UpdateTelephone(telep);
                }
                Toast.makeText(FarmerUpdateProfile.this, "Success", Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                Toast.makeText(FarmerUpdateProfile.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void UpdateName(String firstname) throws Exception{
        onAuthSuccess1(mAuth.getCurrentUser(), firstname);
    }
    private void onAuthSuccess1(FirebaseUser user,  String firstname) {
        writeNewFirstName(user.getUid(),  firstname);
    }
    private void writeNewFirstName(String userId, String firstname) {
        User1 user = new User1(firstname);

        mDatabase.child("users").child("Farmers").child(userId).child("FirstName").setValue(user);
    }



    private void UpdateSName(String secondname) throws Exception{
        onAuthSuccess2(mAuth.getCurrentUser(),secondname);
    }
    private void onAuthSuccess2(FirebaseUser user, String secondname) {
        writeNewSecondName(user.getUid(), secondname);
    }
    private void writeNewSecondName(String userId, String secondname) {
        User2 user = new User2(secondname);

        mDatabase.child("users").child("Farmers").child(userId).child("SecondName").setValue(user);
    }


    private void UpdateTelephone(String telephone) throws Exception{
        onAuthSuccess3(mAuth.getCurrentUser(),telephone);
    }
    private void onAuthSuccess3(FirebaseUser user, String telephone) {
        writeNewTelephone(user.getUid(),telephone);
    }
    private void writeNewTelephone(String userId, String telephone) {
        User3 user = new User3(telephone);

        mDatabase.child("users").child("Farmers").child(userId).child("Telephone").setValue(user);
    }
}

