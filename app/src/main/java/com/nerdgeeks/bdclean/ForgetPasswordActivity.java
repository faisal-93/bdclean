package com.nerdgeeks.bdclean;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        Button submitButton = (Button) findViewById(R.id.btn_submit);
        Button buttonBack = (Button) findViewById(R.id.btn_back);

        submitButton.setOnClickListener(this);
        buttonBack.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        intentActivity(this, LoginActivity.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_submit:
                Toast.makeText(this, "Email Submitted", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_back:
                intentActivity(this, LoginActivity.class);
                break;
            default:
                break;
        }
    }

    private void intentActivity(ForgetPasswordActivity activity, Class activityClass) {
        startActivity(new Intent(activity, activityClass));
        finish();
    }
}
