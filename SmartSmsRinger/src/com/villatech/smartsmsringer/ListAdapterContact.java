package com.villatech.smartsmsringer;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListAdapterContact extends BaseAdapter {
	Context mContext;
	List<ContactBean> mInfoList;

	public ListAdapterContact(Context context, List<ContactBean> datalist) {
		this.mContext = context;
		this.mInfoList = datalist;
	}

	public void addll(List<ContactBean> datas) {
		mInfoList = datas;
	}

	private class ViewHolder {
		TextView ContactDisplayName,ContactNumber;
		ImageView contactImage;
	}

	@Override
	public int getCount() {
		return mInfoList.size();
	}

	@Override
	public Object getItem(int position) {
		return mInfoList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return mInfoList.indexOf(getItem(position));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		LayoutInflater mInflater = (LayoutInflater) mContext
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_item, null);
		}
		holder = new ViewHolder();

		holder.ContactDisplayName = (TextView) convertView
				.findViewById(R.id.name_textview);
		holder.contactImage = (ImageView) convertView
				.findViewById(R.id.contact_imgview);
		holder.ContactNumber = (TextView)convertView.findViewById(R.id.textview_number);

		ContactBean contactItem = (ContactBean) getItem(position);

		holder.ContactDisplayName.setText(contactItem.getmContactName());
		holder.contactImage.setImageURI(contactItem.getmContactPhoto());
		
		holder.ContactNumber.setText(contactItem.getContactNumber());
		
		if (holder.contactImage.getDrawable() == null) {
			holder.contactImage.setImageResource(R.drawable.ic_launcher);
		}

		return convertView;

	}

}
