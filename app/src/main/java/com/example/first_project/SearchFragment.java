package com.example.first_project;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.first_project.Adapter.MyRecyclerViewAdapter;
import com.example.first_project.Adapter.RecAdapterSingle;
import com.example.first_project.model.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class SearchFragment extends Fragment {
    MyRecyclerViewAdapter myRecyclerViewAdapter;
    //it is for first linear layout spinner and its search functionality
    EditText search;
    Spinner region,category;
    List<String> category_list = new ArrayList<String>();

    //here 7 region initialization
    ArrayList<HashMap<String,String>> reg_7List= new ArrayList<>();
    RecyclerView rec7reg;
    List<String> regSpinner = new ArrayList<>();

    //here Top restaurants initialization
    ArrayList<HashMap<String,String>> top_restaurant = new ArrayList<>();
    RecyclerView rectop_restaurant;

//    single-list restaurents
    ArrayList<Restaurant> single_listrest = new ArrayList<>();
    RecyclerView single_reclist;
    RecAdapterSingle recAdapterSingle;


    public SearchFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_search, container, false);

        //it is spinner view id
        search = view.findViewById(R.id.search_edit);
        region = view.findViewById(R.id.spinner_region);
        category = view.findViewById(R.id.spinner_category);


        //it is for category Spinner
        new GetContacts().execute();

        //it is for 7 region recyclerview
        rec7reg = view.findViewById(R.id.rec_7region);
        new GetRegion7().execute();

        //it is for top restaurants
        rectop_restaurant = view.findViewById(R.id.rec_TopRestaurant);
        new TopRestaurant().execute();

        //it is for single list restaurent
        single_reclist = view.findViewById(R.id.single_reclist);
        new SingleRestaurent().execute();
        return view;
    }

    //category spinner json parsing
    private class GetContacts extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void...voids) {
            Handler handler = new Handler();

            //Making request to url and getting response
            String url = "https://swissgourmets.ch/wp-json/job_listing/category_type";
            Log.d("qwes", url);
            String jsonStr = handler.makeServiceCall(url);
            Log.d("anmb", jsonStr);
            if (jsonStr != null) {
                try {

                    JSONObject jsonObj = new JSONObject(jsonStr);
                    Log.d("frty",jsonObj.toString());
                    //getting Json Array node
                    JSONObject j = jsonObj.getJSONObject("data");
                    JSONArray cat = j.getJSONArray("category");

                    Log.d("lkjh", cat.toString());

                    //looping through All Contacts
                    for (int i = 0; i < cat.length(); i++) {

                        JSONObject c = cat.getJSONObject(i);
                        Log.d("ccccc", c.toString());
                        String id = c.getString("id");
                        String name = c.getString("name");
                        Log.d("fghy", id + "==" + name);


                        category_list.add(name);
                        //add contact to list


                        Log.d("contactlist", category_list.toString());
                    }
                } catch (final JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            Log.d("mmnnh",category_list.toString());
            ArrayAdapter ad
                    = new ArrayAdapter(
                    getContext(),
                    android.R.layout.simple_spinner_item,
                    category_list);

            // set simple layout resource file
            // for each item of spinner
            ad.setDropDownViewResource(
                    android.R.layout
                            .simple_spinner_dropdown_item);
            category.setAdapter(ad);
        }
    }
    //code for 7 region recycler view
    private class GetRegion7 extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            Handler handler = new Handler();

            //Making request to url and getting response
            String url = "https://swissgourmets.ch/wp-json/job_listing/region_data";
            Log.d("qwes", url);
            String jsonStrRegion = handler.makeServiceCall(url);
            Log.d("anmb", jsonStrRegion);
            if (jsonStrRegion != null) {
                try {

                    JSONObject jsonObjRegion = new JSONObject(jsonStrRegion);
                    Log.d("frty",jsonObjRegion.toString());
                    //getting Json Array node
                    JSONObject jr = jsonObjRegion.getJSONObject("data");
                    JSONArray cat = jr.getJSONArray("rgn_info");

                    Log.d("lkjh", cat.toString());

                    //looping through All Contacts
                    for (int i = 0; i < cat.length(); i++) {

                        JSONObject cr = cat.getJSONObject(i);
                        Log.d("ccccc", cr.toString());
                        String id = cr.getString("id");
                        String name = cr.getString("name");
                        String count = cr.getString("count");
                        String img_url = cr.getString("img_url");
                        String icon_url = cr.getString("icon_url");
                        Log.d("fghy", id + "==" + name + "==" + count + "==" + img_url + "==" + icon_url);

                        HashMap<String,String> tempregionList = new HashMap<>();
                        tempregionList.put("id",id);
                        tempregionList.put("name",name);
                        tempregionList.put("count",count);
                        tempregionList.put("img_url",img_url);
                        tempregionList.put("icon_url",icon_url);

                        //add region 7 list
                        reg_7List.add(tempregionList);
                        regSpinner.add(name);
                        Log.d("llli",reg_7List.toString());

                    }
                } catch (final JSONException e) {
                    e.printStackTrace();
                }

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),2);
            rec7reg.setLayoutManager(layoutManager);
            myRecyclerViewAdapter = new MyRecyclerViewAdapter(getContext(),reg_7List);
            rec7reg.setAdapter(myRecyclerViewAdapter);
            myRecyclerViewAdapter.notifyDataSetChanged();
            rec7reg.setNestedScrollingEnabled(false);


            //spinner data
            ArrayAdapter reg
                    = new ArrayAdapter(
                    getContext(),
                    android.R.layout.simple_spinner_item,
                    regSpinner);

            // set simple layout resource file
            // for each item of spinner
            reg.setDropDownViewResource(
                    android.R.layout
                            .simple_spinner_dropdown_item);
            region.setAdapter(reg);


        }
    }

    //code for top restaurant category
    private class TopRestaurant extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            Handler handler = new Handler();

            //Making request to url and getting response
            String url = "https://swissgourmets.ch/wp-json/job_listing/categories_data";
            Log.d("qwes", url);
            String jsonTopRestaurant = handler.makeServiceCall(url);
            Log.d("anmb", jsonTopRestaurant);
            if (jsonTopRestaurant != null) {
                try {

                    JSONObject jsonTopres = new JSONObject(jsonTopRestaurant);
                    Log.d("frty",jsonTopres.toString());
                    //getting Json Array node
                    JSONObject jtoprs = jsonTopres.getJSONObject("data");
                    JSONArray cat = jtoprs.getJSONArray("cat_info");

                    Log.d("lkjh", cat.toString());

                    //looping through All Contacts
                    for (int i = 0; i < cat.length(); i++) {

                        JSONObject ctop = cat.getJSONObject(i);
                        Log.d("ccccc", ctop.toString());
                        String id = ctop.getString("id");
                        String name = ctop.getString("name");
                        String count = ctop.getString("count");
                        String img_url = ctop.getString("img_url");
                        String icon_url = ctop.getString("icon_url");
                        Log.d("fghy", id + "==" + name + "==" + count + "==" + img_url + "==" + icon_url);

                        HashMap<String,String> tempregionListTop = new HashMap<>();
                        tempregionListTop.put("id",id);
                        tempregionListTop.put("name",name);
                        tempregionListTop.put("count",count);
                        tempregionListTop.put("img_url",img_url);
                        tempregionListTop.put("icon_url",icon_url);

                        //add region 7 list
                        top_restaurant.add(tempregionListTop);

                    }
                } catch (final JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),2);
            rectop_restaurant.setLayoutManager(layoutManager);
            myRecyclerViewAdapter = new MyRecyclerViewAdapter(getContext(),top_restaurant);
            rectop_restaurant.setAdapter(myRecyclerViewAdapter);
            myRecyclerViewAdapter.notifyDataSetChanged();
            rectop_restaurant.setNestedScrollingEnabled(false);

        }
    }

    //code for single list restaurent
    private class SingleRestaurent extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            Handler handler = new Handler();

            //Making request to url and getting response
            String url = "https://swissgourmets.ch/wp-json/job_listing/prime_restaurents";
            Log.d("qwes", url);
            String jsonsingleList = handler.makeServiceCall(url);
            Log.d("anmb", jsonsingleList);
            if (jsonsingleList != null) {
                try {

                    JSONObject jsonsingleres = new JSONObject(jsonsingleList);
                    Log.d("frty",jsonsingleres.toString());
                    //getting Json Array node
                    JSONObject jone = jsonsingleres.getJSONObject("data");
                    JSONArray cat = jone.getJSONArray("resto_info");

                    Log.d("lkjh", cat.toString());

                    //looping through All Contacts
                    for (int i = 0; i < cat.length(); i++) {
                        ArrayList<String> smallimagelist= new ArrayList<>();
                        JSONObject cone = cat.getJSONObject(i);
                        Restaurant restaurant= new Restaurant();
                        restaurant.setType_id(cone.getInt("id"));
                        restaurant.setRestaurant_name(cone.getString("restaurant_name"));
                        restaurant.setRestaurant_address(cone.getString("address"));
                        restaurant.setImageUrl(cone.getString("restaurant_image"));
                        Log.d("ccccc", cone.toString());
                        JSONArray jsonsmall_img = cone.getJSONArray("small_icon_categories");

                        for (int j = 0; j < jsonsmall_img.length(); j++) {
                            JSONObject jsonObject2 = jsonsmall_img.getJSONObject(j);
                            smallimagelist.add(jsonObject2.getString("small_image"));
                        }

                            restaurant.setStringArrayList(smallimagelist);
                        single_listrest.add(restaurant);


                    }
                } catch (final JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            single_reclist.setLayoutManager(layoutManager);
            recAdapterSingle = new RecAdapterSingle(getContext(),single_listrest);
            single_reclist.setAdapter(recAdapterSingle);
            recAdapterSingle.notifyDataSetChanged();
            single_reclist.setNestedScrollingEnabled(false);
        }
    }

}