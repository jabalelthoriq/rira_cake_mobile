package com.jabrix.rira_cake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    private static final String KEY_EMAIL = "email";
    private static final String KEY_USERNAME = "username";

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ImageButton buttonDrawerToogle;
    private BottomNavigationView bottomNavigationView;
    private TextView textView;
    private SharedPreferences sharedPreferences;
    private EditText edtUsername;
    DashboardFragment firstFragment = new DashboardFragment();
    OrderFragment secondFragment = new OrderFragment();
    OverviewFragment thirdFragment = new OverviewFragment();
    SettingFragment fourFragment = new SettingFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        handleIntentExtras();
    }

    private void initViews() {
        drawer = findViewById(R.id.drawer_layout);
        edtUsername = findViewById(R.id.edt_username);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.dashboard);
        buttonDrawerToogle = findViewById(R.id.btn_drawer);
        navigationView = findViewById(R.id.drawer_menu);
        textView = findViewById(R.id.txt_halo);

        buttonDrawerToogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.open();
            }
        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item){

                int itemId = item.getItemId();

                if (itemId == R.id.profile) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, firstFragment).commit();
                } else if (itemId == R.id.contact) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, secondFragment).commit();
                } else if (itemId == R.id.settings) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, thirdFragment).commit();
                }
                drawer.close();

                return false;
            }
        });
    }

    private void handleIntentExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.containsKey(KEY_USERNAME)) {
                String username = extras.getString(KEY_USERNAME);
                textView.setText("Haloo "+username);
            }
        }
    }




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();

        if (itemId == R.id.dashboard) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, firstFragment).commit();
            return true;
        } else if (itemId == R.id.order) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, secondFragment).commit();
            return true;
        } else if (itemId == R.id.overview) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, thirdFragment).commit();
            return true;
        } else if (itemId == R.id.settings) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, fourFragment).commit();
            return true;
        }

        return false;
    }
}