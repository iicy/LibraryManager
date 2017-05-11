package com.ljy.librarymanager.di.component;

import android.app.Activity;
import android.content.Context;

import com.ljy.librarymanager.di.module.ActivityModule;
import com.ljy.librarymanager.di.scope.ContextLife;
import com.ljy.librarymanager.di.scope.PerActivity;
import com.ljy.librarymanager.mvp.ui.activity.AddAnnouncementActivity;
import com.ljy.librarymanager.mvp.ui.activity.AddBookActivity;
import com.ljy.librarymanager.mvp.ui.activity.AddBorrowActivity;
import com.ljy.librarymanager.mvp.ui.activity.AddCategoryActivity;
import com.ljy.librarymanager.mvp.ui.activity.AddUserActivity;
import com.ljy.librarymanager.mvp.ui.activity.BookInfoActivity;
import com.ljy.librarymanager.mvp.ui.activity.BookListActivity;
import com.ljy.librarymanager.mvp.ui.activity.LoginActivity;
import com.ljy.librarymanager.mvp.ui.activity.MainActivity;
import com.ljy.librarymanager.mvp.ui.activity.ManagerActivity;
import com.ljy.librarymanager.mvp.ui.activity.ManagerAnnouncementInfoActivity;
import com.ljy.librarymanager.mvp.ui.activity.ManagerBookActivity;
import com.ljy.librarymanager.mvp.ui.activity.ManagerBookInfoActivity;
import com.ljy.librarymanager.mvp.ui.activity.ManagerBorrowInfoActivity;
import com.ljy.librarymanager.mvp.ui.activity.ManagerCommentsActivity;
import com.ljy.librarymanager.mvp.ui.activity.ManagerUserInfoActivity;
import com.ljy.librarymanager.mvp.ui.activity.ModifyPasswordActivity;
import com.ljy.librarymanager.mvp.ui.activity.SearchBarActivity;
import com.ljy.librarymanager.mvp.ui.activity.SearchResultActivity;
import com.ljy.librarymanager.mvp.ui.activity.SendCommentActivity;
import com.ljy.librarymanager.mvp.ui.activity.UserInfoActivity;

import dagger.Component;

/**
 * Created by jiayu on 2017/3/11.
 */

@PerActivity
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();

    void inject(LoginActivity loginActivity);

    void inject(SearchBarActivity searchBarActivity);

    void inject(SearchResultActivity searchResultActivity);

    void inject(MainActivity mainActivity);

    void inject(ManagerActivity managerActivity);

    void inject(AddAnnouncementActivity addAnnouncementActivity);

    void inject(AddUserActivity addUserActivity);

    void inject(AddCategoryActivity addCategoryActivity);

    void inject(AddBookActivity addBookActivity);

    void inject(AddBorrowActivity addBorrowActivity);

    void inject(BookInfoActivity bookInfoActivity);

    void inject(BookListActivity bookListActivity);

    void inject(ManagerBookActivity managerBookActivity);

    void inject(ManagerBookInfoActivity managerBookInfoActivity);

    void inject(ManagerAnnouncementInfoActivity managerAnnouncementInfoActivity);

    void inject(ManagerBorrowInfoActivity managerBorrowInfoActivity);

    void inject(ManagerUserInfoActivity managerUserInfoActivity);

    void inject(ManagerCommentsActivity managerCommentsActivity);

    void inject(SendCommentActivity sendCommentActivity);

    void inject(ModifyPasswordActivity modifyPasswordActivity);

    void inject(UserInfoActivity userInfoActivity);
}
