package com.ljy.librarymanager.mvp.presenter;

import com.ljy.librarymanager.mvp.base.BasePresenter;
import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.entity.Borrow;
import com.ljy.librarymanager.mvp.model.ManagerBookInfoModel;
import com.ljy.librarymanager.mvp.model.ManagerBorrowInfoModel;
import com.ljy.librarymanager.mvp.view.ManagerBookInfoView;
import com.ljy.librarymanager.mvp.view.ManagerBorrowInfoView;

import javax.inject.Inject;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class ManagerBorrowInfoPresenter extends BasePresenter<ManagerBorrowInfoView, String> {

    private ManagerBorrowInfoModel managerBorrowInfoModel;

    @Inject
    public ManagerBorrowInfoPresenter(ManagerBorrowInfoModel managerBorrowInfoModel) {
        this.managerBorrowInfoModel = managerBorrowInfoModel;
    }

    public void save(Borrow borrow) {
        managerBorrowInfoModel.save(this,borrow);
    }

    @Override
    public void success(String s) {
        super.success(s);
        mView.saveSuccess();
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
    }
}
