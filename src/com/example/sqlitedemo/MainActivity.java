package com.example.sqlitedemo;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sqliteFragment.FragmentActivity;
import com.example.sqliteXMl.MyXmlTool;
import com.example.sqlitehelper.MyBaseAdapter;
import com.example.sqlitehelper.MyMusicData;
import com.example.sqlitehelper.MyMusicToolPlay;
import com.example.sqlitehelper.MyMusicToolUI;
import com.example.sqliteservice.MyService;
import com.example.sqliteservice.MyServiceConnect;

public class MainActivity extends Activity {

	private MyServiceConnect msc;
	private MyService mService;
	private MyService.MyBinder mb;
	private MyBaseAdapter<MyMusicData> mbAdapter;
	private ListView list;
	private Cursor resource=null;
	private MyMusicToolUI mmtu_nos;
	private MyMusicToolPlay mmtp;
	private MyMusicData mmd;
	private SeekBar sBar;
	private TextView tv_startTime;
	private TextView tv_endTime;
	private Timer timer;
	private TimerTask task;
	 private View layout;
	 private  Handler myhandler;
	 private ImageButton ib;
	 private int seekbarState=0;
	 private ImageView image_album;

	//private MyBaseAdapter<MyMusicData> adapter;
	 @Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		MyMusicToolPlay.changeUI=0;
		startTimer();
		Log.e("MainActivity", "onStart");
	}
	 
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("onCreate");
        
        
        msc=new MyServiceConnect();
		Intent service=new Intent(this,MyService.class);
		startService(service);
		bindService(service, msc, BIND_AUTO_CREATE);
        
        
        //设置overflow
        setOverFlowAlways();
        
        list=(ListView) findViewById(R.id.listView);
        layout=findViewById(R.id.include);
        
        
       
        
        sBar=(SeekBar) findViewById(R.id.seebar);
//      
        tv_startTime=(TextView) findViewById(R.id.tv_starttime);
        tv_endTime=(TextView) findViewById(R.id.tv_endtime);
        
        //用于更新UI和数据处理
        mmtu_nos=new MyMusicToolUI(this);

        	mmd=mmtu_nos.readXML();
            if(mmd!=null)
            {
                 setInit(mmd, layout);
                 tv_endTime.setText(setEndTimeFormat(mmd.getTime()));
            }
        
       
        //查询的内容
//        String str_item[]={MusicFinalVar.ID,MusicFinalVar.ALBUM,MusicFinalVar.ARTIST,MusicFinalVar.DATA
//        ,MusicFinalVar.DURATION,MusicFinalVar.SIZE,MusicFinalVar.TITLE		
//        };
//        //显示的内容
//        String str_show[]={MusicFinalVar.ID,MusicFinalVar.TITLE,MusicFinalVar.ARTIST		
//                };
//        int layout_id[]={R.id.tv_num,R.id.tv_title,R.id.tv_artist};
////      Cursor resource=getContentResolver().query(MusicFinalVar.uri, str_item, null, null, MusicFinalVar.SIZE);
//       
//        resource=getContentResolver().query(MusicFinalVar.uri, null, null, null,MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
////       
//        adapter=new SimpleCursorAdapter(this, R.layout.listitem_layout, resource, str_show, layout_id,0);
//        list.setAdapter(adapter);
//        startManagingCursor(resource);
     
        
        
         myhandler=new Handler()
        {
        	public void handleMessage(android.os.Message msg) {
        		
        		if(MyMusicToolPlay.state==0)
        		{
        			//sBar.setMax((int)(mmtp.getMyMusicData().getTime()));
    				//tv_endTime.setText(setEndTimeFormat(mmtp.getMyMusicData().getTime()));
    				tv_startTime.setText("00:00");
    				sBar.setProgress(0);
    				ib.setImageDrawable(getResources().getDrawable(R.drawable.play));
    				
    				return ;
        		}
        		//当mmtp 是service中的mmtp 是使用
        		sBar.setMax((int)(mmtp.getMyMusicData().getTime()));
				tv_endTime.setText(setEndTimeFormat(mmtp.getMyMusicData().getTime()));
				ib.setImageDrawable(getResources().getDrawable(R.drawable.music_pause));
				MainActivity.this.setInit(mmtp.getMyMusicData(), layout);
        	};
        };
        
        mbAdapter=mmtu_nos.setListadapter(list);
      
       ib =(ImageButton) layout.findViewById(R.id.ib_low_play);
        
       //list的监听
       list.setOnItemClickListener(new OnItemClickListener() {
        	
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stu
			MyBaseAdapter<MyMusicData> mba=(MyBaseAdapter<MyMusicData>) parent.getAdapter();
			if(mService==null)
			{
				mService=msc.getMyService();
				mmtp=mService.getMmtp();
				mmtp.setAdapter(mbAdapter);
				mmtp.setMyMusicData(mmd);
			}
			
//			seekbarState=1;
//			sBar.setEnabled(true);
			
		    //mmd=(MyMusicData) mba.getItem(position);
			//mmtp.setMyMusicData((MyMusicData) mba.getItem(position));
			mmtp.setMyMusicData((MyMusicData) mba.getItem(position));
		    setInit(mmtp.getMyMusicData(), layout);
		  
			MyMusicToolPlay.state=-1;
			Log.e("MainActivityPath",mmtp.getMyMusicData().getData() );
			mmtp.play(mmtp.getMyMusicData().getData());
			ib.setImageDrawable(getResources().getDrawable(R.drawable.music_pause));
		    Toast.makeText(MainActivity.this, "bo", Toast.LENGTH_SHORT).show();
		    sBar.setMax((int) (mmtp.getMyMusicData().getTime()));
		    stopTimer();
		    startTimer();
		    tv_endTime.setText(setEndTimeFormat(mmtp.getMyMusicData().getTime()));
			}
		});
        //播放按钮
        
        
        ib.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//mmtp.play();
				if(mService==null)
				{
					mService=msc.getMyService();
					mmtp=mService.getMmtp();
					mmtp.setMyMusicData(mmd);
					mmtp.setAdapter(mbAdapter);
					System.out.println("onPlayclick");
					//MyMusicToolPlay.state=1;
					mmtp.play(mmd.getData());
				}
				
				//默认么 1播放 2 暂停 0停止
				if(MyMusicToolPlay.state==1)
				{
				       
					    mmtp.pause();
					    ib.setImageDrawable(getResources().getDrawable(R.drawable.play));
					    Toast.makeText(MainActivity.this, "zan1", Toast.LENGTH_SHORT).show();
					    return ;
				}
				if(MyMusicToolPlay.state==2||MyMusicToolPlay.state==0)
				{
					
					mmtp.play(mmtp.getMyMusicData().getData());	
					
					ib.setImageDrawable(getResources().getDrawable(R.drawable.music_pause));
//					ib.setImageBitmap(bitmap);
					Toast.makeText(MainActivity.this, "播", Toast.LENGTH_SHORT).show();
					return ;
				}
				
