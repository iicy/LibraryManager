package com.ljy.librarymanager.mvp.model;

import com.ljy.librarymanager.mvp.entity.Announcement;
import com.ljy.librarymanager.mvp.entity.Booking;
import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.entity.Borrow;
import com.ljy.librarymanager.mvp.entity.Category;
import com.ljy.librarymanager.mvp.entity.User;
import com.ljy.librarymanager.mvp.presenter.ManagerAnnouncementPresenter;
import com.ljy.librarymanager.mvp.presenter.SearchBarPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class SearchBarModel {

    @Inject
    public SearchBarModel() {
    }

    public void searchAnnouncement(final SearchBarPresenter searchBarPresenter, List<Announcement> data, String key) {
        List<Announcement> result = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getContent().contains(key)
                    || data.get(i).getAccount().contains(key)) {
                result.add(data.get(i));
            }
        }
        searchBarPresenter.searchAnnouncementSuccess(result);
    }

    public void searchBooking(final SearchBarPresenter searchBarPresenter, List<Booking> data, String key) {
        List<Booking> result = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getBookId().contains(key)
                    || data.get(i).getBookName().contains(key)
                    || data.get(i).getUser().contains(key)) {
                result.add(data.get(i));
            }
        }
        searchBarPresenter.searchBookingSuccess(result);
    }

    public void searchBooks(final SearchBarPresenter searchBarPresenter, List<Books> data, String key) {
        List<Books> result = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getBookName().contains(key)
                    || data.get(i).getAuthor().contains(key)
                    || data.get(i).getCategory().contains(key)
                    || data.get(i).getPublication().contains(key)
                    || data.get(i).getSummary().contains(key)) {
                result.add(data.get(i));
            }
        }
        searchBarPresenter.searchBooksSuccess(result);
    }

    public void searchBorrow(final SearchBarPresenter searchBarPresenter, List<Borrow> data, String key) {
        List<Borrow> result = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getBookName().contains(key)
                    || data.get(i).getUser().contains(key)
                    || data.get(i).getBookId().contains(key)
                    || data.get(i).getManager().contains(key)) {
                result.add(data.get(i));
            }
        }
        searchBarPresenter.searchBorrowSuccess(result);
    }

    public void searchUser(final SearchBarPresenter searchBarPresenter, List<User> data, String key) {
        List<User> result = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getUsername().contains(key)
                    || data.get(i).getAccount().contains(key)) {
                result.add(data.get(i));
            }
        }
        searchBarPresenter.searchUserSuccess(result);
    }

    public void searchCategory(final SearchBarPresenter searchBarPresenter, List<Category> data, String key) {
        List<Category> result = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getCategory_name().contains(key)) {
                result.add(data.get(i));
            }
        }
        searchBarPresenter.searchCategorySuccess(result);
    }

}
