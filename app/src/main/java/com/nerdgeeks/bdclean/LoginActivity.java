package com.nerdgeeks.bdclean;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = (Button) findViewById(R.id.btn_login);
        TextView forgetView = (TextView) findViewById(R.id.tv_forget);
        TextView registerView = (TextView) findViewById(R.id.tv_register);

        forgetView.setOnClickListener(this);
        registerView.setOnClickListener(this);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                intentActivity(this, MainActivity.class);
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

    private void intentActivity(LoginActivity loginActivity, Class activityClass) {
        startActivity(new Intent(loginActivity, activityClass));
        finish();
    }
}
