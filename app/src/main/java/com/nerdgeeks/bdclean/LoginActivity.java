package com.nerdgeeks.bdclean;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.nerdgeeks.bdclean.app.AppConfig;
import com.nerdgeeks.bdclean.app.AppController;
import com.nerdgeeks.bdclean.helper.SQLiteHandler;
import com.nerdgeeks.bdclean.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private EditText email;
    private EditText password;
    private SessionManager session;
    private SQLiteHandler db;
    private ProgressBar pBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        Button loginButton = (Button) findViewById(R.id.btn_login);
        TextView forgetView = (TextView) findViewById(R.id.tv_forget);
        TextView registerView = (TextView) findViewById(R.id.tv_register);
        pBar = (ProgressBar) findViewById(R.id.progressBar);

        forgetView.setOnClickListener(this);
        registerView.setOnClickListener(this);
        loginButton.setOnClickListener(this);

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());
        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                String userEmail = email.getText().toString().trim();
                String userPassword = password.getText().toString().trim();

                if (!userEmail.isEmpty() && !userPassword.isEmpty()) {
                    // login user
                    checkLogin(userEmail, userPassword);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),"Email & password is required.", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.tv_forget:
                intentActivity(this, ForgetPasswordActivity.class);
                finish();
                break;
            case R.id.tv_register:
                intentActivity(this, RegisterActivity.class);
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * function to verify login details in mysql db
     * */
    private void checkLogin(final String email, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pBar.setVisibility(View.VISIBLE);

        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());

                pBar.setVisibility(View.INVISIBLE);

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean success = jObj.getBoolean("success");

                    // Check for error node in json
                    if (success) {
                        // user successfully logged in
                        // Create login session
                        session.setLogin(true);

                        // Now store the user in SQLite

                        JSONObject user = jObj.getJSONObject("user");
                        String uid = user.getString("id");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String password = user.getString("password");

                        // Inserting row in users table
                        db.addUser(uid, name, email, password);

                        // Launch main activity
                        Intent intent = new Intent(LoginActivity.this,
                                MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                pBar.setVisibility(View.INVISIBLE);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void intentActivity(LoginActivity loginActivity, Class activityClass) {
        startActivity(new Intent(loginActivity, activityClass));
        finish();
    }
}
