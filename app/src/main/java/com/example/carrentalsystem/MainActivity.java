package com.example.carrentalsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.carrentalsystem.Adapter.HomeHorAdapter;
import com.example.carrentalsystem.Model.HorModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private RecyclerView horRecyclerView;
    private HomeHorAdapter homeHorAdapter;
    private ArrayList<HorModel> homeHorModelsList;

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
        homeHorModelsList.add(new HorModel("BMW", R.drawable.bmw_icon));
        homeHorModelsList.add(new HorModel("Audi", R.drawable.audi_iocn));
        homeHorModelsList.add(new HorModel("Mercedes", R.drawable.mercedes_icon));
        homeHorModelsList.add(new HorModel("Chevrolet", R.drawable.chevrolet_logo));
        homeHorModelsList.add(new HorModel("BMW", R.drawable.bmw_icon));
        homeHorModelsList.add(new HorModel("BMW", R.drawable.bmw_icon));
    }
}