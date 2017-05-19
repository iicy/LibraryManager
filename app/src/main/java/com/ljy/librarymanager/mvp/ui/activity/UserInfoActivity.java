package com.ljy.librarymanager.mvp.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ljy.librarymanager.R;
import com.ljy.librarymanager.mvp.base.BaseActivity;
import com.ljy.librarymanager.mvp.entity.User;
import com.ljy.librarymanager.mvp.presenter.UserInfoPresenter;
import com.ljy.librarymanager.mvp.view.UserInfoView;
import com.ljy.librarymanager.widget.ModifyDialog;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by jiayu on 2017/5/11.
 */

public class UserInfoActivity extends BaseActivity implements UserInfoView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.account)
    TextView user_account;
    @BindView(R.id.username)
    TextView user_username;
    @BindView(R.id.permission)
    TextView user_permission;
    @BindView(R.id.id_num)
    TextView user_id_num;
    @BindView(R.id.create_date)
    TextView user_create_date;
    @BindView(R.id.modify)
    TextView modify_username;

    private ProgressDialog pg;
    private String account;
    private String username;
    private String userId;

    @Inject
    UserInfoPresenter mPresenter;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_user_info);
        //注入对象
        mActivityComponent.inject(this);
        mPresenter.attachView(this);
        pg = new ProgressDialog(UserInfoActivity.this);
        pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pg.setMessage(getString(R.string.waiting));
        pg.setCancelable(false);
    }

    @Override
    protected void init() {
        account = getIntent().getStringExtra("account");
        mToolbar.setTitle("个人信息");
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
        modify_username.setOnClickListener(this);
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
            case R.id.modify: {
                final ModifyDialog modifyDialog = new ModifyDialog(UserInfoActivity.this, username);
                modifyDialog.setOnConfirmListener(new ModifyDialog.OnConfirmListener() {
                    @Override
                    public void onConfirmListener() {
                        User user = new User();
                        user.setUsername(modifyDialog.getUsername());
                        mPresenter.modifyUsername(user, userId);
                    }
                });
                modifyDialog.show();
            }
        }
    }

    @Override
    public void getInfo(User user) {
        userId = user.getObjectId();
        username = user.getUsername();
        user_account.setText("账号: " + user.getAccount());
        user_username.setText("用户名: " + user.getUsername());
        user_id_num.setText("身份证号: " + user.getId_num());
        if (user.getPermission().equals("0")) {
            user_permission.setText("身份: " + "管理员");
            ManagerActivity.instance.setUsername(username);
        } else {
            user_permission.setText("身份: " + "普通用户");
            MainActivity.instance.setUsername(username);
        }
        user_create_date.setText("创建时间: " + user.getCreatedAt());
    }

    @Override
    public void modifyUsername() {
        Toast.makeText(UserInfoActivity.this, "修改成功！", Toast.LENGTH_LONG).show();
        mPresenter.getUserInfo(account);
    }

    @Override
    public void showProgress() {
        pg.show();
    }

    @Override
    public void hideProgress() {
        pg.hide();
    }

    @Override
    public void showMsg(String message) {
        Toast.makeText(UserInfoActivity.this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getUserInfo(account);
    }
}
