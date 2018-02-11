package com.nerdgeeks.bdclean;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.DatePicker;
import com.applandeo.materialcalendarview.builders.DatePickerBuilder;
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.nerdgeeks.bdclean.app.AppConfig;
import com.nerdgeeks.bdclean.app.AppController;
import com.nerdgeeks.bdclean.helper.SQLiteHandler;
import com.nerdgeeks.bdclean.helper.SessionManager;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, MaterialSpinner.OnItemSelectedListener {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    private SessionManager session;
    private SQLiteHandler db;
    private EditText fullname;
    private EditText email;
    private EditText password;
    private ProgressBar pBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //get string-arrays from resource value
        String[] districts = getResources().getStringArray(R.array.districts_of_bd);

        //init/casting layout components
        fullname = (EditText) findViewById(R.id.fullname);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        pBar = (ProgressBar) findViewById(R.id.progressBar);
        Button registerButton = (Button) findViewById(R.id.btn_reg);
        Button cancelButton = (Button) findViewById(R.id.btn_cancel);
        ImageView datePicker = (ImageView) findViewById(R.id.datePicker);
        MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner);
        spinner.setItems(districts);
        spinner.setBackgroundColor(Color.WHITE);
        spinner.setArrowColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        //init event listener
        datePicker.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        intentActivity(this, LoginActivity.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_reg:
                String userName = fullname.getText().toString().trim();
                String userEmail = email.getText().toString().trim();
                String userPassword = password.getText().toString().trim();

                if (!userName.isEmpty() && !userEmail.isEmpty() && !userPassword.isEmpty()) {

                    registerUser(userName, userEmail, userPassword);
                }
                else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                }
                break;
            case R.id.btn_cancel:
                intentActivity(this, LoginActivity.class);
                break;
            case R.id.datePicker:
                onActionOpenDatePicker();
                break;
            default:
                break;
        }
    }

    private void onActionOpenDatePicker() {
        DatePickerBuilder builder = new DatePickerBuilder(this, listener)
                .pickerType(CalendarView.ONE_DAY_PICKER)
                .headerColor(R.color.colorPrimaryDark)
                .headerLabelColor(R.color.cardview_light_background);

        DatePicker datePicker = builder.build();
        datePicker.show();
    }

    private OnSelectDateListener listener = new OnSelectDateListener() {
        @Override
        public void onSelect(List<Calendar> calendars) {
            Toast.makeText(RegisterActivity.this, "" + calendars.get(0).getTime(), Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */
    private void registerUser(final String name, final String email,
                              final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pBar.setVisibility(View.VISIBLE);

        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                pBar.setVisibility(View.INVISIBLE);

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean success = jObj.getBoolean("success");
                    if (success) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite

                        JSONObject user = jObj.getJSONObject("user");
                        String uid = user.getString("id");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String password = user.getString("password");

                        Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();

                        // Launch login activity
                        Intent intent = new Intent(
                                RegisterActivity.this,
                                LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                pBar.setVisibility(View.INVISIBLE);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {

                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void intentActivity(RegisterActivity activity, Class activityClass) {
        startActivity(new Intent(activity, activityClass));
        finish();
    }

    @Override
    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {

    }
}
