package com.example.pharma.Notification.Service;


import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.util.Log;
import com.example.pharma.MainActivity;
import com.example.pharma.Notification.Utils.NotificationUtils;
import com.example.pharma.Notification.VO.NotificationVO;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgingService";
    private static final String TITLE = "title";
    private static final String EMPTY = "";
    private static final String MESSAGE = "message";
    private static final String IMAGE = "image";
    private static final String ACTION = "action";
    private static final String DATA = "data";
    private static final String ACTION_DESTINATION = "action_destination";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

       Log.d(TAG, "From: " + remoteMessage.getFrom());

   if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            SharedPreferences pref = getSharedPreferences("fileName", Context.MODE_PRIVATE);

           if(pref.getBoolean("connected", false))
            {   SharedPreferences pref1 = getSharedPreferences("fileName",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref1.edit();
                editor.putInt("decision", 0);
                editor.putInt("notif",52);
                editor.commit();

                handleNotification(remoteMessage.getNotification());}
            }

    }

    private void handleNotification(RemoteMessage.Notification RemoteMsgNotification) {
        String message = RemoteMsgNotification.getBody();
        String title = RemoteMsgNotification.getTitle();
        NotificationVO notificationVO = new NotificationVO();
        notificationVO.setTitle(title);
        notificationVO.setMessage(message);

        Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
        NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
        notificationUtils.displayNotification(notificationVO, resultIntent);
        notificationUtils.playNotificationSound();
    }


}
