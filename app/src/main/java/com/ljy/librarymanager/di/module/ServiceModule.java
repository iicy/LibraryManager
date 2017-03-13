package com.ljy.librarymanager.di.module;

import android.app.Service;
import android.content.Context;

import com.ljy.librarymanager.di.scope.ContextLife;
import com.ljy.librarymanager.di.scope.PerService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jiayu on 2017/3/11.
 */

@Module
public class ServiceModule {
    private Service mService;

    public ServiceModule(Service service) {
        mService = service;
    }

    @Provides
    @PerService
    @ContextLife("Service")
    public Context ProvideServiceContext() {
        return mService;
    }
}
