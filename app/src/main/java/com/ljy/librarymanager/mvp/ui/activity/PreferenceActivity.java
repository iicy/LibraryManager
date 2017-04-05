package com.ljy.librarymanager.mvp.ui.activity;

import android.os.Bundle;

import com.ljy.librarymanager.R;

/**
 * Created by luojiayu on 2017/3/30.
 */

public class PreferenceActivity extends android.preference.PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
