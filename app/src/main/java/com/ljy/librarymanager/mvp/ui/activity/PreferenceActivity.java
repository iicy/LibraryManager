package com.ljy.librarymanager.mvp.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;

import com.ljy.librarymanager.R;
import com.ljy.librarymanager.widget.DeleteDialog;

/**
 * Created by luojiayu on 2017/3/30.
 */

public class PreferenceActivity extends android.preference.PreferenceActivity {

    private Preference modify_password;
    private Preference logout;

    private String account;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        account = getIntent().getStringExtra("account");
        password = getIntent().getStringExtra("password");

        logout = findPreference("logout");
        logout.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                DeleteDialog deleteDialog = new DeleteDialog(PreferenceActivity.this,"确定注销该账号吗？");
                deleteDialog.show();
                deleteDialog.setOnConfirmListener(new DeleteDialog.OnConfirmListener() {
                    @Override
                    public void onConfirmListener() {
                        SharedPreferences sp = PreferenceActivity.this.getSharedPreferences("loginFlag", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putBoolean("login", false);
                        editor.commit();
                        Intent i = new Intent(PreferenceActivity.this,LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
                return true;
            }
        });

        modify_password = findPreference("modify_password");
        modify_password.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(PreferenceActivity.this,ModifyPasswordActivity.class);
                intent.putExtra("account",account);
                intent.putExtra("password",password);
                startActivity(intent);
                return true;
            }
        });
    }
}
