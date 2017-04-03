package com.ljy.librarymanager.mvp.view;

import com.ljy.librarymanager.mvp.base.BaseView;
import com.ljy.librarymanager.mvp.entity.User;

/**
 * Created by jiayu on 2017/3/11.
 */

public interface LoginView extends BaseView {

    void login(User user);
    void changeCheckCode();
    void checkCodeIncorrect();
    void loginFail();
}
