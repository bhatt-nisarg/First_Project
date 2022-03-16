package com.example.first_project;

import android.app.DownloadManager;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class SearchFragment extends Fragment {


    EditText search;
    Spinner region,category;
    List<String> category_list = new ArrayList<String>();


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
        search = view.findViewById(R.id.search_edit);
        region = view.findViewById(R.id.spinner_region);
        category = view.findViewById(R.id.spinner_category);


//        getHttpResponse();
        new GetContacts().execute();
//        Log.d("puio", String.valueOf(category_list.size()));


        return view;
    }


//    public void getHttpResponse() {
//
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//        Log.d("qwer",url);
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (response.isSuccessful()){
//
//                    String categ = response.body().string();
//                    //in below line we make our cardview visible after  we get all the data.
//                    Log.d("fgrt",categ);
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                JSONObject reader = new JSONObject(categ);
//                                JSONObject c = reader.getJSONObject("data");
//                                Log.d("vbcf",reader.toString());
//
//                                JSONArray restaurentCat = c.getJSONArray("category");
//
//                                for(int i =0; i < restaurentCat.length(); i++){
//                                    JSONObject cat = restaurentCat.getJSONObject(i);
//                                    category_list.add(cat.getString("name"));
//                                }
//
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                                Log.d("sdd",e.getMessage().toString());
//                            }
//                        }
//                    });
//                }
//            }
//        });
//    }

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
}