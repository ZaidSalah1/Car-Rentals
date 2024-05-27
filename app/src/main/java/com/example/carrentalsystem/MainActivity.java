package com.example.carrentalsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.carrentalsystem.Adapter.HomeHorAdapter;
import com.example.carrentalsystem.Model.BrandsModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private RecyclerView horRecyclerView;
    private HomeHorAdapter homeHorAdapter;
    private ArrayList<BrandsModel> homeHorModelsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        horData();
        horRecyclerView = findViewById(R.id.rec);
        horRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));

        homeHorAdapter = new HomeHorAdapter(this, homeHorModelsList);
        horRecyclerView.setAdapter(homeHorAdapter);



    }

    private void horData(){
        homeHorModelsList = new ArrayList<>();
        homeHorModelsList.add(new BrandsModel("BMW", R.drawable.bmw_icon));
        homeHorModelsList.add(new BrandsModel("Audi", R.drawable.audi_iocn));
        homeHorModelsList.add(new BrandsModel("Mercedes", R.drawable.mercedes_icon));
        homeHorModelsList.add(new BrandsModel("Chevrolet", R.drawable.chevrolet_logo));
        homeHorModelsList.add(new BrandsModel("BMW", R.drawable.bmw_icon));
        homeHorModelsList.add(new BrandsModel("BMW", R.drawable.bmw_icon));
    }
}