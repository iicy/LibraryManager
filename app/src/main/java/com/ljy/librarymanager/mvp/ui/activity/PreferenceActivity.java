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

    private Preference logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

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
                return false;
            }
        });
    }
}
