package com.buns.mgadmin.Utils;

import android.util.Log;

import androidx.annotation.NonNull;

import com.buns.mgadmin.Models.Transaction.Deposit;
import com.buns.mgadmin.Models.Transaction.Transaction;
import com.buns.mgadmin.Models.Transaction.Withdrawal;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class SingletonTransactions {
    private static SingletonTransactions instance;
    private OnSuccessListener<?> listener;
    private List<Withdrawal> withdrawalList;
    private List<Deposit> depositList;

    private Boolean w = false, d = false;

    public static SingletonTransactions getInstance(OnSuccessListener<?> listener) {
        if (instance == null)
            instance = new SingletonTransactions(listener);
        return instance;
    }

    public void setListener(OnSuccessListener<?> listener) {
        this.listener = listener;
    }

    private SingletonTransactions(OnSuccessListener<?> listener) {
        this.listener = listener;
        withdrawalList = new ArrayList<>();
        depositList = new ArrayList<>();
        initWithdrawals();
        initDeposits();
    }

    private void initWithdrawals() {
        Constants
                .getWithdrawalRef()
                .whereEqualTo(Constants.status, Transaction.STATUS.PENDING)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Log.e("trans", "initWithdrawals: ", error);
                        return;
                    }

                    if (value != null) {
                        withdrawalList.clear();
                        for (DocumentSnapshot ds : value.getDocuments()) {
                            Withdrawal obj = ds.toObject(Withdrawal.class);
                            withdrawalList.add(obj);
                        }
                        if (value.getDocumentChanges().size() > 0)
                            Notify(withdrawalList.size(), 2, "new withdrawal requests");
                    }
                    w = true;
                    check();
                });
    }

    private void initDeposits() {
        Constants
                .getDepositsRef()
                .whereEqualTo(Constants.status, Transaction.STATUS.PENDING)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Log.e("trans", "initDeposits: ", error);
                        return;
                    }
                    if (value != null) {
                        depositList.clear();
                        for (DocumentSnapshot ds : value.getDocuments()) {
                            Deposit obj = ds.toObject(Deposit.class);
                            depositList.add(obj);
                        }
                        if (value.getDocumentChanges().size() > 0)
                            Notify(depositList.size(), 1, "new deposit requests");
                    }
                    d = true;
                    check();
                });
    }

    private void Notify(final int size, final int id, @NonNull String desc) {
//        NotificationManager nm = NotificationManager.getInstance(null);
//        if (nm != null)
//            nm.createNotification(size, id, desc);
    }

    private void check() {
        if (w && d)
            if (listener != null)
                listener.onSuccess(null);
    }


    public List<Withdrawal> getWithdrawalList() {
        return withdrawalList;
    }

    public List<Deposit> getDepositList() {
        return depositList;
    }

}
