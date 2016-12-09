package com.example.mutilprocessdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class SecondActivity extends Activity {
	private final static String TAG = "SecondActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		Log.e(TAG, "User.sCode="+User.sCode);
		
	}
}
