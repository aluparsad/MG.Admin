package com.buns.mgadmin.Activities.Admin.AdminDetailsDialog;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.buns.mgadmin.Models.Task;
import com.buns.mgadmin.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;


public class DialogAdminDetails extends DialogFragment implements Contractor.View {
    private Contractor.Presenter presenter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.admin_details_dialog_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InitializeActivity();
        initUI(view);
    }




    private void InitializeActivity() {
        presenter = new Presenter(this);
    }

    private void initUI(@NonNull View v) {
        final AutoCompleteTextView taskTypeTv = v.findViewById(R.id.task_type_TV);
        final EditText
                descrpitionET = ((TextInputLayout) v.findViewById(R.id.taskDescriptionTIL)).getEditText(),
                taskUrlET = ((TextInputLayout) v.findViewById(R.id.taskUrlTIL)).getEditText();

        //Create adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, presenter.getTaskTypesList());
        //Set adapter
        taskTypeTv.setAdapter(adapter);

        //set Upload Button OnClickListener
        v.findViewById(R.id.taskUploadBtn).setOnClickListener(v1 -> {
            String taskType = "", desc = "", url = "";
            taskType = taskTypeTv.getText().toString();
            desc = descrpitionET.getText().toString();
            url = taskUrlET.getText().toString();

            if ((!TextUtils.isEmpty(taskType)) && (!TextUtils.isEmpty(desc)) && (!TextUtils.isEmpty(url))) {
                Task task = presenter.createTask(taskType, desc, url);
                presenter.uploadTask(task);
            }
        });
    }


    @Override
    public void onFailure(@NonNull Exception e) {
        assert e.getMessage() != null;
        showToast(e.getMessage().substring(0, 12));
        dismiss();
    }

    private void showToast(@NonNull String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(List<com.google.android.gms.tasks.Task<?>> tasks) {
        showToast("Done");
        dismiss();
    }
}