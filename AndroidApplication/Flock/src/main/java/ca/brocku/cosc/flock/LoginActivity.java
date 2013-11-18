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

import ca.brocku.cosc.flock.data.api.json.models.user.LoginUserRequestModel;

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

        registerWrapper = (FrameLayout) findViewById(R.id.register_expand_wrapper);
        loginButton = (Button) findViewById(R.id.login_btn);
        usernameInput = (EditText) findViewById(R.id.username_input);
        passwordInput = (EditText) findViewById(R.id.password_input);
        error = (TextView) findViewById(R.id.login_errorMsg);

        registerWrapper.setOnClickListener(new RegisterIntentHandler());
        loginButton.setOnClickListener(new LoginSubmitHandler());
    }


    /**
     * Handles the user clicking the submit button to Login to the application.
     */
    private class LoginSubmitHandler implements Button.OnClickListener {
        @Override
        public void onClick(View v) {
            if (!usernameInput.getText().toString().isEmpty() && !passwordInput.getText().toString().isEmpty()) {

                //TODO: CODE TO AUTHENTICATE USER/THROW ERROR
//                LoginUserRequestModel user =
//                        new LoginUserRequestModel(usernameInput.getText().toString(),
//                                                  passwordInput.getText().toString());

                SharedPreferences prefs = getSharedPreferences(getString(R.string.sharedPrefsKey), MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = prefs.edit();
                //TODO: save the secret
                //prefsEditor.putString("SECRET", loginUserResponseModel.secret); //save secret

                finish();
                startActivity(new Intent(getBaseContext(), MainActivity.class));

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
            startActivity(new Intent(getBaseContext(), RegisterActivity.class));
        }
    }
}
