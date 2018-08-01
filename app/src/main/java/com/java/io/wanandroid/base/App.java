package com.java.io.wanandroid.base;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.Utils;
import com.java.io.wanandroid.BuildConfig;
import com.java.io.wanandroid.di.component.ApplicationComponent;
import com.java.io.wanandroid.di.component.DaggerApplicationComponent;
import com.java.io.wanandroid.di.module.ApplicationModule;

/**
 * @author huguojin
 */
public class App extends Application {

    private ApplicationComponent mApplicationComponent;
    private static App sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        initApplicationComponent();
        Utils.init(this);
        initARouter();
    }

    private void initARouter() {
        // 这两行必须写在init之前，否则这些配置在init过程中将无效
        if (BuildConfig.DEBUG) {
            ARouter.openLog();  // 打印日志
            ARouter.openDebug(); // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        // 尽可能早，推荐在Application中初始化
        ARouter.init(this);
    }


    private void initApplicationComponent() {
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }

    public static App getInstance() {
        return sInstance;
    }
}
