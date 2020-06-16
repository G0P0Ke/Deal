package com.example.antonandreev.deal.FarmerPackage;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.antonandreev.deal.R;
import com.example.antonandreev.deal.Users.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FarmerEntry extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;
    private EditText email;
    private EditText password;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_farmer_entry);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        email = (EditText) findViewById(R.id.far_email);
        password = (EditText) findViewById(R.id.far_password);

        findViewById(R.id.btn_sign_in).setOnClickListener(this);
        findViewById(R.id.btn_registration).setOnClickListener(this);


    }


    @Override

    public void onClick(View v) {
        if(v.getId() == R.id.btn_sign_in){
            try{
                signin(email.getText().toString(), password.getText().toString());
            }catch (Exception e){
                Toast.makeText(FarmerEntry.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }else if(v.getId() == R.id.btn_registration){
            try{
                signup(email.getText().toString(), password.getText().toString());
            }catch(Exception e){
                Toast.makeText(FarmerEntry.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void signin(String email, String password) throws Exception{
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    boolean isnew =task.getResult().getAdditionalUserInfo().isNewUser();
                    if(isnew){
                    }
                    else{
                        Toast.makeText(FarmerEntry.this, "Authorization is successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(FarmerEntry.this, FarmerMain.class);
                        FarmerEntry.this.startActivity(intent);
                    }
                }else{
                    Toast.makeText(FarmerEntry.this, "Authorization failed\nCheck the correctness of the email", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }
    public void signup(String email, String password) throws Exception{
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(FarmerEntry.this, "registration successful", Toast.LENGTH_SHORT).show();
                    onAuthSuccess(mAuth.getCurrentUser());
                } else {
                    Toast.makeText(FarmerEntry.this, "registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void onAuthSuccess(FirebaseUser user) {
        writeNewUser(user.getUid(),user.getEmail());
    }
    private void writeNewUser(String userId,String email) {
        User user = new User(email);
        mDatabase.child("users").child("Businessmen").child(userId).child("Email").setValue(user);
        mDatabase.child("users").child("Farmers").child(userId).child("Email").setValue(user);
    }


}
