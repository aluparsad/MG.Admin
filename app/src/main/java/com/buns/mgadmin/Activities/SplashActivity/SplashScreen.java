package com.buns.mgadmin.Activities.SplashActivity;

import android.content.Intent;
import android.os.Bundle;

import com.buns.mgadmin.Activities.BaseActivity.BaseActivity;
import com.buns.mgadmin.Activities.HomeActivity.HomeActivity;
import com.buns.mgadmin.R;
import com.buns.mgadmin.Utils.NotificationManager;
import com.buns.mgadmin.Utils.SingletonTransactions;
import com.google.android.gms.tasks.OnSuccessListener;

public class SplashScreen extends BaseActivity implements OnSuccessListener<Object> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        SingletonTransactions.getInstance(this);

        //Start Service
//        startService(new Intent(getApplicationContext(), CustomService.class));

    }

    @Override
    public void onSuccess(Object o) {
        navigator.navigateToActivity(HomeActivity.class, null);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        NotificationManager.getInstance(getApplicationContext());
        SingletonTransactions.getInstance(null).setListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SingletonTransactions.getInstance(null).setListener(null);
    }
}