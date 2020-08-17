package com.example.antonandreev.deal.BusinessmenPackage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.example.antonandreev.deal.R;
import com.example.antonandreev.deal.choice;

public class BusinessmanMain extends AppCompatActivity implements View.OnClickListener{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_businessman_main);
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
        menu.inflate(R.menu.menu_businessman_main);

        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.updateprofile:
                                Intent intent = new Intent(BusinessmanMain.this, BusinessmanUpdateProfile.class);
                                startActivity(intent);
                                return true;
                            case R.id.list_farmers:
                                Intent intent2 = new Intent(BusinessmanMain.this, BusinessmanFarmersList.class);
                                startActivity(intent2);
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
            Intent hoba = new Intent(BusinessmanMain.this, choice.class);
            startActivity(hoba);
        }
    }
}
