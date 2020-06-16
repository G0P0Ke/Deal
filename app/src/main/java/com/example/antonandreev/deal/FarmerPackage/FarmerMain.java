package com.example.antonandreev.deal.FarmerPackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.example.antonandreev.deal.R;
import com.example.antonandreev.deal.choice;

public class FarmerMain extends AppCompatActivity implements View.OnClickListener{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_farmer_main);
        Button button = findViewById(R.id.btn_menu);
        button.setOnClickListener(viewClickListener);
        findViewById(R.id.btn_logout).setOnClickListener(this);

    }
    View.OnClickListener viewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showPopupMenu(v);
        }
    };

    private void showPopupMenu(View v) {
        PopupMenu menu = new PopupMenu(this, v);
        menu.inflate(R.menu.menu_farmer_main);

        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.updateprofile:
                                Intent hoba = new Intent(FarmerMain.this, FarmerUpdateProfile.class);
                                startActivity(hoba);
                                return true;
                            case R.id.products:
                                Intent intent = new Intent(FarmerMain.this, FarmerProducts.class);
                                startActivity(intent);
                                return true;
                            default:
                                return false;
                        }
                    }
                });


        menu.show();
    }

    public void onClick(View v) {
        if (v.getId() == R.id.btn_logout) {
            Intent hoba = new Intent(FarmerMain.this, choice.class);
            startActivity(hoba);
        }
    }
}
