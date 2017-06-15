package com.xqq.fm.account.presenter;

import com.xqq.fm.account.model.calculate.CalculateModel;
import com.xqq.fm.account.model.calculate.ICalculateModel;
import com.xqq.fm.account.view.IAccountView;
import com.xqq.fm.data.local.bill.BillsResponse;
import com.xqq.fm.data.local.bill.entity.Bill;
import com.xqq.fm.data.local.card.entity.Card;
import com.xqq.fm.data.local.card.ICardModel;
import com.xqq.fm.data.local.card.db.CardModel;

import java.util.List;

/**
 * Created by xqq on 2017/3/23.
 */

public class AccountPresenter implements IAccountPresenter {

    private IAccountView accountView;
    private ICalculateModel accountModel;
    private ICardModel cardModel;
    private BillsResponse billsResponse;

    public AccountPresenter(IAccountView accountView) {
        this.accountView = accountView;
        this.accountModel = new CalculateModel();
        this.cardModel = new CardModel();
        billsResponse = new BillsResponse();
    }

    @Override
    public void count(String currData) {
        accountModel.count(currData, new ICalculateModel.CountCallback() {
            @Override
            public void onCount(String result) {
                setMoney(result);
            }
        });
    }

    @Override
    public void setMoney(String money) {
        accountView.setMoney(money);
    }

    /**
     * 显示账户选择对话框
     */
    @Override
    public void showCardDialog() {
        accountView.showCardDialog();
    }

    @Override
    public List<Card> getAllCard() {
        List<Card> cards = cardModel.getAllCard();
        return cards;
    }

    @Override
    public void saveBill(Bill bill) {
        billsResponse.saveBill(bill);
    }

    @Override
    public void updateBill(Bill bill) {
        billsResponse.updateBill(bill);
    }
}
