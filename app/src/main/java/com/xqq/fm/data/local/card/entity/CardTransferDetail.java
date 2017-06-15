package com.xqq.fm.data.local.card.entity;

/**
 * Created by xqq on 2017/4/16.
 * <p>
 * 转账模块分析的详细类
 */
public class CardTransferDetail {

    private String cardNumber;
    private String cardType;
    private double pay;
    private double income;

    public CardTransferDetail() {
    }

    public CardTransferDetail(String cardNumber, String cardType,
                              double pay, double income) {
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.pay = pay;
        this.income = income;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public void setPay(double pay) {
        this.pay = pay;
    }

    public double getIncome() {
        return income;
    }

    public double getPay() {
        return pay;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardType() {
        return cardType;
    }
}

