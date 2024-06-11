package com.example.carrenals;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

public class RentCar extends AppCompatActivity {

    ImageView imageView;
    TextView txtInfo;
    Button btnRentDone;

    String model_name, availability;
    private TextInputLayout editName, editEmail, editPhone, editLicense, editStartDate, editEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_car);

        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editPhone = findViewById(R.id.editPhone);
        editLicense = findViewById(R.id.editLicense);
        editStartDate = findViewById(R.id.editStartDate);
        editEndDate = findViewById(R.id.editEndDate);

        imageView = findViewById(R.id.carRentImage);
        txtInfo = findViewById(R.id.carRentInfoText);

        Intent intent = getIntent();
        String img = intent.getStringExtra("image");
        String name = intent.getStringExtra("name");
        String[] tmp = name.split(" ");
        model_name = tmp[1];
        String year = intent.getStringExtra("year");
        String color = intent.getStringExtra("color");
        String carInfo = model_name + "\n" + year + "\n" + color;

        txtInfo.setText(carInfo);
        Glide.with(this).load(img).into(imageView);

        btnRentDone = findViewById(R.id.btnRentDone);
        btnRentDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = editName.getEditText().getText().toString();
                String email = editEmail.getEditText().getText().toString();
                String phone = editPhone.getEditText().getText().toString();
                String license = editLicense.getEditText().getText().toString();
                String startDate = editStartDate.getEditText().getText().toString();
                String endDate = editEndDate.getEditText().getText().toString();

                Toast.makeText(RentCar.this, userName + email + phone + license + startDate, Toast.LENGTH_SHORT).show();
                updateAvailability(model_name, "no");
                addRentalData(userName, email, phone, license, startDate, endDate);
            }
        });
    }

    private void updateAvailability(String modelName, String availability) {
        String url = "http://192.168.1.117:80/api/cars.php?model_name=" + modelName + "&availability=" + availability;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(RentCar.this, "Availability updated successfully", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RentCar.this, "Error updating availability: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(RentCar.this).add(stringRequest);
    }

    private void addRentalData(String user_name, String email, String phone, String license_no, String start_date, String end_date) {
        String url = "http://192.168.1.117:80/api/cars.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(RentCar.this, "Rental data added successfully", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RentCar.this, "Error adding rental data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user_name", user_name);
                params.put("email", email);
                params.put("phone", phone);
                params.put("license_no", license_no);
                params.put("start_date", start_date);
                params.put("end_date", end_date);
                return params;
            }
        };

        Volley.newRequestQueue(RentCar.this).add(stringRequest);
    }
}
