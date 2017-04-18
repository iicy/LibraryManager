package com.ljy.librarymanager.mvp.view;

import com.ljy.librarymanager.mvp.base.BaseView;
import com.ljy.librarymanager.mvp.entity.User;

import java.util.List;

/**
 * Created by luojiayu on 2017/4/6.
 */

public interface ManagerUserView extends BaseView{

    void setList(List<User> mData);

    void deleteSuccess();
}
