package com.nerdgeeks.bdclean;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button registerButton = (Button) findViewById(R.id.btn_reg);
        Button cancelButton = (Button) findViewById(R.id.btn_cancel);

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
                Toast.makeText(this, "Registration Submitted", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_cancel:
                intentActivity(this, LoginActivity.class);
                break;
            default:
                break;
        }
    }

    private void intentActivity(RegisterActivity activity, Class activityClass) {
        startActivity(new Intent(activity, activityClass));
        finish();
    }
}
