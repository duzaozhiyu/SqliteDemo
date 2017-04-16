package com.example.sqlitehelper;

import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

public abstract class MusicToolUI {
     
	public abstract BaseAdapter setListadapter(ListView list);
	public abstract void setAlbumImage(int id,View view);
	
}
