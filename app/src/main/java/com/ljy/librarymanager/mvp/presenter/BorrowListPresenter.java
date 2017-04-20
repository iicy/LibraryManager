package com.ljy.librarymanager.mvp.presenter;

import com.ljy.librarymanager.mvp.base.BasePresenter;
import com.ljy.librarymanager.mvp.entity.Borrow;
import com.ljy.librarymanager.mvp.model.BorrowListModel;
import com.ljy.librarymanager.mvp.model.ManagerBorrowModel;
import com.ljy.librarymanager.mvp.view.BorrowListView;
import com.ljy.librarymanager.mvp.view.ManagerBorrowView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class BorrowListPresenter extends BasePresenter<BorrowListView, List<Borrow>> {

    private BorrowListModel borrowListModel;

    @Inject
    public BorrowListPresenter(BorrowListModel borrowListModel) {
        this.borrowListModel = borrowListModel;
    }

    public void getList(String account) {
        borrowListModel.getList(this,account);
    }

    @Override
    public void success(List<Borrow> data) {
        super.success(data);
        mView.setList(data);
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
    }
}
