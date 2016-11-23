package com.shop.xinoo.jpush;

import java.util.HashMap;
import java.util.Map;
import com.shop.xinoo.modle.Constants;
import com.shop.xinoo.modle.NetCallBack;
import com.shop.xinoo.modle.NetUtil;
import android.content.Context;
import android.util.Log;

public class SetingAliasToServer {
	public static void sendAlias(Context context, String alias) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("CODE", "7");
		params.put("alias", alias);
		new NetUtil(context).httpPost(Constants.MYWORK, params,
				new NetCallBack() {
					@Override
					public void getResult(String result) {
						// TODO Auto-generated method stub
					}
				});
	}
}
