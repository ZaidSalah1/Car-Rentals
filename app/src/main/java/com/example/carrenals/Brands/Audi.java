package com.example.carrenals.Brands;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrenals.Adapter.CarsAdapter;
import com.example.carrenals.Model.CarModel;
import com.example.carrenals.R;

import java.util.ArrayList;

public class Audi extends AppCompatActivity {


    ArrayList<CarModel> carModels = new ArrayList<>();
    int [] carsImages = {R.drawable.audi_r8, R.drawable.audi_rs7};

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audi);

        recyclerView  = findViewById(R.id.audi_list);

        setCarModels();

        CarsAdapter adapter = new CarsAdapter(this,carModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void setCarModels(){
        String[] carsName = getResources().getStringArray(R.array.audi_names);
        String[] carsPrice = getResources().getStringArray(R.array.audi_prices);

        for (int i=0; i<carsName.length;i++){
            carModels.add(new CarModel(carsName[i],carsImages[i], carsPrice[i]));
        }
    }
}