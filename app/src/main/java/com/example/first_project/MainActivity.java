package com.example.first_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import java.io.FileReader;

public class MainActivity extends AppCompatActivity  {
    DrawerLayout drawerLayout;

    Toolbar toolbar;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.topappbar);
        setSupportActionBar(toolbar);

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        setupdrawercontent(navigationView);
        loadFragment(new SearchFragment());
        }

    private void setupdrawercontent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                int id = item.getItemId();
                switch (id) {
                    case R.id.search:
                        fragment = new SearchFragment();
                        loadFragment(fragment);
                        item.setChecked(true);

                        return true;

                    case R.id.news:
                        fragment = new NewsFragment();
                        item.setChecked(true);
                        loadFragment(fragment);

                        return true;
                    case R.id.contact:
                        fragment = new ContactFragment();
                        item.setChecked(true);
                        loadFragment(fragment);

                        return true;
                }
                return false;

            }
        });

    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        drawerLayout.closeDrawers();

    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return  super.onOptionsItemSelected(item);
    }

}

