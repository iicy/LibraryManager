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
import com.ljy.librarymanager.mvp.view.ManagerUserInfoView;

import butterknife.BindView;

/**
 * Created by luojiayu on 2017/4/11.
 */

public class ManagerUserInfoActivity extends BaseActivity implements ManagerUserInfoView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.user_name)
    TextView user_name;
    @BindView(R.id.user_account)
    TextView user_account;
    @BindView(R.id.user_password)
    TextView user_password;
    @BindView(R.id.user_permission)
    TextView user_permission;
    @BindView(R.id.user_id_num)
    TextView user_id_num;
    @BindView(R.id.user_created_date)
    TextView user_created_date;
    private ProgressDialog pg;

    private User user;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_manager_user_info);
        //注入对象
        mActivityComponent.inject(this);
        pg = new ProgressDialog(ManagerUserInfoActivity.this);
        pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pg.setMessage(getString(R.string.waiting));
        pg.setCancelable(false);
    }

    @Override
    protected void init() {
        showProgress();
        user = (User) getIntent().getSerializableExtra("user");
        mToolbar.setTitle("");
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));
        user_name.setText("用户名：" + user.getUsername());
        user_account.setText("账号：" + user.getAccount());
        user_password.setText("密码：" + user.getPassword());
        user_id_num.setText("身份证号：" + user.getId_num());
        if (user.getPermission().equals("0")) {
            user_permission.setText("权限：管理员");
        } else {
            user_permission.setText("权限：普通用户");
        }
        user_created_date.setText("创建日期：" + user.getCreatedAt());
        hideProgress();
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
        return this;
    }

    @Override
    public void onClick(View v) {
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
        Toast.makeText(ManagerUserInfoActivity.this, "", Toast.LENGTH_LONG).show();
    }
}
