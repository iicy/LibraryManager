package com.ljy.librarymanager.mvp.presenter;

import com.ljy.librarymanager.mvp.base.BasePresenter;
import com.ljy.librarymanager.mvp.entity.Announcement;
import com.ljy.librarymanager.mvp.entity.Booking;
import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.entity.Borrow;
import com.ljy.librarymanager.mvp.entity.Category;
import com.ljy.librarymanager.mvp.entity.User;
import com.ljy.librarymanager.mvp.model.SearchResultModel;
import com.ljy.librarymanager.mvp.view.SearchResultView;

import javax.inject.Inject;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class SearchResultPresenter extends BasePresenter<SearchResultView, String> {

    private SearchResultModel searchResultModel;

    @Inject
    public SearchResultPresenter(SearchResultModel searchResultModel) {
        this.searchResultModel = searchResultModel;
    }

    public void getBook(String bookId){
        searchResultModel.getBook(this,bookId);
    }

    public void getBorrow(Borrow borrow){
        searchResultModel.getBorrow(this,borrow);
    }

    public void deleteBooking(Booking booking){
        searchResultModel.deleteBooking(this,booking);
    }

    public void deleteAnnouncement(Announcement announcement){
        searchResultModel.deleteAnnouncement(this,announcement);
    }

    public void deleteBorrow(Borrow borrow){
        searchResultModel.deleteBorrow(this,borrow);
    }

    public void deleteCategory(Category category){
        searchResultModel.deleteCategory(this,category);
    }

    public void deleteUser(User user){
        searchResultModel.deleteUser(this,user);
    }

    public void deleteBook(Books book){
        searchResultModel.deleteBook(this,book);
    }

    @Override
    public void success(String data) {
        super.success(data);
    }

    public void deleteSuccess(){
        mView.deleteSuccess();
    }

    public void getBookSuccess(Books book){
        mView.getBookSuccess(book);
    }

    public void getBorrowSuccess(Books book,Borrow borrow){
        mView.getBorrowSuccess(book,borrow);
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
    }
}
