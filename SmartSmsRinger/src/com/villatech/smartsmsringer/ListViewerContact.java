package com.villatech.smartsmsringer;

import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class ListViewerContact extends Activity implements OnItemClickListener,
		TextWatcher, OnClickListener {

	private final static String PERSON_NAME = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY
			: ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
	private final static String PERSON_PHOTO = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? ContactsContract.CommonDataKinds.Phone.PHOTO_URI
			: ContactsContract.CommonDataKinds.Phone.PHOTO_ID;
	public static final String KEY_CONTACT_NUMBER = "number";

	private ListView mContactsList;
	long mContactId;
	private Uri picUri;
	private List<ContactBean> mFetchedContactList;
	private List<ContactBean> mTempContactList;
	private String mSelectName;
	private ListAdapterContact conAdapter;
	private List<ContactBean> mSearchList;
	private EditText mEdtTextSearch;
	private ImageView mImageViewClear;
	private RelativeLayout mLayoutEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_contact_list);
		setTitle("Contacts");

		mContactsList = (ListView) findViewById(android.R.id.list);
		mEdtTextSearch = (EditText) findViewById(R.id.edtTextSearch);
		mImageViewClear = (ImageView) findViewById(R.id.imageViewClear);
		mImageViewClear.setOnClickListener(this);
		mLayoutEditText = (RelativeLayout) findViewById(R.id.layoutEditText);
		mFetchedContactList = new ArrayList<ContactBean>();
		mTempContactList = new ArrayList<ContactBean>();

		mSearchList = new ArrayList<ContactBean>();
		readAllContacts();
		// set adapter
		conAdapter = new ListAdapterContact(this, mFetchedContactList);
		mContactsList.setAdapter(conAdapter);
		// setting listener for listview
		mContactsList.setOnItemClickListener(this);
		mEdtTextSearch.addTextChangedListener(this);

	}

	@Override
	public void onResume() {

		super.onResume();

	}

	/**
	 * This method is used to read all contact from phone .
	 * 
	 */
	public void readAllContacts() {
		Cursor contactCursor = this.getContentResolver().query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,
				null, PERSON_NAME);

		while (contactCursor.moveToNext()) {

			String contactName = contactCursor.getString(contactCursor
					.getColumnIndex(PERSON_NAME));
			String contactNumber = contactCursor.getString(contactCursor
					.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
			
			int contactId = contactCursor
					.getInt(contactCursor
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
			try {

				if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
					if (contactCursor
							.getString(
									contactCursor
											.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
							.equalsIgnoreCase("1")) {
						// contact has phone number
						mSelectName = contactName;
					} else {
						continue;
					}
				} else {
					mSelectName = contactName;
				}
			} catch (IllegalStateException ile) {
				ile.printStackTrace();
				mSelectName = contactName;
			} catch (Exception e) {
				e.printStackTrace();
				mSelectName = contactName;
			}
			// Log.d("contact", contactName);
			try {
				picUri = Uri.parse(contactCursor.getString(contactCursor
						.getColumnIndex(PERSON_PHOTO)));
			} catch (NullPointerException nullexp) {
				picUri = Uri.parse("android.resource://"
						+ this.getPackageName() + "/drawable/ic_contact");
			}

			ContactBean cData = new ContactBean(mSelectName, picUri);
			cData.setmContactId(contactId);
			cData.setContactNumber(contactNumber);
			mFetchedContactList.add(cData);

		}
		filterRepeatedValues();
		mTempContactList = mFetchedContactList;
		contactCursor.close();

	}

	private void filterRepeatedValues() {
		try {
			for (int i = 0; i < mFetchedContactList.size(); i++) {
				for (int j = 1; j < mFetchedContactList.size(); j++) {
					if (mFetchedContactList.get(i).getmContactId() == mFetchedContactList
							.get(j).getmContactId()) {
						mFetchedContactList.remove(i);
					}
				}
			}
		} catch (Exception e) {

		}

	}

	@TargetApi(11)
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem item = menu.add("Search");
		item.setIcon(android.R.drawable.ic_menu_search);
		if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.HONEYCOMB) {
			item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		}
		return super.onCreateOptionsMenu(menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mLayoutEditText.getVisibility() == View.VISIBLE) {
			mLayoutEditText.setVisibility(View.GONE);
			mEdtTextSearch.setText("");
		} else {
			mLayoutEditText.setVisibility(View.VISIBLE);
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent();
		 intent.putExtra(KEY_CONTACT_NUMBER,
				getPhoneNumberByName(mFetchedContactList.get(arg2)
						.getmContactName()));
		 

		setResult(0, intent);
		this.finish();

	}

	/**
	 * 
	 * 
	 * @param name
	 * @return
	 */
	public String getPhoneNumberByName(String name) {
		String ret = null;
		String selection = PERSON_NAME + " like'%" + name + "%'";
		String[] projection = new String[] { ContactsContract.CommonDataKinds.Phone.NUMBER };
		Cursor c = getContentResolver().query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection,
				selection, null, null);
		if (c.moveToFirst()) {
			ret = c.getString(0);
		}
		c.close();
		if (ret == null)
			ret = "Unsaved";
		return ret;
	}

	/**
	 * This method is used to filter the contacts
	 * 
	 * @param newText
	 */
	private void searchContacts(String newText) {
		int textlength = newText.length();
		mSearchList.clear();
		for (int i = 0; i < mTempContactList.size(); i++) {
			if (mTempContactList.get(i).getmContactName() != null) {
				if (textlength <= mTempContactList.get(i).getmContactName()
						.length()) {
					if (newText.equalsIgnoreCase((String) mTempContactList
							.get(i).getmContactName()
							.subSequence(0, textlength))) {
						mSearchList.add(mTempContactList.get(i));
					}
				}
			}
			mFetchedContactList = mSearchList;
			conAdapter.addll(mFetchedContactList);
			conAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void afterTextChanged(Editable s) {

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		searchContacts(mEdtTextSearch.getText().toString());

	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.imageViewClear) {
			mLayoutEditText.setVisibility(View.GONE);
			mEdtTextSearch.setText("");
		}

	}

}
