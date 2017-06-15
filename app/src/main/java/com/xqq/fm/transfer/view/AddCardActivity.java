package com.xqq.fm.transfer.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.xqq.fm.R;
import com.xqq.fm.account.view.AccountActivity;
import com.xqq.fm.data.local.card.entity.Card;
import com.xqq.fm.data.local.card.ICardModel;
import com.xqq.fm.data.local.card.db.CardModel;

public class AddCardActivity extends AppCompatActivity{

    private Toolbar mToolbar;
    private ActionBar mActionBar;
    private EditText etCardType;
    private EditText etCardNumber;
    private ICardModel cardModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        initView();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        // 初始化ActionBar
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle(R.string.title_add_card);

        etCardType = (EditText) findViewById(R.id.et_card_type);
        etCardNumber = (EditText) findViewById(R.id.et_card_number);

        cardModel = new CardModel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_add_card, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_card_done:
                // 保存改账户
                String cardNumber = etCardNumber.getText().toString();
                String cardType = etCardType.getText().toString();
                cardModel.addCard(new Card(cardNumber, cardType));
                // 返回
                Intent intent = new Intent(this, AccountActivity.class);
                setResult(AccountActivity.CARD_RESULT, intent);
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
