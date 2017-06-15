package com.xqq.fm.transfer.model;

import com.xqq.fm.data.local.bill.IBillModel;
import com.xqq.fm.data.local.bill.db.BillModel;
import com.xqq.fm.data.local.card.ICardModel;
import com.xqq.fm.data.local.card.db.CardModel;
import com.xqq.fm.data.local.card.entity.Card;
import com.xqq.fm.data.local.card.entity.CardTransferDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xqq on 2017/4/16.
 * <p>
 * 负责转账模块的数据处理
 */

public class TransferResponse {

    private ICardModel cardModel;
    private IBillModel billModel;


    public TransferResponse() {
        cardModel = new CardModel();
        billModel = new BillModel();
    }

    public List<CardTransferDetail> getCardTransferDetail() {

        List<Card> cards = cardModel.getAllCard();
        List<CardTransferDetail> details = new ArrayList<>();
        for (Card card : cards) {
            CardTransferDetail detail = new CardTransferDetail();
            detail.setCardType(card.getCardType());
            detail.setCardNumber(card.getCardNumber());
            detail.setIncome(billModel.getPayOrInMoneyByCardNumber("收入", card.getCardNumber()));
            detail.setPay(billModel.getPayOrInMoneyByCardNumber("支出", card.getCardNumber()));
            details.add(detail);
        }
        return details;
    }
}