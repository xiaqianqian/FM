package com.xqq.fm.data.local.card;

import com.xqq.fm.data.local.card.entity.Card;

import java.util.List;

/**
 * Created by xqq on 2017/3/27.
 */

public interface ICardModel {
    void addCard(Card card);
    List<Card> getAllCard();
}
