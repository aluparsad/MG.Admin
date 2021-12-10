package com.buns.mgadmin.Activities.UserDetails.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.buns.mgadmin.Models.User;
import com.buns.mgadmin.R;

public class UserDetailFragment extends Fragment implements Contractor.View {
    private User user;
    private Contractor.Presenter presenter;
    private TextView name, plan, date, uid, balance, refId, refBy, number, tasksDone, deposits, withdrawal;
    private Button deleteBtn;

    public UserDetailFragment(User user) {
        this.presenter = new Presenter(this);
        this.user = user;
    }

    private void updateView() {
        if (user != null) {
            name.setText(user.getName());
            plan.setText(user.getPlan().toString());
            number.setText(user.getNumber());
            date.setText(presenter.getDateFromMilliSeconds(user.getTimeAuth()));
            uid.setText(user.getUserId());
            balance.setText(String.valueOf(user.getBalance()));
            refId.setText(user.getRefferId());
            refBy.setText(user.getRefferedBy());
            //TODO: setTasks, deposits, withdrawals
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InitializeView(view);
        updateView();
        setBtnClick();

    }

    private void setBtnClick() {
        deleteBtn.setOnClickListener(v -> {
            if (user != null)
                presenter.deleteUser(user);
        });
    }

    private void InitializeView(View v) {
        name = v.findViewById(R.id.nameTv);
        number = v.findViewById(R.id.numberTV);
        plan = v.findViewById(R.id.planTv);
        date = v.findViewById(R.id.dateTv);
        uid = v.findViewById(R.id.uidTv);
        balance = v.findViewById(R.id.balanceTv);
        refId = v.findViewById(R.id.refIdTv);
        refBy = v.findViewById(R.id.refByTv);
        tasksDone = v.findViewById(R.id.numTasksTv);
        deposits = v.findViewById(R.id.depositsTv);
        withdrawal = v.findViewById(R.id.withdrawalTv);
        deleteBtn = v.findViewById(R.id.dltBtn);
    }

    @Override
    public void DSuccess() {
        showToast("SuccessFul");
    }

    private void showToast(@NonNull String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void DError() {
        showToast("Error");
    }
}