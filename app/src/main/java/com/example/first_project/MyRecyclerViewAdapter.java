package com.example.first_project;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_7reg, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("oiup",mInfoList.get(position).get("count"));
               holder.count_item.setText(mInfoList.get(position).get("count"));
               holder.title.setText(mInfoList.get(position).get("name"));
        Picasso.with(context)
                .load(mInfoList.get(position).get("img_url"))
                .into(holder.backgroungImg);
        Picasso.with(context)
                .load(mInfoList.get(position).get("icon_url"))
                .into(holder.iconImage);

    }

    @Override
    public int getItemCount() {
        return mInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView backgroungImg,iconImage;
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
