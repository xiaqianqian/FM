package com.xqq.fm.base;

/**
 * Created by xqq on 2017/3/18.
 */

public interface BaseView<T> {

    // 为View设置Presenter
    void setPresenter(T presenter);

}
