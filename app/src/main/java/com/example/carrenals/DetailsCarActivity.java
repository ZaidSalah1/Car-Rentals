package com.example.carrenals;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailsCarActivity extends AppCompatActivity {

    TextView txtName,txtYear,txtColor,txtSeats,txtTransmission,txtPrice;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_car);
        // Initialize TextViews
        txtName = findViewById(R.id.txtCarModel);
        txtYear = findViewById(R.id.txtCarYear);
        txtColor = findViewById(R.id.txtCarColor);
        txtSeats = findViewById(R.id.txtCarSeatsNum);
        txtTransmission = findViewById(R.id.txtCarTransmissionr);
        txtPrice = findViewById(R.id.txtCarPrice);
        imageView = findViewById(R.id.imageView);

        // Get intent extras
        Intent intent = getIntent();
        if (intent != null) {
            String name = intent.getStringExtra("name");
            int year = intent.getIntExtra("year", 0);
            String color = intent.getStringExtra("color");
            int seats = intent.getIntExtra("carsSeatingCapacity", 0);
            String transmission = intent.getStringExtra("transmission");
            String price = intent.getStringExtra("price");
            int img = intent.getIntExtra("carImage",0);
            // Set text to TextViews
            txtName.setText("Car Model: " + name);
            txtYear.setText("Year: " + year);
            txtColor.setText("Color: " + color);
            txtSeats.setText("Seats: " + seats);
            txtTransmission.setText("Transmission: " + transmission);
            txtPrice.setText("Rental Price: " + price);
            imageView.setImageResource(img);
        }


    }
}