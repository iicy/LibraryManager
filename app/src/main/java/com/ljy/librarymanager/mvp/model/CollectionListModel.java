package com.ljy.librarymanager.mvp.model;

import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.entity.Collection;
import com.ljy.librarymanager.mvp.presenter.CollectionListPresenter;

import java.util.List;

import javax.inject.Inject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class CollectionListModel {

    @Inject
    public CollectionListModel() {
    }

    public void getList(final CollectionListPresenter collectionListPresenter, String account, int more) {
        BmobQuery<Collection> bmobQuery = new BmobQuery<Collection>();
        bmobQuery.addWhereEqualTo("user",account);
        bmobQuery.order("-createdAt");
        bmobQuery.setLimit(10 + more);
        bmobQuery.findObjects(new FindListener<Collection>() {
            @Override
            public void done(List<Collection> list, BmobException e) {
                if (e == null) {
                    collectionListPresenter.success(list);
                } else {
                    collectionListPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    public void getBook(final CollectionListPresenter collectionListPresenter,String bookId){
        BmobQuery<Books> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("objectId",bookId);
        bmobQuery.findObjects(new FindListener<Books>() {
            @Override
            public void done(List<Books> list, BmobException e) {
                if (e == null&&list.size()>0) {
                    collectionListPresenter.getBookSuccess(list.get(0));
                } else {
                    collectionListPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

}
