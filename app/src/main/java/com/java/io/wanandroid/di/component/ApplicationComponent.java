package com.java.io.wanandroid.di.component;

import android.content.Context;

import com.java.io.wanandroid.di.module.ApplicationModule;
import com.java.io.wanandroid.di.scope.ContextLife;
import com.java.io.wanandroid.di.scope.PerApp;

import dagger.Component;

/**
 * @author huguojin
 */

@PerApp
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ContextLife("Application")
    Context getApplication();
}
