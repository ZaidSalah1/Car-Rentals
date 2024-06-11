package com.example.carrenals.Brands;



public class BrandsModel {

    private int brandId;
    private String name;

    public BrandsModel(Integer brandId) {
        this.brandId = brandId;
    }

    public BrandsModel(Integer brandId, String brandName, String image) {
        this.brandId = brandId;
        this.name = brandName;
        this.image2 = image;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

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

    public BrandsModel() {

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
