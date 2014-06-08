package com.klpd.shakecharger;

import java.util.Random;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener {

	private static final int MIN_SHAKE_ACCELERATION = 10;

	private static final int MIN_MOVEMENTS = 2;

	private static final int MAX_SHAKE_DURATION = 500;

	private float[] mGravity = { 0.0f, 0.0f, 0.0f };
	private float[] mLinearAcceleration = { 0.0f, 0.0f, 0.0f };

	private static final int X = 0;
	private static final int Y = 1;
	private static final int Z = 2;

	long startTime = 0;

	int moveCount = 0;

	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
private ImageView mImageViewCharhing;

private AnimationDrawable animationDrawable;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		mImageViewCharhing = (ImageView)findViewById(R.id.imageview_battery);
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		mAccelerometer = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mSensorManager.registerListener(MainActivity.this, mAccelerometer,
				SensorManager.SENSOR_DELAY_UI);


	}

 
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		setCurrentAcceleration(event);

		// Get the max linear acceleration in any direction
		float maxLinearAcceleration = getMaxCurrentLinearAcceleration();

		// Check if the acceleration is greater than our minimum threshold
		if (maxLinearAcceleration > MIN_SHAKE_ACCELERATION) {
			long now = System.currentTimeMillis();

			// Set the startTime if it was reset to zero
			if (startTime == 0) {
				startTime = now;
			}

			long elapsedTime = now - startTime;

			// Check if we're still in the shake window we defined
			if (elapsedTime > MAX_SHAKE_DURATION) {
				// Too much time has passed. Start over!
				resetShakeDetection();
			} else {
				// Keep track of all the movements
				moveCount++;

				// Check if enough movements have been made to qualify as a
				// shake
				if (moveCount > MIN_MOVEMENTS) {
					// It's a shake! Notify the listener.
					/* mShakeListener.onShake(); */

				startCharging();

					// Reset for the next one!
					resetShakeDetection();
				}
			}
		}


		
	}
	
	private void startCharging() {
		if(animationDrawable!=null){
			animationDrawable.stop();
		}
		  animationDrawable = (AnimationDrawable) mImageViewCharhing.getBackground();
		animationDrawable.start();
		handlerStopBattery.removeMessages(1);
		handlerStopBattery.sendEmptyMessageDelayed(1, 5000+new Random().nextInt(5000));
		
	}

@Override
protected void onResume() {
Toast.makeText(this, "Please shake the phone to charge battery!", Toast.LENGTH_LONG).show();
	super.onResume();
}
	private void setCurrentAcceleration(SensorEvent event) {
		/*
		 * BEGIN SECTION from Android developer site. This code accounts for
		 * gravity using a high-pass filter
		 */

		// alpha is calculated as t / (t + dT)
		// with t, the low-pass filter's time-constant
		// and dT, the event delivery rate

		final float alpha = 0.8f;

		// Gravity components of x, y, and z acceleration
		mGravity[X] = alpha * mGravity[X] + (1 - alpha) * event.values[X];
		mGravity[Y] = alpha * mGravity[Y] + (1 - alpha) * event.values[Y];
		mGravity[Z] = alpha * mGravity[Z] + (1 - alpha) * event.values[Z];

		// Linear acceleration along the x, y, and z axes (gravity effects
		// removed)
		mLinearAcceleration[X] = event.values[X] - mGravity[X];
		mLinearAcceleration[Y] = event.values[Y] - mGravity[Y];
		mLinearAcceleration[Z] = event.values[Z] - mGravity[Z];

		/*
		 * END SECTION from Android developer site
		 */
	}
	private float getMaxCurrentLinearAcceleration() {
		// Start by setting the value to the x value
		float maxLinearAcceleration = mLinearAcceleration[X];

		// Check if the y value is greater
		if (mLinearAcceleration[Y] > maxLinearAcceleration) {
			maxLinearAcceleration = mLinearAcceleration[Y];
		}

		// Check if the z value is greater
		if (mLinearAcceleration[Z] > maxLinearAcceleration) {
			maxLinearAcceleration = mLinearAcceleration[Z];
		}

		// Return the greatest value
		return maxLinearAcceleration;
	}

	private void resetShakeDetection() {
		startTime = 0;
		moveCount = 0;
	}
	private android.os.Handler.Callback haCallback = new Callback() {
		
		@Override
		public boolean handleMessage(Message arg0) {
			if(arg0.what==1){
			 if(animationDrawable!=null){
				 animationDrawable.stop();
				 Toast.makeText(MainActivity.this, "Please shake the phone to charge battery!", Toast.LENGTH_LONG).show();
			 }
			}
			return true;
		}
	};
private Handler handlerStopBattery = new Handler(haCallback);

}
