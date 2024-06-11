package com.example.carrenals;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.carrenals.Model.BrandsModel;
import com.example.carrenals.Model.CarModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateCar extends AppCompatActivity {
    private Spinner brandSpinner;
    private Spinner modelSpinner;
    private EditText plateNumberField;
    private EditText colorField;
    private CheckBox availability;
    private Button submitButton;
    private List<BrandsModel> brandList = new ArrayList<>();
    private List<CarModel> carList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_car);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        Intent intent = getIntent();
        setupViews();
        submitButton.setOnClickListener(v -> {
            try {
                uploadCarDetails();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });
    }
        private void setupViews() {
            brandSpinner = findViewById(R.id.brandSpinner);
            modelSpinner = findViewById(R.id.modelSpinner);
            colorField = findViewById(R.id.colorField);
            plateNumberField = findViewById(R.id.plateNumberField);
            submitButton = findViewById(R.id.submitButton);
        }

        private void populateSpinners() {
            ArrayAdapter<BrandsModel> brandAdapter = new ArrayAdapter<>(CreateCar.this, android.R.layout.simple_spinner_item, brandList);
            brandSpinner.setAdapter(brandAdapter);
            ArrayAdapter<CarModel> modelAdapter = new ArrayAdapter<>(CreateCar.this, android.R.layout.simple_spinner_item, carList);
            brandSpinner.setAdapter(modelAdapter);

        }


    private void loadBrands() {

            StringRequest request = new StringRequest(Request.Method.GET, "http://192.168.1.3" + "/api/khalil/brands.php/", res -> {
                try {
                    JSONArray brandArray = new JSONObject(res).getJSONArray("brands");
                    for (int i = 0; i < brandArray.length(); i++) {
                        JSONObject brandObj = (JSONObject) brandArray.get(i);
                        Integer brandId = brandObj.getInt("brand_id");
                        String brandName = brandObj.getString("brand_name");
                        String image = brandObj.getString("image");

                        BrandsModel brand = new BrandsModel(brandId, brandName, image);
                    }


                } catch (Exception e) {
                    Toast.makeText(CreateCar.this, "Error parsing brand details", Toast.LENGTH_LONG).show();
                }
            }, err -> Toast.makeText(CreateCar.this, "Error fetching brand details", Toast.LENGTH_LONG).show());

            Volley.newRequestQueue(this).add(request);
    }

    private void loadCars() {

    }


    private void uploadCarDetails() throws JSONException {
        String carURL = "http://192.168.1.2/api/khalil/cars.php";
        JSONObject carObj = new JSONObject();



        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, carURL, carObj,
                res -> {
            try {
                Toast.makeText(CreateCar.this, "Car details uploaded successfully", Toast.LENGTH_LONG).show();

            } catch (Exception er) {
                Toast.makeText(CreateCar.this, "Failed!", Toast.LENGTH_LONG).show();
            }
                }, err ->{
                        Toast.makeText(CreateCar.this, "Failed to upload car details", Toast.LENGTH_LONG).show();
                });
        Volley.newRequestQueue(this).add(request);
    }
}