package com.views.KeyboardPassword.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.gun0912.tedpicker.R;

import java.util.ArrayList;
import java.util.Map;

/**
 * 九宫格键盘适配器
 */
public class KeyBoardAdapter extends BaseAdapter {


    private GridView gridView;
    private Context mContext;
    private ArrayList<Map<String, String>> valueList;
    private boolean isPassWord = false;

    public KeyBoardAdapter(Context mContext, ArrayList<Map<String, String>> valueList, GridView gridView) {
        this.mContext = mContext;
        this.valueList = valueList;
        this.gridView = gridView;
    }

    @Override
    public int getCount() {
        return valueList.size();
    }

    @Override
    public Object getItem(int position) {
        return valueList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.grid_item_virtual_keyboard, null);
            viewHolder = new ViewHolder();
            viewHolder.btnKey = (TextView) convertView.findViewById(R.id.btn_keys);
            viewHolder.imgDelete = (RelativeLayout) convertView.findViewById(R.id.imgDelete);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        AbsListView.LayoutParams param = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                gridView.getHeight() / getLineNumber());
        convertView.setLayoutParams(param);

        if (position == 9) {
            viewHolder.imgDelete.setVisibility(View.INVISIBLE);
            viewHolder.btnKey.setVisibility(View.VISIBLE);
            viewHolder.btnKey.setText(valueList.get(position).get("name"));
            //viewHolder.btnKey.setBackgroundColor(Color.parseColor("#e0e0e0"));
        } else if (position == 11) {//左下角位置空着
            viewHolder.btnKey.setBackgroundResource(R.mipmap.keyboard_delete_img);
            viewHolder.imgDelete.setVisibility(View.VISIBLE);
            viewHolder.btnKey.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.imgDelete.setVisibility(View.INVISIBLE);
            viewHolder.btnKey.setVisibility(View.VISIBLE);

            viewHolder.btnKey.setText(valueList.get(position).get("name"));
        }

        if(isPassWord && position == 9){ //是密码的情况 密码的 .键不可点击
            //if(){
                convertView.setClickable(true);
                viewHolder.btnKey.setVisibility(View.INVISIBLE);
                viewHolder.btnKey.setBackgroundColor(Color.parseColor("#dddddd"));
//            }else{
//                convertView.setClickable(false);
//                viewHolder.btnKey.setBackgroundColor(Color.parseColor("#ffffff"));
//                //viewHolder.btnKey.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.selector_gird_item));
//            }
        }else{
            convertView.setClickable(false);
            viewHolder.btnKey.setVisibility(View.VISIBLE);
            //viewHolder.btnKey.setBackgroundColor(Color.parseColor("#ffffff"));
            viewHolder.btnKey.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.selector_gird_item_default));

        }
        return convertView;
    }

    public int getLineNumber(){
        if(valueList == null || valueList.size() == 0 || valueList.size() <= gridView.getNumColumns()){
            return 1;
        }else{
            return (int)Math.ceil(valueList.size() / ((double)gridView.getNumColumns()));
        }
    }

    /**
     * 存放控件
     */
    public final class ViewHolder {
        public TextView btnKey;
        public RelativeLayout imgDelete;
    }

    public boolean isPassWord() {
        return isPassWord;
    }

    public void setPassWord(boolean passWord) {
        isPassWord = passWord;
        notifyDataSetChanged();
    }
}
