package com.ljy.librarymanager.mvp.view;


import com.ljy.librarymanager.mvp.base.BaseView;
import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.entity.Borrow;

/**
 * Created by jiayu on 2017/3/11.
 */

public interface SearchResultView extends BaseView {

    void getBookSuccess(Books books);

    void deleteSuccess();

    void getBorrowSuccess(Books book, Borrow borrow);
}
