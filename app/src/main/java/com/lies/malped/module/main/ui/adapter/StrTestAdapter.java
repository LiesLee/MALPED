package com.lies.malped.module.main.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseViewHolder;
import com.common.base.ui.BaseAdapter;
import com.lies.malped.R;

import java.util.List;

/**
 * Created by LiesLee on 17/4/18.
 */

public class StrTestAdapter extends BaseAdapter<String> {

    public StrTestAdapter(Context ctx) {
        super(ctx, R.layout.item_test, null);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, String s) {
        baseViewHolder.setText(R.id.tv_str, getFinalPositionOnList(baseViewHolder)+"");
    }
}
