package com.ljy.librarymanager.mvp.ui.activity;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ljy.librarymanager.R;
import com.ljy.librarymanager.mvp.base.BaseActivity;
import com.ljy.librarymanager.widget.SearchView;

import butterknife.BindView;

/**
 * Created by luojiayu on 2017/3/17.
 */

public class SearchActivity extends BaseActivity{

    @BindView(R.id.search_toolbar)
    Toolbar search_toolbar;
    @BindView(R.id.search_search_view)
    SearchView search_search_view;
    @BindView(R.id.test)
    TextView textView;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_search);
    }

    @Override
    protected void init() {
        search_toolbar.setTitle("");
        setSupportActionBar(search_toolbar);
    }

    @Override
    protected void setListener() {
        search_search_view.setOnSearchListener(new SearchView.OnSearchListener() {
            @Override
            public void onCheckBoxClick(EditText v) {
                v.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
                v.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                                || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                            textView.setText(v.getText().toString());
                            return true;
                        }
                        return false;
                    }
                });
            }
        });
    }

    @Override
    protected void processLogic() {

    }

    @Override
    protected Context getActivityContext() {
        return null;
    }

    @Override
    public void onClick(View v) {

    }

}
