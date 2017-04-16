package com.example.sqlitehelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sqliteXMl.MyXmlTool;
import com.example.sqlitedemo.R;

public class MyMusicToolUI extends MusicToolUI {

	private MyBaseAdapter<MyMusicData> adapter;
	private Context context;
	
	public MyMusicToolUI(Context context)
	{
		this.context=context;
	}
	
	@Override
	public MyBaseAdapter setListadapter(ListView list) {
		// TODO Auto-generated method stub
	    String str_item[]={MusicFinalVar.ID,MusicFinalVar.ALBUM,MusicFinalVar.ARTIST,MusicFinalVar.DATA
			        ,MusicFinalVar.DURATION,MusicFinalVar.SIZE,MusicFinalVar.TITLE,MusicFinalVar.ALBUM_ID		
			        };
		Cursor resource=context.getContentResolver().query(MusicFinalVar.uri, str_item, null, null, MusicFinalVar.SIZE);
	    init();
	    int count=1;
		 while(resource.moveToNext())
	        {
	        	MyMusicData mmd=new MyMusicData();
	        	mmd.setId(count);
	        	mmd.setTitle(resource.getString(resource.getColumnIndex(MusicFinalVar.TITLE)));
	        	mmd.setAlbum(resource.getString(resource.getColumnIndex(MusicFinalVar.ALBUM)));
	        	mmd.setArtist(resource.getString(resource.getColumnIndex(MusicFinalVar.ARTIST)));
	        	mmd.setAlbum_id(resource.getInt(resource.getColumnIndex(MusicFinalVar.ALBUM_ID)));
	        	mmd.setSize(resource.getLong(resource.getColumnIndex(MusicFinalVar.SIZE)));
	        	mmd.setData(resource.getString(resource.getColumnIndex(MusicFinalVar.DATA)));
	        	mmd.setTime(resource.getLong(resource.getColumnIndex(MusicFinalVar.DURATION)));
	        	//System.out.println(resource.getInt(resource.getColumnIndex(MusicFinalVar.ALBUM_ID)));
	        	adapter.addData(mmd);
	        	count++;
	        }
		 resource.close();
		 list.setAdapter(adapter);
		return adapter;
	}
	
	
	 private void init() {
		    //使用自定义adapter
//			// TODO Auto-generated method stub
			adapter=new MyBaseAdapter<MyMusicData>(context,R.layout.listitem_layout) {
	
				@Override
				public void getView2(int position, View convertView,
						ViewGroup parent) {
					
					TextView tv_num=(TextView) convertView.findViewById(R.id.tv_num);
					TextView tv_title=(TextView) convertView.findViewById(R.id.tv_title);
					TextView tv_artist=(TextView) convertView.findViewById(R.id.tv_artist);
					MyMusicData mmd=(MyMusicData) this.getItem(position);
					tv_num.setText(mmd.getId()+"");
					tv_title.setText(mmd.getTitle());
					tv_artist.setText(mmd.getArtist());
				}
			};
		
     }
	 //获取文件路径
	 public String getStringAlbum(int album_id)
	 {
		 String str_uri="content://media/external/audio/albums";
		 String []projection={"album_art"};
		
		 Uri uri=Uri.parse(str_uri+"/"+Integer.toString(album_id));
		 Cursor cur=context.getContentResolver().query(uri, projection, null, null, null);
		 String album_art=null;
		 if(cur.getCount()>0&&cur.getColumnCount()>0)
		 {
			 cur.moveToNext();
			 album_art=cur.getString(0);
		 }
		 
		 cur.close();
		 cur=null;
		 return album_art;
		 
	 }

	@Override
	public void setAlbumImage(int id, View view) {
		// TODO Auto-generated method stub
		
		String pathName=getStringAlbum(id);
		
		//BitmapDrawable bd=new 
		BitmapFactory.Options o=new BitmapFactory.Options();
		o.inSampleSize=2;
		Bitmap bitmap=BitmapFactory.decodeFile(pathName,o);
		System.out.println(bitmap);
		if(bitmap==null)
		{
			((ImageView)view).setImageDrawable(context.getResources().getDrawable(R.drawable.music_icon));
		     return ;
		}
		
		 ((ImageView)view).setImageBitmap(bitmap);
	}
	
	//读取xml 文件中的内容
	public MyMusicData readXML()
	{
		MyXmlTool mxt=new MyXmlTool();
		MyMusicData mmd=null;
		InputStream is;
		try {
			is = context.openFileInput("music.xml");
			mmd=mxt.parse(is);
			is.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mmd;
	}
	//写如xml文件
	public  void writeXML(MyMusicData mmd)
	{
		MyXmlTool mxt=new MyXmlTool();
		try {
			String xml=mxt.serialize(mmd);
			FileOutputStream fos=context.openFileOutput("music.xml", Context.MODE_PRIVATE);
//			FileOutputStream fos=new FileOutputStream(new File("mnt/sdcard/books.xml"));
			fos.write(xml.getBytes("utf-8"));
			fos.flush();
			fos.close();
//			System.out.println(xml+">>>>");
			
			
//			OutputStream os=context.openFileOutput("music.xml", Context.MODE_PRIVATE);
//			byte []buffer=xml.toString().trim().getBytes("utf-8");
//			os.write(buffer);
//			os.flush();
//			os.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
