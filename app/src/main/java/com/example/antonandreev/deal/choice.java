package com.example.antonandreev.deal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.antonandreev.deal.BusinessmenPackage.BusinessmanEntry;
import com.example.antonandreev.deal.FarmerPackage.FarmerEntry;

public class choice extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_choice);
        ImageButton businessman = (ImageButton) this.findViewById(R.id.businessman);

        businessman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(choice.this, BusinessmanEntry.class);
                choice.this.startActivity(intent);
            }
        });


        ImageButton farmer = (ImageButton) this.findViewById(R.id.farmer);

        farmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(choice.this, FarmerEntry.class);
                choice.this.startActivity(intent);
            }
        });
    }

}
