package com.ljy.librarymanager.mvp.view;

import com.ljy.librarymanager.mvp.base.BaseView;
import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.entity.Borrow;

import java.util.List;

/**
 * Created by luojiayu on 2017/4/6.
 */

public interface ManagerBorrowView extends BaseView{

    void setList(List<Borrow> list);

    void deleteSuccess();

    void getBorrowSuccess(Books book, Borrow borrow);
}
