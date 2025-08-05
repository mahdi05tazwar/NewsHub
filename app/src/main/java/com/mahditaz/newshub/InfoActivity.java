package com.mahditaz.newshub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mahditaz.newshub.databinding.ActivityInfoBinding;

public class InfoActivity extends AppCompatActivity {

    ActivityInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}