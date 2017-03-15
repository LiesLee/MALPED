package com.views;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gun0912.tedpicker.R;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;


/**
 * 图片展示
 */
public class ImageActivity extends Activity {

    ViewGroup ll_ProductDetail_DotGroup;// 商品图片的小圆点

    RelativeLayout imageLayout; //图片外层整个屏幕的Layout

    private SparseArray<Bitmap> bitmapArr = new SparseArray<Bitmap>();
    private ImageView[] dotImageViews;

    private ArrayList<String> imgUrls = new ArrayList<String>();


    ViewPager pager;
    ProgressBar spinner;

    private int pagerPosition;   //图片播放的位置

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  // 隐藏状态栏
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_image);

        this.imgUrls = getIntent().getStringArrayListExtra("imgUrls");
        //图片初始化编号，从0开始
        this.pagerPosition = getIntent().getIntExtra("pagerPosition", 0);

        ll_ProductDetail_DotGroup = (ViewGroup) findViewById(R.id.ll_ProductDetail_DotGroup);
        imageLayout = (RelativeLayout) findViewById(R.id.imageLayout);

        dotImageViews = new ImageView[imgUrls.size()];

        for (int i = 0; i < imgUrls.size(); i++) {
            LayoutParams margin = new LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            // 设置每个小圆点距离左边的间距
            margin.setMargins(10, 0, 0, 0);
            ImageView dotImageView = new ImageView(this);
            // 设置每个小圆点的宽高
            dotImageView.setLayoutParams(new LayoutParams(15, 15));
            dotImageViews[i] = dotImageView;
            if (i == pagerPosition) {
                // 设置当前图片为选中状态
                dotImageViews[i]
                        .setBackgroundResource(R.mipmap.advert_indicator_focused_white);
            } else {
                // 其他图片都设置未选中状态
                dotImageViews[i]
                        .setBackgroundResource(R.mipmap.advert_indicator_unfocused);
            }
            ll_ProductDetail_DotGroup.addView(dotImageViews[i], margin);
        }

        pager = (ViewPager) findViewById(R.id.product_image_pager);
        pager.setAdapter(new ImagePagerAdapter(imgUrls));
        pager.setCurrentItem(pagerPosition + 1);
        //标记当前第几副图片
        pager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                if (imgUrls.size() > 1) {
                    if (1 > position) {
                        pager.setCurrentItem(imgUrls.size(), false);
                    } else if (position > imgUrls.size()) {
                        pager.setCurrentItem(1, false);
                    } else {
                        for (int i = 0; i < dotImageViews.length; i++) {
                            dotImageViews[position - 1]
                                    .setBackgroundResource(R.mipmap.advert_indicator_focused_white);
                            if (position - 1 != i) {
                                dotImageViews[i]
                                        .setBackgroundResource(R.mipmap.advert_indicator_unfocused);
                            }
                        }
                    }
                } else {
                    for (int i = 0; i < dotImageViews.length; i++) {
                        dotImageViews[position]
                                .setBackgroundResource(R.mipmap.advert_indicator_focused_white);
                        if (position != i) {
                            dotImageViews[i]
                                    .setBackgroundResource(R.mipmap.advert_indicator_unfocused);
                        }
                    }
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

        //单击屏幕的时候返回原页面
        imageLayout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                ImageActivity.this.finish();
            }

        });

    }

    private class ImagePagerAdapter extends PagerAdapter {

        private ArrayList<String> imgUrls;
        private LayoutInflater inflater;

        ImagePagerAdapter(ArrayList<String> imgUrls) {
            if (imgUrls.size() > 1) {
                this.imgUrls = new ArrayList<String>();
                this.imgUrls.add(imgUrls.get(imgUrls.size() - 1));
                this.imgUrls.addAll(imgUrls);
                this.imgUrls.add(imgUrls.get(0));
            } else {
                this.imgUrls = imgUrls;
            }
            inflater = getLayoutInflater();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }

        @Override
        public void finishUpdate(View container) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void startUpdate(View container) {
        }

        @Override
        public int getCount() {
            return imgUrls.size();
        }

        @Override
        public Object instantiateItem(ViewGroup view, final int position) {
            View imageLayout = inflater.inflate(R.layout.item_pager_image,
                    view, false);
            PhotoView imageView = (PhotoView) imageLayout.findViewById(R.id.image);
            imageView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {

                @Override
                public void onViewTap(View view, float x, float y) {
                    ImageActivity.this.finish();
                }
            });
            Glide.with(ImageActivity.this)
                    .load(imgUrls.get(position))
                    .placeholder(R.mipmap.img_default)
                    .error(R.mipmap.img_default)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .into(imageView);

            ((ViewPager) view).addView(imageLayout, 0);
            return imageLayout;
        }
    }


}
