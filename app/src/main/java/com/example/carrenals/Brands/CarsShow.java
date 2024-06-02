package com.example.carrenals.Brands;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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


    ArrayList<CarModel> carModels = new ArrayList<>();
    int [] carsImages = {R.drawable.bmw_car, R.drawable.bmw_m3,R.drawable.bmw_x6m};
    private static final String BASE_URL = "http://192.168.1.105:80/api/cars.php";
    RecyclerView recyclerView;

    String brand;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmw);

        recyclerView  = findViewById(R.id.bmw_list);
        intent = getIntent();
        brand = intent.getStringExtra("brand");
        
       // setCarModels();
        loadCars();
      //  CarsAdapter adapter = new CarsAdapter(this,carModels,this);
      //  recyclerView.setAdapter(adapter);
      //  recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void loadCars(){
        String url = BASE_URL +"?brand=" + brand;
        StringRequest request = new StringRequest(Request.Method.GET,url, res->{
            String brandImage = "";
            try{
                JSONObject responseObj = new JSONObject(res);
                if(responseObj.has("cars")){
                    JSONArray carsArray = responseObj.getJSONArray("cars");
                    for (int i=0; i< carsArray.length(); i++){
                        JSONObject carObj = carsArray.getJSONObject(i);
                        String modelName = carObj.getString("model_name");
                //        Toast.makeText(CarsShow.this, modelName, Toast.LENGTH_LONG).show();
                        String brandName = carObj.getString("brand_name");
                        String carName = brandName + modelName;
//                        //  String color = carObj.getString("color");
                        int num_of_seats = carObj.getInt("num_of_seats");
                        String year = carObj.getString("model_year");
                        String modelImage  = carObj.getString("model_image");
                        brandImage = carObj.getString("brand_image");
                        CarModel car = new CarModel(carName,"20$", year,num_of_seats, "blue", modelImage,brandImage);
//                        Toast.makeText(CarsShow.this, modelName, Toast.LENGTH_LONG).show();
                        carModels.add(car);
                    }
                   intent.putExtra("brandTopImage",brandImage);
                    CarsAdapter adapter = new CarsAdapter(CarsShow.this,carModels);
                   recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                }
            }catch(Exception ex){
                Log.e("MainActivity", "Error parsing JSON response", ex);
                Toast.makeText(CarsShow.this, "Error parsing data", Toast.LENGTH_LONG).show();
            }
        },error ->{
           // Toast.makeText(CarsShow.this, "Error fetching data: " + error.toString(), Toast.LENGTH_LONG).show();

        });
        Volley.newRequestQueue(CarsShow.this).add(request);
    }



    private void setCarModels() {
        String[] carsName = getResources().getStringArray(R.array.bmw_names);
        String[] carsPrice = getResources().getStringArray(R.array.bmw_prices);
        String[] carsYear = getResources().getStringArray(R.array.bmw_year);
        String[] carsFuelType = getResources().getStringArray(R.array.bmw_fuel_type);//??????
        String[] carsTransmission = getResources().getStringArray(R.array.bmw_transmission);
        String[] carsSeatingCapacity = getResources().getStringArray(R.array.bmw_seating_capacity);
        String[] carsColor = getResources().getStringArray(R.array.bmw_color);

        for (int i = 0; i < carsName.length; i++) {
            carModels.add(new CarModel(carsName[i], carsImages[i], carsPrice[i], Integer.parseInt(carsYear[i]),
                    carsFuelType[i], carsTransmission[i], Integer.parseInt(carsSeatingCapacity[i]), carsColor[i]));
        }
    }


    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(CarsShow.this, DetailsCarActivity.class);
        intent.putExtra("name",carModels.get(position).getName());
        intent.putExtra("price",carModels.get(position).getPrice());
        intent.putExtra("color",carModels.get(position).getColor());
        intent.putExtra("year",carModels.get(position).getYear());
        intent.putExtra("transmission",carModels.get(position).getTransmission());
        intent.putExtra("carsSeatingCapacity",carModels.get(position).getSeatingCapacity());
        intent.putExtra("carImage",carModels.get(position).getImg());
        startActivity(intent);
    }
}