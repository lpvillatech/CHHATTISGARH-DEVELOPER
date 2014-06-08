package com.klpd.lovetest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.klpd.lovetest.util.Utility;

 
public class MainActivity extends Activity {
 	private EditText mEditTextBoy, mEditTextGirl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mEditTextBoy = (EditText) findViewById(R.id.edittext_boy_name);
		mEditTextGirl = (EditText) findViewById(R.id.edittext_girl_name);
 	}

 	public void loveStrengthTest(View v) {
		String boy = mEditTextBoy.getText().toString();
		String girl = mEditTextGirl.getText().toString();
		if (!boy.equals("") && !girl.equals("")) {
			int percent = Utility.getLoveStrength(boy, girl);
			mLovePercent = percent;
			initLoveResultViews();

		} else {
			Toast.makeText(this, getString(R.string.warn_msg),
					Toast.LENGTH_SHORT).show();
		}

	}

 	private int mLovePercent = 50;
	private TextView mTextViewLoveProcess, mTextViewYouHave, mTextViewBonding;
	private static int lovePer = 0;
	private boolean isLoveProcess;
 	private void initLoveResultViews() {
		isLoveProcess = true;
		setContentView(R.layout.activity_love_result_process);
		mTextViewLoveProcess = (TextView) findViewById(R.id.textview_love_process);
		mTextViewYouHave = (TextView) findViewById(R.id.textview_youhave);
		mTextViewBonding = (TextView) findViewById(R.id.textview_bonding);

		overridePendingTransition(R.anim.slide_from_right,
				R.anim.slide_from_right);

		 
		 	if (isLoveProcess) {
			thread.start();
		}

	}

 	private TextView textViewLoveDone;
	private boolean isLoveCalcDone;

 	private void initLovedoneViews() {

		setContentView(R.layout.activity_love_result_done);

		textViewLoveDone = (TextView) findViewById(R.id.textview_lovedone);
		if (isLoveCalcDone) {
			textViewLoveDone.setText(mLovePercent + "%");
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void onBackPressed() {
		if (isLoveCalcDone || isLoveProcess) {
			startActivity(new Intent(this, MainActivity.class));
			finish();
		} else {
			finish();
		}

	};
 	Thread thread = new Thread(new Runnable() {

		@Override
		public void run() {
			lovePer = 0;
			synchronized (this) {
				for (int i = 0; i <= mLovePercent; i++) {
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					handler.sendEmptyMessage(0);

				}
				isLoveCalcDone = true;
				handler.sendEmptyMessage(0);
			}

		}
	});
	Handler handler = new Handler(new Callback() {

		@Override
		public boolean handleMessage(Message msg) {

			mTextViewLoveProcess.setText(lovePer + "%");
			lovePer++;
			if (isLoveCalcDone) {
				initLovedoneViews();
			}
			return false;
		}
	});

}
