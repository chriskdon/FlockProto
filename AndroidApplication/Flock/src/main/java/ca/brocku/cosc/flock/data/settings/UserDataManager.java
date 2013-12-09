package ca.brocku.cosc.flock.data.settings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import ca.brocku.cosc.flock.LoginActivity;
import ca.brocku.cosc.flock.R;
import ca.brocku.cosc.flock.data.api.APIResponseHandler;
import ca.brocku.cosc.flock.data.api.actions.LocationAPIAction;
import ca.brocku.cosc.flock.data.api.json.models.ErrorModel;
import ca.brocku.cosc.flock.data.api.json.models.GenericSuccessModel;
import ca.brocku.cosc.flock.data.exceptions.NoUserSecretException;
import ca.brocku.cosc.flock.utils.TryCallback;

/**
 * Store and retrieve application specific settings.
 *
 * Author: Chris Kellendonk
 * Student #: 4810800
 * Date: 11/18/2013
 */
public class UserDataManager {
    private SharedPreferences prefs;
    private SharedPreferences.Editor prefsEditor;

    public UserDataManager(Context context) {
        prefs = context.getSharedPreferences(context.getString(R.string.sharedPrefsKey),
                                             context.MODE_PRIVATE);

        prefsEditor = prefs.edit();
    }

    /**
     * Set a user's secret value.
     *
     * @param secret
     */
    public void setUserSecret(String secret) {
        prefsEditor.putString("SECRET", secret);
        prefsEditor.commit();
    }

    /**
     * Try and get the 'secret' key for the user.
     * @return
     * @throws NoUserSecretException
     */
    public String getUserSecret() throws NoUserSecretException {
        String secret = prefs.getString("SECRET", null);

        if(secret == null) {
            throw new NoUserSecretException();
        }

        return secret;
    }

    /**
     * Remove the user's secret token. They must login
     * again to get a new token.
     */
    public void removeUserSecret() {
        prefsEditor.remove("SECRET");
        prefsEditor.commit();
    }

    /**
     * @return True if there is a secret token set for the user.
     */
    public boolean hasUserSecret() {
        try {
            return !getUserSecret().isEmpty();
        } catch(Exception ex) {
            return false;
        }
    }

    /**
     * Sets whether a user can be seen by his/her friends
     *
     * @param isVisible whether or not the use is visible
     */
    public void setUserVisibility(final boolean isVisible, final TryCallback tryCallback) {
        if(isVisible) {
            prefsEditor.putBoolean("VISIBLE", true);
            prefsEditor.commit();

            if(tryCallback != null) {
                tryCallback.success();
            }
        } else {
            // Update the server so they are hidden
            try {
                String secret = getUserSecret();

                LocationAPIAction.hide(secret, new APIResponseHandler<GenericSuccessModel>() {
                    /**
                     * Made them invisible
                     *
                     * @param genericSuccessModel The result data from the server.
                     */
                    @Override
                    public void onResponse(GenericSuccessModel genericSuccessModel) {
                        prefsEditor.putBoolean("VISIBLE", false);
                        prefsEditor.commit();

                        if(tryCallback != null) {
                            tryCallback.success();
                        }
                    }

                    @Override
                    public void onError(ErrorModel result) {

                    }
                });
            } catch(NoUserSecretException ex) {
                if(tryCallback != null) {
                    tryCallback.failure();
                }
            }
        }
    }

    /**
     * Returns whether or not a user can be seen by his/her friends
     */
    public boolean getUserVisibility() {
        return prefs.getBoolean("VISIBLE", false);
    }

    /**
     * Remove all of a users local data. Useful upon deauthentication.
     */
    public void clearUserData() {
        prefsEditor.clear();
        prefsEditor.commit();
    }

    /**
     * Get google cloud messaging registration ID
     * @return
     */
    public String getGCMRegistrationID() {
        return prefs.getString("GCM_REGISTRATION_ID", "");
    }

    /**
     * Set the Google Cloud Messaging registration ID
     * @param value
     */
    public void setGCMRegistrationID(String value) {
        prefsEditor.putString("GCM_REGISTRATION_ID", value);
        prefsEditor.commit();
    }
}
