package com.villatech.nationalfruitquiz;

 
public class CountryFlagModel {
	private String mCountryCode;
	private String mCountryName;
	private String mGeoLocation;
	private String mFlagFile;

	public CountryFlagModel(String cc, String cn, String geo, String flagfile) {
		this.setmCountryCode(cc);
		this.setmCountryName(cn);
		this.setmGeoLocation(geo);
		this.setmFlagFile(flagfile);
	}

	public CountryFlagModel() {
	}

	public String getmGeoLocation() {
		return mGeoLocation;
	}

	public void setmGeoLocation(String mGeoLocation) {
		this.mGeoLocation = mGeoLocation;
	}

	public String getmFlagFile() {
		return mFlagFile;
	}

	public void setmFlagFile(String mFlagFile) {
		this.mFlagFile = mFlagFile;
	}

	public String getmCountryName() {
		return mCountryName;
	}

	public void setmCountryName(String mCountryName) {
		this.mCountryName = mCountryName;
	}

	public String getmCountryCode() {
		return mCountryCode;
	}

	public void setmCountryCode(String mCountryCode) {
		this.mCountryCode = mCountryCode;
	}

}
