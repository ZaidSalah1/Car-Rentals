package com.example.carrenals.Brands;

import com.example.carrenals.Model.CarModel;

public class VendorCar extends CarModel {

    private Integer vendorId;

    private Integer carId;

    private Double dailyCost;

    public VendorCar(int vendorId, int carId, double dailyCost) {
        super();
        this.vendorId = vendorId;
        this.carId = carId;
        this.dailyCost = dailyCost;
    }

    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public double getDailyCost() {
        return dailyCost;
    }

    public void setDailyCost(double dailyCost) {
        this.dailyCost = dailyCost;
    }
}
