package com.ljy.librarymanager.mvp.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;

import com.ljy.librarymanager.R;

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
                SharedPreferences sp = PreferenceActivity.this.getSharedPreferences("loginFlag", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("login", false);
                editor.commit();
                Intent i = new Intent(PreferenceActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
                return false;
            }
        });
    }
}
