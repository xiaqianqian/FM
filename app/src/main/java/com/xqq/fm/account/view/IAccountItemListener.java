package com.xqq.fm.account.view;

import com.xqq.fm.data.local.bill.entity.Bill;

/**
 * Created by xqq on 2017/3/28.
 */

public interface IAccountItemListener {
    void setCalculator(int visible);

    void showCategory(String payOrIncome);

    void showCardDialog();// 显示账户

    void showDateSelect();

    void updateBill(Bill bill);

    void saveBill(Bill bill);
}
