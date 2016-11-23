package com.sample.drawpassword;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends Activity {
	Button mButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CrashHanlder mCrashHanlder = new CrashHanlder();
		mCrashHanlder.init(this);
		setContentView(R.layout.draw_pwd);
		mButton.setOnClickListener(null);
	}

}
