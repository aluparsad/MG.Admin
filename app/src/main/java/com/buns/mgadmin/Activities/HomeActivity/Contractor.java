package com.buns.mgadmin.Activities.HomeActivity;

import androidx.annotation.NonNull;

import com.buns.mgadmin.Models.Transaction.Deposit;
import com.buns.mgadmin.Models.Transaction.Transaction;
import com.buns.mgadmin.Models.Transaction.Withdrawal;

import java.util.List;

public interface Contractor {
    interface View{
        void setDepositsRequests(List<Deposit> transaction);
        void setWithdrawalRequests(List<Withdrawal> transaction);
    }
    interface Presenter{
        void getDepositTransactions();
        void getWithdrawalTransaction();

        void rejectTransactionRequest(@NonNull Transaction transaction);
        void acceptTransactionRequest(@NonNull Transaction transaction);
    }
}
