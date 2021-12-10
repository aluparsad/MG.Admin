package com.buns.mgadmin.Activities.HomeActivity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.buns.mgadmin.Activities.Admin.AdminDetailsDialog.DialogAdminDetails;
import com.buns.mgadmin.Activities.Admin.UpiDialog.UpiUpdateDialog;
import com.buns.mgadmin.Activities.BaseActivity.BaseActivity;
import com.buns.mgadmin.Activities.UserDetails.UserDetailActivity;
import com.buns.mgadmin.Models.Transaction.Deposit;
import com.buns.mgadmin.Models.Transaction.Transaction;
import com.buns.mgadmin.Models.Transaction.Withdrawal;
import com.buns.mgadmin.R;
import com.buns.mgadmin.Utils.Constants;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity implements Contractor.View, PaymentsAdapter.OnItemClick {
    private Contractor.Presenter presenter;
    private RecyclerView rv;
    private BottomNavigationView bottomNavBar;
    private PaymentsAdapter wAdapter, dAdapter;
    private LottieAnimationView loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        InitializeView();
    }

    private void InitializeView() {
        presenter = new Presenter(this);
        loading = findViewById(R.id.loading);
        rv = findViewById(R.id.rv_payments);
        bottomNavBar = findViewById(R.id.bottomNavBar);

        setRv();
        initBottomNavBar();
        InitActionBar();
    }

    private void setRv() {
        wAdapter = new PaymentsAdapter(new ArrayList<>(), this);
        dAdapter = new PaymentsAdapter(new ArrayList<>(), this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(dAdapter);
    }

    private void initBottomNavBar() {
        presenter.getDepositTransactions();
        bottomNavBar.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.withdrawal:
                    showLoading();
                    rv.setAdapter(wAdapter);
                    presenter.getWithdrawalTransaction();
                    break;
                case R.id.deposit:
                    showLoading();
                    rv.setAdapter(dAdapter);
                    presenter.getDepositTransactions();
                    break;
            }
            return true;
        });
    }

    private void InitActionBar() {
        //Set Action Bar Layout
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.darkgrey));
        getSupportActionBar().setCustomView(R.layout.custom_actionbar_layout);
        View actionBarView = getSupportActionBar().getCustomView();


        //Set Button Click Listeners
        actionBarView.findViewById(R.id.upi).setOnClickListener(v -> {
            new UpiUpdateDialog().show(getSupportFragmentManager(), null);
        });
        actionBarView.findViewById(R.id.tasks).setOnClickListener(v -> {
            new DialogAdminDetails().show(getSupportFragmentManager(), null);
        });

        findViewById(R.id.floatingActionButton).setOnClickListener(v -> {
            navigator.navigateToActivity(UserDetailActivity.class, null);
            finish();
        });
    }


    @Override
    public void setDepositsRequests(List<Deposit> transaction) {
        dAdapter.setData(transaction);
        hideLoading();
    }

    @Override
    public void setWithdrawalRequests(List<Withdrawal> transaction) {
        wAdapter.setData(transaction);
        hideLoading();
    }

    @Override
    public void showUser(@NonNull String uid) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.userid, uid);
        navigator.navigateToActivity(UserDetailActivity.class, bundle);
    }

    @Override
    public void accept(@NonNull Transaction transaction) {
        presenter.acceptTransactionRequest(transaction);
    }

    @Override
    public void reject(@NonNull Transaction transaction) {
        presenter.rejectTransactionRequest(transaction);
    }

    private void showLoading() {
        loading.setVisibility(View.VISIBLE);
        loading.playAnimation();
    }

    private void hideLoading() {
        loading.setVisibility(View.GONE);
        loading.pauseAnimation();
    }


}