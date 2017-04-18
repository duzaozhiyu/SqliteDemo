package com.example.sqliteimageview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.widget.ImageView;

public class MyRoundImageView extends ImageView {

	private Context context;
	private Bitmap bitmap;
	private BitmapShader shader;
	private Matrix matrix=new Matrix();
	
	
	public MyRoundImageView(Context context,AttributeSet attrs)
	{
		super(context,attrs);
	}
	
	public MyRoundImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context=context;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		//super.onDraw(canvas);
		Bitmap rawbitmap=g
	}
	
}
