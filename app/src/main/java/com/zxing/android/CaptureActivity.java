package com.zxing.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.qijukeji.entityModel.Smxx;
import com.qijukeji.entityModel.Yhxx;
import com.qijukeji.qiju_dg.R;
import com.qijukeji.utils.ConstantValues;
import com.qijukeji.utils.HttpUtil;
import com.qijukeji.utils.IntentUtil;
import com.qijukeji.utils.StaticField;
import com.qijukeji.utils.Utils;
import com.qijukeji.view.OrderSaveActivity;
import com.zxing.android.camera.CameraManager;
import com.zxing.android.decoding.CaptureActivityHandler;
import com.zxing.android.decoding.InactivityTimer;
import com.zxing.android.view.ViewfinderView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.qijukeji.utils.JsonToObjUtil.jsonArrayToListObj;

/**
 * 扫描页面
 */

public class CaptureActivity extends Activity implements Callback {
    private static final String TAG = CaptureActivity.class.getSimpleName();
    public static final String QR_RESULT = "RESULT";

    @Bind(R.id.iv_title_right)
    ImageView iv_title_right;
    @Bind(R.id.select_title_show)
    RelativeLayout select_title_show;
    @Bind(R.id.tv_title_name)
    TextView tv_title_name;
    @Bind(R.id.ll_yhm)
    LinearLayout llYhm;
    @Bind(R.id.bt_richscan_affirm)
    Button btRichscanAffirm;

    private CaptureActivityHandler handler;
    @Bind(R.id.viewfinderview)
    ViewfinderView viewfinderView;

    @Bind(R.id.surfaceview)
    SurfaceView surfaceView;
    @Bind(R.id.et_coupon_code)
    EditText et_coupon_code;

    @Bind(R.id.scan_success)
    ImageView img_scan;

    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    // private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    CameraManager cameraManager;
    private Dialog mWeiboDialog;
    private static final int HTTP_SELECT_YH = 1;//查询用户信息
    private static final int HTTP_SELECT_YHQ = 2;//查询优惠券信息


    private static final int HTTP_SELECT_YHQ_1 = 3;//只查询优惠券信息
    public static final int INTENT_RETURN = 1;//intent 返回值


    private Context context;

    private int key;

