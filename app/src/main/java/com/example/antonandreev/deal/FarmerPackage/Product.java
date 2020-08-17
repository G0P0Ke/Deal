package com.example.antonandreev.deal.FarmerPackage;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.PriorityQueue;

@IgnoreExtraProperties
public class Product {
    public String Name;
    public String Price;

    public Product(String Price){
        this.Price = Price;
    }


    public Product(String Name, String Price){
        this.Name = Name;
        this.Price = Price;
    }
}
