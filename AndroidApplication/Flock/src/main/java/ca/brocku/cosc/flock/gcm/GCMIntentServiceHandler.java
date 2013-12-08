package ca.brocku.cosc.flock.gcm;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * Author: Chris Kellendonk
 * Student #: 4810800
 * Date: 12/7/2013
 */
public class GCMIntentServiceHandler extends IntentService {
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager notificationmanager;
    NotificationCompat.Builder builder;

    public GCMIntentServiceHandler() {
        super("GCMIntentServiceHandler");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);

        if(!extras.isEmpty()) {
            if(GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                // ERROR
            } else if(GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                // DELETED
            } else if(GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                // TODO: Do something with message data
                String action = extras.getString("action");

                if(action.equals("FLOCK_NEW_FRIEND_REQUEST")) {
                    // TODO: Update friends Table
                } else if(action.equals("FLOCK_NEW_FRIEND_LOCATION")) {
                    // TODO: Update friends location
                }
            }
        }

        GCMBroadcastReceiver.completeWakefulIntent(intent);
    }
}
