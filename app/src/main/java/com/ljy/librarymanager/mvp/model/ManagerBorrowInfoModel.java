package com.ljy.librarymanager.mvp.model;

import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.entity.Borrow;
import com.ljy.librarymanager.mvp.presenter.ManagerBookInfoPresenter;
import com.ljy.librarymanager.mvp.presenter.ManagerBorrowInfoPresenter;

import javax.inject.Inject;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class ManagerBorrowInfoModel {

    @Inject
    public ManagerBorrowInfoModel() {
    }

    public void save(final ManagerBorrowInfoPresenter managerBorrowInfoPresenter, Borrow borrow) {
        borrow.update(borrow.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    managerBorrowInfoPresenter.success("success");
                } else {
                    managerBorrowInfoPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }


}
