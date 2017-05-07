package com.ljy.librarymanager.mvp.presenter;

import android.content.Context;

import com.ljy.librarymanager.mvp.base.BasePresenter;
import com.ljy.librarymanager.mvp.entity.Announcement;
import com.ljy.librarymanager.mvp.entity.Booking;
import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.entity.Borrow;
import com.ljy.librarymanager.mvp.entity.Category;
import com.ljy.librarymanager.mvp.entity.SearchHistory;
import com.ljy.librarymanager.mvp.entity.User;
import com.ljy.librarymanager.mvp.model.SearchBarModel;
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

    public void searchAnnouncement(String key) {
        searchBarModel.searchAnnouncement(this,key);
    }

    public void searchBooking(String key) {
        searchBarModel.searchBooking(this,key);
    }

    public void searchBooks(String key) {
        searchBarModel.searchBooks(this,key);
    }

    public void searchBorrow(String key) {
        searchBarModel.searchBorrow(this,key);
    }

    public void searchUser(String key) {
        searchBarModel.searchUser(this,key);
    }

    public void searchCategory(String key) {
        searchBarModel.searchCategory(this,key);
    }

    public void getSearchHistory(Context context, String account) {
        searchBarModel.getSearchHistory(this, context, account);
    }

    public void saveSearchHistory(Context context, String account, String content) {
        searchBarModel.saveSearchHistory(context, account, content);
    }

    public void clearSearchHistory(Context context, String account) {
        searchBarModel.clearSearchHistory(this, context, account);
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

    public void getSearchHistorySuccess(List<SearchHistory> data) {
        mView.showSearchHistory(data);
    }

    public void clearSearchHistorySuccess() {
        mView.clearSearchHistorySuccess();
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
    }
}
