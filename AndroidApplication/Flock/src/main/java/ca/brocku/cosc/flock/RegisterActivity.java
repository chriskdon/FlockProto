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

public class RegisterActivity extends Activity implements View.OnClickListener {
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
        loginWrapper.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(loginWrapper.getId() == v.getId()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }else if (id == registerButton.getId()) { //clicked to submit login credentials
            register();
        }
    }

    private void register() {
        if (!firstNameInput.getText().toString().isEmpty() && !lastNameInput.getText().toString().isEmpty() && !usernameInput.getText().toString().isEmpty() && !passwordInput.getText().toString().isEmpty()) { //if all fields aren't null, try to login
            try {
                //TODO: call appropriate method to register user and get secret
                //String secret = API.register(firstNameInput.getText().toString(), lastNameInput.getText().toString(), usernameInput.getText().toString(), passwordInput.getText().toString());
                SharedPreferences prefs = getSharedPreferences(getString(R.string.sharedPrefsKey), MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = prefs.edit();
                //prefsEditor.putString("SECRET", secret);
                finish();
                startActivity(new Intent(this, MainActivity.class));
            } catch (Exception e) { //TODO: catch appropriate exception
                error.setText(e.getMessage());
                error.setVisibility(View.VISIBLE);
            }
        } else { //inform the user that all fields must be filled in
            error.setText("Please fill in all fields.");
            error.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Handles the user clicking the submit button when trying to register
     * as a new user.
     */
    private class RegisterSubmitHandler implements Button.OnClickListener {
        @Override
        public void onClick(View v) {
            error.setVisibility(View.INVISIBLE);

            RegisterUserRequestModel newUser =
                    new RegisterUserRequestModel(usernameInput.getText().toString(),
                                                 firstNameInput.getText().toString(),
                                                 lastNameInput.getText().toString(),
                                                 emailInput.getText().toString(),
                                                 passwordInput.getText().toString());

            FlockUserAPIAction.register(newUser, new IFlockAPIResponse<LoginUserResponseModel>() {
                @Override
                public void onResponse(LoginUserResponseModel loginUserResponseModel) {
                    firstNameInput.setText(loginUserResponseModel.secret);
                }

                @Override
                public void onError(GenericErrorModel result) {
                    error.setText(result.message);
                    error.setVisibility(View.VISIBLE);
                }
            });
        }
    }
}