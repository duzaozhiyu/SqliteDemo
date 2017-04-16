package com.example.sqliteservice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.sqliteXMl.MyHandler;
import com.example.sqlitehelper.MyMusicData;
import com.example.sqlitehelper.MyMusicToolPlay;
import com.example.sqlitehelper.MyMusicToolUI;

public class MyService extends Service {

	private MyMusicToolUI mmtu;
	private MyMusicToolPlay mmtp;
	private MyMusicData mmd;
	private Context context;
	private Handler handler;
	
	private final static String TAG="MyService";
	
	private MyBinder mb=new MyBinder();
	
	
	public void setHandler(Handler handler)
	{
		this.handler=handler;
	}
	public void setContext(Context context)
	{
		this.context=context;
	}
	public Context getContext()
	{
		return context;
	}
	
	
	
	
	
	public MyMusicToolUI getMmtu() {
		return mmtu;
	}
	public void setMmtu(MyMusicToolUI mmtu) {
		this.mmtu = mmtu;
	}
	public MyMusicToolPlay getMmtp() {
		return mmtp;
	}
	public void setMmtp(MyMusicToolPlay mmtp) {
		this.mmtp = mmtp;
	}
	public MyMusicData getMmd() {
		return mmd;
	}
	public void setMmd(MyMusicData mmd) {
		this.mmd = mmd;
	}
	
	
	
	
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stubsd
		super.onCreate();
		 mmtu=new MyMusicToolUI(this);
	     mmtp=new MyMusicToolPlay(this);
		
		
		
		Log.e(TAG,"onCreate service");
	}
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.e(TAG,"onStartCommand service");
		if(mmtp.state==1)
		{
			mmtp.state=2;
			mmtp.play(mmtp.getMyMusicData().getData());
			mmtp.changeUI=1;
			System.out.println(mmtp.getMyMusicData().getData()+">>>>>");
			Toast.makeText(this, "service播", Toast.LENGTH_SHORT).show();
		}
		
		
		
		return super.onStartCommand(intent, flags, startId);
		
	}
	
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mmtp.release();
		Log.e(TAG,"onDestroy service");
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		Log.e("MyService", "MyBinder");
		return mb;
	}
	
	
	public class MyBinder extends Binder{
		public MyService getService()
		{
			return MyService.this;
		}
	}

}
