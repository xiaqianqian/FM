package com.xqq.fm.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.xqq.fm.MainActivity;
import com.xqq.fm.R;

public class LoginActivity extends AppCompatActivity {

    private EditText etUser;
    private EditText etPassword;
    private EditText etPasswordAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("登录");

        etUser = (EditText) findViewById(R.id.et_userName);
        etPassword = (EditText) findViewById(R.id.et_password);
        etPasswordAgain = (EditText) findViewById(R.id.et_password_again);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                action(view);
                break;
        }
    }

    private void action(View view) {
        String userName = etUser.getText().toString();
        String password = etPassword.getText().toString();
        String passwordAgain = etPasswordAgain.getText().toString();
        if (userName == null || password == null || passwordAgain == null) {
            Snackbar.make(view, "不能为空", Snackbar.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(passwordAgain)) {
            Snackbar.make(view, "两次密码不一样", Snackbar.LENGTH_SHORT).show();
            return;
        }
        // 保存注册账号与密码
        UserDataOperating.saveUser(LoginActivity.this, userName, password);
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        overridePendingTransition(R.anim.anim_activity_in, R.anim.anim_activity_out);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
