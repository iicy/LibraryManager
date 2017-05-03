package com.ljy.librarymanager.di.component;

import android.app.Activity;
import android.content.Context;

import com.ljy.librarymanager.di.module.FragmentModule;
import com.ljy.librarymanager.di.scope.ContextLife;
import com.ljy.librarymanager.di.scope.PerFragment;
import com.ljy.librarymanager.mvp.ui.fragment.BookingListFragment;
import com.ljy.librarymanager.mvp.ui.fragment.BorrowListFragment;
import com.ljy.librarymanager.mvp.ui.fragment.CategoryListFragment;
import com.ljy.librarymanager.mvp.ui.fragment.HomeListFragment;
import com.ljy.librarymanager.mvp.ui.fragment.ManagerAnnouncementFragment;
import com.ljy.librarymanager.mvp.ui.fragment.ManagerBookingFragment;
import com.ljy.librarymanager.mvp.ui.fragment.ManagerBorrowFragment;
import com.ljy.librarymanager.mvp.ui.fragment.ManagerCategoryFragment;
import com.ljy.librarymanager.mvp.ui.fragment.ManagerUserFragment;
import com.ljy.librarymanager.mvp.view.CollectionListView;

import dagger.Component;

/**
 * Created by jiayu on 2017/3/11.
 */

@PerFragment
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();

    void inject(HomeListFragment homeListFragment);

    void inject(BookingListFragment bookingListFragment);

    void inject(BorrowListFragment borrowListFragment);

    void inject(CategoryListFragment categoryListFragment);

    void inject(CollectionListView collectionListView);

    void inject(ManagerAnnouncementFragment managerAnnouncementFragment);

    void inject(ManagerBookingFragment managerBookingFragment);

    void inject(ManagerBorrowFragment managerBorrowFragment);

    void inject(ManagerCategoryFragment managerCategoryFragment);

    void inject(ManagerUserFragment managerUserFragment);
}
