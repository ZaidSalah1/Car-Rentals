package com.example.carrenals.Adapter;

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

import com.bumptech.glide.Glide;
import com.example.carrenals.Brands.CarsShow;
import com.example.carrenals.Model.BrandsModel;
import com.example.carrenals.R;

import java.util.ArrayList;

public class BrandsAdapter extends RecyclerView.Adapter<BrandsAdapter.MyViewHolder> {
    Context context;
    ArrayList<BrandsModel> homeHorModelList;
    int row_index = -1;

    public BrandsAdapter(Context context, ArrayList<BrandsModel> homeHorModelList){
        this.context = context;
        this.homeHorModelList = homeHorModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.hor_models_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textName.setText(homeHorModelList.get(position).getName());
        // Load image using Glide (if URL)
        Glide.with(context).load(homeHorModelList.get(position).getImage2()).into(holder.imageView);

        holder.cardView.setOnClickListener(view -> {
            row_index = position;
            Intent intent;
            if (row_index == 0) {
                intent = new Intent(context, CarsShow.class);
                intent.putExtra("brand",homeHorModelList.get(position).getName());
            } else if (row_index == 1) {
                intent = new Intent(context, CarsShow.class);
                intent.putExtra("brand",homeHorModelList.get(position).getName());
            }else if (row_index == 2) {
                intent = new Intent(context, CarsShow.class);
                intent.putExtra("brand",homeHorModelList.get(position).getName());
            }else if (row_index == 3) {
                intent = new Intent(context, CarsShow.class);
                intent.putExtra("brand",homeHorModelList.get(position).getName());
            }
            else if (row_index == 4) {
                intent = new Intent(context, CarsShow.class);
                intent.putExtra("brand",homeHorModelList.get(position).getName());
            }
            else {
                return;
            }
            context.startActivity(intent);
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
