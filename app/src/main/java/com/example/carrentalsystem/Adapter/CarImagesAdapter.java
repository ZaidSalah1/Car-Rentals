package com.example.carrentalsystem.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.carrentalsystem.Model.CarModel;
import com.example.carrentalsystem.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class CarImagesAdapter extends RecyclerView.Adapter<CarImagesAdapter.ViewHolder> {

    private List<CarModel> cars;
    private Context context;

    public CarImagesAdapter( Context context, List<CarModel> cars) {
        this.cars = cars;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.car_item, //huh
                parent,
                false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CarModel pizza = cars.get(position);
        CardView cardView = holder.cardView;
        ImageView imageView = cardView.findViewById(R.id.car_img);
        Glide.with(context).load(pizza.getImage()).into(imageView);
        TextView txt = cardView.findViewById(R.id.txtCarName);
        txt.setText(pizza.getName());
        cardView.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //
            }
        });
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        public ViewHolder(@NonNull View cardView) {
            super(cardView);
            this.cardView = (CardView) cardView;
        }
    }
}

