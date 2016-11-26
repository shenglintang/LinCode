package com.example.pic_checkdemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ImageView iv_check = (ImageView) findViewById(R.id.iv_check);
		iv_check.setImageBitmap(CodeUtils.getInstance().createBitmap());
		TextView tv = (TextView) findViewById(R.id.tv_check);
		tv.setText("ÄÚÈÝ£º "+CodeUtils.getInstance().getCode());
	}
}
