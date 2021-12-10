package com.buns.mgadmin.Activities.UserDetails;

import androidx.annotation.NonNull;

import com.buns.mgadmin.Models.User;
import com.buns.mgadmin.Utils.Constants;

public class Presenter implements Contractor.Presenter {
    private Contractor.View mView;

    public Presenter(@NonNull Contractor.View mView) {
        this.mView = mView;
    }

    @Override
    public void getUser(@NonNull String uid) {
        mView.showLoading();
        Constants.getUserRef(uid)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    assert queryDocumentSnapshots != null;
                    if (queryDocumentSnapshots.getDocuments().size() > 0) {
                        User user = queryDocumentSnapshots.getDocuments().get(0).toObject(User.class);
                        mView.setUser(user);
                    } else
                        mView.error("User not Found!");
                    mView.hideLoading();
                })
                .addOnFailureListener(e -> {
                    assert e.getMessage() != null;
                    mView.error(e.getMessage().subSequence(0, 10).toString());
                    mView.hideLoading();
                });
    }

    @Override
    public void getUserFromNum(@NonNull String number) {
        mView.showLoading();
        Constants
                .getUsersCollectionRef()
                .whereEqualTo(Constants.number, number.trim())
                .limit(1)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    assert queryDocumentSnapshots != null;
                    if (queryDocumentSnapshots.getDocuments().size() > 0) {
                        User user = queryDocumentSnapshots.getDocuments().get(0).toObject(User.class);
                        mView.setUser(user);
                    } else
                        mView.error("User not Found!");
                    mView.hideLoading();
                })
                .addOnFailureListener(e -> {
                    assert e.getMessage() != null;
                    mView.error(e.getMessage().subSequence(0, 10).toString());
                    mView.hideLoading();
                });
    }
}
