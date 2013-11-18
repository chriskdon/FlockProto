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

}
