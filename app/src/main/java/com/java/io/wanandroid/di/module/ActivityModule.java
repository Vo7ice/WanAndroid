package com.java.io.wanandroid.di.module;

import android.app.Activity;
import android.content.Context;

import com.java.io.wanandroid.di.scope.ContextLife;
import com.java.io.wanandroid.di.scope.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * @author huguojin
 */
@Module
public class  ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    @PerActivity
    @ContextLife("Activity")
    public Context provideActivityContext() {
        return mActivity;
    }

    @Provides
    @PerActivity
    public Activity provideActivity() {
        return mActivity;
    }

}

