package com.common.base.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.gun0912.tedpicker.R;
import com.views.ViewsHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter 基类,带自动加载更多、拖动、删除等等功能
 * 文档: https://github.com/CymChad/BaseRecyclerViewAdapterHelper/wiki/%E9%A6%96%E9%A1%B5
 * 领取奖励  特殊 adapter
 */
public abstract class BaseAdapterForMultiItem<T extends MultiItemEntity> extends BaseMultiItemQuickAdapter<T> {


    public BaseAdapterForMultiItem(Context ctx, List<T> data) {
        super(data);
        ViewsHelper.initLoadMoreLayout(ctx, this);
    }

    /*
     * 设置数据
     * @param data
     */
    public void setData(List<T> data){
        if (data != null && data.size() > 0){
            setNewData(data);
            openLoadMore(getData().size());
        }else{
            setNewData(new ArrayList<T>());
        }

    }

    /**
     * 在尾部添加数据
     * @param data
     */
    public void addNewData(List<T> data){
//        loadComplete();
        if (data != null && data.size() > 0){
            addData(data);
//            if(data.size() > 0){
//                openLoadMore(getData().size());
//            }
        }else{
            loadComplete();
            if(getData() != null && getData().size() > 0){
                View view = mLayoutInflater.inflate(R.layout.load_more_failed_end, (ViewGroup)null);
                setLoadMoreFailedView(view);
                showLoadMoreFailedView();
            }
        }
    }

    /**
     * 获取当前position在数据列表的位置,不包括header
     * @param baseViewHolder
     * @return
     */
    public int getFinalPositionOnList(BaseViewHolder baseViewHolder){
        return baseViewHolder.getLayoutPosition() - getHeaderLayoutCount();
    }

    /**
     * 获取当前position在adapter的位置,包括header在内
     * @param baseViewHolder
     * @return
     */
    public int getFinalPositionOnAdapter(BaseViewHolder baseViewHolder){
        return baseViewHolder.getLayoutPosition() + getHeaderLayoutCount();
    }

    /**
     * 获取当前position在adapter的位置,包括header在内
     * @param positionOnList
     * @return
     */
    public int getFinalPositionOnAdapter(int positionOnList){
        return positionOnList + getHeaderLayoutCount();
    }




}