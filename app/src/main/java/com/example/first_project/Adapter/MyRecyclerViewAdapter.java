package com.example.first_project.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.first_project.R;
import com.example.first_project.Activity.restaurent_clicked;
import com.example.first_project.model.Restaurant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    private ArrayList<HashMap<String, String>> mInfoList;
    private Context context;
    public MyRecyclerViewAdapter(Context context, ArrayList<HashMap<String,String>> Infolist){
        this.context = context;
        this.mInfoList = Infolist;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_7reg,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.d("oiup",mInfoList.get(position).get("count"));
               holder.count_item.setText(mInfoList.get(position).get("count"));
               holder.title.setText(mInfoList.get(position).get("name"));
        Picasso.with(context)
                .load(mInfoList.get(position).get("img_url"))
                .into(holder.backgroungImg);
        Picasso.with(context)
                .load(mInfoList.get(position).get("icon_url"))
                .into(holder.iconImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Restaurant res = new Restaurant();
                Intent i = new Intent(context, restaurent_clicked.class);
                i.putExtra("title",mInfoList.get(position).get("title"));
                i.putExtra("count", mInfoList.get(position).get("count"));
                i.putExtra("id",mInfoList.get(position).get("id"));
                Log.d("nnnnn",mInfoList.get(position).get("id")+"");
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView backgroungImg,iconImage,single_imagelist;
        TextView count_item,title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            backgroungImg = (ImageView) itemView.findViewById(R.id.item_7backimg);
            iconImage = (ImageView) itemView.findViewById(R.id.icon_image);
            count_item = (TextView) itemView.findViewById(R.id.numofItem);
            title = (TextView) itemView.findViewById(R.id.text_7reg);


        }
    }
}
