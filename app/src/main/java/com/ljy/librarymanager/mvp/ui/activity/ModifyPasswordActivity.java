package com.ljy.librarymanager.mvp.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ljy.librarymanager.R;
import com.ljy.librarymanager.common.Constant;
import com.ljy.librarymanager.mvp.base.BaseActivity;
import com.ljy.librarymanager.mvp.entity.User;
import com.ljy.librarymanager.mvp.presenter.ModifyPasswordPresenter;
import com.ljy.librarymanager.mvp.view.ModifyPasswordView;
import com.ljy.librarymanager.utils.Encryption;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by luojiayu on 2017/4/27.
 */

public class ModifyPasswordActivity extends BaseActivity implements ModifyPasswordView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.old_pwd)
    EditText edt_old_pwd;
    @BindView(R.id.new_pwd)
    EditText edt_new_pwd;
    @BindView(R.id.confirm_new_pwd)
    EditText edt_confirm_new_pwd;
    @BindView(R.id.confirm)
    Button bt_confirm;
    private ProgressDialog pg;

    @Inject
    ModifyPasswordPresenter mPresenter;

    private String account;
    private String password;
    private String old_pwd;
    private String new_pwd;
    private String confirm_new_pwd;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_modify_password);
        //注入对象
        mActivityComponent.inject(this);
        mPresenter.attachView(this);
        pg = new ProgressDialog(ModifyPasswordActivity.this);
        pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pg.setMessage("请稍候！");
        pg.setCancelable(false);
        account = getIntent().getStringExtra("account");
        password = getIntent().getStringExtra("password");
    }

    @Override
    protected void init() {
        mToolbar.setTitle("修改密码");
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
        bt_confirm.setOnClickListener(this);
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
            case R.id.confirm: {
                old_pwd = edt_old_pwd.getText().toString();
                new_pwd = edt_new_pwd.getText().toString();
                confirm_new_pwd = edt_confirm_new_pwd.getText().toString();
                if(old_pwd.equals(password)){
                    if(new_pwd.equals(confirm_new_pwd)){
                        User user = new User();
                        user.setPassword(new_pwd);
                        mPresenter.modify(user,account);
                    }else {
                        Toast.makeText(ModifyPasswordActivity.this, "新密码不一致！", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(ModifyPasswordActivity.this, "旧密码错误！", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    @Override
    public void modifySuccess() {
        try {
            Toast.makeText(ModifyPasswordActivity.this, "修改成功！", Toast.LENGTH_LONG).show();
            SharedPreferences sp = this.getSharedPreferences("loginFlag", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("password", Encryption.EncodeAES(new_pwd, Constant.key));
            editor.commit();
            finish();
        } catch (Exception e) {
            e.printStackTrace();
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
        Toast.makeText(ModifyPasswordActivity.this, "修改失败！", Toast.LENGTH_LONG).show();
    }
}
