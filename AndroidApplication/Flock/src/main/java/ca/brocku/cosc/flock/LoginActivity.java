package ca.brocku.cosc.flock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import ca.brocku.cosc.flock.data.api.FlockAPIResponseHandler;
import ca.brocku.cosc.flock.data.api.actions.FlockUserAPIAction;
import ca.brocku.cosc.flock.data.api.json.models.GenericErrorModel;
import ca.brocku.cosc.flock.data.api.json.models.user.LoginUserResponseModel;
import ca.brocku.cosc.flock.data.settings.UserDataManager;

public class LoginActivity extends Activity {
    private FrameLayout registerWrapper;
    private Button loginBtn;
    private EditText usernameInput;
    private EditText passwordInput;
    private TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getActionBar().hide();

        registerWrapper = (FrameLayout) findViewById(R.id.register_expand_wrapper);
        loginBtn = (Button) findViewById(R.id.login_btn);
        usernameInput = (EditText) findViewById(R.id.username_input);
        passwordInput = (EditText) findViewById(R.id.password_input);
        error = (TextView) findViewById(R.id.login_errorMsg);

        registerWrapper.setOnClickListener(new RegisterSwitchViewHandler());
        loginBtn.setOnClickListener(new LoginSubmitHandler());
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
                FlockUserAPIAction.login(username, password, new FlockAPIResponseHandler<LoginUserResponseModel>() {
                    @Override
                    public void onResponse(LoginUserResponseModel loginUserResponseModel) {
                        // Store secret
                        (new UserDataManager(LoginActivity.this)).setUserSecret(loginUserResponseModel.secret);

                        // Start Main Activity
                        finish();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onError(GenericErrorModel errorResponse) {
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
     * Switch to the register new user view.
     */
    private class RegisterSwitchViewHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        }
    }
}
