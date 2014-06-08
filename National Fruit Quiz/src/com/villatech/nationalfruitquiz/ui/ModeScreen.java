package com.villatech.nationalfruitquiz.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.villatech.nationalfruitquiz.R;
 public class ModeScreen extends Activity implements OnClickListener,
		AnimationListener {

	private Button mBtnImageMode, mBtnTextOptionMode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mode_selector);
		initViews();

	}

	private void initViews() {

		mBtnImageMode = (Button) findViewById(R.id.btnArcade);
		mBtnTextOptionMode = (Button) findViewById(R.id.btnStandard);
		mBtnImageMode.setOnClickListener(this);
		mBtnTextOptionMode.setOnClickListener(this);

		mBtnTextOptionMode.setAnimation(AnimationUtils.loadAnimation(this,
				R.anim.slide_in_left_anim));
		mBtnImageMode.setAnimation(AnimationUtils.loadAnimation(this,
				R.anim.slide_in_right));

	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.btnArcade) {
			Intent intent = new Intent(this, ImageModeScreen.class);
			startActivity(intent);
		}
		if (view.getId() == R.id.btnStandard) {
			Intent intent = new Intent(this, TextOptionModeScreen.class);
			startActivity(intent);
		}

	}

	@Override
	public void onAnimationStart(Animation animation) {

	}

	@Override
	public void onAnimationEnd(Animation animation) {

	}

	@Override
	public void onAnimationRepeat(Animation animation) {

	}
}
