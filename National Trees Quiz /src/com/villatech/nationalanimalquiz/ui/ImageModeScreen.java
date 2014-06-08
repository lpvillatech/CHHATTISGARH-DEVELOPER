package com.villatech.nationalanimalquiz.ui;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.villatech.nationalanimalquiz.CountryFlagModel;
import com.villatech.nationalanimalquiz.R;

 public class ImageModeScreen extends Activity implements AnimationListener,
		OnClickListener {

	private ImageView mFlag1, mFlag2, mFlag3, mFlag4;
	private Button mBUttonAns;
	private Animation animation;
	private List<CountryFlagModel> countryFlagModel;
	private ArrayList<Integer> CountryFlagList;
	private int FLAG_ANS_INDEX = 0;
	private Button mButtonFocus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imagemode_layout);

		initViews();
		initAnimation();

		countryFlagModel = getCountryFlags();

		shuffleList();
		setDatas();
	}

	private List<CountryFlagModel> getCountryFlags() {
		 List<CountryFlagModel> list = new ArrayList<CountryFlagModel>();
		 
		 AssetManager manager = getResources().getAssets();
		 
		 try {
			String[] filePaths = manager.list("images");
			for (String string : filePaths) {
				CountryFlagModel info = new CountryFlagModel();
				info.setmFlagFile(string);
				info.setmCountryName(getFileNameByRemovingExtension(new File(string).getName()));
				
				list.add(info);
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		return list;
	}
	private String getFileNameByRemovingExtension(String fileName){
		fileName = fileName.substring(0, fileName.indexOf("."));
		return fileName;
		
	}
 	private void shuffleList() {
		long seed = System.nanoTime();
		Collections.shuffle(countryFlagModel, new Random(seed));
	}

 	private void shuffleFlagContainerList() {
		long seed = System.nanoTime();
		Collections.shuffle(CountryFlagList, new Random(seed));
	}

	public Drawable getDataFromAsset(String imageName) {
		try {
			InputStream ims = getAssets().open("images/" + imageName);
			Drawable d = Drawable.createFromStream(ims, null);
			return d;
		} catch (IOException ex) {
			return null;
		}

	}

	private void setDatas() {

		if (countryFlagModel == null) {
			return;
		}

		mBUttonAns.setText(countryFlagModel.get(FLAG_ANS_INDEX)
				.getmCountryName());

		shuffleFlagContainerList();
		for (int i = 0; i < CountryFlagList.size(); i++) {
			ImageView imageView = (ImageView) findViewById(CountryFlagList.get(i));
			imageView.setImageDrawable(getDataFromAsset(countryFlagModel.get(i)
					.getmFlagFile()));
			imageView.setTag(countryFlagModel.get(i).getmCountryName());
			imageView.setOnClickListener(this);
		}

	}

	 
	 private void initAnimation() {
		mFlag1.setVisibility(View.VISIBLE);
		mFlag2.setVisibility(View.VISIBLE);
		mFlag3.setVisibility(View.VISIBLE);
		mFlag4.setVisibility(View.VISIBLE);

		animation = AnimationUtils.loadAnimation(this, R.anim.zoom_in_rotate_animation);
		mFlag1.startAnimation(animation);
		mFlag2.startAnimation(animation);
		mFlag3.startAnimation(animation);
		mFlag4.startAnimation(animation);

		animation.setAnimationListener(this);

	}

	 private void initViews() {
		mFlag1 = (ImageView) findViewById(R.id.imgFlag_1);
		mFlag2 = (ImageView) findViewById(R.id.imgFlag_2);
		mFlag3 = (ImageView) findViewById(R.id.imgFlag_3);
		mFlag4 = (ImageView) findViewById(R.id.imgFlag_4);
		mBUttonAns = (Button) findViewById(R.id.buttonAns);
		mButtonFocus = (Button) findViewById(R.id.btnIndicator);
		mBUttonAns.setVisibility(View.VISIBLE);
		mFlag1.setVisibility(View.INVISIBLE);
		mFlag2.setVisibility(View.INVISIBLE);
		mFlag3.setVisibility(View.INVISIBLE);
		mFlag4.setVisibility(View.INVISIBLE);

		CountryFlagList = new ArrayList<Integer>();
		CountryFlagList.add(R.id.imgFlag_1);
		CountryFlagList.add(R.id.imgFlag_2);
		CountryFlagList.add(R.id.imgFlag_3);
		CountryFlagList.add(R.id.imgFlag_4);

	}

	@Override
	public void onAnimationEnd(Animation animation) {
		mBUttonAns.setVisibility(View.VISIBLE);
	}

	@Override
	public void onAnimationRepeat(Animation animation) {

	}

	@Override
	public void onAnimationStart(Animation animation) {

	}

	@Override
	public void onClick(View v) {
		String taggedId = (String) v.getTag();
		if (taggedId.equals(countryFlagModel.get(FLAG_ANS_INDEX)
				.getmCountryName())) {
			correctAnswer();
		} else {
			wrongAnswer();
		}
	}

	private void wrongAnswer() {
		Animation animBtnAnim = AnimationUtils.loadAnimation(this,
				R.anim.anim_header);
		mButtonFocus.setVisibility(View.VISIBLE);
		mButtonFocus.setBackgroundResource(R.drawable.red_button);
		mButtonFocus.setText(getString(R.string.wrong_ans));
		mButtonFocus.startAnimation(animBtnAnim);
		animBtnAnim.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				showAnsFlagAndAnimate();
			}
		});

	}
	 
	protected void showAnsFlagAndAnimate() {
		final ImageView imageView = getAns();

		if (imageView != null) {
			Animation animation = AnimationUtils.loadAnimation(this,
					R.anim.wobble_animation);
			imageView.startAnimation(animation);
			animation.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation animation) {
					imageView.setFocusable(true);
				}

				@Override
				public void onAnimationRepeat(Animation animation) {

				}

				@Override
				public void onAnimationEnd(Animation animation) {
					imageView.setFocusable(false);
					resetGame();
				}
			});

		}
	}

	 
	private ImageView getAns() {
		for (int i = 0; i < CountryFlagList.size(); i++) {
			ImageView imageView = (ImageView) findViewById(CountryFlagList.get(i));
			String str = (String) imageView.getTag();
			if (str != null
					&& str.equals(countryFlagModel.get(FLAG_ANS_INDEX)
							.getmCountryCode())) {
				return imageView;
			}
		}
		return null;
	}

	 	private void correctAnswer() {

		Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_header);
		mButtonFocus.setVisibility(View.VISIBLE);
		mButtonFocus.setBackgroundResource(R.drawable.button_green);
		mButtonFocus.setText(getString(R.string.ans));
		mButtonFocus.startAnimation(animation);

		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// sleep the UI thread for 500 ms
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				resetGame();
			}
		});

	}

	 
	public void resetGame() {
		Animation animation = AnimationUtils.loadAnimation(this,
				R.anim.revhead);
		mButtonFocus.startAnimation(animation);

		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				mButtonFocus.setVisibility(View.INVISIBLE);
				if (deleteVisibleItemsFromList()) {
					initAnimation();
					shuffleList();
					setDatas();
				}
			}

		});

	}

	private boolean deleteVisibleItemsFromList() {
		if (countryFlagModel != null && countryFlagModel.size() > 0) {
			countryFlagModel.remove(FLAG_ANS_INDEX);
			return true;
		} else {
			Toast.makeText(this, getString(R.string.game_over),
					Toast.LENGTH_LONG).show();
			return false;
		}

	}
}
