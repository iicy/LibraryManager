package com.ljy.librarymanager.mvp.view;


import com.ljy.librarymanager.mvp.base.BaseView;
import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.entity.Borrow;

import java.util.List;

/**
 * Created by jiayu on 2017/3/11.
 */

public interface BorrowListView extends BaseView {

    void setList(List<Borrow> data);

    void getBook(Books book);
}
