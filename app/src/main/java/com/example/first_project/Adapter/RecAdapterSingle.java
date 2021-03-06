package com.example.first_project.Adapter;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.first_project.R;
import com.example.first_project.model.Restaurant;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class RecAdapterSingle extends RecyclerView.Adapter<RecAdapterSingle.ViewHolder> {
    private ArrayList<Restaurant> single_catList;
    private Context context;

    public RecAdapterSingle(Context context, ArrayList<Restaurant> single_listrest) {
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
                .load(single_catList.get(position).getImageUrl())
                .into(holder.single_image);
        holder.single_restname.setText(single_catList.get(position).getRestaurant_name());
        holder.single_address.setText(single_catList.get(position).getRestaurant_address());
        holder.small_image_recyclerview.setLayoutManager(new GridLayoutManager(context, 6));
        if (single_catList.get(position).getRating() != 0.0) {
            holder.rating.setText(single_catList.get(position).getRating() + "");
        }else{
            holder.rating.setVisibility(View.INVISIBLE);
        }
        ArrayList<String> list = new ArrayList<>();
        list = single_catList.get(position).getStringArrayList();
        for (int j = 0; j < list.size(); j++) {
            Restaurant restaurant_image=new Restaurant();
            restaurant_image.setImageurl_small(list.get(j));
            holder.restaurant_img_list.add(restaurant_image);
            Log.d("vfg",position+ " ==" + list.get(j));
            Log.d("qwer", String.valueOf(list.size()));
        }
        SmallImage_Adapter smallImage_adapter= new SmallImage_Adapter(context,holder.restaurant_img_list);
        holder.small_image_recyclerview.setAdapter(smallImage_adapter);
        Log.d("zxc", String.valueOf(single_catList.get(position).getStringArrayList().size()));
        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return (null != single_catList ? single_catList.size():0);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView single_image;
        TextView single_restname,single_address,rating;
        RecyclerView small_image_recyclerview;
        private ArrayList<Restaurant> restaurant_img_list= new ArrayList<Restaurant>();
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            single_image = itemView.findViewById(R.id.single_listimg);
            single_restname = itemView.findViewById(R.id.rest_singlename);
            single_address = itemView.findViewById(R.id.rest_singaddress);
            small_image_recyclerview = itemView.findViewById(R.id.small_iconrec);
            rating = itemView.findViewById(R.id.reting);
        }
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
