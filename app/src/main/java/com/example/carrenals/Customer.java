package com.example.carrenals;


public class Customer {
    private  String first_name;
    private  String second_name;

    private  String Email;
    private String password;
    private String phone;
    private  String gender;

    private  boolean isVendors;
    public Customer(){

    }
    public Customer(String email, String password, String phone, String gender, String city, String country) {
        Email = email;
        this.password = password;
        this.phone = phone;
        this.gender = gender;
        this.city = city;
        this.country = country;
    }

    private String city;
    private String country;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSecond_name() {
        return second_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}

