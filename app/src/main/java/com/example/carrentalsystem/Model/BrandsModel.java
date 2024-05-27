package com.example.carrentalsystem.Model;

public class BrandsModel {
    private String name;
    private int image;


    public BrandsModel(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }
}
