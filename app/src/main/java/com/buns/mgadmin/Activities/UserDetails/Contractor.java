package com.buns.mgadmin.Activities.UserDetails;

import androidx.annotation.NonNull;

import com.buns.mgadmin.Models.User;

public interface Contractor {
    interface View{
        void showLoading();
        void hideLoading();
        void setUser(@NonNull User user);
        void error(@NonNull String msg);
    }
    interface Presenter{
        void getUser(@NonNull String uid);
        void getUserFromNum(@NonNull String num);
    }
}
