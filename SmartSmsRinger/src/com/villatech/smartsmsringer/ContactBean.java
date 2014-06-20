package com.villatech.smartsmsringer;

import android.net.Uri;

public class ContactBean {
	private String mContactName;
	private Uri mContactPhotoUri;
	private int mContactId=-1;
	private String mContactDOB;
	private String contactNumber;

	public ContactBean(String name, Uri photo) {
		this.setmContactName(name);
		this.setmContactPhoto(photo);
	}

	public String getmContactName() {
		return mContactName;
	}

	public void setmContactName(String mContactName) {
		this.mContactName = mContactName;
	}

	public Uri getmContactPhoto() {
		return mContactPhotoUri;
	}

	public void setmContactPhoto(Uri mContactPhoto) {
		this.mContactPhotoUri = mContactPhoto;
	}

	public int getmContactId() {
		return mContactId;
	}

	public void setmContactId(int mContactId) {
		this.mContactId = mContactId;
	}

	public String getmContactDOB() {
		return mContactDOB;
	}

	public void setmContactDOB(String mContactDOB) {
		this.mContactDOB = mContactDOB;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

}
