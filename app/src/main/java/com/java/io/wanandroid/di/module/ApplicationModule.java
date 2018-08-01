package com.java.io.wanandroid.di.module;

import android.content.Context;

import com.java.io.wanandroid.base.App;
import com.java.io.wanandroid.di.scope.ContextLife;
import com.java.io.wanandroid.di.scope.PerApp;

import dagger.Module;
import dagger.Provides;

/**
 * @author huguojin
 */
@Module
public class ApplicationModule {

    private App mApplication;

    public ApplicationModule(App application) {
        mApplication = application;
    }

    @Provides
    @PerApp
    @ContextLife("Application")
    public Context provideApplicationContext() {
        return mApplication.getApplicationContext();
    }
}
