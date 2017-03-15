package com.lies.malped.utils;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.common.base.ui.BaseActivity;
import com.common.utils.UUID;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.lies.malped.R;
import com.views.util.ToastUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiesLee on 16/9/29.
 */
public class UIHelper {

    public static final int INTENT_REQUEST_GET_IMAGES = 13;

    public static void showShakeAnim(Context context, View view, String toast) {
        Animation shake = AnimationUtils.loadAnimation(context, R.anim.shake_x);
        view.startAnimation(shake);
        view.requestFocus();
        if(!TextUtils.isEmpty(toast)){
            ToastUtil.showShortToast(context, toast);
        }

    }

    public static boolean phoneNumberValid(String number) {
        // 手机号固定在5-20范围内
        if (number.length() < 5 || number.length() > 20) {
            return false;
        }

        String match = "";
        if (number.length() != 11) {
            return false;
        } else {
            // match = "^[1]{1}[0-9]{2}[0-9]{8}$";
            match = "^(1[3456789])\\d{9}$";
        }

        // 正则匹配
        if (!"".equals(match)) {
            return number.matches(match);
        }
        return true;
    }


    /**
     * 打开选择图片及拍照页面
     * @param activity
     * @param selectionLimit 选择图片的最大数 0开始
     * @param image_uris  已经选择的图片
     */
    public static void getPictures(Activity activity, int selectionLimit, ArrayList<Uri> image_uris){
//        Config config = new Config();
//        config.setSelectionMin(1);
//        config.setSelectionLimit(selectionLimit);
//        config.setToolbarTitleRes(R.string.choice_image);
//
//        ImagePickerActivity.setConfig(config);
//
//        Intent intent = new Intent(activity, ImagePickerActivity.class);
//
//        if (image_uris != null) {
//            intent.putParcelableArrayListExtra(ImagePickerActivity.EXTRA_IMAGE_URIS, image_uris);
//        }
//        activity.startActivityForResult(intent, INTENT_REQUEST_GET_IMAGES);
    }

    /**
     * 拍照保存并上传
     * @param baseActivity
     */
    public static void takePicture(final BaseActivity baseActivity){
        List<String> pList = new ArrayList<>();
        pList.add(Manifest.permission.CAMERA);
        pList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        pList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        Dexter.checkPermissions(new MultiplePermissionsListener(){
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                //granted all
                if (report.getDeniedPermissionResponses() == null || report.getDeniedPermissionResponses().isEmpty()) {
                    //随机一个图片文件名放在固定的文件夹里面
                    String imgPath = FileUtils.getPath(baseActivity, false) + UUID.randomUUID().toString() + ".jpg";
                    File imgf = new File(imgPath);
                    if(imgf.exists()){
                        FileUtils.deleteFolder(imgf);
                    }
                    //实例化一个intent，并指定action
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //指定一个图片路径对应的file对象
                    Uri imgUri = Uri.fromFile(imgf);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
                    //启动activity
                    baseActivity.startActivityForResult(intent, 1024);
                } else {//denied permission responses isn't empty
                    DialogHelper.show2btnDialog(baseActivity, "拍照上传需要文件读写及相机权限，请打开系统设置的应用设置页面开启相关权限！",
                            "取消", "打开设备应用设置", false, null, new DialogHelper.DialogOnclickCallback() {
                        @Override
                        public void onButtonClick(Dialog dialog) {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", baseActivity.getPackageName(), null);
                            intent.setData(uri);
                            baseActivity.startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }, pList);

    }



}
