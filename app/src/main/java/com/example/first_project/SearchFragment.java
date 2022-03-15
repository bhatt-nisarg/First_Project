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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class SearchFragment extends Fragment {


    EditText search;
    Spinner region,category;
    ArrayList<String> category_list;

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



//        Log.d("puio", String.valueOf(category_list.size()));
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
        getHttpResponse();
        return view;
    }


    public void getHttpResponse() {

        String url = "https://swissgourmets.ch/wp-json/job_listing/category_type";


        OkHttpClient client = new OkHttpClient();
        okhttp3.Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){

                    String recs = response.body().string();
                    //in below line we make our cardview visible after  we get all the data.

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject reader = new JSONObject(recs);
                                Log.d("vbcf",reader.toString());

                                JSONArray usersArr = reader.getJSONArray("name");

                                for(int i =0; i < usersArr.length(); i++){
                                    JSONObject user = usersArr.getJSONObject(i);
                                    Log.e("user", user.getString("username"));
                                    category_list.add(user.getString("name"));
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.d("sdd",e.getMessage().toString());
                            }
                        }
                    });
                }
            }
        });
    }

}