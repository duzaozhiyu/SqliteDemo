package com.example.sqliteFragment;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sqlitedemo.R;
import com.example.sqliteimageview.MyRoundImageView;

public class MyImageFragment extends Fragment {
	
	private MyRoundImageView image;
	private Bitmap map;
	private String strpath;
	

	
   @Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	//return super.onCreateView(inflater, container, savedInstanceState);
	   View view=inflater.inflate(R.layout.album_fragment, container,false);
	   image=(MyRoundImageView)view.findViewById(R.id.myImageView);
	   strpath=getArguments().getString("filepath");
	   map=setBitmap(strpath);
	   if(map!=null)
	   {
		   image.setImageBitmap(map); 
	   }	   
	   //image.draw(canvas);
	   return view;
}  
   public Bitmap setBitmap(String filepath)
	{
	    if(filepath==null)
	    {
	    	return null;
	    }
	    Bitmap map=BitmapFactory.decodeFile(filepath);
	    return map;
	}
   
}
