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
		// ��ʾ������������ж������� �� item
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		// ��ʾ������position��Ӧ��������
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// ��ʾ������position��Ӧ��item��id
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
		} else {// listView��ҳ���е�item����֮�󣬻�һ�� ͨ��tag��ʶ �������ڴ��е�convertView
			viewHolder = (ViewHolder) convertView.getTag();
		}

		// Ϊitem�����е�ÿһ��item�������ֵ
		viewHolder.pic.setImageResource(R.drawable.jingjing);
		viewHolder.sharepic.setImageResource(R.drawable.huixin);
		viewHolder.name.setText("�Ž�");
		viewHolder.content.setText("aaaaaa");
		viewHolder.qiandao.setText("ǩ��ʱ�䣺2016��07��07 09:32:15");
		viewHolder.share.setText("����ͼƬ");
		viewHolder.pinglun.setText("����");
		viewHolder.zan.setText("��");

		return convertView;
	}

}
