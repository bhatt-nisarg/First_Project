package com.example.first_project.Activity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.first_project.Adapter.RecAdapterSingle;
import com.example.first_project.Fragment.SearchFragment;
import com.example.first_project.R;
import com.example.first_project.model.Restaurant;
import com.example.first_project.utils.AppConstants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
public class restaurent_clicked extends AppCompatActivity {
    TextView back_btn,clicked_name,clicked_region,clicked_countrestaurent,Load_more;
    RecyclerView click_resRecycle;
    RecAdapterSingle recAdapterSingle;
    Fragment fragment;
    ArrayList<Restaurant> clicked_category_list = new ArrayList<>();

    String id;
    Boolean loading = true;
    private LinearLayoutManager layoutManager;
    int page = 1;
    int pastVisibleItems, visibleItemCount, totalItemCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurent_clicked);
        back_btn = findViewById(R.id.clicked_back);
        clicked_name = findViewById(R.id.name);
        clicked_region = findViewById(R.id.region);
        clicked_countrestaurent = findViewById(R.id.item_count);
        //recycleview
        click_resRecycle = findViewById(R.id.clickres_rec);
        Load_more = findViewById(R.id.load_more);

        Intent i = getIntent();
        String title = i.getStringExtra("title");
        String count = i.getStringExtra("count");
         id = i.getStringExtra("id");
        Log.d("dsfe",id + "");
        Log.d("abcd",title +" == "+ count);

        clicked_name.setText(title);
        clicked_countrestaurent.setText(count + " " + "Ergebnisse");
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new SearchFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container,fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        new RestaurentClicked().execute(AppConstants.CLICKED_CATEGORY_DATA,AppConstants.REGION_CLICK_DATA);
//        load more functionality in recyclerview
        loadmoreData();

    }

    private void loadmoreData() {
        click_resRecycle.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0){
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition();
                if (!loading){
                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount){
                        loading = true;
                        page = page+1;
//                        new RestaurentClicked().execute();
                        new RestaurentClicked().execute(AppConstants.CLICKED_CATEGORY_DATA,AppConstants.REGION_CLICK_DATA);
                    }
                }
                }

            }
        });
    }
    private class RestaurentClicked extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... url) {
            OkHttpClient  okHttpClient = new OkHttpClient();
            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10,TimeUnit.SECONDS)
                    .readTimeout(10,TimeUnit.SECONDS)
                    .build();
            Request req_cat,region_req;
            req_cat = new Request.Builder()
                    .url(url[0]+id)
                    .build();
            region_req = new Request.Builder()
                    .url(url[1]+id +"&page="+page)
                    .build();
            Response response = null,region_res;
            try{
            //recycle data
                response =  okHttpClient.newCall(req_cat).execute();
                String jsonData = response.body().string();
                JSONObject jsonObject = new JSONObject(jsonData);
                int success = jsonObject.getInt("success");
                String message = jsonObject.getString("message");
                if (success == 1) {
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    JSONArray jsonArray = jsonObject1.getJSONArray("resto_info");
                    Log.d("amcer", String.valueOf(jsonArray.length()));
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    ArrayList<String> clicked_categoryList = new ArrayList<>();
//                    JSONObject cat_click = jsonArray.getJSONObject(i);
//                    Restaurant restaurant = new Restaurant();
//                    restaurant.setType_id(cat_click.getInt("id"));
//                    restaurant.setRestaurant_name(cat_click.getString("restaurant_name"));
//                    restaurant.setRating(cat_click.getDouble("rating"));
//                    restaurant.setRestaurant_address(cat_click.getString("address"));
//                    restaurant.setImageUrl(cat_click.getString("restaurant_image"));
//                    restaurant.setHighilighted_code(cat_click.getInt("highlighted_tag"));
//                    JSONArray smallimg = cat_click.getJSONArray("small_icon_categories");
//                    for (int j = 0; j < smallimg.length(); j++) {
//                        JSONObject jsonObject2 = smallimg.getJSONObject(i);
//                        clicked_categoryList.add(jsonObject2.getString("small_image"));
//                    }
                    for (int i = 0; i < jsonArray.length(); i++) {
                        ArrayList<String> smallimage_clickcat = new ArrayList<>();
                        JSONObject cone = jsonArray.getJSONObject(i);
                        Restaurant restaurant = new Restaurant();
                        restaurant.setType_id(cone.getInt("id"));
                        restaurant.setRestaurant_name(cone.getString("restaurant_name"));
                        restaurant.setRestaurant_address(cone.getString("address"));
                        restaurant.setImageUrl(cone.getString("restaurant_image"));
                        restaurant.setRating(cone.getDouble("rating"));

                        JSONArray jsonsmall_img = cone.getJSONArray("small_icon_categories");

                        for (int j = 0; j < jsonsmall_img.length(); j++) {
                            JSONObject jsonObject2 = jsonsmall_img.getJSONObject(j);
                            smallimage_clickcat.add(jsonObject2.getString("small_image"));
                            Log.d("bgrtid", jsonObject2.getString("small_image"));
                        }
                        restaurant.setStringArrayList(smallimage_clickcat);
                        clicked_category_list.add(restaurant);
                    }
                }
//            }else {
////                    runOnUiThread(new Runnable() {
////                        @Override
////                        public void run() {
////                            final Toast toast = Toast.makeText(restaurent_clicked.this, "" + message, Toast.LENGTH_SHORT);
////                            toast.show();
////                        }
////                    });
//
//            }
                loading = false;
        } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
////            //for region clicked
            try{
                //recycle data
                region_res =  okHttpClient.newCall(region_req).execute();
                String jsonData = region_res.body().string();
                JSONObject jsonObject = new JSONObject(jsonData);
                int success = jsonObject.getInt("success");
                String message = jsonObject.getString("message");
                if (success == 1) {
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    JSONArray jsonArray = jsonObject1.getJSONArray("search_info");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        ArrayList<String> smallimage_clickcat= new ArrayList<>();
                        JSONObject cone = jsonArray.getJSONObject(i);
                        Restaurant restaurant= new Restaurant();
                        restaurant.setType_id(cone.getInt("id"));
                        restaurant.setRestaurant_name(cone.getString("restaurant_name"));
                        restaurant.setRestaurant_address(cone.getString("address"));
                        restaurant.setImageUrl(cone.getString("restaurant_image"));
                        restaurant.setRating(cone.getDouble("rating"));
                        Log.d("fdg", String.valueOf(cone.getDouble("rating")));
                        Log.d("ccccc", cone.toString());
                        JSONArray jsonsmall_img = cone.getJSONArray("small_icon_categories");

                        for (int j = 0; j < jsonsmall_img.length(); j++) {
                            JSONObject jsonObject2 = jsonsmall_img.getJSONObject(j);
                            smallimage_clickcat.add(jsonObject2.getString("small_image"));
                        }
                        restaurant.setStringArrayList(smallimage_clickcat);
                        clicked_category_list.add(restaurant);
                    }

                }else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final Toast toast = Toast.makeText(restaurent_clicked.this, "" + message, Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });

                }
                loading = false;

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            layoutManager = new LinearLayoutManager(restaurent_clicked.this);
            click_resRecycle.setLayoutManager(layoutManager);
            recAdapterSingle = new RecAdapterSingle(restaurent_clicked.this,clicked_category_list);
            click_resRecycle.setAdapter(recAdapterSingle);
            recAdapterSingle.notifyDataSetChanged();
            click_resRecycle.setNestedScrollingEnabled(false);



        }
    }
}