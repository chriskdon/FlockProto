package ca.brocku.cosc.flock.gcm;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Handles receiving downstream push notifications from the GCM server.
 *
 * Author: Chris Kellendonk
 * Student #: 4810800
 * Date: 12/7/2013
 */
public class GCMBroadcastReceiver extends WakefulBroadcastReceiver {
    /**
     * Fired when a new notification comes in.
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        ComponentName comp = new ComponentName(context.getPackageName(),
                GCMIntentServiceHandler.class.getName());

        startWakefulService(context, intent.setComponent(comp));
        setResultCode(Activity.RESULT_OK);
    }
}
