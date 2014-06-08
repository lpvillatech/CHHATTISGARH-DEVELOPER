package com.klpd.mobilerechargeprank;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AlarmSettingManager {
	AlarmManager alarmManager;

	public static void schedule(Context context, int intervalMinute,
			String sender, String msgBody) {
		// Get CheckEventsService pending intent
		Intent intentStart = new Intent(context, RechargeReceiver.class);
		intentStart.putExtra(context.getString(R.string.extra_sender), sender);
		intentStart.putExtra(context.getString(R.string.extra_msg_body),
				msgBody);

		Calendar calStart = new GregorianCalendar();
		long intervalTime = intervalMinute * 1000 * 60;

		PendingIntent pendingStart = PendingIntent.getBroadcast(context, msgBody.hashCode(),
				intentStart, 0);
		AlarmManager alarmStart = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		alarmStart.set(AlarmManager.RTC_WAKEUP, calStart.getTimeInMillis()
				+ intervalTime, pendingStart);

	}
}
