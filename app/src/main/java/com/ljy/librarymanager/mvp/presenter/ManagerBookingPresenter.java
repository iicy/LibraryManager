package com.ljy.librarymanager.mvp.presenter;

import com.ljy.librarymanager.mvp.base.BasePresenter;
import com.ljy.librarymanager.mvp.entity.Booking;
import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.model.ManagerBookingModel;
import com.ljy.librarymanager.mvp.view.ManagerBookingView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class ManagerBookingPresenter extends BasePresenter<ManagerBookingView, List<Booking>> {

    private ManagerBookingModel managerBookingModel;

    @Inject
    public ManagerBookingPresenter(ManagerBookingModel managerBookingModel) {
        this.managerBookingModel = managerBookingModel;
    }

    public void getList(int more) {
        mView.showProgress();
        managerBookingModel.getList(this, more);
    }

    public void getBook(String bookId){
        managerBookingModel.getBook(this,bookId);
    }

    public void delete(Booking booking){
        managerBookingModel.delete(this,booking);
    }

    @Override
    public void success(List<Booking> data) {
        super.success(data);
        mView.setList(data);
    }

    public void deleteSuccess(){
        mView.deleteSuccess();
    }

    public void getBookSuccess(Books book){
        mView.getBookSuccess(book);
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
    }
}
