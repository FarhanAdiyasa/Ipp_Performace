package com.polytechnic.astra.ac.id.ippperformance;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.polytechnic.astra.ac.id.ippperformance.Fragment.DashboardFragment;
import com.polytechnic.astra.ac.id.ippperformance.Fragment.DetailPekerjaanListFragment;
import com.polytechnic.astra.ac.id.ippperformance.Fragment.LoginFragment;
import com.polytechnic.astra.ac.id.ippperformance.Fragment.PekerjaanListFragment;
import com.polytechnic.astra.ac.id.ippperformance.Fragment.RekapitulasiListFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBottomNavigationView = findViewById(R.id.bottomNavigationView);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = LoginFragment.newInstance();
        fm.beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit();
        mBottomNavigationView.setVisibility(View.GONE);


        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.btnNavHome){
                    fm.beginTransaction()
                            .replace(R.id.fragment_container, DashboardFragment.newInstance())
                            .addToBackStack(null)
                            .commit();
                }else if(item.getItemId() == R.id.btnNavPekerjaan){
                    fm.beginTransaction()
                            .replace(R.id.fragment_container, PekerjaanListFragment.newInstance())
                            .addToBackStack(null)
                            .commit();
                } else if (item.getItemId() == R.id.btnNavDetail) {
                    fm.beginTransaction()

                            .replace(R.id.fragment_container, DetailPekerjaanListFragment.newInstance())
                            .addToBackStack(null)
                            .commit();
                }else if (item.getItemId() == R.id.btnNavRekapitulasi) {
                    fm.beginTransaction()
                            .replace(R.id.fragment_container, RekapitulasiListFragment.newInstance())
                            .addToBackStack(null)
                            .commit();
                }else {
                    fm.beginTransaction()
                            .replace(R.id.fragment_container, null)
                            .commit();
                }

                return true;
            }
        });

    }
    public void showBottomNavigationView() {
        mBottomNavigationView.setVisibility(View.VISIBLE);
    }
}
