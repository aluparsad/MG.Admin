package com.buns.mgadmin.Utils;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.DocumentChange;

import java.util.ArrayList;
import java.util.List;

public class CustomService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();

        MediaPlayer mediaPlayer = MediaPlayer.create(getBaseContext(), Settings.System.DEFAULT_NOTIFICATION_URI);
        NotificationManager notificationManager = NotificationManager.getInstance(getBaseContext());

        List<DocumentChange> cacheNotification = new ArrayList<>();

        listenDepositRequests(cacheNotification, notificationManager, mediaPlayer);
        listenWithdrawalRequests(cacheNotification, notificationManager, mediaPlayer);
    }

    private void listenDepositRequests(List<DocumentChange> cacheNotification, NotificationManager notificationManager, MediaPlayer mediaPlayer) {
        Constants
                .getDepositsRef()
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        return;
                    }

                    if (value != null && value.getDocumentChanges().size() > 0) {
                        int count = 0;
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType().equals(DocumentChange.Type.ADDED))
                                if (!checkNotificationAlreadyShown(cacheNotification, dc)) {
                                    ++count;
                                    cacheNotification.add(dc);
                                }
                        }
                        if (count > 0) {
                            notificationManager.createNotification(count, 145, "New Deposit Requests");
                            mediaPlayer.start();
                        }
                    }
                });
    }

    private void listenWithdrawalRequests(List<DocumentChange> cacheNotification, NotificationManager notificationManager, MediaPlayer mediaPlayer) {
        Constants
                .getWithdrawalRef()
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        return;
                    }

                    if (value != null && value.getDocumentChanges().size() > 0) {
                        int count = 0;
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType().equals(DocumentChange.Type.ADDED)) {
                                if (!checkNotificationAlreadyShown(cacheNotification, dc)) {
                                    ++count;
                                    cacheNotification.add(dc);
                                }
                            }
                        }
                        if (count > 0) {
                            notificationManager.createNotification(count, 146, "New WithDraw Requests");
                            mediaPlayer.start();
                        }
                    }

                });
    }

    private boolean checkNotificationAlreadyShown(List<DocumentChange> data, DocumentChange doc) {
        for (DocumentChange dc : data)
            if (dc == doc)
                return true;
        return false;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}


