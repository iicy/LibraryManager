package com.ljy.librarymanager.mvp.presenter;

import com.ljy.librarymanager.mvp.base.BasePresenter;
import com.ljy.librarymanager.mvp.entity.Booking;
import com.ljy.librarymanager.mvp.entity.Collection;
import com.ljy.librarymanager.mvp.entity.Comment;
import com.ljy.librarymanager.mvp.model.BookInfoModel;
import com.ljy.librarymanager.mvp.view.BookInfoView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class BookInfoPresenter extends BasePresenter<BookInfoView, String> {

    private BookInfoModel bookInfoModel;

    @Inject
    public BookInfoPresenter(BookInfoModel bookInfoModel) {
        this.bookInfoModel = bookInfoModel;
    }

    public void getComments(String bookId) {
        bookInfoModel.getComments(this, bookId);
    }

    public void addBooking(Booking booking) {
        bookInfoModel.addBooking(this,booking);
    }

    public void addCollection(Collection collection) {
        bookInfoModel.addCollection(this,collection);
    }

    public void hasBooking(String user,String bookId){
        bookInfoModel.hasBooking(this,user,bookId);
    }

    public void hasBooking(boolean result,String id){
        mView.hasBooking(result,id);
    }

    public void hasCollect(String user,String bookId){
        bookInfoModel.hasCollect(this,user,bookId);
    }

    public void hasCollect(boolean result,String id){
        mView.hasCollect(result,id);
    }

    public void cancelBooking(Booking booking){
        bookInfoModel.cancelBooking(this,booking);
    }

    public void cancelCollect(Collection collection){
        bookInfoModel.cancelCollect(this,collection);
    }

    @Override
    public void success(String s) {
        super.success(s);
        mView.success();
    }

    public void getCommentsSuccess(List<Comment> data) {
        mView.hideProgress();
        mView.getComments(data);
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
    }
}
