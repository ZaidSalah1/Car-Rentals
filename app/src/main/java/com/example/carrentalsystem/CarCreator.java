//package com.example.carrentalsystem;
//
//import android.os.Bundle;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.util.Base64;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//public class CarCreator extends AppCompatActivity {
//
//    private static final int PICK_IMAGE_REQUEST = 1;
//
//    private EditText brandField;
//    private EditText modelField;
//    private EditText colorField;
//    private EditText numOfSeatsField;
//    private EditText plateNumberField;
//    private EditText modelYearField;
//    private ImageView carView;
//    private Bitmap carBitmap;
//    private Button imageButton;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_car_creator);
//
////        setupViews();
//
//        imageButton.setOnClickListener(v -> {
//            submitCarDetails();
//        });
//    }
//
//    private void setupViews() {
//        brandField = findViewById(R.id.brandField);
//        modelField = findViewById(R.id.modelField);
//        colorField = findViewById(R.id.colorField);
//        numOfSeatsField = findViewById(R.id.numOfSeatsField);
//        plateNumberField = findViewById(R.id.plateNumberField);
//        modelYearField = findViewById(R.id.modelYearField);
//        carView = findViewById(R.id.carView);
//        imageButton = findViewById(R.id.imageButton);
//    }
//
//    private void openImageSelector() {
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(intent, PICK_IMAGE_REQUEST);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            Uri imageUri = data.getData();
//            try {
//                carBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
//                carView.setImageBitmap(carBitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//    private void submitCarDetails() {
//        String brand = brandField.getText().toString().trim();
//        String model = modelField.getText().toString().trim();
//        String color = colorField.getText().toString().trim();
//        String numOfSeats = numOfSeatsField.getText().toString().trim();
//        String plateNumber = plateNumberField.getText().toString().trim();
//        String modelYear = modelYearField.getText().toString().trim();
//
//        if (carBitmap != null) {
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            carBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
//            String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
//
//            uploadCarDetails(brand, model, color, numOfSeats, plateNumber, modelYear, encodedImage);
//        }
//    }
//
//    private void uploadCarDetails(String brand, String model, String color, String numOfSeats, String plateNumber, String modelYear, String encodedImage) {
//        String carURL = "http://192.168.1.2/api/cars.php";
//        String vendorCarURL = "http://192.168.1.2/api/vendor-cars.php";
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, carURL,
//                v -> {
//                        Toast.makeText(CarCreator.this, "Car details uploaded successfully", Toast.LENGTH_LONG).show();
//
//                }, err -> Toast.makeText(CarCreator.this, "Failed to upload car details", Toast.LENGTH_LONG).show());
//
//        };
//
//    }
package com.example.carrentalsystem;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CarCreator extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText brandField;
    private EditText modelField;
    private EditText colorField;
    private EditText numOfSeatsField;
    private EditText plateNumberField;
    private EditText modelYearField;
    private ImageView carView;
    private Bitmap carBitmap;
    private Button imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_creator);
        EdgeToEdge.enable(this);

        Intent intent = getIntent();


//        imageButton.setOnClickListener(v -> openImageSelector());
//        findViewById(R.id.submitButton).setOnClickListener(v -> submitCarDetails());
    }

    private void setupViews() {
        brandField = findViewById(R.id.brandField);
        modelField = findViewById(R.id.modelField);
        colorField = findViewById(R.id.colorField);
        numOfSeatsField = findViewById(R.id.numOfSeatsField);
        plateNumberField = findViewById(R.id.plateNumberField);
        modelYearField = findViewById(R.id.modelYearField);
//        carView = findViewById(R.id.carView);
        imageButton = findViewById(R.id.imageButton);
    }

//    private void openImageSelector() {
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(intent, PICK_IMAGE_REQUEST);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            Uri imageUri = data.getData();
//            try {
//                carBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
//                carView.setImageBitmap(carBitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private void submitCarDetails() {
//        String brand = brandField.getText().toString().trim();
//        String model = modelField.getText().toString().trim();
//        String color = colorField.getText().toString().trim();
//        String numOfSeats = numOfSeatsField.getText().toString().trim();
//        String plateNumber = plateNumberField.getText().toString().trim();
//        String modelYear = modelYearField.getText().toString().trim();
//
//        if (carBitmap != null) {
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            carBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
//            String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
//
//            uploadCarDetails(brand, model, color, numOfSeats, plateNumber, modelYear, encodedImage);
//        }
//    }
//
//    private void uploadCarDetails(String brand, String model, String color, String numOfSeats, String plateNumber, String modelYear, String encodedImage) {
//        String carURL = "http://192.168.1.2/api/cars.php";
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, carURL,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Toast.makeText(CarCreator.this, "Car details uploaded successfully", Toast.LENGTH_LONG).show();
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(CarCreator.this, "Failed to upload car details", Toast.LENGTH_LONG).show();
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<>();
//                params.put("brand", brand);
//                params.put("model", model);
//                params.put("color", color);
//                params.put("num_of_seats", numOfSeats);
//                params.put("plate_number", plateNumber);
//                params.put("model_year", modelYear);
//                params.put("image", encodedImage);
//                return params;
//            }
//        };
//
//        requestQueue.add(stringRequest);
//    }

}
