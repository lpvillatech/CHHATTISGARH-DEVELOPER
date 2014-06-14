package com.example.imagearrangequiz;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

 
public class MainScreen extends Activity {
	private Intent mIntent;
	private int INTENT_BROWSE_CODE = 1;
	private Uri mImageUri;
	public static String mUriName = "image_uri";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainscreen);
	}

	 
	public void browseGallery(View v) {
		mIntent = new Intent(Intent.ACTION_PICK);
		mIntent.setType("image/*");
		boolean validIntent = isIntentAvailable(this, mIntent);
		if (validIntent) {
			startActivityForResult(mIntent, INTENT_BROWSE_CODE);
		} else {
			Toast.makeText(this,
					getResources().getString(R.string.no_gallery_app),
					Toast.LENGTH_LONG).show();
		}
	}

	 
	public void openCamera(View v) {
		mIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

		boolean validIntent = isIntentAvailable(this, mIntent);
		if (validIntent) {
			startActivityForResult(mIntent, INTENT_BROWSE_CODE);
		} else {
			Toast.makeText(this,
					getResources().getString(R.string.no_camera_app),
					Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * checks whether any application exists to handle event
	 * */

	public static boolean isIntentAvailable(Context ctx, Intent intent) {
		final PackageManager mgr = ctx.getPackageManager();
		List<ResolveInfo> list = mgr.queryIntentActivities(intent,
				PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}

	/**
	 * Receive the image uri returned by gallery or camera.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent browsed) {
		super.onActivityResult(requestCode, resultCode, browsed);
		if (resultCode == RESULT_OK && requestCode == INTENT_BROWSE_CODE) {
			mImageUri = browsed.getData();
			Intent intent;
			intent = new Intent(this, MainActivity.class);
			if (mImageUri != null) {
				intent.putExtra(mUriName, mImageUri.toString());
				Log.d("uri", mImageUri.toString());
				boolean validIntent = isIntentAvailable(this, intent);
				if (validIntent) {
					startActivity(intent);
				} else {
					Toast.makeText(this, getString(R.string.img_read_fail),
							Toast.LENGTH_LONG).show();
				}
			} else {
				Toast.makeText(this, getString(R.string.browse_warn_msg),
						Toast.LENGTH_LONG).show();

			}

		}

	}

}
