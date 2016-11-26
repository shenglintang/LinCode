package com.lin.jpushdemo.com.lin.jpushdemo.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.lin.jpushdemo.R;
import com.lin.jpushdemo.receiver.MyReceiver;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //设置别名
//        JPushInterface.setAlias(this, "alias1", new TagAliasCallback() {
//            @Override
//            public void gotResult(int i, String s, Set<String> set) {
//                Toast.makeText(getApplication(), s, Toast.LENGTH_SHORT).show();
//            }
//        });
        //设置tag
//        Set<String> mSet = new HashSet<>();
//        mSet.add("tv");
//        mSet.add("music");
//        JPushInterface.setTags(this, mSet, new TagAliasCallback() {
//            @Override
//            public void gotResult(int i, String s, Set<String> set) {
//                Toast.makeText(getApplication(), set.toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        registerBroadcast();
        init();

    }

    /**
     * 极光推送统计API
     */
    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    /**
     * 极光推送统计API
     */
    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                startActivity(new Intent(this, De_Activity.class));
                break;
        }
    }

    // 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
    private void init() {
        JPushInterface.init(getApplicationContext());
    }

    private void registerBroadcast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("FINISH");
        registerReceiver(mReceiver, filter);
    }

    BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals("FINISH")) {
                finish();
            }
        }
    };

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        this.unregisterReceiver(mReceiver);
//    }
}
