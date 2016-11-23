package com.sample.drawpassword;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

public class CrashHanlder implements UncaughtExceptionHandler {
	private final static String TAG = "CrashHanlder";
	private static CrashHanlder instance = new CrashHanlder();
	private UncaughtExceptionHandler mDefaultCrashHanlder;
	private Context mContext;

	public static CrashHanlder getInstance() {
		return instance;

	}

	public void init(Context context) {
		// mDefaultCrashHanlder = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
		mContext = context.getApplicationContext();
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		// TODO Auto-generated method stub
		try {
			saveToSdcard(ex);
			post2Server(ex);
			// showCrashDialog();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ex.printStackTrace();
		if (mDefaultCrashHanlder != null) {
			mDefaultCrashHanlder.uncaughtException(thread, ex);
		} else {
			Process.killProcess(Process.myPid());
		}
	}

	/**
	 * 保存到本地
	 * 
	 * @param ex
	 */
	@SuppressLint("SimpleDateFormat")
	private void saveToSdcard(Throwable ex) {
		// TODO Auto-generated method stub
		long current = System.currentTimeMillis();
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(current));
		String errorlog = time + ".txt";
		String savePath = "";
		String logFilePath = "";
		FileWriter fw = null;
		PrintWriter pw = null;
		BufferedWriter bf = null;
		try {
			// 判断是否挂载了SD卡
			String storageState = Environment.getExternalStorageState();
			if (storageState.equals(Environment.MEDIA_MOUNTED)) {
				savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Crash/Log/";
				File file = new File(savePath);
				if (!file.exists()) {
					file.mkdirs();
				}
				logFilePath = savePath + errorlog;
			}
			// 没有挂载SD卡，无法写文件
			if (logFilePath == "") {
				return;
			}
			File logFile = new File(logFilePath);
			if (!logFile.exists()) {
				logFile.createNewFile();
			}
			fw = new FileWriter(logFile, true);
			bf = new BufferedWriter(new FileWriter(logFile, true));
			pw = new PrintWriter(bf);
			ex.printStackTrace(pw);
			getPhoneInfo(pw);
			pw.close();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "save2Sdcard failed");
		} finally {
			if (pw != null) {
				pw.close();
			}
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
				}
			}
		}
	}

	/**
	 * 发送到服务器
	 * 
	 * @param ex
	 */
	private void post2Server(Throwable ex) {
		// TODO Auto-generated method stub
	}

	/**
	 * //获取手机信息
	 * 
	 * @param pw
	 * @throws NameNotFoundException
	 */
	@SuppressWarnings("deprecation")
	private void getPhoneInfo(PrintWriter pw) throws NameNotFoundException {
		// TODO Auto-generated method stub
		PackageManager pm = mContext.getPackageManager();
		PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
		pw.print("App Version: ");
		pw.print(pi.versionName);
		pw.print('_');
		pw.println(pi.versionCode);

		pw.print("OS Version: ");
		pw.print(Build.VERSION.RELEASE);
		pw.print('_');
		pw.println(Build.VERSION.SDK_INT);

		pw.print("Vendor: ");
		pw.println(Build.MANUFACTURER);

		pw.print("Model: ");
		pw.println(Build.MODEL);

		pw.print("CPU ABI: ");
		pw.println(Build.CPU_ABI);

		pw.print("CPU SUPPORTED_ABIS: ");
		pw.println(Build.SUPPORTED_ABIS);

	}

	/**
	 * 依附在activty中才有提示
	 */
	private void showCrashDialog() {
		// TODO Auto-generated method stub
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				new AlertDialog.Builder(mContext).setTitle("提示").setCancelable(false).setMessage("程序崩溃了...")
						.setNeutralButton("我知道了", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Log.i(TAG, "exit");
						System.exit(0);
					}
				}).create().show();
				Looper.loop();
			}
		}.start();
	}

}
