package com.java.io.wanandroid.di.component;

import android.app.Activity;
import android.content.Context;

import com.java.io.wanandroid.di.module.FragmentModule;
import com.java.io.wanandroid.di.scope.ContextLife;
import com.java.io.wanandroid.di.scope.PerFragment;

import dagger.Component;

@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getAcitivity();


}