//				
			}
		});
        
        //下一曲按钮
        ImageButton ibn=(ImageButton) layout.findViewById(R.id.ib_low_next);
        
        ibn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mService==null)
				{
					mService=msc.getMyService();
					mmtp=mService.getMmtp();
					mmtp.setMyMusicData(mmd);
					mmtp.setAdapter(mbAdapter);
					MyMusicToolPlay.state=1;
				}
				if(MyMusicToolPlay.state!=-1)
				{
					if(mmtp.getList().getCount()==mmtp.getMyMusicData().getId())
					{
						Toast.makeText(MainActivity.this, "这已经是最后一首歌", Toast.LENGTH_SHORT).show();
						return ;
					}
					mmtp.playNext();
				}
				
			}
		});
        
        
        //seekbar的监听
        sBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
        	
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				//MainActivity.this.startTimer();//如果ji'sh
				int position=seekBar.getProgress();
				mmtp.seekToPlay(position);
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				MyMusicToolPlay.state=2;
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				tv_startTime.setText(MainActivity.this.setEndTimeFormat(progress));//设置时间格式
				//prosition=progress;
				
			}
		});

        //跳转歌词界面
        image_album=(ImageView) layout.findViewById(R.id.imageview);
        image_album.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MainActivity.this,FragmentActivity.class);
				Bundle bundle=new Bundle();
				if(mmtp==null||mmtp.getMyMusicData()==null)
				{
					bundle.putSerializable("MusicData", mmd);
				}
				else
				{
					bundle.putSerializable("MusicData", mmtp.getMyMusicData());
				}
				//
				intent.putExtra("MyMusicData",bundle);
				startActivity(intent);
			}
		});
      
    }
	
	
	
	//startTimer
	public void startTimer()
	{
		if(timer==null)
		{
			timer=new Timer();
			
			task=new TimerTask() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					//timer.schedule(this, 10, 1000);
					
					if(MyMusicToolPlay.state==-1||MyMusicToolPlay.state==2)
					{
						return ;
					}
					if(MyMusicToolPlay.changeUI==1)
					{
						MyMusicToolPlay.changeUI=0;
						new Thread()
						{
							public void run() {
								Message message=new Message();
								message.obj="ssss";
								myhandler.sendMessage(message);
							};
						}.start();
//												
						System.out.println("改变");
					}
					if(mService==null)
					{
						mService=msc.getMyService();						
						return;
					}
					else
					{
						if(mmtp==null)
						{
							mmtp=mService.getMmtp();
							//mmtp.setMyMusicData(mmd);
							mmtp.setAdapter(mbAdapter);
						}
						
					}
					int tt=mmtp.getCurrentPosition();
					sBar.setProgress(tt);
					//System.out.println(tt);
				}
			};
			
			timer.schedule(task,0, 10);
		}
		
	}
	
	//停止计时
	public void stopTimer()
	{
		if(timer!=null)
		{
			timer.cancel();
			task.cancel();
			timer=null;
			task=null;
					
		}
		
	}
	
    
	
	
	
	//初始化endTime
	public String setEndTimeFormat(long str_time)
	{
		Date date=new Date(str_time);
		SimpleDateFormat format=new SimpleDateFormat("mm:ss");
		String time=format.format(date);
		return time;
	}

	//初始化低端控件
	public void setInit(MyMusicData mmd,View layout)
	{
		TextView tv_title=(TextView) layout.findViewById(R.id.tv_low_title);
		TextView tv_artist=(TextView) layout.findViewById(R.id.tv_low_artist);
		ImageView ib=(ImageView) layout.findViewById(R.id.imageview);
		tv_title.setText(mmd.getTitle());
		tv_artist.setText(mmd.getArtist());
		mmtu_nos.setAlbumImage(mmd.getAlbum_id(), ib);
	}

    private void onPose() {
		// TODO Auto-generated method stub
    	//暂时不解为什么close
    	
        resource.close();
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
 
        setIconEnable(menu, true);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
      //默认-1 单曲循环，0 顺序播放列表，1列表无限循环 2随机 ，3单曲一次
        switch(id)
        {
       
        case R.id.action_settings:  break;
        case R.id.action_listloop: MyMusicToolPlay.playmode=1; break;
        case R.id.action_listorder: MyMusicToolPlay.playmode=0; break;
        case R.id.action_random: MyMusicToolPlay.playmode=2; break;
        case R.id.action_signleloop: MyMusicToolPlay.playmode=-1; break;
        case R.id.action_signleorder: MyMusicToolPlay.playmode=3; break;  
        }
        System.out.println(MyMusicToolPlay.playmode);
        return super.onOptionsItemSelected(item);
    }

    
    @Override
    protected void onStop() {
    	// TODO Auto-generated method stub
    	super.onStop();
    	System.out.println("onStop");
    	//
    	//mmtu.writeXML(mmd);
    	stopTimer();
    	if(mmtp!=null)
    	{
    		mmtu_nos.writeXML(mmtp.getMyMusicData());
    	}
    	else
    	{
    		mmtu_nos.writeXML(mmd);
    	}
    	
    	
    }
    
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	
 //   	
