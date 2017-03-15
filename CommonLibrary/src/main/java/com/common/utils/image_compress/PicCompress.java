package com.common.utils.image_compress;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.socks.library.KLog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arlen on 2016/6/30 10:47.
 */
public class PicCompress {

    private Activity mContext;

    public interface HandlePicCompressCallBack{
        void callBack(boolean isSuccess, String picpath);
    }

    public interface HandlePicArrayCompressCallBack {
        void callBack(boolean isSuccess, List<String> picpaths);
    }

    public static void CompressImage(final String picPath, final Activity act, final HandlePicCompressCallBack callBack){
        KLog.e("picString picPath 0=="+picPath+"  size=" + FileSizeUtil.getAutoFileOrFilesSize(picPath));
        new Thread(new Runnable() {
            @Override
            public void run() {
                File file = new File(picPath);
                BitmapFactory.Options options = FileSizeUtil.getBitmapOptions(file.getPath());
                int screenMax = Math.max(DensityUtils.getWindowWidth(act),
                        DensityUtils.getWindowHeight(act));
                int imgMax = Math.max(options.outWidth, options.outHeight);
                int inSimpleSize = 1;
                if (screenMax <= imgMax) {
                    inSimpleSize = Math.max(screenMax, imgMax) / Math.min(screenMax, imgMax);
                }
                final String picString = FileSizeUtil.compressBitmap(act,
                        file.getAbsolutePath(),
                        Bitmap.CompressFormat.JPEG,
                        options.outWidth / inSimpleSize,
                        options.outHeight / inSimpleSize,
                        false);

                act.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(!TextUtils.isEmpty(picString)){
                            KLog.e("picString 1=="+picString);
                            callBack.callBack(true,picString);
                        }else {
                            KLog.e("picString 2=="+picString);
                            callBack.callBack(false,picString);
                        }
                    }
                });

            }
        }).start();
    }

    public static void CompressImage(final List<String> picPaths, final Activity act, final HandlePicArrayCompressCallBack callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<String> picCompress = new ArrayList<String>();
                for (int i = 0; i < picPaths.size(); i++) {
                    File file = new File(picPaths.get(i));
                    KLog.e("picString picPath 0==" + i + "  " + file.getPath() + "  size=" + FileSizeUtil.getAutoFileOrFilesSize(file.getPath()));
                    BitmapFactory.Options options = FileSizeUtil.getBitmapOptions(file.getPath());
                    int screenMax = Math.max(DensityUtils.getWindowWidth(act),
                            DensityUtils.getWindowHeight(act));
                    int imgMax = Math.max(options.outWidth, options.outHeight);
                    int inSimpleSize = 1;
                    if(imgMax >= 1000){
                        inSimpleSize = (imgMax / 1000);
                        if(inSimpleSize > 2){
                            inSimpleSize = 3;
                        }else if(inSimpleSize > 4){
                            inSimpleSize = 4;
                        }
                    }
                    //压缩
                    final String picString = FileSizeUtil.compressBitmap(act,
                            file.getAbsolutePath(),
                            Bitmap.CompressFormat.JPEG,
                            options.outWidth / inSimpleSize,
                            options.outHeight / inSimpleSize,
                            false);
                    picCompress.add(picString);
                }

                act.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (picCompress.size() > 0) {
                            KLog.e("picString 1==" + picCompress.size());
                            callBack.callBack(true, picCompress);
                        } else {
                            KLog.e("picString 2==" + picCompress.size());
                            callBack.callBack(false, picCompress);
                        }
                    }
                });

            }
        }).start();
    }
}
