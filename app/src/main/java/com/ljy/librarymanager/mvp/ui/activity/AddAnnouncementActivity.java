package com.ljy.librarymanager.mvp.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ljy.librarymanager.R;
import com.ljy.librarymanager.mvp.base.BaseActivity;
import com.ljy.librarymanager.mvp.entity.Announcement;
import com.ljy.librarymanager.mvp.view.AddAnnouncementView;

import butterknife.BindView;

/**
 * Created by luojiayu on 2017/4/11.
 */

public class AddAnnouncementActivity extends BaseActivity implements AddAnnouncementView {

    @BindView(R.id.add_announcement_toolbar)
    Toolbar mToolbar;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_add_announcement);
        //注入对象
        mActivityComponent.inject(this);
    }

    @Override
    protected void init() {
        mToolbar.setTitle("添加公告");
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));
    }

    @Override
    protected void setListener() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

    @Override
    public void add(Announcement announcement) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showMsg(String message) {

    }
}
