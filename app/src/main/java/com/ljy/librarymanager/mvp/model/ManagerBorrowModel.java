package com.ljy.librarymanager.mvp.model;

import com.ljy.librarymanager.mvp.entity.Booking;
import com.ljy.librarymanager.mvp.entity.Borrow;
import com.ljy.librarymanager.mvp.presenter.ManagerBookingPresenter;
import com.ljy.librarymanager.mvp.presenter.ManagerBorrowPresenter;

import java.util.List;

import javax.inject.Inject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class ManagerBorrowModel {

    @Inject
    public ManagerBorrowModel() {
    }

    public void getList(final ManagerBorrowPresenter managerBorrowPresenter) {
        BmobQuery<Borrow> bmobQuery = new BmobQuery<Borrow>();
        bmobQuery.order("-createdAt");
//        bmobQuery.setLimit(10);
        bmobQuery.findObjects(new FindListener<Borrow>() {
            @Override
            public void done(List<Borrow> list, BmobException e) {
                if (e == null) {
                    managerBorrowPresenter.success(list);
                } else {
                    managerBorrowPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    public void delete(final ManagerBorrowPresenter managerBorrowPresenter,Borrow borrow){
        borrow.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    managerBorrowPresenter.deleteSuccess();
                } else {
                    managerBorrowPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

}
