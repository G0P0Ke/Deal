package com.example.antonandreev.deal;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

@IgnoreExtraProperties
public class Order {
    public ArrayList <String> Products;

    public Order(ArrayList <String> Products){
        this.Products = Products;
    }
}
