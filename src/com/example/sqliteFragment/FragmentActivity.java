package com.example.sqliteFragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.TextView;

import com.example.sqlitedemo.R;
import com.example.sqlitehelper.MyMusicData;
import com.example.sqlitehelper.MyMusicToolPlay;
import com.example.sqlitehelper.MyMusicToolUI;

public class FragmentActivity extends Activity{
	
	private MyMusicToolPlay mmtp;
	private MyMusicData mmd;
	private MyMusicToolUI mmtu;
	private TextView tv_ablume;
	private TextView tv_arist;
	private TextView tv_name;
	
	
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
	   
	   
	tv_name=(TextView) findViewById(R.id.tv_name);
	tv_ablume=(TextView) findViewById(R.id.tv_albume);
	tv_arist=(TextView) findViewById(R.id.tv_artist);
	
	
	
	mmtu=new MyMusicToolUI(this);
	mmd=(MyMusicData)(getIntent().getBundleExtra("MyMusicData").getSerializable("MusicData"));
	//mmtp=new MyMusicToolPlay(this);
	//mmtp.setMyMusicData(mmd);
	
   tv_name.setText(mmd.getTitle());
   tv_ablume.setText(mmd.getAlbum());
   tv_arist.setText(mmd.getArtist());
}

//设置初始的Fragment
   public void setFrament()
   {
	   FragmentManager fm=getFragmentManager();
	   FragmentTransaction ft=fm.beginTransaction();
	   MyImageFragment fragment=new MyImageFragment();
//	   
	   Bundle bundle=new Bundle();
	   bundle.putString("filepath", mmtu.getStringAlbum(mmd.getAlbum_id()));
	   fragment.setArguments(bundle);
	   ft.add(R.id.fragment_activity, fragment);
	   ft.commit();
   }
}
