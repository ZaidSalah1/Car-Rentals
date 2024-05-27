package com.example.carrentalsystem.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrentalsystem.Brands.Audi;
import com.example.carrentalsystem.Brands.BMW;
import com.example.carrentalsystem.Model.HorModel;
import com.example.carrentalsystem.R;

import java.util.ArrayList;

public class HomeHorAdapter extends RecyclerView.Adapter<HomeHorAdapter.MyViewHolder> {
    Context context;
    ArrayList<HorModel> homeHorModelList;
    int row_index = -1;

    public HomeHorAdapter(Context context, ArrayList<HorModel> homeHorModelList){
        this.context = context;
        this.homeHorModelList = homeHorModelList;
    }

    @NonNull
    @Override
    public HomeHorAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.hor_models_list, parent, false);
        return new HomeHorAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeHorAdapter.MyViewHolder holder, int position) {
        holder.textName.setText(homeHorModelList.get(position).getName());
        holder.imageView.setImageResource(homeHorModelList.get(position).getImage());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index = position;
                if(row_index == 0){
                    Intent intent = new Intent(context, BMW.class);
                    context.startActivity(intent);
                }
                if(row_index == 1){
                    Intent intent = new Intent(context, Audi.class);
                    context.startActivity(intent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return homeHorModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textName;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageHor);
            textName = itemView.findViewById(R.id.textHor);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
