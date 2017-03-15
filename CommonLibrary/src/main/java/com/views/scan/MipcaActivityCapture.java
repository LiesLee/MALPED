package com.views.scan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.gun0912.tedpicker.R;
import com.gun0912.tedpicker.custom.adapter.SpacesItemDecoration;
import com.views.scan.camera.CameraManager;
import com.views.scan.decoding.CaptureActivityHandler;
import com.views.scan.decoding.InactivityTimer;
import com.views.scan.view.ViewfinderView;
import com.views.util.ToastUtil;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Vector;

/**
 * Initial the camera
 */
public class MipcaActivityCapture extends Activity implements Callback {

    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;

    private Button btnOrder;

    private boolean isScanPause;

//    private int from;

    private Handler myHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 14:
//                    dissmissDia();
                    try {
                        String str = (String) msg.obj;
                        JSONObject jo = new JSONObject(str);
                        int k = jo.getInt("errCode");
                        if (k != 0) {
                            Toast.makeText(getApplicationContext(),
                                    jo.getString("errDes"), Toast.LENGTH_SHORT)
                                    .show();
                        } else {
                            JSONObject dataJson = jo.getJSONObject("data");
                            String count = dataJson.getString("count");

                            btnOrder.setVisibility(View.VISIBLE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                    }
                    break;
                case 8:
//                    dissmissDia();
                    try {
                        String str = (String) msg.obj;
                        JSONObject jo = new JSONObject(str);
                        int k = jo.getInt("errCode");
                        if (k != 0) {
                            Toast.makeText(MipcaActivityCapture.this, jo.getString("errMsg"),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            String count = jo.getJSONObject("data")
                                    .getString("count");

                            Toast.makeText(MipcaActivityCapture.this, "AddPruductSucess",
                                    Toast.LENGTH_SHORT).show();
                            showScan(null);
                        }

                        myHandler.sendEmptyMessageDelayed(16, 1000);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;

                case 16:
                    if (isScanPause) {
                        resumeScan();
                    }
                    break;

            }
            return false;
        }
    });

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        from = getIntent().getIntExtra("from", 0);

        setContentView(R.layout.activity_capture);
        //ViewUtil.addTopView(getApplicationContext(), this, R.string.scan_card);
        CameraManager.init(getApplication());
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);

        initTitle();


//        titleTv = (TextView) findViewById(R.id.titleTv);
//        titleTv.setText(LanguageWord.instance(this).getLanguageWord("Scan"));

//        status_view = (CheckBox) findViewById(R.id.status_view);

//        btnOrder = (Button) findViewById(R.id.btnOrder);

//        ((ImageButton) findViewById(R.id.backBtn)).setImageResource(R.drawable.back_bg);

//        status_view.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    status_view.setText(LanguageWord.instance(MipcaActivityCapture.this).getLanguageWord("Light_OFF"));
//                    CameraManager.get().openLight();
//                } else {
//                    status_view.setText(LanguageWord.instance(MipcaActivityCapture.this).getLanguageWord("Light_ON"));
//                    CameraManager.get().offLight();
//                }
//            }
//        });

        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);

