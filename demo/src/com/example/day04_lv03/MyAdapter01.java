package com.example.day04_lv03;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter01 extends BaseAdapter {

	private LayoutInflater mInflater;
	private Context context;
	private Activity a;
	private List<Map<String, Object>> mData;

	public MyAdapter01(Context c) {
		this.context = c;
		
	}

	public MyAdapter01(Context c, List<Map<String, Object>> mData,Activity a) {
		mInflater = LayoutInflater.from(c);
		this.context = c;
		this.a = a;
		this.mData = mData;
	}

	@Override
	public int getCount() {
		// 表示这个适配器共有多条数据 或 item
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		// 表示此索引position对应的数据项
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// 表示此索引position对应的item的id
		return position;
	}

	static class ViewHolder {
		ImageView pic, sharepic;
		TextView name, content, qiandao, share;
		CheckBox pinglun,zan;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.sa_item, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.pic = (ImageView) convertView.findViewById(R.id.pic);
			viewHolder.sharepic = (ImageView) convertView.findViewById(R.id.sharepic);
			viewHolder.sharepic.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent i = new Intent(a, ImageActivity.class);
					a.startActivity(i);

				}
			});
			viewHolder.name = (TextView) convertView.findViewById(R.id.name);
			viewHolder.content = (TextView) convertView.findViewById(R.id.content);
			viewHolder.qiandao = (TextView) convertView.findViewById(R.id.qiandao);
			viewHolder.pinglun = (CheckBox) convertView.findViewById(R.id.pinglun);
			viewHolder.zan = (CheckBox) convertView.findViewById(R.id.zan);
			convertView.setTag(viewHolder);
		} else {// listView首页所有的item加载之后，会一致 通过tag标识 来引用内存中的convertView
			viewHolder = (ViewHolder) convertView.getTag();
		}

		// 为item布局中的每一个item的组件赋值
		viewHolder.pic.setImageResource(R.drawable.jingjing);
		viewHolder.sharepic.setImageResource(R.drawable.huixin);
		viewHolder.name.setText("张杰");
		viewHolder.content.setText("aaaaaa");
		viewHolder.qiandao.setText("签到时间：2016—07—07 09:32:15");
		viewHolder.share.setText("分享图片");
		viewHolder.pinglun.setText("评论");
		viewHolder.zan.setText("赞");

		return convertView;
	}

}
