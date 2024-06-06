package com.example.carrentalsystem;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CreateCar extends AppCompatActivity {
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
    private Button submitButton;
    private String encodedImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_car);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        setupViews();
        submitButton.setOnClickListener(v -> {
            try {
                uploadCarDetails();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });
        imageButton.setOnClickListener(v -> {
            openImageSelector();
        });
        }
    private void setupViews() {
        brandField = findViewById(R.id.brandField);
        modelField = findViewById(R.id.modelField);
        colorField = findViewById(R.id.colorField);
        numOfSeatsField = findViewById(R.id.numOfSeatsField);
        plateNumberField = findViewById(R.id.plateNumberField);
        modelYearField = findViewById(R.id.modelYearField);
        imageButton = findViewById(R.id.imageButton);
        submitButton = findViewById(R.id.submitButton);
    }

    private void openImageSelector() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                carBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                carView.setImageBitmap(carBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadCarDetails() throws JSONException {
        String carURL = "http://192.168.1.2/api/cars.php";
        JSONObject carObj = new JSONObject();
        carObj.put("brand", brandField.getText());
        carObj.put("model", modelField.getText());
        carObj.put("color", colorField.getText());
        carObj.put("num_of_seats", numOfSeatsField.getText());
        carObj.put("plate_number", plateNumberField.getText());
        carObj.put("model_year", modelYearField.getText());
        carObj.put("image", "");
//        if (carBitmap != null) {
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            carBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
//            encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
//            carObj.put("image", encodedImage);
//        }

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