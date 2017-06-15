package com.xqq.fm.splash;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.xqq.fm.R;
import com.xqq.fm.login.LoginActivity;
import com.xqq.fm.login.LoginAgainActivity;
import com.xqq.fm.login.UserDataOperating;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private TextView mTextViewVersion;// 当前版本号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mTextViewVersion = (TextView) findViewById(R.id.textView_version);
        mTextViewVersion.setText(getAppVersion());

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                action();
            }
        }, 2000);
    }

    private void action() {
        Intent intent;
        if ("".equals(UserDataOperating.getUserName(SplashActivity.this))) {
            intent = new Intent(SplashActivity.this, LoginActivity.class);
        } else {
            intent = new Intent(SplashActivity.this, LoginAgainActivity.class);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.anim_activity_in, R.anim.anim_activity_out);
    }

    private String getAppVersion() {
        String localVersion = "1.0";
        try {
            PackageInfo packageInfo = this
                    .getPackageManager()
                    .getPackageInfo(this.getPackageName(), 0);
            localVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
