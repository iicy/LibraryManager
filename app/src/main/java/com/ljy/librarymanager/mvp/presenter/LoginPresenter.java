package com.ljy.librarymanager.mvp.presenter;

import com.ljy.librarymanager.mvp.base.BasePresenter;
import com.ljy.librarymanager.mvp.entity.User;
import com.ljy.librarymanager.mvp.model.LoginModel;
import com.ljy.librarymanager.mvp.view.LoginView;
import com.ljy.librarymanager.utils.CheckCodeUtil;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class LoginPresenter extends BasePresenter<LoginView,List<User>> {

    private LoginModel loginModel;

    @Inject
    public LoginPresenter(LoginModel loginModel) {
        this.loginModel = loginModel;
    }

    public void login(String account,String password,String checkcode){
        if(checkcode.equals(CheckCodeUtil.getInstance().getCode())){
            loginModel.login(this,account,password);
        }else{
            mView.hideProgress();
            mView.checkCodeIncorrect();
            mView.changeCheckCode();
        }
    }

    @Override
    public void success(List<User> data) {
        super.success(data);
        if(data.size()==1){
            User user = data.get(0);
            mView.login(user);
        }else{
            mView.loginFail();
        }
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
    }
}
