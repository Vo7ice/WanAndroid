package com.java.io.wanandroid.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle2.components.support.RxFragment;

import javax.inject.Inject;


/**
 * @author huguojin
 */
public abstract class BaseFragment<T extends BaseContract.BasePresenter> extends RxFragment
        implements BaseContract.BaseView {


}
