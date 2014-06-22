package com.villatech.applist;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AppAdapter extends BaseAdapter {

	private List<AppInfoBean> list;
	private Activity context;

	public AppAdapter(Activity con, List<AppInfoBean> packageList1) {
		super();
		this.context = con;
		this.list = packageList1;

	}

	private class ViewHolder {
		TextView appName, description;
		ImageView iconImage;

	}

	public int getCount() {
		return list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return 0;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		LayoutInflater inflater = context.getLayoutInflater();

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.layout_list_item, null);
			holder = new ViewHolder();

			holder.appName = (TextView) convertView
					.findViewById(R.id.textview_app_name);

			holder.iconImage = (ImageView) convertView
					.findViewById(R.id.imageview_apps_icon);
			holder.description = (TextView) convertView
					.findViewById(R.id.textview_description);

			holder.iconImage.setImageResource(list.get(position).getImageId());
			holder.appName.setText(list.get(position).getAppName());
			holder.description.setText(list.get(position).getDescriptions());
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.description.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startPlayStore(list.get(position));

			}
		});
		holder.appName.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startPlayStore(list.get(position));

			}
		});
		holder.iconImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startPlayStore(list.get(position));

			}
		});

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startPlayStore(list.get(position));

			}

		});
		return convertView;
	}

	public void addall(List<AppInfoBean> applist) {
		list = applist;

	}

	private void startPlayStore(AppInfoBean appInfoBean) {
		final String appPackageName = appInfoBean.getUri(); // getPackageName()
															// from Context or
															// Activity object
		try {
			context.startActivity(new Intent(Intent.ACTION_VIEW, Uri
					.parse("market://details?id=" + appPackageName)));
		} catch (android.content.ActivityNotFoundException anfe) {
			context.startActivity(new Intent(Intent.ACTION_VIEW, Uri
					.parse("http://play.google.com/store/apps/details?id="
							+ appPackageName)));
		}

	}
}