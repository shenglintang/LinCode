package com.shop.xinoo.jpush;

import java.util.LinkedHashSet;
import java.util.Set;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

@SuppressLint("HandlerLeak")
public class PushSetActivitys {
	private static final int MSG_SET_ALIAS = 1001;
	private static final int MSG_SET_TAGS = 1002;
	private Context context;
	private boolean islogin=false;
	public PushSetActivitys(Context context){
		this.context=context;
	}	
	//设置标签
	public void setTag(String tag){			
		// ","隔开的多个 转换成 Set
		String[] sArray = tag.split(",");
		Set<String> tagSet = new LinkedHashSet<String>();
		for (String sTagItme : sArray) {
			if (!ExampleUtil.isValidTagAndAlias(sTagItme)) {
				Toast.makeText(context,"别名为空", Toast.LENGTH_SHORT).show();
				return;
			}
			tagSet.add(sTagItme);
		}
		
		//调用JPush API设置Tag
		mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_TAGS, tagSet));

	} 
	//设置别名
	public void setAlias(String alias,boolean islogin){
		this.islogin=islogin;
		if (!ExampleUtil.isValidTagAndAlias(alias)) {
			Toast.makeText(context,"别名为空", Toast.LENGTH_SHORT).show();
			return;
		}		
		//调用JPush API设置Alias
		mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
//		JPushInterface.setAliasAndTags(context.getApplicationContext(), alias, null, mAliasCallback);
	}				
	private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            switch (code) {
            case 0:
//                logs = "Set tag and alias success";
//                Log.i("TTT", logs);
//                Log.i("TTT", "==========islogin========"+islogin+"=======alias======"+alias);
                if(!islogin){
                	SetingAliasToServer.sendAlias(context, alias); 
                }
                              
                break;
                
            case 6002:
                logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                Log.i("TTT", logs);
                if (ExampleUtil.isConnected(context.getApplicationContext())) {
                	mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                } else {
                	Log.i("TTT", "No network");
                }
                break;
            
            default:
                logs = "Failed with errorCode = " + code;
                Log.e("TTT", logs);
            }
            
//            ExampleUtil.showToast(logs, context.getApplicationContext());
        }
	    
	};
	
	private final TagAliasCallback mTagsCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            switch (code) {
            case 0:
                logs = "Set tag and alias success";
                Log.i("TTT", logs);
                break;
                
            case 6002:
                logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                Log.i("TTT", logs);
                if (ExampleUtil.isConnected(context.getApplicationContext())) {
                	mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_TAGS, tags), 1000 * 60);
                } else {
                	Log.i("TTT", "No network");
                }
                break;
            
            default:
                logs = "Failed with errorCode = " + code;
                Log.e("TTT", logs);
            }
            
            ExampleUtil.showToast(logs, context.getApplicationContext());
        }
        
    };
	
	
	 private final Handler mHandler = new Handler() {
	        @SuppressWarnings("unchecked")
			@Override
	        public void handleMessage(android.os.Message msg) {
	            super.handleMessage(msg);
	            switch (msg.what) {
	            case MSG_SET_ALIAS:
	                JPushInterface.setAliasAndTags(context.getApplicationContext(), (String) msg.obj, null, mAliasCallback);
	                break;	                
	            case MSG_SET_TAGS:
	                JPushInterface.setAliasAndTags(context.getApplicationContext(), null, (Set<String>) msg.obj, mTagsCallback);
	                break;	                
	            default:
	            }
	        }
	    };
}
