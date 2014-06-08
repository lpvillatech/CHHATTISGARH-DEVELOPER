package com.klpd.mobilerechargeprank;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {
	private EditText mEditTextSender, mEditTextMsgBody;
	private Spinner mSpinnerMinute;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mEditTextSender = (EditText) findViewById(R.id.edittext_rechargeamount);
		mSpinnerMinute = (Spinner) findViewById(R.id.spinner_minute);
		mEditTextMsgBody = (EditText) findViewById(R.id.edittext_msgbody);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onSchedule(View v) {
		String sender = mEditTextSender.getText().toString();
		String msgBody = mEditTextMsgBody.getText().toString();

		int spinnerIndex = mSpinnerMinute.getSelectedItemPosition();
		String[] minuteArray = getResources().getStringArray(
				R.array.array_minute);
		int minute = Integer.parseInt(minuteArray[spinnerIndex]);
		if (sender != null && sender.length()>0 && msgBody != null && msgBody.length()>0) {
			AlarmSettingManager
					.schedule(this, minute,sender,msgBody);
			finish();
		} else {
			Toast.makeText(MainActivity.this, "Please fill all field",
					Toast.LENGTH_SHORT).show();
		}

	}
}
