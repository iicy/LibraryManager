package com.ljy.librarymanager.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ljy.librarymanager.R;
import com.ljy.librarymanager.mvp.base.BaseActivity;
import com.ljy.librarymanager.mvp.presenter.LoginPresenter;
import com.ljy.librarymanager.mvp.view.LoginView;
import com.ljy.librarymanager.utils.CheckCodeUtil;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by jiayu on 2017/3/11.
 */

public class LoginActivity extends BaseActivity implements LoginView {

    @BindView(R.id.login_accounts)
    EditText login_accounts;
    @BindView(R.id.login_password)
    EditText login_password;
    @BindView(R.id.login_checkcode_edittext)
    EditText login_checkcode_edittext;
    @BindView(R.id.login_checkcode_view)
    ImageView login_checkcode_view;
    @BindView(R.id.login_button)
    Button login_button;

    @Inject
    LoginPresenter loginPresenter;

    private Integer result;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_login);
        //注入对象
        mActivityComponent.inject(this);
        //绑定presenter
        loginPresenter.attachView(this);
    }

    @Override
    protected void init() {
        login_checkcode_view.setImageBitmap(CheckCodeUtil.getInstance().getBitmap());
    }

    @Override
    protected void setListener() {
        login_button.setOnClickListener(this);
        login_checkcode_view.setOnClickListener(this);
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
        if(v.getId()==R.id.login_button){
//            loginPresenter.test();
            Intent i = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(i);
            finish();
        }
        if(v.getId()==R.id.login_checkcode_view){
            login_checkcode_view.setImageBitmap(CheckCodeUtil.getInstance().getBitmap());
        }
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

    @Override
    public void login(String username, String password) {

    }

}
