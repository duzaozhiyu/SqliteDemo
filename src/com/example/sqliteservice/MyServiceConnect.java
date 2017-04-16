package com.example.sqliteservice;

import com.example.sqliteservice.MyService.MyBinder;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

public class MyServiceConnect implements ServiceConnection {

	
//	private MyBinder mb=null;
	private MyService mService;
//	public MyBinder getBinder()
//	{
//		return mb;
//	}
	public MyService getMyService()
	{
		return mService;
	}
	
	@Override
	public void onServiceConnected(ComponentName name, IBinder service) {
		// TODO Auto-generated method stub
        
        mService=((MyBinder)service).getService();
        Log.e("ServiceConnect", "onService Connected");
        //System.out.println();
	}

	@Override
	public void onServiceDisconnected(ComponentName name) {
		// TODO Auto-generated method stub
   
	}

}
