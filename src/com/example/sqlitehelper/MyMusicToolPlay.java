package com.example.sqlitehelper;


import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Message;
import android.widget.BaseAdapter;
import android.widget.SeekBar;

import com.example.sqlitedemo.R;

public class MyMusicToolPlay extends MusicToolPlay {

	private Context context;
	private MyMusicData play_mmd;
	private int soundID;
	private MediaPlayer mp;
	public  static int state=-1;   //默认么有播放  1播放 2 暂停 0停止
	public  static int playmode=1;  //默认-1 单曲循环，0 顺序播放列表，1列表无限循环 2随机 ，3单曲一次
	public static int changeUI=0; //0不用 1要
	
	
	
	//private List<MyMusicData> mList;
	private MyBaseAdapter<MyMusicData> adapter;//记录播放列表
	private String playPath=null;
	
	public MyMusicToolPlay(Context context)
	{
		this.context=context;
		mp=new MediaPlayer();
		soundID=sp.load(context,R.raw.alarm1, 1);
		initsetList();
	}
	
	
	public void setMyMusicData(MyMusicData play_mmd)
	{
		this.play_mmd=play_mmd;
	}
	
	public MyMusicData getMyMusicData()
	{
		return play_mmd;
	}
	
	public BaseAdapter getList()
	{
		return adapter;
	}
	
	public void setAdapter(MyBaseAdapter adapte)
	{
		this.adapter=adapte;
	}
	//第一种使用soundPool 来测试
	/*
	 * (non-Javadoc)
	 * @see com.example.sqlitehelper.MusicToolPlay#play()
	 * 使用  load 加载音频  比较小的音频文件
	 * 
	 */
	//maxStreams, streamType, srcQuality  播放的文件数  文件类型，通常为0
	
	private void initsetList() {
		// TODO Auto-generated method stub
		//音乐播完后如何操作
		mp.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mph) {
				// TODO Auto-generated method stub
				//play(playPath);
				//默认-1 单曲循环，0 顺序播放列表，1列表无限循环 2随机 ，3单曲一次
				if(playmode==0)
				{
					if(adapter.getCount()==play_mmd.getId())
					{						
						stop();	
						return;
					}
					playNext();
					return ;
				}
				
				
				
				if(playmode==-1)
				{
					seekToPlay(0);
					return ;
				}
				//1列表无限循环
				if(playmode==1)
				{
					
					if(adapter.getCount()==play_mmd.getId())
					{		
						state=-1;
						play_mmd=(MyMusicData) adapter.getItem(0);
						play(play_mmd.getData());
						changeUI=1;
						//System.out.println("<<<<<1");
						return ;
					}
					else
					{
						playNext();
					}
					
				}
				// 2随机
				if(playmode==2)
				{
					state=-1;
					int position=(int) (Math.random()*10);//有问题
					play_mmd=(MyMusicData) adapter.getItem(position);
					play(play_mmd.getData());
					changeUI=1;//更新低端界面
					return;
				}
				//3单曲一次
				if(playmode==3)
				{
					stop();	
					return ;
				}
			}
		});
	}

	private SoundPool sp=new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
	/*
	 * (non-Javadoc)
	 * @see com.example.sqlitehelper.MusicToolPlay#play()
	 * 
	 * 
	 */
	@Override
	public void play() {
		// TODO Auto-generated method stub
        
        
        sp.play(soundID, 1, 1, 0, 0, 2);
        System.out.println(">>>>>>>>>>play1");
	}

	
	public void play(String path)
	{
		playPath=path;
		try {
			//暂停状态
			if(state==2)
			{
				mp.start();
				state=1;
				return ;
			}
			//停止状态
			if(state==0)
			{
				mp.prepareAsync();
				mp.setOnPreparedListener(new OnPreparedListener() {
					
					@Override
					public void onPrepared(MediaPlayer mpp) {
						// TODO Auto-generated method stub
						mp.start();
						state=1;
						System.out.println("stoptoplay>>>>");
					}
				});
			}
			//播放状态
			if(state==-1||state==1)
			{
				mp.reset();			
				mp.setDataSource(path);
				mp.prepareAsync();
				mp.setOnPreparedListener(new OnPreparedListener() {
					
					@Override
					public void onPrepared(MediaPlayer mpp) {
						// TODO Auto-generated method stub
						mp.start();
						state=1;
					}
				});
			}
					
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void stop() {
		// TODO Auto-generated method stub
        
        	mp.stop();
        	state=0;
        	changeUI=1;//告诉子线程更新UI
        	System.out.println("changeUI==1");
        	//mp.release();
     
        
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		 if(mp.isPlaying())
	        {
	        	mp.pause();
	        	state=2;
	        }
	}
	
	//释放播放资源
	public void release()
	{
		if(mp!=null)
		{
			state=-1;
			mp.release();
			mp=null;
		}
	}
	//获取当前播放进度
	public int getCurrentPosition()
	{
		if(state==1||state==2)
		{
			return mp.getCurrentPosition();
		}
		return 0;
//		return 100;
	}
	
	//播放下一曲
	public void playNext()
	{
		if(mp!=null&&adapter.getCount()>0)
		{
			
			MyMusicData mmd=(MyMusicData) adapter.getItem(play_mmd.getId());
			state=-1;//计时器暂停
			play_mmd=mmd;
			changeUI=1;//告诉子线程更新UI
			play(mmd.getData());		
		}
	}
    
	//跳转
	public void seekToPlay(int position)
	{
		if(mp!=null)
		{
			mp.seekTo(position);
			mp.start();
			state=1;
		}
	}
	
	
	
//	public void startTimer(final Handler mHandler,final SeekBar sBar)
//	{
//		if(timer==null)
//		{
//			timer=new Timer();
//			
//			task=new TimerTask() {
//				
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					//timer.schedule(this, 10, 1000);
//					int tt=mp.getCurrentPosition();
//					if(MyMusicToolPlay.state==-1||MyMusicToolPlay.state==2)
//					{
//						return ;
//					}
//					if(MyMusicToolPlay.changeUI==1)
//					{
//						MyMusicToolPlay.changeUI=0;
//						new Thread()
//						{
//							public void run() {
//								Message message=new Message();
//								message.obj="ssss";
//								mHandler.sendMessage(message);
//								
//							};
//						}.start();
////						
//						
//						System.out.println("改变");
//					}
//					sBar.setProgress(tt);
//					//System.out.println(tt);
//				}
//			};
//			
//			timer.schedule(task,0, 10);
//		}
//		
//	}
	
//	public void stopTimer()
//	{
//		if(timer!=null)
//		{
//			timer.cancel();
//			task.cancel();
//			timer=null;
//			task=null;
//					
//		}
//	}

}
