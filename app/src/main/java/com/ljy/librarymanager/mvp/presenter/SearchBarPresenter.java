package com.ljy.librarymanager.mvp.presenter;

import com.ljy.librarymanager.mvp.base.BasePresenter;
import com.ljy.librarymanager.mvp.entity.Announcement;
import com.ljy.librarymanager.mvp.entity.Booking;
import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.entity.Borrow;
import com.ljy.librarymanager.mvp.entity.Category;
import com.ljy.librarymanager.mvp.entity.User;
import com.ljy.librarymanager.mvp.model.ManagerAnnouncementModel;
import com.ljy.librarymanager.mvp.model.SearchBarModel;
import com.ljy.librarymanager.mvp.view.ManagerAnnouncementView;
import com.ljy.librarymanager.mvp.view.SearchBarView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class SearchBarPresenter extends BasePresenter<SearchBarView, String> {

    private SearchBarModel searchBarModel;

    @Inject
    public SearchBarPresenter(SearchBarModel searchBarModel) {
        this.searchBarModel = searchBarModel;
    }

    public void searchAnnouncement(List<Announcement> data,String key) {
        searchBarModel.searchAnnouncement(this,data,key);
    }

    public void searchBooking(List<Booking> data, String key) {
        searchBarModel.searchBooking(this,data,key);
    }
    public void searchBooks(List<Books> data, String key) {
        searchBarModel.searchBooks(this,data,key);
    }
    public void searchBorrow(List<Borrow> data, String key) {
        searchBarModel.searchBorrow(this,data,key);
    }
    public void searchUser(List<User> data, String key) {
        searchBarModel.searchUser(this,data,key);
    }

    public void searchCategory(List<Category> data, String key) {
        searchBarModel.searchCategory(this,data,key);
    }

    @Override
    public void success(String data) {
        super.success(data);
    }

    public void searchAnnouncementSuccess(List<Announcement> data){
        mView.hideProgress();
        mView.searchAnnouncement(data);
    }
    public void searchBookingSuccess(List<Booking> data){
        mView.hideProgress();
        mView.searchBooking(data);
    }
    public void searchBooksSuccess(List<Books> data){
        mView.hideProgress();
        mView.searchBooks(data);
    }
    public void searchBorrowSuccess(List<Borrow> data){
        mView.hideProgress();
        mView.searchBorrow(data);
    }
    public void searchUserSuccess(List<User> data){
        mView.hideProgress();
        mView.searchUser(data);
    }

    public void searchCategorySuccess(List<Category> data){
        mView.hideProgress();
        mView.searchCategory(data);
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
    }
}
