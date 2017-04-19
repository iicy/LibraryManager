package com.ljy.librarymanager.mvp.presenter;

import com.ljy.librarymanager.mvp.base.BasePresenter;
import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.model.BookListModel;
import com.ljy.librarymanager.mvp.model.ManagerBookModel;
import com.ljy.librarymanager.mvp.view.BookListView;
import com.ljy.librarymanager.mvp.view.ManagerBookView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class BookListPresenter extends BasePresenter<BookListView, List<Books>> {

    private BookListModel bookListModel;

    @Inject
    public BookListPresenter(BookListModel bookListModel) {
        this.bookListModel = bookListModel;
    }

    public void getList(String category) {
        bookListModel.getList(this,category);
    }

    @Override
    public void success(List<Books> data) {
        super.success(data);
        mView.setList(data);
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
    }
}
