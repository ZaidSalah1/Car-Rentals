package com.example.carrenals;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DetailsCarActivity extends AppCompatActivity {

    TextView txtName,txtYear,txtColor,txtSeats,txtTransmission,txtPrice,txtAvailable;
    ImageView imageView;

    Button btnRent;
    String name,color,year,available;
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
        txtAvailable = findViewById(R.id.txtAvailable);
        btnRent = findViewById(R.id.btnRent);

        // Get intent extras
        Intent intent = getIntent();
        if (intent != null) {
            name = intent.getStringExtra("name");
            year = intent.getStringExtra("year");
            color = intent.getStringExtra("color");
            int seats = intent.getIntExtra("carsSeatingCapacity", 0);
            String transmission = intent.getStringExtra("transmission");
            String price = intent.getStringExtra("price");
            String img = intent.getStringExtra("carImage");
            available = intent.getStringExtra("available");
            // Set text to TextViews
            txtName.setText("Car Model: " + name);
            txtYear.setText("Year: " + year);
            txtColor.setText("Color: " + color);
            txtSeats.setText("Seats: " + seats);
            txtTransmission.setText("Transmission: " + transmission);
            txtPrice.setText("Rental Price: " + price);
            txtAvailable.setText("Availability: " + available);
            Glide.with(this).load(img).into(imageView);
        }

        btnRent.setOnClickListener(view -> {
            String check = available;
            if(check.equalsIgnoreCase("yes")){
                Intent intent2 = new Intent(DetailsCarActivity.this, RentCar.class);
                intent2.putExtra("name",name);
                intent2.putExtra("year",year);
                intent2.putExtra("color",color);
                intent2.putExtra("image",intent.getStringExtra("carImage"));

                startActivity(intent2);
            }else{
                Toast.makeText(DetailsCarActivity.this, "This Car Is Not Available", Toast.LENGTH_LONG).show();
            }
        });
    }


}