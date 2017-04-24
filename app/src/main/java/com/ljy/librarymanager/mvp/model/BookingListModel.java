package com.ljy.librarymanager.mvp.model;

import com.ljy.librarymanager.mvp.entity.Booking;
import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.presenter.BookingListPresenter;
import com.ljy.librarymanager.mvp.presenter.ManagerBookingPresenter;

import java.util.List;

import javax.inject.Inject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class BookingListModel {

    @Inject
    public BookingListModel() {
    }

    public void getList(final BookingListPresenter bookingListPresenter,String account) {
        BmobQuery<Booking> bmobQuery = new BmobQuery<Booking>();
        bmobQuery.addWhereEqualTo("user",account);
        bmobQuery.order("-createdAt");
//        bmobQuery.setLimit(10);
        bmobQuery.findObjects(new FindListener<Booking>() {
            @Override
            public void done(List<Booking> list, BmobException e) {
                if (e == null) {
                    bookingListPresenter.success(list);
                } else {
                    bookingListPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    public void getBook(final BookingListPresenter bookingListPresenter,String bookId){
        BmobQuery<Books> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("objectId",bookId);
        bmobQuery.findObjects(new FindListener<Books>() {
            @Override
            public void done(List<Books> list, BmobException e) {
                if (e == null&&list.size()>0) {
                    bookingListPresenter.getBookSuccess(list.get(0));
                } else {
                    bookingListPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

}