//        loadingDialog = new ProgressDialog(this);
//        loadingDialog.setMessage(tip);
    }

    private void initTitle() {
//        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBack(view);
//            }
//        });
//
//        ((TextView) findViewById(R.id.title)).setVisibility(View.GONE);
//
//        findViewById(R.id.save).setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        loadingDialog.show();
//        ConnectHelper.doOrderCount(myHandler, PPPOSApplication.token,
//                PPPOSApplication.mallId, LanguageWord.getLanguageNow() + "");
        resumeScan();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pauseScan();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    private void resumeScan() {
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;
        isScanPause = false;
    }

    private void pauseScan() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
        isScanPause = true;
    }

    /**
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText();
        if (resultString.equals("")) {
            ToastUtil.showShortToast(MipcaActivityCapture.this, "无效的二维码");
        } else {
//            Intent resultIntent = new Intent();
//            Bundle bundle = new Bundle();

//            if (from == 1) {
//                Intent intent = new Intent();
//                intent.putExtra("code", resultString);
//                setResult(RESULT_OK, intent);
//                finish();
//            } else {

            pauseScan();

            if(TextUtils.isEmpty(resultString)){
                ToastUtil.showShortToast(MipcaActivityCapture.this, "无效的二维码");
                resumeScan();
            }else{
                Intent intent = new Intent();
                intent.putExtra("resultString", resultString);
                setResult(333, intent);
                finish();
            }




//                Gson gson = new Gson();
//                AddToShopcart[] shopcarts = new AddToShopcart[1];
//                shopcarts[0] = new AddToShopcart();
//                shopcarts[0].productId = "";
//                shopcarts[0].barCode = resultString;
//                shopcarts[0].count = 1;
//                String batchParam = gson.toJson(shopcarts);
//                ConnectHelper.doAddToShopcart(myHandler, PPPOSApplication.token,
//                        batchParam, PPPOSApplication.mallId,
//                        LanguageWord.getLanguageNow() + "");
//            }

//            bundle.putString("result", resultString);
//            resultIntent.putExtras(bundle);
//            this.setResult(RESULT_OK, resultIntent);
        }
//        MipcaActivityCapture.this.finish();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(MipcaActivityCapture.this, decodeFormats, characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    public void onBack(View v) {
        finish();
    }

//    public void showInput(View v) {
//        findViewById(R.id.showInputBtn).setVisibility(View.VISIBLE);
//        findViewById(R.id.backBtn).setVisibility(View.GONE);
////        titleTv.setText(LanguageWord.instance(this).getLanguageWord("Input"));
//        pauseScan();
//        findViewById(R.id.scanView).setVisibility(View.GONE);
//        findViewById(R.id.inputView).setVisibility(View.VISIBLE);
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
////        if (!imm.isActive()) {
////            //如果开启
////            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
////            //关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
////        }
//    }

    public void showScan(View v) {
//        findViewById(R.id.showInputBtn).setVisibility(View.GONE);
//        findViewById(R.id.backBtn).setVisibility(View.VISIBLE);
//        titleTv.setText(LanguageWord.instance(this).getLanguageWord("Scan"));
        resumeScan();
        findViewById(R.id.scanView).setVisibility(View.VISIBLE);
//        findViewById(R.id.inputView).setVisibility(View.GONE);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //得到InputMethodManager的实例
//        if (imm.isActive()) {
//            //如果开启
//            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
//            //关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
//        }
    }

    public void getCodeByInput(View v) {
//        String code = ((EditText) findViewById(R.id.codeEdit)).getText().toString();
//        if ("".equals(code.trim())) {
//            Toast.makeText(this, LanguageWord.instance(this).getLanguageWord("TIP_INPUT_CODE"), Toast.LENGTH_SHORT).show();
//            return;
//        }

//        Intent resultIntent = new Intent();
//        Bundle bundle = new Bundle();
//        bundle.putString("result", code);
//        resultIntent.putExtras(bundle);
//        this.setResult(RESULT_OK, resultIntent);

//        if (from == 1) {
//            Intent intent = new Intent();
//            intent.putExtra("code", code);
//            setResult(RESULT_OK, intent);
//            finish();
//        } else {
//            Gson gson = new Gson();
//            AddToShopcart[] shopcarts = new AddToShopcart[1];
//            shopcarts[0] = new AddToShopcart();
//            shopcarts[0].productId = "";
//            shopcarts[0].barCode = code;
//            shopcarts[0].count = 1;
//            String batchParam = gson.toJson(shopcarts);
//            ConnectHelper.doAddToShopcart(myHandler, PPPOSApplication.token,
//                    batchParam, PPPOSApplication.mallId,
//                    LanguageWord.getLanguageNow() + "");
//        }

//        finish();
    }

    public void onGotoOrderList(View v) {
//        Intent intent3 = new Intent(this, CurrentOrderActivity.class);
//        this.startActivity(intent3);
    }
}