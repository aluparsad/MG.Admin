package com.buns.mgadmin.Activities.Admin.UpiDialog;

import androidx.annotation.NonNull;

public interface Contractor {
    interface View {
        void onUpiUpdated();

        void onUpiUpdateFailure();
    }

    interface Presenter {
        void updateUpi(@NonNull String upi);
    }
}
