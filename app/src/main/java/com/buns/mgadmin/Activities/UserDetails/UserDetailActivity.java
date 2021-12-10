package com.buns.mgadmin.Activities.UserDetails;

import androidx.annotation.NonNull;

import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.buns.mgadmin.Activities.BaseActivity.BaseActivity;
import com.buns.mgadmin.Activities.HomeActivity.HomeActivity;
import com.buns.mgadmin.Activities.UserDetails.Fragment.UserDetailFragment;
import com.buns.mgadmin.Models.User;
import com.buns.mgadmin.R;
import com.buns.mgadmin.Utils.Constants;

public class UserDetailActivity extends BaseActivity implements Contractor.View {

    private Contractor.Presenter presenter;
    private SearchView sv;
    private UserDetailFragment udFragment;
    private LottieAnimationView loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        getSupportActionBar().hide();
        InitializeView();
        InitPreUser();
        InitializeSV();
    }

    private void InitPreUser() {
        String uid = getIntent().getStringExtra(Constants.userid);
        if (uid != null)
            presenter.getUser(uid);
    }

    private void InitializeSV() {
        sv = findViewById(R.id.user_sv);

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.getUserFromNum("+91"+query);
                sv.clearFocus();
                sv.setQuery("", false);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
    @Override
    public void showLoading() {
        loading.setVisibility(View.VISIBLE);
        loading.playAnimation();
    }
    @Override
    public void hideLoading() {
        loading.setVisibility(View.GONE);
        loading.pauseAnimation();
    }


    private void InitializeView() {
        presenter = new Presenter(this);
        loading = findViewById(R.id.loading);
    }

    private void showUserFragment(@NonNull User user) {
        udFragment = new UserDetailFragment(user);
        getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .replace(R.id.fl_userDetails, udFragment)
                .commit();
        hideLoading();
    }

    @Override
    public void setUser(User user) {
        if (user != null)
            showUserFragment(user);
        else
            error("User Not Found");
    }

    @Override
    public void error(@NonNull String msg) {
        showToast(msg);
    }

    private void showToast(@NonNull String msg) {
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        navigator.navigateToActivity(HomeActivity.class, null);
        finish();
    }
}