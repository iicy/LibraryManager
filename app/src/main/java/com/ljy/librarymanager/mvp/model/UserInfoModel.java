package com.ljy.librarymanager.mvp.model;

import com.ljy.librarymanager.mvp.entity.User;
import com.ljy.librarymanager.mvp.presenter.UserInfoPresenter;

import java.util.List;

import javax.inject.Inject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by jiayu on 2017/5/11.
 */

public class UserInfoModel {

    @Inject
    public UserInfoModel() {
    }

    public void getUserInfo(final UserInfoPresenter userInfoPresenter, String account) {
        BmobQuery<User> bmobQuery = new BmobQuery<User>();
        bmobQuery.addWhereEqualTo("account", account);
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {
                    userInfoPresenter.success(list.get(0));
                } else {
                    userInfoPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    public void modifyUsername(final UserInfoPresenter userInfoPresenter, User user, String id) {
        user.update(id, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    userInfoPresenter.updateSuccess();
                } else {
                    userInfoPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }
}
