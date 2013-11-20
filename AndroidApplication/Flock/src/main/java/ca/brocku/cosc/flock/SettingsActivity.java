package ca.brocku.cosc.flock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import ca.brocku.cosc.flock.data.settings.UserDataManager;

/**
 * Created by kubasub on 11/20/2013.
 */
public class SettingsActivity extends Activity {
    private Switch locationSwitch;
    private Button logoutButton, deleteAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Bind Controls
        locationSwitch = (Switch) findViewById(R.id.location_switch);
        logoutButton = (Button) findViewById(R.id.logout_button);
        deleteAccountButton = (Button) findViewById(R.id.delete_button);

        //Bind Handlers
        locationSwitch.setOnCheckedChangeListener(new LocationSettingHandler());
        logoutButton.setOnClickListener(new LogoutHandler());
        deleteAccountButton.setOnClickListener(new DeleteAccountHandler());

    }

    private class LocationSettingHandler implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            //TODO: Add preference to show/hide and ensure that it is refreshed on radar screen
        }
    }

    private class LogoutHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            new UserDataManager(getBaseContext()).removeUserSecret();
            startActivity(new Intent(getBaseContext(), MainActivity.class));
        }
    }

    private class DeleteAccountHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }
}
