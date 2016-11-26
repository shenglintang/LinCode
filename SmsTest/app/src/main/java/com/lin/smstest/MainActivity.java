package com.lin.smstest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.utils.SMSLog;


public class MainActivity extends AppCompatActivity {
    private String phone = "1234667845";
    // 填写从短信SDK应用后台注册得到的APPKEY
    private static String APPKEY = "16f21ca3c5e00";
    // 填写从短信SDK应用后台注册得到的APPSECRET
    private static String APPSECRET = "93bdfd7063d063afb3a2730bdd913956";

    private EditText phoneEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
// 初始化短信SDK
        SMSSDK.initSDK(this, APPKEY, APPSECRET);
        phoneEt = (EditText) findViewById(R.id.ed_phone);

        findViewById(R.id.btn_getnum).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(phoneEt.getText())) {
                    phone = phoneEt.getText().toString();
                    //获取短信验证码
                    SMSSDK.getVerificationCode("86", phone);
                }

            }
        });
        //注册回调监听接口
        SMSSDK.registerEventHandler(eventHandler);
    }


    EventHandler eventHandler = new EventHandler() {
        public void afterEvent(final int event, final int result, final Object arg2) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (result == SMSSDK.RESULT_COMPLETE) {
                        if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                            Toast.makeText(getApplicationContext(), "申请验证码成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, CheckCodeAty.class);
                            intent.putExtra("phone", phone);
                            startActivity(intent);
                        }
                    } else {
                        // 根据服务器返回的网络错误，给toast提示
                        try {
                            ((Throwable) arg2).printStackTrace();
                            Throwable throwable = (Throwable) arg2;

                            JSONObject object = new JSONObject(
                                    throwable.getMessage());
                            String des = object.optString("detail");
                            if (!TextUtils.isEmpty(des)) {
                                Toast.makeText(MainActivity.this, des, Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (Exception e) {
                            SMSLog.getInstance().w(e);
                        }
                        // 如果木有找到资源，默认提示
                        Toast.makeText(getApplicationContext(), "连接服务器失败", Toast.LENGTH_SHORT).show();
                    }
                }

            });

        }

        ;
    };
}
