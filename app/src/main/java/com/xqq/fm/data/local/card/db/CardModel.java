package com.xqq.fm.data.local.card.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xqq.fm.MyApplication;
import com.xqq.fm.data.local.card.entity.Card;
import com.xqq.fm.data.local.card.ICardModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xqq on 2017/3/27.
 */

public class CardModel implements ICardModel {

    private CardDBOpenHelper helper = null;

    public CardModel() {
        helper = new CardDBOpenHelper(MyApplication.getContext());
    }

    @Override
    public void addCard(Card card) {
        SQLiteDatabase database = null;

        try {
            String sql = "insert into card(cardNumber, cardType) values (?, ?)";
            database = helper.getWritableDatabase();
            database.execSQL(sql, new Object[]{card.getCardNumber(), card.getCardType()});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
        }
    }

    @Override
    public List<Card> getAllCard() {
        List<Card> cards = new ArrayList<>();
        SQLiteDatabase database = null;
        try {
            String sql = "select * from card";
            database = helper.getReadableDatabase();
            Cursor cursor = database.rawQuery(sql, null);

            while (cursor.moveToNext()) {
                cards.add(new Card(cursor.getString(cursor.getColumnIndex("cardNumber"))
                        , cursor.getString(cursor.getColumnIndex("cardType"))));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return cards;
    }
}