package com.buns.mgadmin.Activities.HomeActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.buns.mgadmin.Models.Transaction.Deposit;
import com.buns.mgadmin.Models.Transaction.Transaction;
import com.buns.mgadmin.Models.Transaction.Withdrawal;
import com.buns.mgadmin.R;
import com.buns.mgadmin.Utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class PaymentsAdapter extends RecyclerView.Adapter<PaymentsAdapter.ViewHolder> {

    private List<Transaction> data;
    private OnItemClick listener;

    public PaymentsAdapter(List<? extends Transaction> transactions, @NonNull OnItemClick listener) {
        data = new ArrayList<>();
        this.listener = listener;
        if (transactions != null)
            data.addAll(transactions);
    }

    private void removeTransaction(@NonNull Transaction transaction) {
        final int index = data.indexOf(transaction);
        data.remove(index);
        notifyItemRemoved(index);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setTransactionView(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(@NonNull List<? extends Transaction> transaction) {
        data.clear();
        data.addAll(0, transaction);
        notifyItemInserted(0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Transaction tempTransaction;

        private void setTransactionView(@NonNull Transaction transaction) {
            tempTransaction = transaction;

            ((TextView) itemView.findViewById(R.id.trans_uid)).setText(transaction.getUid());
            ((TextView) itemView.findViewById(R.id.trans_amount)).setText(String.valueOf(transaction.getAmount()));
            ((TextView) itemView.findViewById(R.id.trans_orderid)).setText(transaction.getOrderId());
            ((TextView) itemView.findViewById(R.id.trans_time)).setText(Constants.getDateFromMilliSeconds(transaction.getTime()));

            if (transaction instanceof Deposit)
                setDepositView(transaction);
            else
                setWithdrawalView();
        }

        private void setDepositView(@NonNull Transaction transaction) {
            String transactionId = ((Deposit) transaction).getTransactionId();
            LinearLayout ll = itemView.findViewById(R.id.trans_transIdLL);
            ll.setVisibility(View.VISIBLE);
            itemView.findViewById(R.id.trans_transIdEt).setVisibility(View.GONE);
            ((TextView) ll.findViewById(R.id.trans_transIdTv)).setText(transactionId);
        }

        private void setWithdrawalView() {
            EditText transactionIdEt = itemView.findViewById(R.id.trans_transIdEt);
            transactionIdEt.setVisibility(View.VISIBLE);
            itemView.findViewById(R.id.trans_transIdLL).setVisibility(View.GONE);
            ((TextView) itemView.findViewById(R.id.trans_orderid)).setText(((Withdrawal) tempTransaction).getUpiId());
            tempTransaction.setTransactionId(transactionIdEt.getText().toString());
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            setClickListener();
        }

        private void setClickListener() {
            itemView.findViewById(R.id.btnAccept).setOnClickListener(v -> {
                if (tempTransaction instanceof Deposit) {
                    if (!TextUtils.isEmpty(tempTransaction.getTransactionId())) {
                        listener.accept(tempTransaction);
                        removeTransaction(tempTransaction);
                    }
                } else {
                    setWithdrawalView();
                    if (!TextUtils.isEmpty(tempTransaction.getTransactionId())) {
                        listener.accept(tempTransaction);
                        removeTransaction(tempTransaction);
                    }
                }
            });
            itemView.findViewById(R.id.btnReject).setOnClickListener(v -> {
                removeTransaction(tempTransaction);
                listener.reject(tempTransaction);
            });
            itemView.setOnClickListener(v -> listener.showUser(tempTransaction.getUid()));
        }
    }

    public interface OnItemClick {
        void showUser(@NonNull String uid);

        void accept(@NonNull Transaction Transaction);

        void reject(@NonNull Transaction Transaction);
    }

}
