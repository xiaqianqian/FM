package com.xqq.fm.data.local.card.entity;

import java.io.Serializable;

/**
 * Created by xqq on 2017/3/27.
 * <p>
 * 账户类
 */

public class Card implements Serializable {
    private String cardNumber;
    private String cardType;

    public Card() {
    }

    public Card(String cardNumber, String cardType) {
        this.cardNumber = cardNumber;
        this.cardType = cardType;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    @Override
    public String toString() {
        return getCardType() + " " + getCardNumber();
    }
}
