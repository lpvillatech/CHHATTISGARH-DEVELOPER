package com.klpd.luckydaycalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
private EditText mEditTextName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mEditTextName = (EditText)findViewById(R.id.edittext_name);
	}
	
	public void onTest(View v){
		String personName = mEditTextName.getText().toString();
		if(personName!=null && personName.length()>0){
		personName = personName.toLowerCase();
		Intent intent = new  Intent(this, ResultScreen.class);
		intent.putExtra(getString(R.string.extra_person_name),personName);
		startActivity(intent);
		}else{
			
			Toast.makeText(this, "Please enter person name",Toast.LENGTH_SHORT).show();
		}
	}

	 

}
