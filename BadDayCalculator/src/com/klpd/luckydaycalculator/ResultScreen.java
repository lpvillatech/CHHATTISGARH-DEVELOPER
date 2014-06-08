package com.klpd.luckydaycalculator;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.widget.TextView;

public class ResultScreen extends Activity {
	private String personName;
	private TextView mTextViewLuckyDay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result_screen);
		mTextViewLuckyDay = (TextView) findViewById(R.id.textview_luckyday);
		personName = getIntent().getStringExtra(
				getString(R.string.extra_person_name));
		thread.start();

	}

	private Thread thread = new Thread(new Runnable() {

		@Override
		public void run() {

			for (int i = 0; i <= 100; i++) {
				handler.sendEmptyMessage(i);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	});

	Handler handler = new Handler(new Callback() {

		@Override
		public boolean handleMessage(Message arg0) {
			if (arg0.what < 100) {
				mTextViewLuckyDay
						.setText(getString(R.string.text_process_result) + " "
								+ arg0.what + "%");
			} else {
				mTextViewLuckyDay.setText(getString(R.string.text_result) + " "
						+ getLuckyDay(personName));
			}
			return false;
		}
	});

	private String getLuckyDay(String person) {
		long l = person.hashCode() * AlarmManager.INTERVAL_DAY;
		l = l % (AlarmManager.INTERVAL_DAY * 30);
		l = (AlarmManager.INTERVAL_DAY * 30) - l;
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(calendar.getTimeInMillis() + l);

		return calendar.get(Calendar.DAY_OF_MONTH) + "-"
				+ getMonthStringByInt(calendar.get(Calendar.MONTH) + 1) + "-"
				+ calendar.get(Calendar.YEAR);

	}

	private String getMonthStringByInt(int month) {

		switch (month) {
		case 0:
			return "January";
		case 1:
			return "February";
		case 2:
			return "March";
		case 3:
			return "April";
		case 4:
			return "May";
		case 5:
			return "Jun";
		case 6:
			return "July";
		case 7:
			return "August";
		case 8:
			return "September";
		case 9:
			return "October";
		case 10:
			return "November";
		case 11:
			return "December";

		default:
			return "";

		}
	}

}
