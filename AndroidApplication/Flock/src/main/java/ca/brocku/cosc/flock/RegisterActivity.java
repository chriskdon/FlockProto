package ca.brocku.cosc.flock;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import ca.brocku.cosc.flock.data.api.actions.FlockUserAPIAction;
import ca.brocku.cosc.flock.data.api.IFlockAPIResponse;
import ca.brocku.cosc.flock.data.api.json.models.GenericErrorModel;
import ca.brocku.cosc.flock.data.api.json.models.user.LoginUserResponseModel;
import ca.brocku.cosc.flock.data.api.json.models.user.RegisterUserRequestModel;

public class RegisterActivity extends Activity {
    private FrameLayout loginWrapper;
    private Button registerButton; // Button to register a new user
    private EditText firstNameInput, lastNameInput, usernameInput, passwordInput, emailInput;
    private TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getActionBar().hide();

        // Bind Controls
        loginWrapper = (FrameLayout)findViewById(R.id.login_expand_wrapper);
        registerButton = (Button)findViewById(R.id.submit_registration_button);
        firstNameInput = (EditText)findViewById(R.id.firstName_input);
        lastNameInput = (EditText)findViewById(R.id.lastName_input);
        usernameInput = (EditText)findViewById(R.id.username_input);
        passwordInput = (EditText)findViewById(R.id.password_input);
        emailInput = (EditText)findViewById(R.id.email_input);
        error = (TextView) findViewById(R.id.login_errorMsg);

        // Bind Handlers
        registerButton.setOnClickListener(new RegisterSubmitHandler());
        loginWrapper.setOnClickListener(new LoginIntentHandler());
    }

    /**
     * Handles the user clicking the submit button when trying to register
     * as a new user.
     */
    private class RegisterSubmitHandler implements Button.OnClickListener {
        @Override
        public void onClick(View v) {
            error.setVisibility(View.INVISIBLE);

            //ensure fields are not null
            if (!firstNameInput.getText().toString().isEmpty() && !lastNameInput.getText().toString().isEmpty() && !emailInput.getText().toString().isEmpty() && !usernameInput.getText().toString().isEmpty() && !passwordInput.getText().toString().isEmpty()) {

                RegisterUserRequestModel newUser =
                        new RegisterUserRequestModel(usernameInput.getText().toString(),
                                                     firstNameInput.getText().toString(),
                                                     lastNameInput.getText().toString(),
                                                     emailInput.getText().toString(),
                                                     passwordInput.getText().toString());

                FlockUserAPIAction.register(newUser, new IFlockAPIResponse<LoginUserResponseModel>() {
                    @Override
                    public void onResponse(LoginUserResponseModel loginUserResponseModel) {
                        SharedPreferences prefs = getSharedPreferences(getString(R.string.sharedPrefsKey), MODE_PRIVATE);
                        SharedPreferences.Editor prefsEditor = prefs.edit();
                        prefsEditor.putString("SECRET", loginUserResponseModel.secret);
                        finish();
                        startActivity(new Intent(getBaseContext(), MainActivity.class));
                    }

                    @Override
                    public void onError(GenericErrorModel result) {
                        error.setText(result.message);
                        error.setVisibility(View.VISIBLE);
                    }
                });

            } else { //inform the user that all fields must be filled in
                error.setText("Please fill in all fields.");
                error.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * Handles the user clicking the Login tab to switch to the Login Activity.
     */
    private class LoginIntentHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
            startActivity(new Intent(getBaseContext(), LoginActivity.class));
        }
    }
}