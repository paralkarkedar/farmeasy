package com.example.farmeasyapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmeasyapp.R;
import com.example.farmeasyapp.models.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {

    Context context;
    List<MyCartModel> list;
    int totalAmount = 0;
    FirebaseFirestore firestore;
    FirebaseAuth auth;

    public MyCartAdapter(Context context, List<MyCartModel> list){
        this.context = context;
        this.list = list;
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType){
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_item,parent,false));

    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){

        holder.date.setText(list.get(position).getCurrentDate());
        holder.time.setText(list.get(position).getCurrentTime());
        holder.price.setText(list.get(position).getProductPrice()+"₹");
        holder.name.setText(list.get(position).getProductName());
        holder.totalPrice.setText(String.valueOf(list.get(position).getTotalPrice()));
        holder.totalQuantity.setText(list.get(position).getTotalQuantity());
        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                        .collection("User")
                        .document(list.get(position).getDocumentId())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()){
                                    list.remove(list .get(position));
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(context, "Error"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

            }
        });

        //Total amount pass to Cart Activity
        totalAmount = totalAmount + list.get(position).getTotalPrice();
        Intent intent = new Intent("MyTotalAmount");
        intent.putExtra("totalAmount",totalAmount);

        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);


    }

    @Override
    public int getItemCount(){  return  list.size();   }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,price,date,time,totalQuantity,totalPrice;
        ImageView deleteItem;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.product_price);
            date = itemView.findViewById(R.id.currant_date);
            time = itemView.findViewById(R.id.current_time);
            totalQuantity = itemView.findViewById(R.id.total_quantity);
            totalPrice = itemView.findViewById(R.id.total_price);
            deleteItem = itemView.findViewById(R.id.delete);




        }
    }
}