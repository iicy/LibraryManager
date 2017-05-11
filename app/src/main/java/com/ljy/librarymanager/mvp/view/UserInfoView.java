package com.ljy.librarymanager.mvp.view;

import com.ljy.librarymanager.mvp.base.BaseView;
import com.ljy.librarymanager.mvp.entity.User;

/**
 * Created by jiayu on 2017/5/11.
 */

public interface UserInfoView extends BaseView {

    void getInfo(User user);

    void modifyUsername();

}
