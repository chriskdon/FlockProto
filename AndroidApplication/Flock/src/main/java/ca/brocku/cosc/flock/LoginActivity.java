package ca.brocku.cosc.flock;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LoginActivity extends Activity implements View.OnClickListener {
    private FrameLayout registerWrapper;
    private Button loginBtn;
    private EditText username;
    private EditText password;
    private TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getActionBar().hide();

        registerWrapper = (FrameLayout) findViewById(R.id.register_expand_wrapper);
        loginBtn = (Button) findViewById(R.id.login_btn);
        username = (EditText) findViewById(R.id.username_input);
        password = (EditText) findViewById(R.id.password_input);
        error = (TextView) findViewById(R.id.login_errorMsg);

        registerWrapper.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == registerWrapper.getId()) { //clicked Register option
            finish();
            startActivity(new Intent(this, RegisterActivity.class));
        } else if (id == loginBtn.getId()) { //clicked to submit login credentials
            login();
        }
    }

    private void login() {
        if (!username.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
            try {
                //String secret = API.login(username.getText().toString(), password.getText().toString()); //TODO: call appropriate method to authenticate user and get secret
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
}
