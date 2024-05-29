package com.example.carrentalsystem.Model;

public class CarModel {
    private String name;
    private int img;
    private String price;
    private String year;
    private String fuelType;
    private String transmission;
    private int seatingCapacity;
    private String color;

    public CarModel(String name, int img, String price) {
        this.name = name;
        this.img = img;
        this.price = price;
    }

    public CarModel(String name, int img, String price, int year, String fuelType, String transmission, int seatingCapacity, String color) {
        this.name = name;
        this.img = img;
        this.price = price;
        this.fuelType = fuelType;
        this.transmission = transmission;
        this.seatingCapacity = seatingCapacity;
        this.color = color;
    }

    public CarModel(String name, String color, int seatingCapacity, String year) {

        this.name = name;
        this.year = year;
        this.seatingCapacity = seatingCapacity;
        this.color = color;
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    public void setSeatingCapacity(int seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "CarModel{" +
                "name='" + name + '\'' +
                ", img=" + img +
                ", price='" + price + '\'' +
                ", year='" + year + '\'' +
                ", fuelType='" + fuelType + '\'' +
                ", transmission='" + transmission + '\'' +
                ", seatingCapacity=" + seatingCapacity +
                ", color='" + color + '\'' +
                '}';
    }
}