    private String yhm;//优惠码
    private String staffid;
    private String staffUuid;
    private String brandid;
    private String companyid;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_capture);
        ButterKnife.bind(this);
        context = this;
        SharedPreferences preferences = getSharedPreferences("qiju", Context.MODE_PRIVATE);
        staffid = preferences.getString("staffid", "");
        staffUuid = preferences.getString("staffUuid", "");
        brandid = preferences.getString("brandid", "");
        companyid = preferences.getString("companyid", "");
        Intent intent = getIntent();
        key = intent.getIntExtra(StaticField.KEY, 0);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
        iv_title_right.setVisibility(View.INVISIBLE);
        select_title_show.setVisibility(View.GONE);
        tv_title_name.setText("扫一扫");
        tv_title_name.setVisibility(View.VISIBLE);
        float density = context.getResources().getDisplayMetrics().density;
        //屏幕的宽度
        int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        int heightPixels = context.getResources().getDisplayMetrics().heightPixels;
        int a = (int) (screenWidth / 6.0);
        int b = screenWidth;
        llYhm.setPadding(a, b, a, 0);
        et_coupon_code.setWidth((int) (115 * density));
        btRichscanAffirm.setHeight((int) (25 * density));
        btRichscanAffirm.setWidth((int) (45 * density));
    }

    private void showResult(final Result rawResult, Bitmap barcode) {
        //获取扫描信息
        toHttp(rawResult.getText() + "&staffuuid=" + staffUuid, "");
    }

    private void toHttp(String rawResult, String yhm) {
        if (key == StaticField.key1) {
            HttpUtil.VolleyHttpGet(this, rawResult, yhm, staffid, Http_handler, HTTP_SELECT_YHQ);
        } else if (key == StaticField.key2) {
//            HttpUtil httpUtil_2 = new HttpUtil(Http_handler, HTTP_SELECT_YHQ_1, this);
//            httpUtil_2.httpPost(ConstantValues.SYS_URL_1, json);
//            HttpUtil.VolleyHttpPost(this, ConstantValues.SYS_URL_1, json, Http_handler, HTTP_SELECT_YHQ_1);
        }

    }

    Handler Http_handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HTTP_SELECT_YHQ:
                    String data2 = msg.obj.toString();
                    JsonToObj(data2);
                    break;
                case HTTP_SELECT_YHQ_1:
                    String data3 = msg.obj.toString();
                    List<Object> listYhxx_1 = jsonArrayToListObj(data3, Yhxx.class);
                    Yhxx yhxx_1 = (Yhxx) listYhxx_1.get(0);
                    returnIntent(yhxx_1);
                    break;
                case StaticField.MSGOGJ:
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("温馨提示").setMessage("网络请求错误");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            finish();
                        }
                    }).show();
                    break;
                default:
                    break;
            }
        }

        private void JsonToObj(String data2) {
            String hasErrors = null;
            String errorMessage = null;
            JSONObject json;
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            try {
                json = new JSONObject(data2);
                hasErrors = json.getString("hasErrors");
                errorMessage = json.getString("errorMessage");
                int giftnum = json.length();
                if (hasErrors.equals("true")) {
                    builder.setTitle("温馨提示").setMessage(errorMessage);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            finish();
                        }
                    }).show();
                } else {
                    if (giftnum < 4) {
                        builder.setTitle("温馨提示").setMessage("礼品券使用成功");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                finish();
                            }
                        }).show();
                    } else {
                        toIntent(data2);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        private void toIntent(String data) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", data);
            IntentUtil.intentToNull(context, OrderSaveActivity.class, bundle);
            finish();
        }

        private void returnIntent(Yhxx yhxx_1) {
            Intent intent = new Intent();
            intent.putExtra("yhxx", yhxx_1);
            setResult(INTENT_RETURN, intent);
            finish();
        }
    };

    @OnClick(R.id.bt_richscan_affirm)
    public void bt_richscan_affirm(View v) {
        yhm = et_coupon_code.getText().toString();
        if (yhm.equals("") || yhm == null) {
            Utils.setToast(context, "请输入优惠码");
            return;
        } else {
            toHttp(ConstantValues.SYS_URL, yhm);
        }
    }

    @OnClick(R.id.ll_title_return)
    public void ll_title_return(View v) {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        // CameraManager.init(getApplication());
        float density;
        // density = context.getResources().getDisplayMetrics().density;
        cameraManager = new CameraManager(getApplication());

        viewfinderView.setCameraManager(cameraManager);

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
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        cameraManager.closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            // CameraManager.get().openDriver(surfaceHolder);
            cameraManager.openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
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

    public CameraManager getCameraManager() {
        return cameraManager;
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

    public void handleDecode(Result obj, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        showResult(obj, barcode);
    }


    public void restartPreviewAfterDelay(long delayMS) {
        if (handler != null) {
            handler.sendEmptyMessageDelayed(MessageIDs.restart_preview, delayMS);
        }
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

            try {
                AssetFileDescriptor fileDescriptor = getAssets().openFd(
                        "qrbeep.ogg");
                this.mediaPlayer.setDataSource(
                        fileDescriptor.getFileDescriptor(),
                        fileDescriptor.getStartOffset(),
                        fileDescriptor.getLength());
                this.mediaPlayer.setVolume(0.1F, 0.1F);
                this.mediaPlayer.prepare();
            } catch (IOException e) {
                this.mediaPlayer = null;
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setResult(RESULT_CANCELED);
            finish();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_FOCUS
                || keyCode == KeyEvent.KEYCODE_CAMERA) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}