//    	mmtp.release();
//    	unbindService(msc);
//    	if(MyMusicToolPlay.state==-1)
//    	{
//    		Intent stopIntent=new Intent(this,MyService.class);
//    		stopService(stopIntent);
//    	}
    	unbindService(msc);
    	if(MyMusicToolPlay.state==2||MyMusicToolPlay.state==-1)
        {
    		
    	Intent service=new Intent(MainActivity.this,MyService.class);
    	//stopService(service);
    	System.out.println("play stopservice");
        }
    	
		
		
    	
    	super.onDestroy();
    	System.out.println("onDestroy");
    	
    }
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
    	// TODO Auto-generated method stub
    	 //设置overflow 用
    	 if(featureId==Window.FEATURE_ACTION_BAR&&menu!=null)
    	 {
    		 if(menu.getClass().getSimpleName().equals("MenuBuilder"))
    		 {
    			 try {
					Method m=menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
				    m.setAccessible(true);
				    m.invoke(menu, true);
    			 } catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		 }
    	 }
    	 
    	return super.onMenuOpened(featureId, menu);
    }
    
    
    
    /////////////////////////////////////////////
    
  //enable为true时，菜单添加图标有效，enable为false时无效。4.0系统默认无效
    private void setIconEnable(Menu menu, boolean enable)
    {
        try 
        {
            Class<?> clazz = Class.forName("com.android.internal.view.menu.MenuBuilder");
            Method m = clazz.getDeclaredMethod("setOptionalIconsVisible", boolean.class);
            m.setAccessible(true);
            
            //MenuBuilder实现Menu接口，创建菜单时，传进来的menu其实就是MenuBuilder对象(java的多态特征)
            m.invoke(menu, enable);
            
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

    
    private void setIconVisable(Menu menu,boolean flag)
    {
    	if(menu != null) {
            try {
                //如果不为空,就反射拿到menu的setOptionalIconsVisible方法
                Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                //暴力访问该方法
                method.setAccessible(true);
                //调用该方法显示icon
                method.invoke(menu, flag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    
    //使用内部反射的方式将shasPermanentMenuKey的值设置成false
    private void setOverFlowAlways()
    {
    	ViewConfiguration config=ViewConfiguration.get(this);
    	try {
			Field menu=ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
			menu.setAccessible(true);
			menu.setBoolean(config, false);
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
   

}
