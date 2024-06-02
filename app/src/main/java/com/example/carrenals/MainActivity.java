package com.example.carrenals;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.carrenals.Adapter.BrandsAdapter;
import com.example.carrenals.Model.BrandsModel;
import com.example.carrenals.Model.CarModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private List<CarModel> cars = new ArrayList<>();
    private static final String URL = "http://192.168.1.105:80/api/cars.php";
    private static final String URL_Brands = "http://192.168.1.105:80/api/get_brands.php";

    private RecyclerView brandRecyclerView;
    private BrandsAdapter brandsAdapter;
    private ArrayList<BrandsModel> modelsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listView = findViewById(R.id.cars);
        loadCars();
//        brandRecyclerView = findViewById(R.id.rec);
//        brandRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
//
//        brandsAdapter = new BrandsAdapter(this,modelsList);
//        brandRecyclerView.setAdapter(brandsAdapter);
        modelsList = new ArrayList<>();
        loadBrands();
    }

    private void loadCars() {
        StringRequest request = new StringRequest(Request.Method.GET, URL, res -> {
            try {
                JSONObject responseObj = new JSONObject(res);

                if (responseObj.has("cars")) {
                    JSONArray carsArray = responseObj.getJSONArray("cars");

                    for (int i = 0; i < carsArray.length(); i++) {
                        JSONObject carObj = carsArray.getJSONObject(i);

                        String modelName = carObj.getString("model_name");
                        String brandName = carObj.getString("brand_name");
                        String carName = brandName + modelName;
                      //  String color = carObj.getString("color");
                        int num_of_seats = carObj.getInt("num_of_seats");
                        String year = carObj.getString("model_year");
                        String modelImage  = carObj.getString("model_image");
                        String brandImage = carObj.getString("brand_image");

                        //String name, String color, int seatingCapacity, String year
                      //  CarModel car = new CarModel(modelName, "blue", num_of_seats, year);

                        //String name,  String price, String year, String fuelType, int seatingCapacity, String color, String carImage, String brandImage
                        CarModel car = new CarModel(carName,"20$", year,num_of_seats, "blue", modelImage,brandImage);

                        cars.add(car);
                    }

                    ArrayAdapter<CarModel> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, cars);
                    listView.setAdapter(adapter);
                } else {
                    Toast.makeText(MainActivity.this, "No cars found", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("MainActivity", "Error parsing JSON response", e);
                Toast.makeText(MainActivity.this, "Error parsing data", Toast.LENGTH_LONG).show();
            }
        }, err -> {
            Log.e("MainActivity", "Error fetching data", err);
            Toast.makeText(MainActivity.this, "Error fetching data: " + err.toString(), Toast.LENGTH_LONG).show();
        });

        Volley.newRequestQueue(MainActivity.this).add(request);
    }

    private void loadBrands(){
        brandRecyclerView = findViewById(R.id.rec);
        brandRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_Brands,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i <array.length(); i++){
                                JSONObject brandObj = array.getJSONObject(i);
                                String name = brandObj.getString("brand_name");
                                String image = brandObj.getString("image");
                                Log.d("MainActivity", "Brand: " + name + ", Image: " + image); // Log each brand's data
                                modelsList.add(new BrandsModel(name,image));
                            }
                            brandsAdapter = new BrandsAdapter(MainActivity.this,modelsList);
                            brandRecyclerView.setAdapter(brandsAdapter);
                        }catch (Exception e){
                            e.printStackTrace();
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

    private void horData(){
        modelsList = new ArrayList<>();
        modelsList.add(new BrandsModel("BMW", R.drawable.bmw_icon));
        modelsList.add(new BrandsModel("Audi", R.drawable.audi_iocn));
        modelsList.add(new BrandsModel("Mercedes", R.drawable.mercedes_icon));
        modelsList.add(new BrandsModel("Chevrolet", R.drawable.chevrolet_logo));
        modelsList.add(new BrandsModel("BMW", R.drawable.bmw_icon));
        modelsList.add(new BrandsModel("BMW", R.drawable.bmw_icon));
    }

}
