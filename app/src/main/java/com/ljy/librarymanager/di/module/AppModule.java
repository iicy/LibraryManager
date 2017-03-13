package com.ljy.librarymanager.di.module;

import android.content.Context;

import com.ljy.librarymanager.MyApplication;
import com.ljy.librarymanager.di.scope.ContextLife;
import com.ljy.librarymanager.di.scope.PerApp;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jiayu on 2017/3/11.
 */

@Module
public class AppModule {
    private MyApplication mApplication;

    public AppModule(MyApplication application) {
        mApplication = application;
    }

    @Provides
    @PerApp
    @ContextLife("Application")
    public Context provideApplicationContext() {
        return mApplication.getApplicationContext();
    }
}
