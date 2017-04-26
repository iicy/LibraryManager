package com.ljy.librarymanager.mvp.presenter;

import com.ljy.librarymanager.mvp.base.BasePresenter;
import com.ljy.librarymanager.mvp.entity.Booking;
import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.model.BookingListModel;
import com.ljy.librarymanager.mvp.model.ManagerBookingModel;
import com.ljy.librarymanager.mvp.view.BookingListView;
import com.ljy.librarymanager.mvp.view.ManagerBookingView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class BookingListPresenter extends BasePresenter<BookingListView, List<Booking>> {

    private BookingListModel bookingListModel;

    @Inject
    public BookingListPresenter(BookingListModel bookingListModel) {
        this.bookingListModel = bookingListModel;
    }

    public void getList(String account) {
        mView.showProgress();
        bookingListModel.getList(this,account);
    }

    public void getBook(String bookId){
        bookingListModel.getBook(this,bookId);
    }

    @Override
    public void success(List<Booking> data) {
        super.success(data);
        mView.setList(data);
    }

    public void getBookSuccess(Books book){
        mView.getBook(book);
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
    }
}
