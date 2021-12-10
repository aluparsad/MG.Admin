package com.buns.mgadmin.Utils;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Navigator {
    private static Navigator instance;
    private static AppCompatActivity context;

    public static Navigator getInstance(AppCompatActivity ctx) {
        if (instance == null)
            instance = new Navigator(ctx);
        return instance;
    }

    private Navigator(AppCompatActivity ctx) {
        context = ctx;
    }

    public void navigateToActivity(@NonNull Class<?> cls, Bundle bundle) {
        if (context != null) {
            Intent intent = new Intent(context, cls);
            if (bundle != null)
                intent.putExtras(bundle);
            context.startActivity(intent);
        }
    }
}
