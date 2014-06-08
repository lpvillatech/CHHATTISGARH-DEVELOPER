package com.klpd.mobilerechargeprank;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class RechargeReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		String sender = arg1.getStringExtra(arg0
				.getString(R.string.extra_sender));
		String body = arg1.getStringExtra(arg0
				.getString(R.string.extra_msg_body));
		sendSms(arg0, sender, body);
	}

	private void sendSms(Context context, String sender, String body) {
		ContentValues my_values = new ContentValues();
		my_values.put("address", sender);// sender name
		my_values.put("body", body);
		context.getContentResolver().insert(Uri.parse("content://sms/inbox"),
				my_values);
	}

}
