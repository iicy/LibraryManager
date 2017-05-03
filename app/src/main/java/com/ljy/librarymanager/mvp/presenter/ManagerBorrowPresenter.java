package com.ljy.librarymanager.mvp.presenter;

import com.ljy.librarymanager.mvp.base.BasePresenter;
import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.entity.Borrow;
import com.ljy.librarymanager.mvp.model.ManagerBorrowModel;
import com.ljy.librarymanager.mvp.view.ManagerBorrowView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class ManagerBorrowPresenter extends BasePresenter<ManagerBorrowView, List<Borrow>> {

    private ManagerBorrowModel managerBorrowModel;

    @Inject
    public ManagerBorrowPresenter(ManagerBorrowModel managerBorrowModel) {
        this.managerBorrowModel = managerBorrowModel;
    }

    public void getList() {
        mView.showProgress();
        managerBorrowModel.getList(this);
    }

    public void getBorrow(Borrow borrow){
        managerBorrowModel.getBorrow(this,borrow);
    }

    public void delete(Borrow borrow){
        managerBorrowModel.delete(this,borrow);
    }

    @Override
    public void success(List<Borrow> data) {
        super.success(data);
        mView.setList(data);
    }

    public void deleteSuccess(){
        mView.deleteSuccess();
    }

    public void getBorrowSuccess(Books book,Borrow borrow){
        mView.getBorrowSuccess(book,borrow);
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
    }
}
