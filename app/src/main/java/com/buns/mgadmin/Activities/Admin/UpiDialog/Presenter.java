package com.buns.mgadmin.Activities.Admin.UpiDialog;

import androidx.annotation.NonNull;
import com.buns.mgadmin.Utils.Constants;
import java.util.HashMap;
import java.util.Map;

public class Presenter implements Contractor.Presenter {
    private Contractor.View mView;

    public Presenter(@NonNull Contractor.View mView) {
        this.mView = mView;
    }


    @Override
    public void updateUpi(@NonNull String upi) {
        Map<String, String> data = new HashMap<>();
        data.put(Constants.upi, upi);

        Constants.getAdminDocRef()
                .set(data)
                .addOnSuccessListener(aVoid -> mView.onUpiUpdated())
                .addOnFailureListener(e -> mView.onUpiUpdateFailure());
    }
}
