package com.buns.mgadmin.Activities.Admin.AdminDetailsDialog;

import androidx.annotation.NonNull;

import com.buns.mgadmin.Models.Subscription;
import com.buns.mgadmin.Models.Task;
import com.buns.mgadmin.Utils.Constants;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.List;

public class Presenter implements Contractor.Presenter {
    private Contractor.View mView;

    public Presenter(@NonNull Contractor.View view) {
        this.mView = view;
    }


    @Override
    public List<String> getTaskTypesList() {
        ArrayList<String> customers = new ArrayList<>();
        customers.add(Task.TaskType.FACEBOOK.toString());
        customers.add(Task.TaskType.INSTAGRAM.toString());
        customers.add(Task.TaskType.LINE.toString());
        customers.add(Task.TaskType.YOUTUBE.toString());
        return customers;
    }

    @Override
    public Task createTask(@NonNull String taskType, @NonNull String desc, @NonNull String url) {
        Task task = new Task();
        task.setDescription(desc);
        task.setLink(url);
        task.setType(Task.TaskType.valueOf(taskType));
        task.setIcon(Task.TaskType.valueOf(taskType).toString());
        return task;
    }

    @Override
    public void uploadTask(@NonNull Task task) {
        String date = Constants.getCurrentDate();
        DocumentReference dr1 = Constants.getTasksRef(Subscription.DIRECTOR, date).document();
        DocumentReference dr2 = Constants.getTasksRef(Subscription.VISITOR, date).document();
        DocumentReference dr3 = Constants.getTasksRef(Subscription.SUPERVISOR, date).document();
        DocumentReference dr4 = Constants.getTasksRef(Subscription.FULL_TIME_EMPLOYEE, date).document();
        DocumentReference dr5 = Constants.getTasksRef(Subscription.MANAGER, date).document();

        uploadAllTasks(task, dr1, dr2, dr3, dr4, dr5);
    }

    private void uploadAllTasks(@NonNull Task task, @NonNull DocumentReference... drs) {
        List<com.google.android.gms.tasks.Task<Void>> tasks = new ArrayList<>();
        for (DocumentReference dr : drs) {
            task.setId(dr.getId());
            tasks.add(dr.set(task));
        }

        Tasks.whenAllComplete(tasks).addOnFailureListener(mView).addOnSuccessListener(mView);

    }


}
