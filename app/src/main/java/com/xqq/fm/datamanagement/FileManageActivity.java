package com.xqq.fm.datamanagement;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.xqq.fm.MainActivity;
import com.xqq.fm.R;
import com.xqq.fm.datamanagement.view.DataManagementFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileManageActivity extends ListActivity {

    private List<String> items = null;
    private List<String> paths = null;
    private String rootPath = "/";
    private String curPath = "/";
    private TextView tvPath;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_file_manage);
        tvPath = (TextView) findViewById(R.id.tv_path);
        getFileDir(rootPath);
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                break;
            case R.id.btn_ok:
                Intent data = new Intent(FileManageActivity.this, MainActivity.class);
                data.putExtra("path", curPath);
                setResult(DataManagementFragment.REQUEST_PUT, data);
                break;
        }
        finish();
    }

    private void getFileDir(String filePath) {
        tvPath.setText(filePath);
        items = new ArrayList<>();
        paths = new ArrayList<>();
        File f = new File(filePath);
        File[] files = f.listFiles();
        if (!filePath.equals(rootPath)) {
            items.add("b1");
            paths.add(rootPath);
            items.add("b2");
            paths.add(f.getParent());
        }
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            items.add(file.getName());
            paths.add(file.getPath());
        }
        if (items != null && items.size() > 0) {
            setListAdapter(new MyAdapter(this, items, paths));
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        File file = new File(paths.get(position));
        curPath = paths.get(position);
        tvPath.setText(curPath);
        if (file.isDirectory()) {
            getFileDir(paths.get(position));
        }
    }

    private class MyAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private Bitmap mIcon1;
        private Bitmap mIcon2;
        private Bitmap mIcon3;
        private Bitmap mIcon4;
        private List<String> items;
        private List<String> paths;

        public MyAdapter(Context context, List<String> it, List<String> pa) {
            mInflater = LayoutInflater.from(context);
            items = it;
            paths = pa;
            mIcon1 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.back01);
            mIcon2 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.back02);
            mIcon3 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.folder);
            mIcon4 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.doc);
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.activity_file_manage_item, null);
                holder = new ViewHolder();
                holder.text = (TextView) convertView.findViewById(R.id.text);
                holder.icon = (ImageView) convertView.findViewById(R.id.icon);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            File f = new File(paths.get(position).toString());
            if (items.get(position).toString().equals("b1")) {
                holder.text.setText("返回根目录..");
                holder.icon.setImageBitmap(mIcon1);
            } else if (items.get(position).toString().equals("b2")) {
                holder.text.setText("返回上一层..");
                holder.icon.setImageBitmap(mIcon2);
            } else {
                holder.text.setText(f.getName());
                if (f.isDirectory()) {
                    holder.icon.setImageBitmap(mIcon3);
                } else {
                    holder.icon.setImageBitmap(mIcon4);
                }
            }
            return convertView;
        }

        private class ViewHolder {
            TextView text;
            ImageView icon;
        }
    }
}
