package com.ljy.librarymanager.mvp.presenter;

import com.ljy.librarymanager.mvp.base.BasePresenter;
import com.ljy.librarymanager.mvp.entity.User;
import com.ljy.librarymanager.mvp.model.UserInfoModel;
import com.ljy.librarymanager.mvp.view.UserInfoView;

import javax.inject.Inject;

/**
 * Created by jiayu on 2017/5/11.
 */

public class UserInfoPresenter extends BasePresenter<UserInfoView, User> {

    private UserInfoModel userInfoModel;

    @Inject
    public UserInfoPresenter(UserInfoModel userInfoModel) {
        this.userInfoModel = userInfoModel;
    }

    public void getUserInfo(String account) {
        mView.showProgress();
        userInfoModel.getUserInfo(this, account);
    }

    public void modifyUsername(User user, String id) {
        mView.showProgress();
        userInfoModel.modifyUsername(this, user, id);
    }

    @Override
    public void success(User data) {
        super.success(data);
        mView.getInfo(data);
    }

    public void updateSuccess() {
        mView.hideProgress();
        mView.modifyUsername();
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
    }
}
