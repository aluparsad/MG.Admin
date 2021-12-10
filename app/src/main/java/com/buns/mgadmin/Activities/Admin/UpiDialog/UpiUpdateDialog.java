package com.buns.mgadmin.Activities.Admin.UpiDialog;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.buns.mgadmin.R;

public class UpiUpdateDialog extends DialogFragment implements Contractor.View {

    private Contractor.Presenter presenter;

    public UpiUpdateDialog() {
        presenter = new Presenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.upi_dialog_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        InitializeView(view);

    }

    private void InitializeView(@NonNull View view) {

        view.findViewById(R.id.upiUpdateBtn).setOnClickListener(v -> {
            final String upi = ((EditText) view.findViewById(R.id.upiET)).getText().toString();
            if (!TextUtils.isEmpty(upi)) {
                presenter.updateUpi(upi);
            } else
                showToast("Enter New UPI");
        });

    }

    private void showToast(@NonNull String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpiUpdated() {
        showToast("UPI updated");
        dismiss();
    }

    @Override
    public void onUpiUpdateFailure() {
        showToast("UPI Update Failed");
    }
}
