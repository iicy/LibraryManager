package com.ljy.librarymanager.mvp.presenter;

import com.ljy.librarymanager.mvp.base.BasePresenter;
import com.ljy.librarymanager.mvp.entity.Announcement;
import com.ljy.librarymanager.mvp.entity.User;
import com.ljy.librarymanager.mvp.model.AddAnnouncementModel;
import com.ljy.librarymanager.mvp.model.AddUserModel;
import com.ljy.librarymanager.mvp.view.AddAnnouncementView;
import com.ljy.librarymanager.mvp.view.AddUserView;

import javax.inject.Inject;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class AddUserPresenter extends BasePresenter<AddUserView, String> {

    private AddUserModel addUserModel;

    @Inject
    public AddUserPresenter(AddUserModel addUserModel) {
        this.addUserModel = addUserModel;
    }

    public void add(User user) {
        addUserModel.add(this,user);
    }

    @Override
    public void success(String s) {
        super.success(s);
        mView.add();
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
    }
}
