package com.example.carrenals.Brands;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrenals.Adapter.CarsAdapter;
import com.example.carrenals.Adapter.RecyclerViewInterface;
import com.example.carrenals.DetailsCarActivity;
import com.example.carrenals.Model.CarModel;
import com.example.carrenals.R;

import java.util.ArrayList;

public class BMW extends AppCompatActivity implements RecyclerViewInterface {


    ArrayList<CarModel> carModels = new ArrayList<>();
    int [] carsImages = {R.drawable.bmw_car, R.drawable.bmw_m3,R.drawable.bmw_x6m};

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmw);

        recyclerView  = findViewById(R.id.bmw_list);

        setCarModels();

        CarsAdapter adapter = new CarsAdapter(this,carModels,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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
        Intent intent = new Intent(BMW.this, DetailsCarActivity.class);
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