package com.example.carrenals;



public class CustomerModel {
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "CustomerModel{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public CustomerModel( String email, String password, String firstName, String lastName) {

        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    int id  ;
    String email ;
    String password;
    String firstName ;
    String lastName;


}
