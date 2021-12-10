package com.buns.mgadmin.Activities.UserDetails.Fragment;

import androidx.annotation.NonNull;

import com.buns.mgadmin.Models.User;
import com.buns.mgadmin.Utils.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Presenter implements Contractor.Presenter {
    private Contractor.View mView;

    public Presenter(@NonNull Contractor.View mView) {
        this.mView = mView;
    }

    @Override
    public String getDateFromMilliSeconds(long timeAuth) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeAuth);
        return sdf.format(calendar.getTime());
    }

    @Override
    public void deleteUser(@NonNull User user) {
        getBackUp(user, o -> getUserRef(user), e -> mView.DError());
    }

    private void getUserRef(@NonNull User user) {
        Constants
                .getUserRef(user.getUserId()).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    assert queryDocumentSnapshots != null;
                    if (queryDocumentSnapshots.getDocuments().size() > 0)
                        deleteDoc(queryDocumentSnapshots);
                    else
                        mView.DError();
                })
                .addOnFailureListener(e -> mView.DError());
    }

    private void deleteDoc(QuerySnapshot queryDocumentSnapshots) {
        queryDocumentSnapshots
                .getDocuments()
                .get(0)
                .getReference()
                .delete()
                .addOnSuccessListener(aVoid -> mView.DSuccess())
                .addOnFailureListener(e -> mView.DError());
    }

    private void getBackUp(@NonNull User user, @NonNull OnSuccessListener suLit, @NonNull OnFailureListener faLit) {
        DocumentReference dr = Constants.getDeletedReference().document();
        dr
                .set(user)
                .addOnSuccessListener(suLit)
                .addOnFailureListener(faLit);
    }

}
