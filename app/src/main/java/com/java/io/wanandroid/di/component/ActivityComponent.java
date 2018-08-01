package com.java.io.wanandroid.di.component;


import android.app.Activity;
import android.content.Context;

import com.java.io.wanandroid.di.module.ActivityModule;
import com.java.io.wanandroid.di.scope.ContextLife;
import com.java.io.wanandroid.di.scope.PerActivity;

import dagger.Component;

/**
 * @author huguojin
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    @ContextLife
    Context getActivityContext();

    @ContextLife
    Context getApplicationContext();

    Activity getActivity();

}
