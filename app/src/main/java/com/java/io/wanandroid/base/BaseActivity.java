package com.java.io.wanandroid.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import javax.inject.Inject;

/**
 * @author huguojin
 */
public abstract class BaseActivity<T extends BaseContract.BasePresenter> extends RxAppCompatActivity
        implements BaseContract.BaseView {

    @Nullable
    @Inject
    private T mPresenter;

    @Nullable
    protected Toolbar mToolbar;

    protected abstract int getLayoutId();

    protected abstract void initInjector();

    protected abstract void initView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    protected boolean showHomeAsUp() {
        return false;
    }

    /**
     * 初始化toolbar
     */
    /*private void initToolBar() {
        mToolbar = findViewById(R.id.toolbar);
        if (mToolbar == null) {
            throw new NullPointerException("toolbar can not be null");
        }
        setSupportActionBar(mToolbar);
        if (null != getSupportActionBar()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(showHomeAsUp());
            // toolbar除掉阴影
            getSupportActionBar().setElevation(0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getSupportActionBar().setElevation(0);
            }
        }
    }*/

    private void onAttachView() {
        if (mPresenter != null) {
            mPresenter.onAttachView(this);
        }
    }

    private void onDetachView() {
        if (mPresenter != null) {
            mPresenter.onDetachView();
        }
    }

}
