package ca.brocku.cosc.flock;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import ca.brocku.cosc.flock.data.api.FlockAPIResponseHandler;
import ca.brocku.cosc.flock.data.api.actions.FlockUserAPIAction;
import ca.brocku.cosc.flock.data.api.json.models.GenericErrorModel;
import ca.brocku.cosc.flock.data.api.json.models.user.LoginUserResponseModel;
import ca.brocku.cosc.flock.data.settings.UserDataManager;

/**
 * Created by kubasub on 11/20/2013.
 */
public class SettingsActivity extends Activity {
    private Switch locationSwitch;
    private Button logoutButton, deleteAccountButton;
    private Dialog dialog;

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
            finish();
            startActivity(new Intent(getBaseContext(), MainActivity.class));
        }
    }

    private class DeleteAccountHandler implements View.OnClickListener {
        private Button cancelButton, acceptButton;
        private EditText passwordInput;
        private TextView errorMessage;

        @Override
        public void onClick(View v) {
            dialog = new Dialog(SettingsActivity.this);
            dialog.setContentView(R.layout.stub_delete_confirmation);
            dialog.setTitle("Are you sure?");
            dialog.show();

            cancelButton = (Button) dialog.findViewById(R.id.cancel_delete_notification_button);
            acceptButton = (Button) dialog.findViewById(R.id.accept_delete_notification_button);
            passwordInput = (EditText) dialog.findViewById(R.id.delete_confirmation_password_input);
            errorMessage = (TextView) dialog.findViewById(R.id.delete_confirmation_error_message);


            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    passwordInput.setText("");
                    dialog.hide();
                }
            });

            acceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String password = passwordInput.getText().toString();

                    // Make sure fields aren't empty
                    if (!password.isEmpty()) {
                        //TODO: delete the user

                        new UserDataManager(getBaseContext()).removeUserSecret();
                        finish();
                        startActivity(new Intent(SettingsActivity.this, RegisterActivity.class));
                    } else { //inform the user that all fields must be filled in
                        errorMessage.setText("Invalid password.");
                        errorMessage.setVisibility(View.VISIBLE);
                    }
                }
            });

        }
    }
}
