package com.ljy.librarymanager.mvp.model;

import com.ljy.librarymanager.mvp.entity.User;
import com.ljy.librarymanager.mvp.presenter.LoginPresenter;

import java.util.List;

import javax.inject.Inject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class LoginModel {

    @Inject
    public LoginModel() {
    }

    public void login(final LoginPresenter loginPresenter, String account, String password) {
        BmobQuery<User> bmobQuery = new BmobQuery<User>();
        bmobQuery.addWhereEqualTo("account", account);
        bmobQuery.addWhereEqualTo("password", password);
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {
                    loginPresenter.success(list);
                } else {
                    loginPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

}
