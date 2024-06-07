package com.example.carrentalsystem.Model;

public class BrandsModel {
    private String name;
    private int image;
    private String image2;
    public BrandsModel(String name, String image2) {
        this.name = name;
        this.image2 = image2;
    }

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

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }
}

