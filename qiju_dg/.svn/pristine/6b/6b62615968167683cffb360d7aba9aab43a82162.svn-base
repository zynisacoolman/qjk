package com.qijukeji.receiver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.NotificationCompat;

import com.qijukeji.qiju_dg.R;

/**
 * App自动更新之通知栏下载
 *
 */
public class UpdateService extends Service {
    private String SavePath;
    private Notification notification;
    private NotificationManager nManager;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    new Thread(new updateRunnable()).start();// 这个是下载的重点，是下载的过程
                    break;
            }
        }
    };

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        // 用NotificationManager的notify方法通知用户生成标题栏消息通知
        nManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        notifyUser("开始下载", 0);
        handler.sendEmptyMessage(0);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    private void notifyUser(String result, int progress) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.tubiao)
                .setContentTitle("启居导购端");
        if (progress > 0 && progress < 100) {
            builder.setContentText(progress + "%");
        } else {
            builder.setContentText("下载完成");
        }
        builder.setAutoCancel(true);
        builder.setWhen(System.currentTimeMillis());
        builder.setTicker(result);
        builder.setContentIntent(progress >= 100 ? getContentIntent() :
                PendingIntent.getActivity(this, 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT));
        notification = builder.build();
        nManager.notify(0, notification);
    }

    private PendingIntent getContentIntent() {
        File apkFile = new File(SavePath + "/qiju.apk");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + apkFile.getAbsolutePath()),
                "application/vnd.android.package-archive");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        startActivity(intent);
        return pendingIntent;
    }

    class updateRunnable implements Runnable {
        int downnum = 0;// 已下载的大小
        int downcount = 0;// 下载百分比

        @Override
        public void run() {
            // TODO Auto-generated method stub
            try {
                DownLoadApp("http://www.qijukeji.cn:8080/test/qjkj.apk");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        public void DownLoadApp(String urlString) throws Exception {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();
            int length = urlConnection.getContentLength();
            InputStream inputStream = urlConnection.getInputStream();
            OutputStream outputStream = new FileOutputStream(getFile());
            byte buffer[] = new byte[1024 * 3];
            int readsize = 0;
            while ((readsize = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, readsize);
                downnum += readsize;
                if ((downcount == 0)
                        || (downnum * 100 / length) - 1 > downcount) {
                    downcount += 1;
                    notifyUser("下载中", downcount);
                }
                if (downnum == length) {
                    notifyUser("下载完成", 100);
                    stopSelf();
                }
            }
            inputStream.close();
            outputStream.close();
        }

        // 获取文件的保存路径
        public File getFile() throws Exception {
            SavePath = getSDCardPath() + "/App";
            File path = new File(SavePath);
            File file = new File(SavePath + "/qiju.apk");
            if (!path.exists()) {
                path.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            return file;
        }

        // 获取SDCard的目录路径功能
        private String getSDCardPath() {
            File sdcardDir = null;
            // 判断SDCard是否存在
            boolean sdcardExist = Environment.getExternalStorageState().equals(
                    android.os.Environment.MEDIA_MOUNTED);
            if (sdcardExist) {
                sdcardDir = Environment.getExternalStorageDirectory();
            }
            return sdcardDir.toString();
        }
    }
}