package com.example.carrenals;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.carrenals.Model.VendorCar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class ManageCar extends AppCompatActivity {
    private ImageView carImage;
    private TextView carModelText;
    private TextView carColorText;
    private TextView numOfSeatsText;
    private TextView transmissionText;
    private TextView carPriceText;
    private EditText carPriceField;
    private Button saveChangesButton;
    private Button deleteCarButton;

    private int vendorId;
    private int carId;

    private String dailyCost;

    private static String URL = "http://192.168.1.3";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_car);
        setupViews();
        getCarDetails();

        saveChangesButton.setOnClickListener(v -> {
            try {
                updateCarPrice();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });

        deleteCarButton.setOnClickListener(v -> {
            try {
                removeCar();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void setupViews() {
        carModelText = findViewById(R.id.carModelText);
        carPriceText = findViewById(R.id.carPriceText);
        carPriceField = findViewById(R.id.priceField);
        saveChangesButton = findViewById(R.id.saveChangesButton);
        deleteCarButton = findViewById(R.id.deleteCarButton);
        Intent intent = getIntent();
        vendorId = intent.getIntExtra("vendor_id", 0);
        carId = intent.getIntExtra("car_id", 0 );
        carModelText.setText(intent.getStringExtra("car_brand"));

    }

    private void getCarDetails() {

        StringRequest request = new StringRequest(Request.Method.GET, URL +"/api/khalil/vendor-cars.php/?vendor_id="
        + vendorId + "&model_id=" + carId, res -> {
            try {
                JSONObject obj = new JSONObject(res);
                JSONObject vendorCar = obj.getJSONObject("vendor_car");
                dailyCost = vendorCar.getString("daily_cost");
                carPriceText.setText("Daily rent cost: $" +dailyCost);
            } catch (Exception ex) {
                Toast.makeText(this, "FAIL", Toast.LENGTH_LONG).show();
            }
        }, err -> Toast.makeText(this, err.toString(), Toast.LENGTH_LONG).show());

        Volley.newRequestQueue(this).add(request);
    }

    private void updateCarPrice() throws JSONException {
        JSONObject updatedCarPrice = new JSONObject();
        if (!carPriceField.getText().equals(""))
            updatedCarPrice.put("daily_cost", carPriceField.getText());
        else
            updatedCarPrice.put("", carPriceField.getText());



        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, URL+"/api/khalil/vendor-cars.php/?vendor_id="
                + vendorId + "&model_id=" + carId, updatedCarPrice,
                res -> {
                    try {
                        Toast.makeText(ManageCar.this, res.toString(), Toast.LENGTH_LONG).show();
                        carPriceText.setText("Daily rent cost: $" +carPriceField.getText());
                    } catch (Exception ex) {
                        Toast.makeText(ManageCar.this, "Fail", Toast.LENGTH_LONG).show();
                    }
                }, err -> Toast.makeText(ManageCar.this, err.toString(), Toast.LENGTH_LONG).show());

        Volley.newRequestQueue(this).add(request);
        Volley.newRequestQueue(this).add(request);
        getCarDetails();
    }

    private void removeCar() throws JSONException {


        StringRequest request = new StringRequest(Request.Method.DELETE, URL+"/api/khalil/vendor-cars.php/?vendor_id="
                + vendorId + "&model_id=" + carId ,
                res -> {
                    try {
                        Toast.makeText(ManageCar.this, res.toString(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ManageCar.this, VendorCars.class);
                        intent.putExtra("vendor_id", vendorId);
                        startActivity(intent);
                    } catch (Exception ex) {
                        Toast.makeText(ManageCar.this, "Fail", Toast.LENGTH_LONG).show();
                    }
                }, err -> Toast.makeText(ManageCar.this, err.toString(), Toast.LENGTH_LONG).show());

        Volley.newRequestQueue(this).add(request);
        startActivity(new Intent(this, VendorCars.class));
    }
}