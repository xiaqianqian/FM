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

public class LoginAgainActivity extends AppCompatActivity {

    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_again);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("登录");

        etPassword = (EditText) findViewById(R.id.et_password);
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
        String password = etPassword.getText().toString();
        if (password == null) {
            Snackbar.make(view, "密码不能为空", Snackbar.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(UserDataOperating.getPassword(LoginAgainActivity.this))) {
            Snackbar.make(view, "密码错误，请重新输入", Snackbar.LENGTH_SHORT).show();
            return;
        }
        startActivity(new Intent(LoginAgainActivity.this, MainActivity.class));
        overridePendingTransition(R.anim.anim_activity_in, R.anim.anim_activity_out);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
