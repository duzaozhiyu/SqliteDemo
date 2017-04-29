package com.example.sqliteFragment;

import com.example.sqlitedemo.R;
import com.example.sqlitehelper.MyMusicData;
import com.example.sqlitehelper.MyMusicToolPlay;
import com.example.sqlitehelper.MyMusicToolUI;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class FragmentActivity extends Activity{
	
	private MyMusicToolPlay mmtp;
	private MyMusicData mmd;
	private MyMusicToolUI mmtu;
	
	
   @Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.fragment_activity);
	init();
	setFrament();
	
}
   
   private void init() {
	// TODO Auto-generated method stub
	mmtu=new MyMusicToolUI(this);
	//mmd=(MyMusicData)(getIntent().getBundleExtra("MyMusicToolPlay").getSerializable("MusicData"));
	//mmtp=new MyMusicToolPlay(this);
	mmtp.setMyMusicData(mmd);
	
}

//设置初始的Fragment
   public void setFrament()
   {
	   FragmentManager fm=getFragmentManager();
	   FragmentTransaction ft=fm.beginTransaction();
	   MyImageFragment fragment=new MyImageFragment();
	   
	   //Bundle bundle=new Bundle();
	   //bundle.putString("filepath", mmtu.getStringAlbum(mmd.getAlbum_id()));
	   //fragment.setArguments(bundle);
	   ft.add(R.id.fragment_activity, fragment);
	   ft.commit();
   }
}
