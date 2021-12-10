package com.buns.mgadmin.Activities.HomeActivity;

import android.util.Log;

import androidx.annotation.NonNull;

import com.buns.mgadmin.Models.Transaction.Deposit;
import com.buns.mgadmin.Models.Transaction.Transaction;
import com.buns.mgadmin.Models.Transaction.Withdrawal;
import com.buns.mgadmin.Utils.Constants;
import com.buns.mgadmin.Utils.SingletonTransactions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import java.util.List;

public class Presenter implements Contractor.Presenter {
    private Contractor.View mView;

    public Presenter(Contractor.View mView) {
        this.mView = mView;
    }

    @Override
    public void getDepositTransactions() {
        List<Deposit> data = SingletonTransactions.getInstance(null).getDepositList();
        Log.d("transaction", "getDepositTransactions: "+data.size());
        mView.setDepositsRequests(data);
    }

    @Override
    public void getWithdrawalTransaction() {
        List<Withdrawal> data = SingletonTransactions.getInstance(null).getWithdrawalList();
        Log.d("transaction", "getWithdrawalTransaction: "+data.size());
        mView.setWithdrawalRequests(data);
    }

    @Override
    public void rejectTransactionRequest(Transaction transaction) {
        if (transaction instanceof Withdrawal) {
            updateWithdrawalTransactionRejected(transaction);
        } else if (transaction instanceof Deposit) {
            updateDepositTransactionRejected(transaction);
        }
    }

    @Override
    public void acceptTransactionRequest(Transaction transaction) {
        if (transaction instanceof Withdrawal) {
            updateWithdrawalTransactionAccepted(transaction);
        } else if (transaction instanceof Deposit) {
            updateDepositTransactionAccepted(transaction);
        }
    }

    private void updateWithdrawalTransactionAccepted(@NonNull Transaction transaction) {
        DocumentReference dr = Constants.getWithdrawalRef().document(transaction.getOrderId());
        updateTransStatus(false, transaction.getOrderId(), Transaction.STATUS.SUCCESSFULL);
        dr.update(Constants.transactionId, transaction.getTransactionId());
    }

    private void updateWithdrawalTransactionRejected(@NonNull Transaction transaction) {
        DocumentReference dr = Constants.getWithdrawalRef().document(transaction.getOrderId());
        updateTransStatus(false, transaction.getOrderId(), Transaction.STATUS.ERROR);
        dr.update(Constants.transactionId, "NoTransaction");
        incrementUserBalance(transaction.getAmount(), transaction.getUid());
    }

    private void updateDepositTransactionAccepted(@NonNull Transaction transaction) {
        DocumentReference dr = Constants.getDepositsRef().document(transaction.getOrderId());
        updateTransStatus(true, transaction.getOrderId(), Transaction.STATUS.SUCCESSFULL);
        incrementUserBalance(transaction.getAmount(), transaction.getUid());
    }

    private void updateDepositTransactionRejected(@NonNull Transaction transaction) {
        updateTransStatus(true, transaction.getOrderId(), Transaction.STATUS.ERROR);
    }

    private void updateTransStatus(@NonNull Boolean isDepositType, @NonNull String orderId, @NonNull Transaction.STATUS status) {
        DocumentReference dr;
        if (isDepositType) {
            dr = Constants.getDepositsRef().document(orderId);
        } else {
            dr = Constants.getWithdrawalRef().document(orderId);
        }
        dr.update(Constants.status, status);
    }

    private void incrementUserBalance(final int amount, @NonNull String uid) {
        Constants
                .getUserRef(uid)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    assert queryDocumentSnapshots != null;
                    if (queryDocumentSnapshots.getDocuments().size() > 0) {
                        queryDocumentSnapshots.getDocuments().get(0).getReference().update(Constants.balance, FieldValue.increment(amount));
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("transaction", "failed Balance update: ", e);
                });
    }

}
