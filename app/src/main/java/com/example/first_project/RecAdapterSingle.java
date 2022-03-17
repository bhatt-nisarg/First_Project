package com.example.first_project;

import android.content.Context;
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
import java.util.zip.Inflater;

public class RecAdapterSingle extends RecyclerView.Adapter<RecAdapterSingle.ViewHolder> {
    private ArrayList<HashMap<String, String>> single_catList;
    private Context context;
    public RecAdapterSingle(Context context, ArrayList<HashMap<String, String>> single_listrest) {
        this.single_catList = single_listrest;
        this.context = context;
    }

    @NonNull
    @Override
    public RecAdapterSingle.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_singlerec,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecAdapterSingle.ViewHolder holder, int position) {
        Picasso.with(context)
                .load(single_catList.get(position).get("res_img"))
                .into(holder.single_image);
        holder.single_restname.setText(single_catList.get(position).get("res_name"));
        holder.single_address.setText(single_catList.get(position).get("res_address"));

    }

    @Override
    public int getItemCount() {
        return single_catList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView single_image;
        TextView single_restname,single_address;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            single_image = itemView.findViewById(R.id.single_listimg);
            single_restname = itemView.findViewById(R.id.rest_singlename);
            single_address = itemView.findViewById(R.id.rest_singaddress);
        }
    }
}
