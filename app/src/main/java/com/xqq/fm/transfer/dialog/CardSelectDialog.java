package com.xqq.fm.transfer.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.xqq.fm.R;
import com.xqq.fm.data.local.card.entity.Card;

import java.util.List;

/**
 * Created by xqq on 2017/3/27.
 */

public class CardSelectDialog extends Dialog implements View.OnClickListener, AdapterView.OnItemClickListener {

    private TextView tvAddAccount;
    private ListView lvAccountType;
    private IAccountSelectDialogClickListener listener;

    private CardSelectDialogAdapter adapter;
    private List<Card> cards;

    public CardSelectDialog(Context context, List<Card> cards,
                            IAccountSelectDialogClickListener listener) {
        this(context, R.style.dialog, cards, listener);
    }

    public CardSelectDialog(Context context, int themeResId,
                            List<Card> cards, IAccountSelectDialogClickListener listener) {
        super(context, themeResId);

        setContentView(R.layout.dialog_account_select);

        this.listener = listener;
        this.cards = cards;

        Window window = getWindow();

        // 设置显示动画
        window.setWindowAnimations(R.style.dialog_account_select_anim);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = getWindow().getWindowManager().getDefaultDisplay().getHeight();

        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        onWindowAttributesChanged(wl);
        setCanceledOnTouchOutside(true);

        initView();
    }

    private void initView() {
        tvAddAccount = (TextView) findViewById(R.id.tv_add_account_method);
        lvAccountType = (ListView) findViewById(R.id.lv_account_select);

        tvAddAccount.setOnClickListener(this); // 设置添加账户的点击事件

        // 初始化listView
        listViewInit();
    }

    private void listViewInit() {

        // 设置适配器
        adapter = new CardSelectDialogAdapter(cards);
        lvAccountType.setAdapter(adapter);
        lvAccountType.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add_account_method:
                listener.addCardOnClick();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        listener.setCardName(cards.get(position).toString());
        dismiss();
    }

    public interface IAccountSelectDialogClickListener {
        void addCardOnClick();

        void setCardName(String content);
    }

    // 更新账户列表
    public void refreshData(List<Card> cards) {
        this.cards = cards;
        adapter.replaceData(cards);
    }

    private static class CardSelectDialogAdapter extends BaseAdapter {
        private List<Card> accounts;

        public CardSelectDialogAdapter(List<Card> accounts) {
            setAccounts(accounts);
        }

        @Override
        public Object getItem(int position) {
            return accounts.get(position);
        }

        public void setAccounts(List<Card> accounts) {
            this.accounts = accounts;
        }

        public void replaceData(List<Card> accounts) {
            this.accounts = accounts;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                convertView = inflater.inflate(R.layout.activity_category_item, null);
            }

            TextView textView = (TextView) convertView.findViewById(R.id.tv_item_category);
            textView.setText(accounts.get(position).toString());
            return convertView;
        }

        @Override
        public int getCount() {
            return accounts.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }
}
