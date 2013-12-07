package ca.brocku.cosc.flock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import ca.brocku.cosc.flock.data.api.APIResponseHandler;
import ca.brocku.cosc.flock.data.api.actions.UserAPIAction;
import ca.brocku.cosc.flock.data.api.json.models.ErrorModel;
import ca.brocku.cosc.flock.data.api.json.models.user.LoginUserResponseModel;
import ca.brocku.cosc.flock.data.settings.UserDataManager;

public class LoginActivity extends Activity {
    private FrameLayout registerWrapper;
    private Button loginButton;
    private EditText usernameInput;
    private EditText passwordInput;
    private TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getActionBar().hide();

        // Bind Controls
        registerWrapper = (FrameLayout) findViewById(R.id.register_expand_wrapper);
        loginButton = (Button) findViewById(R.id.login_btn);
        usernameInput = (EditText) findViewById(R.id.username_input);
        passwordInput = (EditText) findViewById(R.id.password_input);
        error = (TextView) findViewById(R.id.login_errorMsg);

        // Bind Handlers
        registerWrapper.setOnClickListener(new RegisterIntentHandler());
        loginButton.setOnClickListener(new LoginSubmitHandler());
    }


    /**
     * Handle logging in the user when they click the login button.
     */
    private class LoginSubmitHandler implements Button.OnClickListener {
        @Override
        public void onClick(View v) {
            error.setVisibility(View.INVISIBLE);

            String username = usernameInput.getText().toString();
            String password = passwordInput.getText().toString();

            // Make sure fields aren't empty
            if (!username.isEmpty() && !password.isEmpty()) {
                UserAPIAction.login(username, password, new APIResponseHandler<LoginUserResponseModel>() {
                    @Override
                    public void onResponse(LoginUserResponseModel loginUserResponseModel) {
                        // Store secret and visibility
                        UserDataManager dataManager = new UserDataManager(LoginActivity.this);
                        dataManager.setUserSecret(loginUserResponseModel.secret);
                        dataManager.setUserVisibility(false);

                        // Start Main Activity
                        finish();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onError(ErrorModel errorResponse) {
                        error.setText(errorResponse.message);
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
     * Handles the user clicking on the Register Tab to switch to the Register Activity.
     */
    private class RegisterIntentHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        }
    }
}
