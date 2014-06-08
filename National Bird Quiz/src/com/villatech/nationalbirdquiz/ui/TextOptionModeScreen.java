package com.villatech.nationalbirdquiz.ui;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.villatech.nationalbirdquiz.CountryFlagModel;
import com.villatech.nationalbirdquiz.R;

public class TextOptionModeScreen extends Activity implements OnClickListener,
		AnimationListener {
	private Drawable mButtonDefault;
	private Animation mAnimationZoomIn;

	private List<CountryFlagModel> mListResult;
	private List<Button> mButtonAnswerList;
	private CountryFlagModel flag1, flag2, flag3, flag4;
	private Button mButtonFocus;
	private ImageView mFlag;
	private Button mAns1, mAns2, mAns3, mAns4, mAns;
	private int FIRST = 0, SECOND = 1, THIRD = 2, FOURTH = 3;
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.standard_mode_layout);
		initViews();
		mButtonDefault = mAns1.getBackground();
		mListResult = getCountryFlagList();
		setRandomCountryFlags();
		initializeAnimation();
		setAnswerRandom();
	}

	private List<CountryFlagModel> getCountryFlagList() {
		List<CountryFlagModel> list = new ArrayList<CountryFlagModel>();

		AssetManager manager = getResources().getAssets();

		try {
			String[] filePaths = manager.list("images");
			for (String string : filePaths) {
				CountryFlagModel info = new CountryFlagModel();
				info.setmFlagFile(string);
				info.setmCountryName(getFileNameByRemovingExtension(new File(
						string).getName()));

				list.add(info);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return list;
	}

	private String getFileNameByRemovingExtension(String fileName) {
		fileName = fileName.substring(0, fileName.indexOf("."));
		return fileName;

	}
 	private void initializeAnimation() {

		mAnimationZoomIn = AnimationUtils.loadAnimation(
				getApplicationContext(), R.anim.zoom_in_rotate_animation);
		mFlag.startAnimation(mAnimationZoomIn);

		mAnimationZoomIn.setAnimationListener(this);

	}

	private void initViews() {
		mFlag = (ImageView) findViewById(R.id.flag_imageview);
		mAns1 = (Button) findViewById(R.id.answer1);
		mAns2 = (Button) findViewById(R.id.answer2);
		mAns3 = (Button) findViewById(R.id.answer3);
		mAns4 = (Button) findViewById(R.id.answer4);
		mButtonFocus = (Button) findViewById(R.id.btnIndicator);
		setAnsButtonVisibility(View.INVISIBLE);

		mAns1.setOnClickListener(this);
		mAns2.setOnClickListener(this);
		mAns3.setOnClickListener(this);
		mAns4.setOnClickListener(this);
	}

	public void setAnsButtonVisibility(int visiblity) {
		mAns1.setVisibility(visiblity);
		mAns2.setVisibility(visiblity);
		mAns3.setVisibility(visiblity);
		mAns4.setVisibility(visiblity);
	}

	private void setImage(CountryFlagModel cFlag) throws IOException {
		InputStream is = this.getAssets()
				.open("images/" + cFlag.getmFlagFile());
		Bitmap bmp = BitmapFactory.decodeStream(is);
		mFlag.setImageBitmap(bmp);
	}

	private void setAnswerRandom() {
		mButtonAnswerList = new ArrayList<Button>();
		mButtonAnswerList.add(mAns1);
		mButtonAnswerList.add(mAns2);
		mButtonAnswerList.add(mAns3);
		mButtonAnswerList.add(mAns4);
		Collections.shuffle(mButtonAnswerList, new Random(System.nanoTime()));
		mAns = mButtonAnswerList.get(FIRST);

		mButtonAnswerList.get(FIRST).setTag(flag1.getmCountryName());
		mButtonAnswerList.get(FIRST).setText(flag1.getmCountryName());

		mButtonAnswerList.get(SECOND).setTag(flag2.getmCountryName());
		mButtonAnswerList.get(SECOND).setText(flag2.getmCountryName());

		mButtonAnswerList.get(THIRD).setTag(flag3.getmCountryName());
		mButtonAnswerList.get(THIRD).setText(flag3.getmCountryName());

		mButtonAnswerList.get(FOURTH).setTag(flag4.getmCountryName());
		mButtonAnswerList.get(FOURTH).setText(flag4.getmCountryName());

		mListResult.remove(FIRST);

	}

	public void setRandomCountryFlags() {
		Collections.shuffle(mListResult);
		flag1 = mListResult.get(FIRST);
		flag2 = mListResult.get(SECOND);
		flag3 = mListResult.get(THIRD);
		flag4 = mListResult.get(FOURTH);
		try {
			if (mListResult != null) {
				setImage(flag1);
			} else {
				throw new IOException();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View ansButton) {
		Animation animation = AnimationUtils.loadAnimation(this,
				R.anim.anim_header);

		if (ansButton.getTag().equals(flag1.getmCountryName())) {
			ansButton.setBackgroundResource(R.drawable.button_green);

			mButtonFocus.setVisibility(View.VISIBLE);
			mButtonFocus.setBackgroundResource(R.drawable.button_green);
			mButtonFocus.setText(getString(R.string.ans));
			mButtonFocus.startAnimation(animation);

			animation.setAnimationListener(ansStatusAnimListener);

		} else {

			ansButton.setBackgroundResource(R.drawable.red_button);
			mAns.setBackgroundResource(R.drawable.button_green);

			mButtonFocus.setVisibility(View.VISIBLE);
			mButtonFocus.setBackgroundResource(R.drawable.red_button);
			mButtonFocus.setText(getString(R.string.wrong_ans));
			mButtonFocus.startAnimation(animation);

			animation.setAnimationListener(ansStatusAnimListener);

		}

	}

	private AnimationListener ansStatusAnimListener = new AnimationListener() {

		@Override
		public void onAnimationStart(Animation animation) {

		}

		@Override
		public void onAnimationRepeat(Animation animation) {

		}

		@Override
		public void onAnimationEnd(Animation animation) {
			delayNextQues();
		}
	};

	@Override
	public void onAnimationEnd(Animation animation) {
		setAnsButtonVisibility(View.VISIBLE);

		mAns1.startAnimation(AnimationUtils.loadAnimation(
				TextOptionModeScreen.this, R.anim.slide_in_left_anim));
		mAns2.startAnimation(AnimationUtils.loadAnimation(
				TextOptionModeScreen.this, R.anim.slide_in_right));
		mAns3.startAnimation(AnimationUtils.loadAnimation(
				TextOptionModeScreen.this, R.anim.slide_in_left_anim));
		mAns4.startAnimation(AnimationUtils.loadAnimation(
				TextOptionModeScreen.this, R.anim.slide_in_right));

	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		setAnsButtonVisibility(View.VISIBLE);

	}

	@Override
	public void onAnimationStart(Animation animation) {

	}

	private void reInitAll() {
		setAnsButtonVisibility(View.INVISIBLE);

		Animation animation = AnimationUtils.loadAnimation(getBaseContext(),
				R.anim.revhead);
		mButtonFocus.startAnimation(animation);

		mButtonFocus.setVisibility(View.INVISIBLE);
		setRandomCountryFlags();
		setAnswerRandom();
		initializeAnimation();
	}

	@SuppressWarnings("deprecation")
	private void resetButtonColors() {
		mAns1.setBackgroundDrawable(mButtonDefault);
		mAns2.setBackgroundDrawable(mButtonDefault);
		mAns3.setBackgroundDrawable(mButtonDefault);
		mAns4.setBackgroundDrawable(mButtonDefault);

	}

	public void delayNextQues() {
		handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				resetButtonColors();
				reInitAll();

			}
		}, 1100);
	}

}
