package com.lies.malped.module.main.ui.adapter;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.common.base.ui.BaseActivity;
import com.lies.malped.R;
import com.lies.malped.utils.DialogHelper;
import com.lies.malped.utils.GlideUtil;
import com.views.ImageActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiesLee on 16/10/20.
 */
public class DynamicImgsAdapter extends RecyclerView.Adapter<DynamicImgsAdapter.Holder> {

    List<String> imgs = new ArrayList<>();
    BaseActivity baseActivity;

    public DynamicImgsAdapter(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(baseActivity).inflate(R.layout.publish_dynamic_imgs_item, null));
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {

        if(imgs.size() < 9 && position == imgs.size()){
            holder.iv_publish_dynamic_imgs.setImageResource(R.mipmap.icon_add_dynamic_imgs);
            holder.iv_publish_dynamic_imgs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { //选择图片

                }
            });
            holder.iv_remove_imgs.setVisibility(View.GONE);

        }else{
            GlideUtil.loadImage(baseActivity,imgs.get(position), holder.iv_publish_dynamic_imgs);
            holder.iv_remove_imgs.setVisibility(View.VISIBLE);
            holder.iv_remove_imgs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogHelper.show2btnDialog(baseActivity, "是否删除？", "取消", "确定", false, null, new DialogHelper.DialogOnclickCallback() {
                        @Override
                        public void onButtonClick(Dialog dialog) {
                            notifyItemRemoved(holder.getLayoutPosition());
                            imgs.remove(holder.getLayoutPosition());
                        }
                    });
                }
            });

            holder.iv_publish_dynamic_imgs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return imgs == null ? 0 : imgs.size();
    }


    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder{
        public ImageView iv_remove_imgs;
        public ImageView iv_publish_dynamic_imgs;

        public Holder(View itemView) {
            super(itemView);
            iv_remove_imgs = (ImageView) itemView.findViewById(R.id.iv_remove_imgs);
            iv_publish_dynamic_imgs = (ImageView) itemView.findViewById(R.id.iv_publish_dynamic_imgs);
        }

    }

}
