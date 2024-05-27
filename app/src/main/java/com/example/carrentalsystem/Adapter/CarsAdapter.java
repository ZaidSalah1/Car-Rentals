package com.example.carrentalsystem.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrentalsystem.Model.CarModel;
import com.example.carrentalsystem.R;

import java.util.ArrayList;

public class CarsAdapter extends RecyclerView.Adapter<CarsAdapter.MyViewHolder> {


    private  RecyclerViewInterface recyclerViewInterface;

    Context context;
    ArrayList<CarModel> carModelList;

    public CarsAdapter(Context context, ArrayList<CarModel> carModelList) {
        this.context = context;
        this.carModelList = carModelList;
    }

    public CarsAdapter(Context context, ArrayList<CarModel> carModelList, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.carModelList = carModelList;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public CarsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // This is where you inflate the layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.car_item,parent,false);
        return new CarsAdapter.MyViewHolder(view,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull CarsAdapter.MyViewHolder holder, int position) {
        // Assigning values to the views we created in the recycler_view layout file
        // based on the position of the recycler view

        holder.carName.setText(carModelList.get(position).getName());
        holder.price.setText(carModelList.get(position).getPrice());
        holder.image.setImageResource(carModelList.get(position).getImg());

    }

    @Override
    public int getItemCount() {
        return carModelList.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder {
        // Its like on create method

        TextView carName,price;
        ImageView image;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            carName = itemView.findViewById(R.id.txtCarName);
            price = itemView.findViewById(R.id.txtPrice);
            image = itemView.findViewById(R.id.car_img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });

        }
    }
}