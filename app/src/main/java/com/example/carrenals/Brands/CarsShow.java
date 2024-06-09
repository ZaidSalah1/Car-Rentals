package com.example.carrenals.Brands;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.carrenals.Adapter.CarsAdapter;
import com.example.carrenals.Adapter.RecyclerViewInterface;
import com.example.carrenals.DetailsCarActivity;
import com.example.carrenals.Model.CarModel;
import com.example.carrenals.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class CarsShow extends AppCompatActivity implements RecyclerViewInterface {

    private ArrayList<CarModel> carModels = new ArrayList<>();
    private static final String BASE_URL = "http://192.168.1.117/api/cars.php";
    private RecyclerView recyclerView;
    private String brand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_show_activity);
        View rootView = findViewById(android.R.id.content);
        rootView.setBackgroundColor(Color.parseColor("#FFFFFF"));

        recyclerView = findViewById(R.id.bmw_list);
        Intent intent = getIntent();
        brand = intent.getStringExtra("brand");

        loadCars();
    }

    private void loadCars() {
        String url = BASE_URL + "?brand=" + brand;
        StringRequest request = new StringRequest(Request.Method.GET, url, res -> {
            try {
                JSONObject responseObj = new JSONObject(res);
                if (responseObj.has("cars")) {
                    JSONArray carsArray = responseObj.getJSONArray("cars");
                    for (int i = 0; i < carsArray.length(); i++) {
                        JSONObject carObj = carsArray.getJSONObject(i);
                        String modelName = carObj.getString("model_name");
                        String brandName = carObj.getString("brand_name");
                        String carName = brandName + " " + modelName;
                        int num_of_seats = carObj.getInt("num_of_seats");
                        String year = carObj.getString("model_year");
                        String modelImage = carObj.getString("model_image");
                        String brandImage = carObj.getString("brand_image");
                        String color = carObj.getString("color");
                        String price = carObj.getString("price");
                        String available = carObj.getString("availability");
                        CarModel car = new CarModel(carName, price + "$", year, num_of_seats, color, modelImage, brandImage, available);
                        carModels.add(car);
                    }
                    CarsAdapter adapter = new CarsAdapter(CarsShow.this, carModels, CarsShow.this);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                }
            } catch (Exception ex) {
                Log.e("CarsShow", "Error parsing JSON response", ex);
                Toast.makeText(CarsShow.this, "Error parsing data", Toast.LENGTH_LONG).show();
            }
        }, error -> {
            Log.e("CarsShow", "Error fetching data", error);
        });
        Volley.newRequestQueue(CarsShow.this).add(request);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(CarsShow.this, DetailsCarActivity.class);
        CarModel selectedCar = carModels.get(position);
        intent.putExtra("name", selectedCar.getName());
        intent.putExtra("price", selectedCar.getPrice());
        intent.putExtra("color", selectedCar.getColor());
        intent.putExtra("year", selectedCar.getYear());
        intent.putExtra("transmission", selectedCar.getTransmission());
        intent.putExtra("carsSeatingCapacity", selectedCar.getSeatingCapacity());
        intent.putExtra("carImage", selectedCar.getCarImage());
        intent.putExtra("available", selectedCar.getAvailability());
        startActivity(intent);
    }
}