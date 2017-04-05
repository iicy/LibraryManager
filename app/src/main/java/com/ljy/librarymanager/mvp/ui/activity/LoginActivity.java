package com.ljy.librarymanager.mvp.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ljy.librarymanager.R;
import com.ljy.librarymanager.common.Constant;
import com.ljy.librarymanager.mvp.base.BaseActivity;
import com.ljy.librarymanager.mvp.entity.User;
import com.ljy.librarymanager.mvp.presenter.LoginPresenter;
import com.ljy.librarymanager.mvp.view.LoginView;
import com.ljy.librarymanager.utils.CheckCodeUtil;
import com.ljy.librarymanager.utils.Encryption;

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
    private ProgressDialog pg;
    private String account;
    private String password;

    @Inject
    LoginPresenter loginPresenter;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_login);
        //注入对象
        mActivityComponent.inject(this);
        //绑定presenter
        loginPresenter.attachView(this);
        pg = new ProgressDialog(LoginActivity.this);
        pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pg.setMessage("正在登录！");
        pg.setCancelable(false);
    }

    @Override
    protected void init() {
        changeCheckCode();
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
            showProgress();
            account = login_accounts.getText().toString();
            password = login_password.getText().toString();
            loginPresenter.login(account,password,login_checkcode_edittext.getText().toString());
        }
        if(v.getId()==R.id.login_checkcode_view){
            login_checkcode_view.setImageBitmap(CheckCodeUtil.getInstance().getBitmap());
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
        Log.e("bmob",message);
        Toast.makeText(LoginActivity.this,"网络发生错误！",Toast.LENGTH_LONG).show();
    }

    @Override
    public void login(User user) {
        if(user.getPermission().equals("0")){
            Intent i = new Intent(LoginActivity.this,ManagerActivity.class);
            i.putExtra("account",user.getAccount());
            i.putExtra("password", user.getPassword());
            i.putExtra("permission",user.getPermission());
            i.putExtra("username",user.getUsername());
            startActivity(i);
        }else{
            Intent i = new Intent(LoginActivity.this,MainActivity.class);
            i.putExtra("account",user.getAccount());
            i.putExtra("password", user.getPassword());
            i.putExtra("permission",user.getPermission());
            i.putExtra("username",user.getUsername());
            startActivity(i);
        }
        try {
            SharedPreferences sp = this.getSharedPreferences("loginFlag", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("login", true);
            editor.putString("account",user.getAccount());
            editor.putString("password", Encryption.EncodeAES(user.getPassword(), Constant.key));
            Log.e("encryption","en:"+Encryption.EncodeAES(user.getPassword(), Constant.key));
            editor.putString("permission",user.getPermission());
            editor.putString("username",user.getUsername());
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finish();
    }

    @Override
    public void changeCheckCode(){
        login_checkcode_view.setImageBitmap(CheckCodeUtil.getInstance().getBitmap());
    }

    @Override
    public void checkCodeIncorrect() {
        Toast.makeText(LoginActivity.this,"验证码错误，请重新输入！",Toast.LENGTH_LONG).show();
    }

    @Override
    public void loginFail() {
        Toast.makeText(LoginActivity.this,"账号或密码错误，请重新输入！",Toast.LENGTH_LONG).show();
    }

}
