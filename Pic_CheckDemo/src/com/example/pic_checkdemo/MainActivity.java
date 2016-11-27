package com.example.pic_checkdemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	/**
     * 滑块
     */
    private SeekBar mSeekBar;
    /**
     * 自定义的控件
     */
    private DouYuView mDY;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ImageView iv_check = (ImageView) findViewById(R.id.iv_check);
		iv_check.setImageBitmap(CodeUtils.getInstance().createBitmap());
		TextView tv = (TextView) findViewById(R.id.tv_check);
		tv.setText("内容： "+CodeUtils.getInstance().getCode());
		slipCheck();
	}

	private void slipCheck() {
		// TODO Auto-generated method stub
		  mDY = (DouYuView) findViewById(R.id.dy_v);
	        mSeekBar = (SeekBar) findViewById(R.id.sb_dy);
	        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
	            @Override
	            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//	                Log.e("main", "当前位置" + i);
	                mDY.setUnitMoveDistance(mDY.getAverageDistance(seekBar.getMax()) * i);
	            }

	            @Override
	            public void onStartTrackingTouch(SeekBar seekBar) {

	            }

	            @Override
	            public void onStopTrackingTouch(SeekBar seekBar) {
	                mDY.testPuzzle();
	            }
	        });
	        mDY.setPuzzleListener(new DouYuView.onPuzzleListener() {
	            @Override
	            public void onSuccess() {
//	                mSeekBar.setEnabled(false);
	                Toast.makeText(MainActivity.this, "验证成功", Toast.LENGTH_SHORT).show();
	                mSeekBar.setProgress(0);
	                mDY.reSet();
	            }

	            @Override
	            public void onFail() {
	                Toast.makeText(MainActivity.this, "验证失败", Toast.LENGTH_SHORT).show();
	                mSeekBar.setProgress(0);
	            }
	        });
	}
}
