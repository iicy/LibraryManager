package com.ljy.librarymanager.mvp.presenter;

import com.ljy.librarymanager.mvp.base.BasePresenter;
import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.model.BookListModel;
import com.ljy.librarymanager.mvp.model.MainModel;
import com.ljy.librarymanager.mvp.view.BookListView;
import com.ljy.librarymanager.mvp.view.MainView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class MainPresenter extends BasePresenter<MainView, List<Books>> {

    private MainModel mainModel;

    @Inject
    public MainPresenter( MainModel mainModel) {
        this.mainModel = mainModel;
    }

    public void getAllBooks(){
        mainModel.getAllBooks(this);
    }

    @Override
    public void success(List<Books> data) {
        super.success(data);
        mView.searchBooks(data);
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
    }
}
