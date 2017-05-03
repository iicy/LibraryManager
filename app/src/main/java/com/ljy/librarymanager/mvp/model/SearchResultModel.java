package com.ljy.librarymanager.mvp.model;

import com.ljy.librarymanager.mvp.entity.Announcement;
import com.ljy.librarymanager.mvp.entity.Booking;
import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.entity.Borrow;
import com.ljy.librarymanager.mvp.entity.Category;
import com.ljy.librarymanager.mvp.entity.User;
import com.ljy.librarymanager.mvp.presenter.SearchResultPresenter;

import java.util.List;

import javax.inject.Inject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class SearchResultModel {

    @Inject
    public SearchResultModel() {
    }

    public void deleteAnnouncement(final SearchResultPresenter searchResultPresenter, Announcement announcement) {
        announcement.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    searchResultPresenter.deleteSuccess();
                } else {
                    searchResultPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    public void deleteBooking(final SearchResultPresenter searchResultPresenter, Booking booking) {
        booking.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    searchResultPresenter.deleteSuccess();
                } else {
                    searchResultPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    public void deleteBorrow(final SearchResultPresenter searchResultPresenter, Borrow borrow) {
        borrow.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    searchResultPresenter.deleteSuccess();
                } else {
                    searchResultPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    public void deleteUser(final SearchResultPresenter searchResultPresenter, User user) {
        user.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    searchResultPresenter.deleteSuccess();
                } else {
                    searchResultPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    public void deleteBook(final SearchResultPresenter searchResultPresenter, Books books) {
        books.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    searchResultPresenter.deleteSuccess();
                } else {
                    searchResultPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    public void deleteCategory(final SearchResultPresenter searchResultPresenter, final Category category){
        BmobQuery<Books> bmobQuery = new BmobQuery<Books>();
        bmobQuery.addWhereEqualTo("category",category.getCategory_name());
        bmobQuery.findObjects(new FindListener<Books>() {
            @Override
            public void done(List<Books> list, BmobException e) {
                if (e == null) {
                    if((list.size()==0)||(list==null)){
                        category.delete(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    searchResultPresenter.deleteSuccess();
                                } else {
                                    searchResultPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                                }
                            }
                        });
                    }else{
                        searchResultPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                    }
                } else {
                    searchResultPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    public void getBook(final SearchResultPresenter searchResultPresenter, String bookId) {
        BmobQuery<Books> bmobQuery = new BmobQuery<Books>();
        bmobQuery.addWhereEqualTo("objectId", bookId);
        bmobQuery.findObjects(new FindListener<Books>() {
            @Override
            public void done(List<Books> list, BmobException e) {
                if (e == null && list.size() != 0) {
                    searchResultPresenter.getBookSuccess(list.get(0));
                } else {
                    searchResultPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    public void getBorrow(final SearchResultPresenter searchResultPresenter, final Borrow borrow) {
        BmobQuery<Books> bmobQuery = new BmobQuery<Books>();
        bmobQuery.addWhereEqualTo("objectId", borrow.getBookId());
        bmobQuery.findObjects(new FindListener<Books>() {
            @Override
            public void done(List<Books> list, BmobException e) {
                if (e == null && list.size() != 0) {
                    searchResultPresenter.getBorrowSuccess(list.get(0),borrow);
                } else {
                    searchResultPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

}
