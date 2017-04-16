package com.example.sqlitehelper;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class MyBaseAdapter<T> extends BaseAdapter {

	private Context context;
	private int resID;
	
	public MyBaseAdapter(Context context,int resID)
	{
		this.context=context;
		this.resID=resID;
	}
	
	
	
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public int getResID() {
		return resID;
	}

	public void setResID(int resID) {
		this.resID = resID;
	}



	private List<T> list=new ArrayList<T>();
	
	
	
	public void addData(T t)
	{
		list.add(t);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView==null)
		{
			convertView=LayoutInflater.from(getContext()).inflate(getResID(), null);
		}
		getView2(position, convertView, parent);
		return convertView;
	}

	public abstract void getView2(int position, View convertView, ViewGroup parent);
}
