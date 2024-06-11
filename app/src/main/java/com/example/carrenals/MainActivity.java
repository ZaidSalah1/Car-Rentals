package com.example.carrenals;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.carrenals.Adapter.BrandsAdapter;
import com.example.carrenals.Adapter.CarsAdapter;
import com.example.carrenals.Model.BrandsModel;
import com.example.carrenals.Model.CarModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<CarModel> cars = new ArrayList<>();
    private static final String URL = "http://192.168.1.117/api/top_cars.php";
    private static final String URL_Brands = "http://192.168.1.117/api/get_brands.php";
    private BottomNavigationView bottomNavigationView;
    private RecyclerView brandRecyclerView,topCarsRecyclerView;
    private BrandsAdapter brandsAdapter;
    private ArrayList<BrandsModel> modelsList;
    private EditText textInputEditText;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textInputEditText = findViewById(R.id.textInputEditText);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

       // logoutButton = findViewById(R.id.logoutButton);

        loadCars();
        modelsList = new ArrayList<>();
        loadBrands();



        textInputEditText = findViewById(R.id.textInputEditText);



        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.navigation_about) {
                    openAboutActivity();
                    return true;
                } else if (item.getItemId() == R.id.navigation_profile) {
                    // Handle profile navigation
                    openProfileActivity();
                    return true;
                }
                else if(item.getItemId() == R.id.navigation_Finnance){

                    openFinnanceActivity();
                    return true;
                }
                return false;
            }
        });



    }

    private void loadCars() {
        topCarsRecyclerView = findViewById(R.id.topCarsList);
        topCarsRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray array = new JSONArray(response);
                            for (int i =0 ; i<array.length(); i++){
                                JSONObject carObj = array.getJSONObject(i);
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
                                cars.add(car);

                            }
                            CarsAdapter adapter = new CarsAdapter(MainActivity.this, cars);
                            topCarsRecyclerView.setAdapter(adapter);
                            topCarsRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        }catch (Exception ex){
                            ex.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error parsing JSON", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error: " + error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(MainActivity.this).add(stringRequest);
    }

    private void loadBrands() {
        brandRecyclerView = findViewById(R.id.rec);
        brandRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_Brands,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject brandObj = array.getJSONObject(i);
                                String name = brandObj.getString("brand_name");
                                String image = brandObj.getString("image");
                                Log.d("MainActivity", "Brand: " + name + ", Image: " + image); // Log each brand's data
                                modelsList.add(new BrandsModel(name, image));
                            }
                            brandsAdapter = new BrandsAdapter(MainActivity.this, modelsList);
                            brandRecyclerView.setAdapter(brandsAdapter);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error: " + error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        Volley.newRequestQueue(MainActivity.this).add(stringRequest);
    }
    private void openProfileActivity() {
        Intent profileIntent = new Intent(MainActivity.this, ProfileActivity.class);
        startActivity(profileIntent);
    }
    public void openAboutActivity(){
        Intent AboutIntent = new Intent(MainActivity.this, AboutActivity.class);
        startActivity(AboutIntent);
    }
    public void openFinnanceActivity(){

        Intent intent = new Intent(MainActivity.this, FinanceActivity.class);
        startActivity(intent);

    }










}
