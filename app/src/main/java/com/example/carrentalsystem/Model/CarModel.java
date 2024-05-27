package com.example.carrentalsystem.Model;

public class CarModel {
    private String name;
    private int img;
    private String price;

    public CarModel(String name, int img, String price) {
        this.name = name;
        this.img = img;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
