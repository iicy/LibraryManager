package com.ljy.librarymanager.mvp.presenter;

import com.ljy.librarymanager.mvp.base.BasePresenter;
import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.entity.Borrow;
import com.ljy.librarymanager.mvp.entity.User;
import com.ljy.librarymanager.mvp.model.ManagerBorrowModel;
import com.ljy.librarymanager.mvp.model.ModifyPasswordModel;
import com.ljy.librarymanager.mvp.view.ManagerBorrowView;
import com.ljy.librarymanager.mvp.view.ModifyPasswordView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class ModifyPasswordPresenter extends BasePresenter<ModifyPasswordView, String> {

    private ModifyPasswordModel modifyPasswordModel;

    @Inject
    public ModifyPasswordPresenter(ModifyPasswordModel modifyPasswordModel) {
        this.modifyPasswordModel = modifyPasswordModel;
    }

    public void modify(User user,String account){
        mView.showProgress();
        modifyPasswordModel.modify(this,user,account);
    }

    @Override
    public void success(String data) {
        super.success(data);
        mView.modifySuccess();
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
    }
}
