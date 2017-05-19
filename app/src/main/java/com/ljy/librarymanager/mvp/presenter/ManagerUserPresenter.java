package com.ljy.librarymanager.mvp.presenter;

import com.ljy.librarymanager.mvp.base.BasePresenter;
import com.ljy.librarymanager.mvp.entity.User;
import com.ljy.librarymanager.mvp.model.ManagerUserModel;
import com.ljy.librarymanager.mvp.view.ManagerUserView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class ManagerUserPresenter extends BasePresenter<ManagerUserView, List<User>> {

    private ManagerUserModel managerUserModel;

    @Inject
    public ManagerUserPresenter(ManagerUserModel managerUserModel) {
        this.managerUserModel = managerUserModel;
    }

    public void getList(int more) {
        mView.showProgress();
        managerUserModel.getList(this, more);
    }

    public void delete(User user){
        managerUserModel.delete(this,user);
    }

    @Override
    public void success(List<User> data) {
        super.success(data);
        mView.setList(data);
    }

    public void deleteSuccess(){
        mView.deleteSuccess();
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
    }
}
