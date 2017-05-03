package com.ljy.librarymanager.mvp.model;

import com.ljy.librarymanager.mvp.entity.Announcement;
import com.ljy.librarymanager.mvp.entity.Booking;
import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.entity.Borrow;
import com.ljy.librarymanager.mvp.entity.Category;
import com.ljy.librarymanager.mvp.entity.User;
import com.ljy.librarymanager.mvp.presenter.SearchBarPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class SearchBarModel {

    @Inject
    public SearchBarModel() {
    }

    public void searchAnnouncement(final SearchBarPresenter searchBarPresenter, final String key) {
        BmobQuery<Announcement> bmobQuery = new BmobQuery<>();
        bmobQuery.order("-createdAt");
        bmobQuery.findObjects(new FindListener<Announcement>() {
            @Override
            public void done(List<Announcement> list, BmobException e) {
                if (e == null) {
                    List<Announcement> result = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getContent().contains(key)
                                || list.get(i).getAccount().contains(key)) {
                            result.add(list.get(i));
                        }
                    }
                    searchBarPresenter.searchAnnouncementSuccess(result);
                } else {
                    searchBarPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });

    }

    public void searchBooking(final SearchBarPresenter searchBarPresenter,final String key) {
        BmobQuery<Booking> bmobQuery = new BmobQuery<>();
        bmobQuery.order("-createdAt");
        bmobQuery.findObjects(new FindListener<Booking>() {
            @Override
            public void done(List<Booking> list, BmobException e) {
                if (e == null) {
                    List<Booking> result = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getBookId().contains(key)
                                || list.get(i).getBookName().contains(key)
                                || list.get(i).getUser().contains(key)) {
                            result.add(list.get(i));
                        }
                    }
                    searchBarPresenter.searchBookingSuccess(result);
                } else {
                    searchBarPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    public void searchBooks(final SearchBarPresenter searchBarPresenter, final String key) {
        BmobQuery<Books> bmobQuery = new BmobQuery<>();
        bmobQuery.order("-createdAt");
        bmobQuery.findObjects(new FindListener<Books>() {
            @Override
            public void done(List<Books> list, BmobException e) {
                if (e == null) {
                    List<Books> result = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getBookName().contains(key)
                                || list.get(i).getAuthor().contains(key)
                                || list.get(i).getCategory().contains(key)
                                || list.get(i).getPublication().contains(key)
                                || list.get(i).getSummary().contains(key)) {
                            result.add(list.get(i));
                        }
                    }
                    searchBarPresenter.searchBooksSuccess(result);
                } else {
                    searchBarPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    public void searchBorrow(final SearchBarPresenter searchBarPresenter, final String key) {
        BmobQuery<Borrow> bmobQuery = new BmobQuery<>();
        bmobQuery.order("-createdAt");
        bmobQuery.findObjects(new FindListener<Borrow>() {
            @Override
            public void done(List<Borrow> list, BmobException e) {
                if (e == null) {
                    List<Borrow> result = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getBookName().contains(key)
                                || list.get(i).getUser().contains(key)
                                || list.get(i).getBookId().contains(key)
                                || list.get(i).getManager().contains(key)) {
                            result.add(list.get(i));
                        }
                    }
                    searchBarPresenter.searchBorrowSuccess(result);
                } else {
                    searchBarPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    public void searchUser(final SearchBarPresenter searchBarPresenter, final String key) {
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.order("-createdAt");
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {
                    List<User> result = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getUsername().contains(key)
                                || list.get(i).getAccount().contains(key)) {
                            result.add(list.get(i));
                        }
                    }
                    searchBarPresenter.searchUserSuccess(result);
                } else {
                    searchBarPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    public void searchCategory(final SearchBarPresenter searchBarPresenter, final String key) {
        BmobQuery<Category> bmobQuery = new BmobQuery<>();
        bmobQuery.order("-createdAt");
        bmobQuery.findObjects(new FindListener<Category>() {
            @Override
            public void done(List<Category> list, BmobException e) {
                if (e == null) {
                    List<Category> result = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getCategory_name().contains(key)) {
                            result.add(list.get(i));
                        }
                    }
                    searchBarPresenter.searchCategorySuccess(result);
                } else {
                    searchBarPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

}
