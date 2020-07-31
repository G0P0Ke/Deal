package com.example.antonandreev.deal.FarmerPackage;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.PriorityQueue;

@IgnoreExtraProperties
public class Product {
    public String Name;
    public Integer Price;

    public Product(Integer Price){
        this.Price = Price;
    }

    public Product(String Name){
        this.Name = Name;
    }

    public Product(String Name, Integer Price){
        this.Name = Name;
        this.Price = Price;
    }
}
