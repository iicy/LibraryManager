package com.ljy.librarymanager.di.component;

import android.app.Activity;
import android.content.Context;

import com.ljy.librarymanager.di.module.FragmentModule;
import com.ljy.librarymanager.di.scope.ContextLife;
import com.ljy.librarymanager.di.scope.PerFragment;

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

//    void inject(NewsListFragment newsListFragment);
}
