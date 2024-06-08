package com.example.carrenals.Model;

public class Car {
    private String brand;
    private String model;
    private String color;
    private int num_of_seats;
    private String plate_number;
    private String model_year;
    private String image;



    public Car(String brand, String model, String color, int num_of_seats, String plate_number, String model_year, String image) {
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.num_of_seats = num_of_seats;
        this.plate_number = plate_number;
        this.model_year = model_year;
        this.image = image;
    }

    public Car() {

    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getNum_of_seats() {
        return num_of_seats;
    }

    public void setNum_of_seats(int num_of_seats) {
        this.num_of_seats = num_of_seats;
    }

    public String getPlate_number() {
        return plate_number;
    }

    public void setPlate_number(String plate_number) {
        this.plate_number = plate_number;
    }

    public String getModel_year() {
        return model_year;
    }

    public void setModel_year(String model_year) {
        this.model_year = model_year;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                ", num_of_seats='" + num_of_seats + '\'' +
                ", plate_number='" + plate_number + '\'' +
                ", model_year='" + model_year + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
