package com.example.sqliteimageview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class MyRoundImageView extends ImageView {

	private Context context;
	private Bitmap bitmap;
	private BitmapShader shader;
	private Matrix matrix=new Matrix();
	private Paint mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
	private String musicPath;
	
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
		
		
//		if(setBitmap(musicPath)!=null)
//		{
//			
//		}
		Drawable drawable=getDrawable();
		if(drawable instanceof BitmapDrawable)
		{
			bitmap=((BitmapDrawable)drawable).getBitmap();
		}
		
		else if (drawable instanceof ColorDrawable){
			        Rect rect = drawable.getBounds();
		         int width = rect.right - rect.left;
	            int height = rect.bottom - rect.top;
			        int color = ((ColorDrawable)drawable).getColor();
		            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			        Canvas canvasa = new Canvas(bitmap);
		           canvas.drawARGB(Color.alpha(color), Color.red(color), Color.green(color), Color.blue(color));

		}
		
		if(bitmap!=null)
		{
			int viewWidth=getWidth();
			int viewHeight=getHeight();
			int minSize=Math.min(viewWidth, viewHeight);
			float bitWidth=minSize;
			float bitHeight=minSize;
			if(shader==null)
			{
				shader=new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP);
			}
			matrix.setScale(bitWidth/bitmap.getWidth(),bitHeight/bitmap.getHeight());
			//matrix.setScale(0.4f,0.4f);
			shader.setLocalMatrix(matrix);//设置图片的缩放
			mPaint.setShader(shader);//设置圆形的内容
			
			canvas.drawCircle(viewWidth/2, viewHeight/2, (float) (minSize/2), mPaint);

		}
		
	}
	
	//设置要现实的Bitmap
	
	
}
