package com.example.kattyapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;

import com.example.kattyapplication.fragment.PetInfoFragment;
import com.example.kattyapplication.fragment.RemindFragment;
import com.example.kattyapplication.fragment.SpendFragment;
import com.example.kattyapplication.fragment.SupportFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottom_nav;

    PetInfoFragment petInfoFragment = new PetInfoFragment();
    RemindFragment remindFragment = new RemindFragment();
    SpendFragment spendFragment = new SpendFragment();
    SupportFragment supportFragment = new SupportFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottom_nav = findViewById(R.id.bottom_nav);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, petInfoFragment).commit();

        bottom_nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.petInfo:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, petInfoFragment).commit();
                        return true;
                    case R.id.Remind:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, remindFragment).commit();
                        return true;
                    case R.id.Spend:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, spendFragment).commit();
                        return true;
                    case R.id.Support:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, supportFragment).commit();
                        return true;
                }

                return false;
            }
        });
    }

}