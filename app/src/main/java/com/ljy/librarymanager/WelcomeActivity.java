package com.ljy.librarymanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ljy.librarymanager.common.Constant;
import com.ljy.librarymanager.mvp.ui.activity.LoginActivity;
import com.ljy.librarymanager.mvp.ui.activity.MainActivity;
import com.ljy.librarymanager.mvp.ui.activity.ManagerActivity;
import com.ljy.librarymanager.utils.Encryption;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sp = WelcomeActivity.this.getSharedPreferences("loginFlag", MODE_PRIVATE);
                if (sp.getBoolean("login", false)) {
                    try {
                        Intent i = null;
                        String account = sp.getString("account", "");
                        String password  = Encryption.DeCodeAES(sp.getString("password", ""), Constant.key);
                        Log.e("encryption","de:"+password);
                        String permission = sp.getString("permission", "");
                        String username = sp.getString("username","");
                        if (permission.equals("0")) {
                            i = new Intent(WelcomeActivity.this, ManagerActivity.class);
                        } else if (!permission.equals("")) {
                            i = new Intent(WelcomeActivity.this, MainActivity.class);
                        } else {
                            finish();
                        }
                        i.putExtra("account", account);
                        i.putExtra("password", password);
                        i.putExtra("permission", permission);
                        i.putExtra("username",username);
                        startActivity(i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                }
                finish();
            }
        }, 2000);
    }
}
