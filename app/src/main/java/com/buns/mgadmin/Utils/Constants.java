package com.buns.mgadmin.Utils;

import androidx.annotation.NonNull;

import com.buns.mgadmin.Models.Subscription;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Constants {

    public static final String plan = "plan",
            notificationChannelName = "channel_name",
            notificationChannelDescription = "channel_desc",
            transactionId = "transactionId",
            balance = "balance",
            deleted = "DELETED",
            upi = "UPI",
            userid = "userId",
            users = "USERS",
            status = "status",
            withdrawal = "WITHDRAWAL",
            deposits = "DEPOSITS",
            tasks = "TASKS",
            number = "number",
            refferId = "refferId",
            admin = "ADMIN", CHANNEL_ID = "101";


    public static String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        return df.format(c);
    }

    public static String getDateFromMilliSeconds(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        return df.format(calendar.getTime());
    }

    public static CollectionReference getUsersCollectionRef() {
        return FirebaseFirestore.getInstance().collection(users);
    }

    public static Query getUserRef(@NonNull String uid) {
        return getUsersCollectionRef().limit(1).whereEqualTo(userid, uid);
    }

    public static CollectionReference getDeletedReference() {
        return FirebaseFirestore.getInstance().collection(deleted);
    }

    public static CollectionReference getAdminRef() {
        return FirebaseFirestore.getInstance().collection(admin);
    }

    public static DocumentReference getAdminDocRef() {
        return FirebaseFirestore.getInstance().collection(admin).document(admin);
    }

    public static DocumentReference getPaymentRef() {
        return getAdminRef().document(admin);
    }

    public static CollectionReference getWithdrawalRef() {
        return getPaymentRef().collection(withdrawal);
    }

    public static CollectionReference getDepositsRef() {
        return getPaymentRef().collection(deposits);
    }

    public static CollectionReference getTasksRef(@NonNull Subscription subscription, @NonNull String currentDate) {
        return FirebaseFirestore.getInstance().collection(tasks).document(currentDate).collection(subscription.toString());
    }
}
