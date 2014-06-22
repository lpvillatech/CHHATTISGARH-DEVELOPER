package com.villatech.applist;

import java.util.ArrayList;
import java.util.List;

public class AppListLoader {
	private static final int[] ICONS = { R.drawable.ic_launcher,
			R.drawable.ic_launcher, R.drawable.ic_launcher };
	private static final String[] APP_NAME = { "Hello", "hi", "Bye" };
	private static final String[] APP_DESCRIPTION = { "This is good app",
			"this is nice app", "this is very good app" };
	private static final String[] URI = {"package name","package name","package name"};

	private List<AppInfoBean> list;

	public AppListLoader() {
		AppInfoBean bean;
		list=new ArrayList<AppInfoBean>();
		for (int i = 0; i < APP_NAME.length; i++) {
			bean = new AppInfoBean();
			bean.setAppName(APP_NAME[i]);
			bean.setDescriptions(APP_DESCRIPTION[i]);
			bean.setImageId(ICONS[i]);
			bean.setUri(URI[i]);
			list.add(bean);
			
		}
	}

	public List<AppInfoBean> getTopFreeApps() {
		return list;
	}
}
