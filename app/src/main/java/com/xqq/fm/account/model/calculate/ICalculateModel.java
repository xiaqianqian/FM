package com.xqq.fm.account.model.calculate;

/**
 * Created by xqq on 2017/3/23.
 */

public interface ICalculateModel {

    void count(String currData, CountCallback countCallback);

    interface CountCallback{
        void onCount(String result);
    }
}
