package com.java.io.wanandroid.di.module;

import android.app.Service;
import android.content.Context;

import com.java.io.wanandroid.di.scope.ContextLife;
import com.java.io.wanandroid.di.scope.PerService;

import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {

    private Service mService;

    public ServiceModule(Service service) {
        mService = service;
    }

    @Provides
    @PerService
    @ContextLife("Service")
    public Context provideServiceContext() {
        return mService;
    }
}
