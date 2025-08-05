package com.mahditaz.newshub.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayoutMediator;

import com.mahditaz.newshub.InfoActivity;
import com.mahditaz.newshub.adapter.ViewPagerAdapter;
import com.mahditaz.newshub.databinding.ActivityMainBinding;
import com.mahditaz.newshub.fragment.SearchFragment;
import com.mahditaz.newshub.fragment.TopHeadlinesFragment;
import com.mahditaz.newshub.util.Utils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityMainBinding binding;
    ViewPagerAdapter vpAdapter;
    ArrayList<Fragment> fragments;
    ArrayList<String> tabNames;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fragments = new ArrayList<>(); fragments.add(new TopHeadlinesFragment()); fragments.add(new SearchFragment());

        vpAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle(), fragments);
        binding.VP.setAdapter(vpAdapter);
        binding.VP.registerOnPageChangeCallback(new PageChangeHandler());


        tabNames = new ArrayList<>(); tabNames.add("TOP HEADLINES"); tabNames.add("SEARCH");
        new TabLayoutMediator(binding.TL, binding.VP, (tab, position) -> tab.setText(tabNames.get(position))).attach();

        binding.button3.setOnClickListener(this);
        binding.button4.setOnClickListener(this);
        binding.button5.setOnClickListener(this);

        if (binding.button3.getText().equals(Utils.currentCategory)) binding.button3.setEnabled(false);
        if (binding.button4.getText().equals(Utils.currentCategory)) binding.button4.setEnabled(false);
        if (binding.button5.getText().equals(Utils.currentCategory)) binding.button5.setEnabled(false);

        binding.searchHolder.setVisibility(View.GONE);

        binding.searchBtn.setOnClickListener(view -> {
            String q = binding.searchBar.getText().toString();
            if (!q.isEmpty()) {
                Utils.currentQuery = q;
                binding.VP.setAdapter(vpAdapter);
                binding.VP.setCurrentItem(1);
            }
            else Toast.makeText(this, "Searchbar is empty!", Toast.LENGTH_SHORT).show();
        });

        i = new Intent(this, InfoActivity.class);
        binding.infoBtn.setOnClickListener(view -> {startActivity(i);});
    }


    @Override
    public void onClick(View view) { // ONLY FOR TOP HEADLINES BTNS. SEARCH BTN ONCLICK IS WITHIN ONCREATE.

        Button btn = (Button) view;
        Utils.currentCategory = btn.getText().toString();
        binding.VP.setAdapter(vpAdapter);
        btn.setEnabled(false);

        if (binding.button3 != btn) binding.button3.setEnabled(true);
        if (binding.button4 != btn) binding.button4.setEnabled(true);
        if (binding.button5 != btn) binding.button5.setEnabled(true);
    }

    private class PageChangeHandler extends ViewPager2.OnPageChangeCallback {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            if (position == 1) {
                binding.btnHolder.setVisibility(View.GONE);
                binding.searchHolder.setVisibility(View.VISIBLE);
            }
            else {
                binding.btnHolder.setVisibility(View.VISIBLE);
                binding.searchHolder.setVisibility(View.GONE);
            }
        }
    }
}