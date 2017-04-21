package com.ljy.librarymanager.mvp.view;

import com.ljy.librarymanager.mvp.base.BaseView;
import com.ljy.librarymanager.mvp.entity.Booking;
import com.ljy.librarymanager.mvp.entity.Books;

import java.util.List;

/**
 * Created by luojiayu on 2017/4/6.
 */

public interface ManagerBookingView extends BaseView{

    void setList(List<Booking> list);

    void deleteSuccess();

    void getBookSuccess(Books book);
}
