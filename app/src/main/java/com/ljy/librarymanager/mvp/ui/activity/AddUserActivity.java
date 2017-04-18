package com.ljy.librarymanager.mvp.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.ljy.librarymanager.R;
import com.ljy.librarymanager.mvp.base.BaseActivity;
import com.ljy.librarymanager.mvp.entity.Announcement;
import com.ljy.librarymanager.mvp.entity.User;
import com.ljy.librarymanager.mvp.presenter.AddAnnouncementPresenter;
import com.ljy.librarymanager.mvp.presenter.AddUserPresenter;
import com.ljy.librarymanager.mvp.view.AddAnnouncementView;
import com.ljy.librarymanager.mvp.view.AddUserView;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by luojiayu on 2017/4/11.
 */

public class AddUserActivity extends BaseActivity implements AddUserView {

    @BindView(R.id.add_user_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.username)
    EditText et_username;
    @BindView(R.id.account)
    EditText et_account;
    @BindView(R.id.password)
    EditText et_password;
    @BindView(R.id.manager)
    RadioButton rb_manager;
    @BindView(R.id.simple_user)
    RadioButton rb_simple_user;
    @BindView(R.id.save)
    Button bt_save;
    private ProgressDialog pg;
    private String username;
    private String account;
    private String password;

    @Inject
    AddUserPresenter mPresenter;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_add_user);
        //注入对象
        mActivityComponent.inject(this);
        mPresenter.attachView(this);
        pg = new ProgressDialog(AddUserActivity.this);
        pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pg.setMessage("正在保存！");
        pg.setCancelable(false);
    }

    @Override
    protected void init() {
        mToolbar.setTitle("添加用户");
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
                username = et_username.getText().toString();
                account = et_account.getText().toString();
                password = et_password.getText().toString();
                User user = new User();
                user.setUsername(username);
                user.setAccount(account);
                user.setPassword(password);
                if(rb_manager.isChecked())
                    user.setPermission("0");
                else if(rb_simple_user.isChecked())
                    user.setPermission("1");
                mPresenter.add(user);
                break;
            }
        }
    }

    @Override
    public void add() {
        Toast.makeText(AddUserActivity.this, "保存成功！", Toast.LENGTH_LONG).show();
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
        Toast.makeText(AddUserActivity.this, "保存失败！该用户已存在！", Toast.LENGTH_LONG).show();
    }
}
