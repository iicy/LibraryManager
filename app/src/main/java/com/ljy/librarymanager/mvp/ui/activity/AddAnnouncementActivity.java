package com.ljy.librarymanager.mvp.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ljy.librarymanager.R;
import com.ljy.librarymanager.mvp.base.BaseActivity;
import com.ljy.librarymanager.mvp.entity.Announcement;
import com.ljy.librarymanager.mvp.presenter.AddAnnouncementPresenter;
import com.ljy.librarymanager.mvp.view.AddAnnouncementView;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by luojiayu on 2017/4/11.
 */

public class AddAnnouncementActivity extends BaseActivity implements AddAnnouncementView {

    @BindView(R.id.add_announcement_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.content)
    EditText et_content;
    @BindView(R.id.save)
    Button bt_save;
    private ProgressDialog pg;
    private String content;
    private String account;

    @Inject
    AddAnnouncementPresenter mPresenter;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_add_announcement);
        //注入对象
        mActivityComponent.inject(this);
        mPresenter.attachView(this);
        pg = new ProgressDialog(AddAnnouncementActivity.this);
        pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pg.setMessage(getString(R.string.waiting));
        pg.setCancelable(false);
    }

    @Override
    protected void init() {
        mToolbar.setTitle("添加公告");
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));
        Intent intent = getIntent();
        account = intent.getStringExtra("account");
    }

    @Override
    protected void setListener() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bt_save.setOnClickListener(this);
    }

    @Override
    protected void processLogic() {

    }

    @Override
    protected Context getActivityContext() {
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save: {
                showProgress();
                content = et_content.getText().toString();
                Announcement announcement = new Announcement();
                announcement.setAccount(account);
                announcement.setContent(content);
                mPresenter.add(announcement);
                break;
            }
        }
    }

    @Override
    public void add() {
        Toast.makeText(AddAnnouncementActivity.this, "保存成功！", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void showProgress() {
        pg.show();
    }

    @Override
    public void hideProgress() {
        pg.dismiss();
    }

    @Override
    public void showMsg(String message) {
        Toast.makeText(AddAnnouncementActivity.this, "保存失败！", Toast.LENGTH_LONG).show();
    }
}
