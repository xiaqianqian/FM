package com.xqq.fm.account.presenter;

import com.xqq.fm.data.local.bill.entity.Bill;
import com.xqq.fm.data.local.card.entity.Card;

import java.util.List;

/**
 * Created by xqq on 2017/3/23.
 */

public interface IAccountPresenter {
    void count(String currData);

    void setMoney(String money);

    void showCardDialog();

    List<Card> getAllCard();

    void saveBill(Bill bill);

    void updateBill(Bill bill);
}
