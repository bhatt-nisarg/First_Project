package com.example.first_project.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.first_project.Adapter.RecAdapterSingle;
import com.example.first_project.R;

public class item_singlerec extends AppCompatActivity {

    RecAdapterSingle recAdapterSingle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_singlerec);


    }
}