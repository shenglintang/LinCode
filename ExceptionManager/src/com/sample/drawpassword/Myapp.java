package com.sample.drawpassword;

import android.app.Application;

public class Myapp extends Application {
	private static Myapp sInstance;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		sInstance = this;
		CrashHanlder mCrashHanlder = new CrashHanlder();
		mCrashHanlder.init(this);
	}

	public static Myapp getApp() {
		return sInstance;

	}
}
