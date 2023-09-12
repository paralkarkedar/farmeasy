package com.example.farmeasyapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.farmeasyapp.R;
import com.example.farmeasyapp.activites.DetailedActivity;
import com.example.farmeasyapp.models.NewProductsModel;

import java.util.List;

public class NewProductsAdapter extends RecyclerView.Adapter<NewProductsAdapter.ViewYHolder> {

    private Context context;
    private List<NewProductsModel> list;

    public NewProductsAdapter(Context context, List<NewProductsModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewYHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewYHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.new_products,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewYHolder holder, int position) {

        Glide.with(context).load(list.get(position).getImg_url()).into(holder.newImg);
        holder.newName.setText(list.get(position).getName());
        holder.newPrice.setText(String.valueOf(list.get(position).getPrice()));


      holder.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent = new Intent(context,DetailedActivity.class);
              intent.putExtra("detailed",list.get(position));
              context.startActivity(intent);
          }
      });




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewYHolder extends RecyclerView.ViewHolder {

        ImageView newImg;
        TextView newName,newPrice;

        public ViewYHolder(@NonNull View itemView) {
            super(itemView);

            newImg = itemView.findViewById(R.id.new_img);
            newName = itemView.findViewById(R.id.new_product_name);
            newPrice = itemView.findViewById(R.id.new_price);
        }
    }
}
