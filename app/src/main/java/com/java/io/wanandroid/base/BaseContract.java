package com.java.io.wanandroid.base;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * @author huguojin
 */
public interface BaseContract {

    interface BaseView {

        /**
         * 显示进度中
         */
        void showLoading();

        /**
         * 隐藏进度
         */
        void hideLoading();

        /**
         * 显示请求成功
         * @param message ert
         */
        void showSuccess(String message);

        /**
         * 失败重试
         * @param message ret
         */
        void showFaild(String message);

        /**
         * 显示当前网络不可用
         */
        void showNoNet();

        /**
         * 重试
         */
        void onRetry();

        /**
         * 绑定生命周期
         *
         * @param <T>
         * @return <T>
         */
        <T> LifecycleTransformer<T> bindToLife();
    }

    interface BasePresenter<T extends BaseContract.BaseView> {

        /**
         * 绑定ui
         * @param view BaseView
         */
        void onAttachView(T view);

        /**
         * 解除绑定
         */
        void onDetachView();
    }
}
