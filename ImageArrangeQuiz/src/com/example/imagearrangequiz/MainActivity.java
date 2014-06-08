package com.example.imagearrangequiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

public class MainActivity extends Activity implements OnTouchListener {
private Bitmap mBitmap;
private List<SplitedImageInfo> mListImageInfos;
private List<SplitedImageInfo> mSplitedImageInfosOriginal;
private RelativeLayout layout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		layout = (RelativeLayout)findViewById(R.id.layout_splitimage);
		layout.setOnTouchListener(this);
		mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image);
		mListImageInfos = ImageSplitterUtility.getSplitedImageInfos(getApplicationContext(), 4, 4, mBitmap, 400, 400);
		mSplitedImageInfosOriginal = new ArrayList<SplitedImageInfo>();
		mSplitedImageInfosOriginal.addAll(mListImageInfos);
		suffleList();
	}
	
	
private void suffleList() {
		 Collections.shuffle(mListImageInfos);
	
		 for (int i = 0; i < mListImageInfos.size(); i++) {
			 int n = mListImageInfos.get(i).getLeft();
			mListImageInfos.get(i).setLeft(mSplitedImageInfosOriginal.get(i).getLeft());
			mSplitedImageInfosOriginal.get(i).setLeft(n);
			n = mListImageInfos.get(i).getTop();
			mListImageInfos.get(i).setTop(mSplitedImageInfosOriginal.get(i).getTop());
			mSplitedImageInfosOriginal.get(i).setTop(n);
			 RelativeLayout.LayoutParams 		params = (LayoutParams) mListImageInfos.get(i).getImageView().getLayoutParams();
			params.leftMargin = mListImageInfos.get(i).getLeft();
			params.topMargin = mListImageInfos.get(i).getTop();
			mListImageInfos.get(i).getImageView().setLayoutParams(params);
			
			
			

			   		params = (LayoutParams) mSplitedImageInfosOriginal.get(i).getImageView().getLayoutParams();
			params.leftMargin = mSplitedImageInfosOriginal.get(i).getLeft();
			params.topMargin = mSplitedImageInfosOriginal.get(i).getTop();
			mSplitedImageInfosOriginal.get(i).getImageView().setLayoutParams(params);
			
			 
		}
		
	}


@Override
	protected void onResume() {
	 addImageViews(mListImageInfos);
		super.onResume();
	}
	 private void addImageViews(List<SplitedImageInfo> infos){
		 layout.removeAllViews();
		 for (SplitedImageInfo splitedImageInfo : infos) {
			 
			layout.addView(splitedImageInfo.getImageView());
		}
	 }

private ImageView mImageViewGlobal;

private int selectedIndex=0;
private RelativeLayout.LayoutParams paramsGlobal;
	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		 int x = (int) arg1.getX();
		 int y=(int) arg1.getY();
		
		 switch(arg1.getAction()){
			 case MotionEvent.ACTION_DOWN:
				 selectedIndex=0;
				 mImageViewGlobal=null;
				 mImageViewGlobal = getSetelctedView(x,y);
				 selectedIndex=getSetelctedViewIndex(x, y);
				 break;
			 case MotionEvent.ACTION_MOVE:
				 if(mImageViewGlobal!=null){
					 paramsGlobal  =  (LayoutParams) mImageViewGlobal.getLayoutParams();
					 paramsGlobal.leftMargin = x-(paramsGlobal.width/2);
					 paramsGlobal.topMargin = y-(paramsGlobal.height/2);
					 mImageViewGlobal.setLayoutParams(paramsGlobal);
				 }
				 break;
			 case MotionEvent.ACTION_UP:
				 int newIndex=getSetelctedViewIndex(x, y);
				 ImageView imageViewReplace = getSetelctedView(x, y);
				 if(imageViewReplace!=null && newIndex!=-1 && mImageViewGlobal!=null){
					 int n = mListImageInfos.get(selectedIndex).getLeft();
					 mListImageInfos.get(selectedIndex).setLeft(mListImageInfos.get(newIndex).getLeft());
					 mListImageInfos.get(newIndex).setLeft(n);
					 
					 n = mListImageInfos.get(selectedIndex).getTop();
					 mListImageInfos.get(selectedIndex).setTop(mListImageInfos.get(newIndex).getTop());
					 mListImageInfos.get(newIndex).setTop(n);
					 SplitedImageInfo info = mListImageInfos.get(selectedIndex);
					 mListImageInfos.set(selectedIndex, mListImageInfos.get(newIndex));
					 mListImageInfos.set(newIndex, info);
					 
					 
					 paramsGlobal = (LayoutParams) mListImageInfos.get(newIndex).getImageView().getLayoutParams();
					 paramsGlobal.leftMargin = mListImageInfos.get(newIndex).getLeft();
					 paramsGlobal.topMargin = mListImageInfos.get(newIndex).getTop();
					 mListImageInfos.get(newIndex).getImageView().setLayoutParams(paramsGlobal);
					 
					 
					 
					 paramsGlobal = (LayoutParams) mListImageInfos.get(selectedIndex).getImageView().getLayoutParams();
					 paramsGlobal.leftMargin = mListImageInfos.get(selectedIndex).getLeft();
					 paramsGlobal.topMargin = mListImageInfos.get(selectedIndex).getTop();
					 mListImageInfos.get(selectedIndex).getImageView().setLayoutParams(paramsGlobal);
					  
				 }else if(selectedIndex!=-1 && mImageViewGlobal!=null && newIndex==-1){
					 mImageViewGlobal.setLayoutParams(mListImageInfos.get(selectedIndex).getImageView().getLayoutParams());
				 }
				 if(isGameCompleted()){
					 Toast.makeText(MainActivity.this, "Game Completed!", Toast.LENGTH_LONG).show();
					 finish();
				 }
				 break;
		 }
		return true;
	}


	private ImageView getSetelctedView(int x, int y) {
		int x1=0,y1=0,w=0,h=0;
		
		 for (int i = 0; i < mListImageInfos.size(); i++) {
			x1 = mListImageInfos.get(i).getLeft();
			y1 = mListImageInfos.get(i).getTop();
			w=mListImageInfos.get(i).getWidth();
			h=mListImageInfos.get(i).getHeight();
			if(x>=x1 && x<=(x1+w) && y>=y1 && y<=(y1+h)){
				return mListImageInfos.get(i).getImageView();
			}
		}
		return null;
	}
	private int getSetelctedViewIndex(int x, int y) {
		int x1=0,y1=0,w=0,h=0;
		
		 for (int i = 0; i < mListImageInfos.size(); i++) {
			x1 = mListImageInfos.get(i).getLeft();
			y1 = mListImageInfos.get(i).getTop();
			w=mListImageInfos.get(i).getWidth();
			h=mListImageInfos.get(i).getHeight();
			if(x>=x1 && x<=(x1+w) && y>=y1 && y<=(y1+h)){
				return i;
			}
		}
		return -1;
	}

	private boolean isGameCompleted(){
		
		for(int i=0;i<mListImageInfos.size() && i<mSplitedImageInfosOriginal.size();i++){
			if(mListImageInfos.get(i).getSno()!=mSplitedImageInfosOriginal.get(i).getSno()){
				return false;
			}
		}
		return true;
	}
	
}