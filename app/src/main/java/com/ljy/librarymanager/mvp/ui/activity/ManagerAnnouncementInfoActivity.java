package com.ljy.librarymanager.mvp.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ljy.librarymanager.R;
import com.ljy.librarymanager.mvp.base.BaseActivity;
import com.ljy.librarymanager.mvp.entity.Announcement;
import com.ljy.librarymanager.mvp.presenter.ManagerAnnouncementInfoPresenter;
import com.ljy.librarymanager.mvp.view.ManagerAnnouncementInfoView;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by luojiayu on 2017/4/11.
 */

public class ManagerAnnouncementInfoActivity extends BaseActivity implements ManagerAnnouncementInfoView {

    @BindView(R.id.manager_announcement_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.content)
    EditText content;
    @BindView(R.id.bt_save)
    Button bt_save;
    @BindView(R.id.bt_reset)
    Button bt_reset;
    private ProgressDialog pg;

    private Announcement announcement;

    @Inject
    ManagerAnnouncementInfoPresenter mPresenter;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_manager_announcement_info);
        //注入对象
        mActivityComponent.inject(this);
        mPresenter.attachView(this);
        pg = new ProgressDialog(ManagerAnnouncementInfoActivity.this);
        pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pg.setMessage("请稍候！");
        pg.setCancelable(false);
    }

    @Override
    protected void init() {
        showProgress();
        announcement = (Announcement) getIntent().getSerializableExtra("announcement");
        mToolbar.setTitle("");
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));
        reset();
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
        bt_reset.setOnClickListener(this);
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
            case R.id.bt_save: {
                showProgress();
                announcement.setContent(content.getText().toString());
                announcement.setAccount(announcement.getAccount());
                mPresenter.save(announcement);
                break;
            }
            case R.id.bt_reset: {
                showProgress();
                reset();
                break;
            }
        }
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
        Toast.makeText(ManagerAnnouncementInfoActivity.this, "", Toast.LENGTH_LONG).show();
    }

    @Override
    public void saveSuccess() {
        Toast.makeText(ManagerAnnouncementInfoActivity.this, "保存成功！", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void reset() {
        content.setText(announcement.getContent());
        hideProgress();
    }
}
