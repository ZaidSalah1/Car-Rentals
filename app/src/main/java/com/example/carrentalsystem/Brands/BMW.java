package com.example.carrentalsystem.Brands;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.carrentalsystem.Adapter.CarsAdapter;
import com.example.carrentalsystem.Model.CarModel;
import com.example.carrentalsystem.R;

import java.util.ArrayList;

public class BMW extends AppCompatActivity {


    ArrayList<CarModel> carModels = new ArrayList<>();
    int [] carsImages = {R.drawable.bmw_car, R.drawable.bmw_m3,R.drawable.bmw_x6m};

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmw);

        recyclerView  = findViewById(R.id.bmw_list);

        setCarModels();

        CarsAdapter adapter = new CarsAdapter(this,carModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void setCarModels(){
        String[] carsName = getResources().getStringArray(R.array.bmw_names);
        String[] carsPrice = getResources().getStringArray(R.array.bmw_prices);

        for (int i=0; i<carsName.length;i++){
            carModels.add(new CarModel(carsName[i],carsImages[i], carsPrice[i]));
        }
    }

}