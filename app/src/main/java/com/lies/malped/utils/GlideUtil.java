package com.lies.malped.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.lies.malped.R;


/**
 * glide图片加载工具类
 * Created by wuchaowen on 2016/4/13.
 */
public class GlideUtil {
    public static void loadImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .placeholder(R.mipmap.bg_default)
                .error(R.mipmap.bg_default)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView);
    }

    public static void loadImage(Context context, int id, ImageView imageView) {
        Glide.with(context)
                .load(id)
                .placeholder(R.mipmap.bg_default)
                .dontAnimate()
                .error(R.mipmap.bg_default)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView);
    }


    public static void loadLocalImage(Context context, String url, final ImageView imageView) {
        Glide.with(context)
                .load("file://" + url)
                .placeholder(R.mipmap.bg_default)
                .error(R.mipmap.bg_default)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(new ImageViewTarget<GlideDrawable>(imageView) {
                    @Override
                    protected void setResource(GlideDrawable resource) {
                        imageView.setImageDrawable(resource);
                    }
                });
    }

    public static void loadImage(Activity context, String url, ImageView imageView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (context.isDestroyed()) return;
        }
        Glide.with(context)
                .load(url)
                .placeholder(R.mipmap.bg_default)
                .error(R.mipmap.bg_default)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView);
    }

    public static void loadImage(Activity context, int id, ImageView imageView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (context.isDestroyed()) return;
        }
        Glide.with(context)
                .load(id)
                .placeholder(R.mipmap.bg_default)
                .error(R.mipmap.bg_default)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView);
    }


    public static void loadLocalImage(Activity context, String url, final ImageView imageView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (context.isDestroyed()) return;
        }
        Glide.with(context)
                .load("file://" + url)
                .placeholder(R.mipmap.bg_default)
                .error(R.mipmap.bg_default)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(new ImageViewTarget<GlideDrawable>(imageView) {
                    @Override
                    protected void setResource(GlideDrawable resource) {
                        imageView.setImageDrawable(resource);
                    }
                });
    }

    public static void loadImage(Fragment context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .placeholder(R.mipmap.bg_default)
                .error(R.mipmap.bg_default)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView);
    }

    public static void loadImage(Fragment context, int id, ImageView imageView) {
        Glide.with(context)
                .load(id)
                .placeholder(R.mipmap.bg_default)
                .error(R.mipmap.bg_default)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView);
    }


    public static void loadLocalImage(Fragment context, String url, final ImageView imageView) {
        Glide.with(context)
                .load("file://" + url)
                .placeholder(R.mipmap.bg_default)
                .error(R.mipmap.bg_default)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(new ImageViewTarget<GlideDrawable>(imageView) {
                    @Override
                    protected void setResource(GlideDrawable resource) {
                        imageView.setImageDrawable(resource);
                    }
                });
    }


}
