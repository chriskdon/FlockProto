package ca.brocku.cosc.flock;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;

import com.fasterxml.jackson.databind.ObjectMapper;

import ca.brocku.cosc.flock.data.ApplicationSettings;
import ca.brocku.cosc.flock.data.api.json.models.GenericSuccessModel;

public class RegisterActivity extends Activity {
    private Button submitButton; // Button to register a new user
    private EditText firstNameInput, lastNameInput, usernameInput, passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getActionBar().hide();

        // Initialze Application Settings
        ApplicationSettings.initialize(getBaseContext());

        // Bind Controls
        submitButton = (Button)findViewById(R.id.submit_registration_button);
        firstNameInput = (EditText)findViewById(R.id.firstName_input);
        lastNameInput = (EditText)findViewById(R.id.lastName_input);
        usernameInput = (EditText)findViewById(R.id.username_input);
        passwordInput = (EditText)findViewById(R.id.password_input);

        // Bind Handlers
        submitButton.setOnClickListener(new RegisterSubmitHandler());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Handles the user clicking the submit button when trying to register
     * as a new user.
     */
    private class RegisterSubmitHandler implements Button.OnClickListener {
        @Override
        public void onClick(View v) {
            // TODO: Register a new user
            firstNameInput.setText((new GenericSuccessModel("Test")).toJsonString());
        }
    }
}
