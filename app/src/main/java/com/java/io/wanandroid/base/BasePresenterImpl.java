package com.java.io.wanandroid.base;

/**
 * @author huguojin
 */
public class BasePresenterImpl<T extends BaseContract.BaseView> implements BaseContract.BasePresenter<T> {

    protected T mView;

    @Override
    public void onAttachView(T view) {
        mView = view;
    }

    @Override
    public void onDetachView() {
        if (null != mView) {
            mView = null;
        }
    }
}
