package com.java.io.wanandroid.di.module;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;

import com.java.io.wanandroid.di.scope.ContextLife;
import com.java.io.wanandroid.di.scope.PerFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {
    private Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        mFragment = fragment;
    }

    @Provides
    @PerFragment
    @ContextLife("Activity")
    public Context provideAcitivtyContext() {
        return mFragment.getActivity();
    }

    @Provides
    @PerFragment
    public Activity provideActivity() {
        return mFragment.getActivity();
    }

    @Provides
    @PerFragment
    public Fragment provideFragment() {
        return mFragment;
    }
}
