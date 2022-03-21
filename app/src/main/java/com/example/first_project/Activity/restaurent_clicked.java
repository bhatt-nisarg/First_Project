package com.example.first_project.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.first_project.Fragment.SearchFragment;
import com.example.first_project.R;

public class restaurent_clicked extends AppCompatActivity {
    TextView back_btn,clicked_name,clicked_region,clicked_countrestaurent,Load_more;
    RecyclerView click_resRecycle;
    Fragment fragment;


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



    }
}