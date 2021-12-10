package com.buns.mgadmin.Activities.Admin.AdminDetailsDialog;

import androidx.annotation.NonNull;

import com.buns.mgadmin.Models.Task;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

public interface Contractor {
    interface View extends OnFailureListener, OnSuccessListener<List<com.google.android.gms.tasks.Task<?>>> {
    }

    interface Presenter {
        List<String> getTaskTypesList();

        Task createTask(@NonNull String taskType, @NonNull String desc, @NonNull String url);

        void uploadTask(@NonNull Task task);
    }
}
