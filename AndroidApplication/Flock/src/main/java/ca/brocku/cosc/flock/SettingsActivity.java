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

import ca.brocku.cosc.flock.data.api.APIResponseHandler;
import ca.brocku.cosc.flock.data.api.actions.UserAPIAction;
import ca.brocku.cosc.flock.data.api.json.models.ErrorModel;
import ca.brocku.cosc.flock.data.api.json.models.ErrorTypes;
import ca.brocku.cosc.flock.data.api.json.models.GenericSuccessModel;
import ca.brocku.cosc.flock.data.exceptions.NoUserSecretException;
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

        locationSwitch.setChecked(new UserDataManager(this).getUserVisibility());

    }

    @Override
    public void onResume() {
        super.onResume();

        locationSwitch.setChecked(new UserDataManager(this).getUserVisibility());
    }

    private class LocationSettingHandler implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            new UserDataManager(SettingsActivity.this).setUserVisibility(isChecked);
        }
    }

    private class LogoutHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            new UserDataManager(SettingsActivity.this).clearUserData();
            finish();
            startActivity(new Intent(SettingsActivity.this, MainActivity.class));
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
                        try {
                            UserAPIAction.delete(new UserDataManager(SettingsActivity.this).getUserSecret(), password, new APIResponseHandler<GenericSuccessModel>() {
                                @Override
                                public void onResponse(GenericSuccessModel genericSuccessModel) {
                                    new UserDataManager(getBaseContext()).clearUserData();
                                    finish();
                                    startActivity(new Intent(SettingsActivity.this, RegisterActivity.class));
                                }

                                @Override
                                public void onError(ErrorModel error) {
                                    if (error.errorType == ErrorTypes.ERROR_TYPE_USER) {
                                        errorMessage.setText(error.message);
                                        errorMessage.setVisibility(View.VISIBLE);
                                    } else {
                                        errorMessage.setText("Could not delete at this time. Try again later.");
                                        errorMessage.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                        } catch (NoUserSecretException e) {
                            finish();
                            startActivity(new Intent(SettingsActivity.this, RegisterActivity.class));
                        }
                    } else { //inform the user that all fields must be filled in
                        errorMessage.setText("Invalid password.");
                        errorMessage.setVisibility(View.VISIBLE);
                    }
                }
            });

        }
    }
}
