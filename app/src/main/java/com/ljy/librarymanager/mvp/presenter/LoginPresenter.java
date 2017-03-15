package com.ljy.librarymanager.mvp.presenter;

import com.ljy.librarymanager.mvp.base.BasePresenter;
import com.ljy.librarymanager.mvp.model.LoginModel;
import com.ljy.librarymanager.mvp.view.LoginView;

import javax.inject.Inject;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class LoginPresenter extends BasePresenter<LoginView,Integer> {

    private LoginModel loginModel;

    @Inject
    public LoginPresenter(LoginModel loginModel) {
        this.loginModel = loginModel;
    }

//    public void test(){
//        loginModel.test(this);
//    }

//    @Override
//    public void success(Integer data) {
//        super.success(data);
//        mView.test(data);
//    }


}
