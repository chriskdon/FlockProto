package ca.brocku.cosc.flock.data.settings;

import android.content.Context;
import android.content.SharedPreferences;

import ca.brocku.cosc.flock.R;
import ca.brocku.cosc.flock.data.exceptions.NoUserSecretException;

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

    //TODO: possibly delete removeUserSecret as it may be replaced by clearUserData method
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
     * @param visibility whether or not the use is visible
     */
    public void setUserVisibility(boolean visibility) {
        prefsEditor.putBoolean("VISIBLE", visibility);
        prefsEditor.commit();
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
}
