package com.example.carrenals;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DetailsCarActivity extends AppCompatActivity {

    TextView txtName,txtYear,txtColor,txtSeats,txtTransmission,txtPrice,txtAvailable;
    ImageView imageView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_car);
        // Initialize TextViews
        txtName = findViewById(R.id.txtCarModel);
        txtYear = findViewById(R.id.txtCarYear);
        txtColor = findViewById(R.id.txtCarColor);
        txtSeats = findViewById(R.id.txtCarSeatsNum);

        txtPrice = findViewById(R.id.txtCarPrice);
        imageView = findViewById(R.id.imageView);
        txtAvailable = findViewById(R.id.txtAvailable);

        // Get intent extras
        Intent intent = getIntent();
        if (intent != null) {
            String name = intent.getStringExtra("name");
            String year = intent.getStringExtra("year");
            String color = intent.getStringExtra("color");
            int seats = intent.getIntExtra("carsSeatingCapacity", 0);
            String transmission = intent.getStringExtra("transmission");
            String price = intent.getStringExtra("price");
            String img = intent.getStringExtra("carImage");
            String available = intent.getStringExtra("available");
            // Set text to TextViews
            txtName.setText("Car Model: " + name);
            txtYear.setText("Year: " + year);
            txtColor.setText("Color: " + color);
            txtSeats.setText("Seats: " + seats);

            txtPrice.setText("Rental Price: " + price);
            txtAvailable.setText("Availability: " + available);
            Glide.with(this).load(img).into(imageView);
        }
    }
}