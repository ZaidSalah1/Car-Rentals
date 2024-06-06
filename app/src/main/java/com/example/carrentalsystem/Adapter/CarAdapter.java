//package com.example.carrentalsystem.Adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.example.carrentalsystem.Model.Car;
//import com.example.carrentalsystem.Model.VendorCar;
//import com.example.carrentalsystem.R;
//
//import java.util.List;
//
//public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder>{
//
//    private Context context;
//
//    private List<VendorCar> vendorCars;
//
//    private CarViewHolder.OnItemClickListener onItemClickListener;
//
//
//    public CarAdapter(Context context, List<VendorCar> vendorCars,  CarViewHolder.OnItemClickListener onItemClickListener ) {
//        this.context = context;
//        this.vendorCars = vendorCars;
//        this.onItemClickListener = onItemClickListener;
//    }
//
//    @NonNull
//    @Override
//    public CarAdapter.CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.car_item, parent, false);
//        return new CarViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull CarAdapter.CarViewHolder holder, int position) {
//        VendorCar vendorCar = vendorCars.get(position);
//        holder.brand.setText(vendorCar.getBrand());
//        holder.cost.setText(String.valueOf(vendorCar.getDailyCost()));
//
//        Glide.with(context).load(vendorCar.getImage()).placeholder(R.drawable.bmw_car).into(holder.image);
//        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(vendorCar));
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return vendorCars.size();
//    }
//
//
//    public static class CarViewHolder extends RecyclerView.ViewHolder {
//        ImageView image;
//        TextView brand;
//
//        TextView cost;
//        //ImageView brandImage;
//
//        public CarViewHolder(@NonNull View itemView) {
//            super(itemView);
//            image = itemView.findViewById(R.id.carImage);
//            brand =  itemView.findViewById(R.id.carModel);;
//            cost = itemView.findViewById(R.id.carPrice);
//            //brandImage = itemView.findViewById(R.id.brandImage);
//        }
//        public interface OnItemClickListener {
//            void onItemClick(VendorCar vendorCar);
//        }
//    }
//}
package com.example.carrentalsystem.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.carrentalsystem.Model.VendorCar;
import com.example.carrentalsystem.R;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder>{

    private Context context;
    private List<VendorCar> vendorCars;
    private OnItemClickListener onItemClickListener;

    // Constructor
    public CarAdapter(Context context, List<VendorCar> vendorCars, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.vendorCars = vendorCars;
        this.onItemClickListener = onItemClickListener;
    }

    // ViewHolder for each item
    public static class CarViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView brand;
        TextView cost;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.carImage);
            brand = itemView.findViewById(R.id.carModel);
            cost = itemView.findViewById(R.id.carPrice);
        }
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.car_item, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        VendorCar vendorCar = vendorCars.get(position);
        holder.brand.setText(vendorCar.getBrand());
        holder.cost.setText(String.valueOf(vendorCar.getDailyCost()));

        Glide.with(context).load(vendorCar.getImage()).placeholder(R.drawable.bmw_car).into(holder.image);

        // Set OnClickListener for item
        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(vendorCar));
    }

    @Override
    public int getItemCount() {
        return vendorCars.size();
    }

    // Interface for item click listener
    public interface OnItemClickListener {
        void onItemClick(VendorCar vendorCar);
    }
}

