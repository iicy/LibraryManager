package com.ljy.librarymanager.mvp.view;


import com.ljy.librarymanager.mvp.base.BaseView;
import com.ljy.librarymanager.mvp.entity.Announcement;
import com.ljy.librarymanager.mvp.entity.Booking;
import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.entity.Borrow;
import com.ljy.librarymanager.mvp.entity.Category;
import com.ljy.librarymanager.mvp.entity.User;

import java.util.List;
import java.util.Map;

/**
 * Created by jiayu on 2017/3/11.
 */

public interface SearchBarView extends BaseView {

    void searchAnnouncement(List<Announcement> mData);

    void searchBooking(List<Booking> mData);

    void searchBooks(List<Books> mData);

    void searchBorrow(List<Borrow> mData);

    void searchUser(List<User> mData);

    void searchCategory(List<Category> mData);

}
