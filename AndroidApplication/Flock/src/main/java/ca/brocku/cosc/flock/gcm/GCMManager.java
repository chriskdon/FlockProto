package ca.brocku.cosc.flock.gcm;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

import ca.brocku.cosc.flock.data.api.APIResponseHandler;
import ca.brocku.cosc.flock.data.api.actions.GCMAPIAction;
import ca.brocku.cosc.flock.data.api.json.models.ErrorModel;
import ca.brocku.cosc.flock.data.api.json.models.GenericSuccessModel;
import ca.brocku.cosc.flock.data.exceptions.NoUserSecretException;
import ca.brocku.cosc.flock.data.settings.UserDataManager;

/**
 * Handle Google Cloud Messaging functionality
 *
 * Author: Chris Kellendonk
 * Student #: 4810800
 * Date: 12/7/2013
 */
public class GCMManager {
    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";

    /**
     * Substitute you own sender ID here. This is the project number you got
     * from the API Console, as described in "Getting Started."
     */
    private static final String SENDER_ID = "255176228943";

    public static void getRegistrationIDAsync(Context context) {
        (new GCMRegistrationAsync(context)).execute();
    }

    private static class GCMRegistrationAsync extends AsyncTask<Void, Void, String> {
        private Context context;

        public GCMRegistrationAsync(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(Void... params) {
            String registrationId = null;
            try {
                GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);

                registrationId = gcm.register(SENDER_ID);

                // You should send the registration ID to your server over HTTP,
                // so it can use GCM/HTTP or CCS to send messages to your app.
                // The request to your server should be authenticated if your app
                // is using accounts.
                try {
                    String secret = (new UserDataManager(context)).getUserSecret();
                    GCMAPIAction.register(secret, registrationId, new APIResponseHandler<GenericSuccessModel>() {
                        @Override
                        public void onResponse(GenericSuccessModel genericSuccessModel) {
                            Log.e("TEST", genericSuccessModel.message);
                        }

                        @Override
                        public void onError(ErrorModel result) {
                        // TODO: Handle error
                            Log.e("GCM", result.message);
                        }
                    });
                } catch(NoUserSecretException ex) {
                    Log.e("GCM", ex.getMessage());
                    // TODO: Take me to login
                }


                // For this demo: we don't need to send it because the device
                // will send upstream messages to a server that echo back the
                // message using the 'from' address in the message.

                // Persist the regID - no need to register again.
                        (new UserDataManager(context)).setGCMRegistrationID(registrationId);
            } catch (IOException ex) {
                // TODO: Handle not being able to register
            }

            return registrationId;
        }
    }
}
