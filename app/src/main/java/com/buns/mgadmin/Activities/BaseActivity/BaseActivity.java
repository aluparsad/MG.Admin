package com.buns.mgadmin.Activities.BaseActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.buns.mgadmin.Utils.Navigator;
import com.buns.mgadmin.Utils.CustomService;

public class BaseActivity extends AppCompatActivity {
    protected Navigator navigator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Initialize();
    }

    @Override
    protected void onResume() {
        super.onResume();
        destroyService();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        InitServices();
    }

    private void InitServices() {
        startService(new Intent(this, CustomService.class));
    }

    private void destroyService() {
        stopService(new Intent(this, CustomService.class));
    }

    private void Initialize() {
        //Initialize Navigator
        navigator = Navigator.getInstance(this);

        //Activate Night Mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

    }


}
