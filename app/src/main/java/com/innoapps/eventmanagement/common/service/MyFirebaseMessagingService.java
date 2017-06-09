package com.innoapps.eventmanagement.common.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.helper.Config;
import com.innoapps.eventmanagement.common.navigation.NavigationActivity;
import com.innoapps.eventmanagement.common.utils.NotificationUtils;

import org.json.JSONException;
import org.json.JSONObject;



public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    private NotificationUtils notificationUtils;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody());
        }
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {

            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                JSONObject jsonObject = json.getJSONObject("notification");
                handleNotification(jsonObject.getString("body"));

            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }


    private void handleNotification(String message) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, pushNotification,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)

                    .setSmallIcon(R.mipmap.ic_ance)
                    .setContentTitle("ANCE")
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

//
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                notificationBuilder.setSmallIcon(R.mipmap.ic_ance);
            } else {
                notificationBuilder.setSmallIcon(R.mipmap.call);
            }

            notificationManager.notify(0, notificationBuilder.build());

            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        } else {
            // If the app is in background, firebase itself handles the notification

            // app is in background, show the notification in notification tray
            Intent resultIntent = new Intent(getApplicationContext(), NavigationActivity.class);
            // resultIntent.putExtra("message", message);
            //  showNotificationMessage(getApplicationContext(), "ANCE", message, "myicon", resultIntent);

            PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), resultIntent, 0);

            if (Build.VERSION.SDK_INT < 16) {
                Notification n = new Notification.Builder(this)
                        .setContentTitle("ANCE")
                        .setContentText(message)
                        .setSmallIcon(R.mipmap.ic_ance)
                        .setContentIntent(pIntent)
                        .setAutoCancel(true).getNotification();
                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                //notificationManager.notify(0, n);
                notificationManager.notify(0, n);
            } else {
                Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_ance);

                Notification n = new Notification.Builder(this)
                        .setContentTitle("ANCE")
                        .setContentText(message)
                        .setSmallIcon(R.mipmap.ic_ance)
                        .setLargeIcon(bm)
                        .setContentIntent(pIntent)
                        .setAutoCancel(true).build();

                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                //notificationManager.notify(0, n);
                notificationManager.notify(0, n);
            }
        }
    }


    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }


}
