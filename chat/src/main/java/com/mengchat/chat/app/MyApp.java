package com.mengchat.chat.app;

import android.app.ActivityManager;
import android.content.Context;

import com.mengchat.chat.BuildConfig;
import com.mengchat.chat.app.finger.FingerMessageContentViewHolder;
import com.mengchat.chat.app.red.RedPackgeMessageContentViewHolder;
import com.mengchat.chat.app.third.location.viewholder.LocationMessageContentViewHolder;
import com.mengchat.chat.kit.WfcUIKit;
import com.mengchat.chat.kit.conversation.message.viewholder.MessageViewHolderManager;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.File;



public class MyApp extends BaseApp {

    private WfcUIKit wfcUIKit;

    @Override
    public void onCreate() {
        super.onCreate();

        // bugly，务必替换为你自己的!!!
        CrashReport.initCrashReport(getApplicationContext(), BuildConfig.BuglyId, false);
        // 只在主进程初始化
        if (getCurProcessName(this).equals(BuildConfig.APPLICATION_ID)) {
            wfcUIKit = new WfcUIKit();
            wfcUIKit.init(this);
            MessageViewHolderManager.getInstance().registerMessageViewHolder(LocationMessageContentViewHolder.class);
            MessageViewHolderManager.getInstance().registerMessageViewHolder(RedPackgeMessageContentViewHolder.class);
            MessageViewHolderManager.getInstance().registerMessageViewHolder(FingerMessageContentViewHolder.class);
            setupWFCDirs();
        }
    }

    private void setupWFCDirs() {
        File file = new File(Config.VIDEO_SAVE_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(Config.AUDIO_SAVE_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(Config.FILE_SAVE_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(Config.PHOTO_SAVE_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
}
