package com.lin.smstest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class CheckCodeAty extends AppCompatActivity implements View.OnClickListener {
    private String phone;
    private EditText et;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_code_aty);
        phone = getIntent().getStringExtra("phone");
        et = (EditText) findViewById(R.id.et);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(this);
        SMSSDK.registerEventHandler(eventHandler);
    }
    EventHandler eventHandler = new EventHandler(){
        public void afterEvent(int arg0, int arg1, Object arg2) {
            if(arg0 == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){
                //提交验证码后的反馈
                afterSubmit(arg1, arg2);
            }
        };
    };

    @Override
    public void onClick(View view) {
        if(!TextUtils.isEmpty(et.getText())){
            SMSSDK.submitVerificationCode("86", phone, et.getText().toString());
        }
    }
    /**
     * 提交验证码成功后的执行事件
     * @param result
     * @param data
     */
    private void afterSubmit(final int result, final Object data) {
        runOnUiThread(new Runnable() {

            public void run() {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //验证码验证成功，准备注册
                    Toast.makeText(getApplicationContext(), "验证成功！！", 0).show();
                } else {
                    // 根据服务器返回的网络错误，给toast提示
                    try {
                        ((Throwable) data).printStackTrace();
                        Throwable throwable = (Throwable) data;

                        JSONObject object = new JSONObject(throwable.getMessage());
                        String des = object.optString("detail");
                        if (!TextUtils.isEmpty(des)) {
                            Toast.makeText(getApplicationContext(), des, 0).show();
                            return;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // 如果木有找到资源，默认提示
                    Toast.makeText(getApplicationContext(), "连接服务器失败", 0).show();
                }
            }
        });
    }
}
