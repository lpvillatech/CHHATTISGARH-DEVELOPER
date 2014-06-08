package com.example.imagearrangequiz;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class ImageSplitterUtility {

	public static List<SplitedImageInfo> getSplitedImageInfos(Context context,int rows,int column,Bitmap bitmap,int layoutWidth,int layoutHeight){
		List<SplitedImageInfo> imageInfos = new ArrayList<SplitedImageInfo>();
		int cellWidth = layoutWidth/column;
		int cellHeight = layoutHeight/rows;
		bitmap = Bitmap.createScaledBitmap(bitmap, layoutWidth, layoutHeight, false);
		//logic to 
		for(int i=0;i<rows;i++){
			for(int j=0;j<column;j++){
				SplitedImageInfo info = new SplitedImageInfo();
				info.setTop(cellHeight*i);
				info.setLeft(cellWidth*j);
				info.setWidth(cellWidth);
				info.setHeight(cellHeight);
				info.setSno(i*column+j);
				RelativeLayout.LayoutParams params = new LayoutParams(cellWidth, cellHeight);
				params.leftMargin = info.getLeft();
				params.topMargin = info.getTop();
				ImageView imageView = new ImageView(context);
				imageView.setLayoutParams(params);
				imageView.setImageBitmap(getCroppedBitmapFromPathForTriangle(context, bitmap, info));
				info.setImageView(imageView);
				imageInfos.add(info);
			}
		}
		
		
		return imageInfos;
		
	}
	
	
	public static Bitmap getCroppedBitmapFromPathForTriangle(Context context,
			Bitmap bitmap, SplitedImageInfo info) {
		try {

			Bitmap resultingImage = Bitmap.createBitmap(bitmap.getWidth(),
					bitmap.getHeight(), bitmap.getConfig());

			Canvas canvas = new Canvas(resultingImage);

			Paint paint = new Paint();
			paint.setAntiAlias(true);
			Path path = new Path();
			path.lineTo(info.getLeft(), info.getTop());
			path.lineTo(info.getLeft()+info.getWidth(), info.getTop());
			path.lineTo(info.getLeft()+info.getWidth(), info.getTop()+info.getHeight());
			path.lineTo(info.getLeft(), info.getTop()+info.getHeight());
			path.lineTo(info.getLeft(), info.getTop());

			canvas.drawPath(path, paint);

			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));

			canvas.drawBitmap(bitmap, 0, 0, paint);
			paint.setColor(Color.RED);
			paint.setStrokeWidth(2);
			paint.setStyle(Style.STROKE);

			canvas.drawPath(path, paint);
			Bitmap croppedBmp = Bitmap.createBitmap(resultingImage,
					info.getLeft(), info.getTop(), info.getWidth(),
					info.getHeight());
			return croppedBmp;

		} catch (Exception e) {
		}
		return null;
	}

}
