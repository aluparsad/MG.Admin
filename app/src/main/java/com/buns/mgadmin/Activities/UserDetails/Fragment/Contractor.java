package com.buns.mgadmin.Activities.UserDetails.Fragment;

import androidx.annotation.NonNull;

import com.buns.mgadmin.Models.User;

public interface Contractor {
    interface View{
        void DSuccess();
        void DError();
    }
    interface Presenter{
        String getDateFromMilliSeconds(long timeAuth);

        void deleteUser(@NonNull User user);
    }
}
