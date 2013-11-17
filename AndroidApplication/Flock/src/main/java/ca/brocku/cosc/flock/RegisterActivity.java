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
import android.widget.TextView;

public class RegisterActivity extends Activity implements View.OnClickListener {
    FrameLayout loginWrapper;
    Button registerBtn;
    EditText firstname, lastname, username, password;
    TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getActionBar().hide();


        loginWrapper = (FrameLayout)findViewById(R.id.login_expand_wrapper);
        registerBtn = (Button) findViewById(R.id.login_btn);
        firstname = (EditText) findViewById(R.id.firstName_input);
        lastname = (EditText) findViewById(R.id.lastName_input);
        username = (EditText) findViewById(R.id.username_input);
        password = (EditText) findViewById(R.id.password_input);
        error = (TextView) findViewById(R.id.login_errorMsg);

        loginWrapper.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(loginWrapper.getId() == v.getId()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }else if (id == registerBtn.getId()) { //clicked to submit login credentials
            register();
        }
    }

    private void register() {
        if (!firstname.getText().toString().isEmpty() && !lastname.getText().toString().isEmpty() && !username.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) { //if all fields aren't null, try to login
            try {
                //String secret = API.register(firstname.getText().toString(), lastname.getText().toString(), username.getText().toString(), password.getText().toString()); //TODO: call appropriate method to authenticate user and get secret
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
