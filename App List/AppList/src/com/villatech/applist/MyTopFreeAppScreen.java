package com.villatech.applist;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;

public class MyTopFreeAppScreen extends Activity {
private ListView  mListViewTopFreeApps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListViewTopFreeApps = (ListView)findViewById(R.id.listview_apps);
        AppAdapter adapter = new AppAdapter(this, new AppListLoader().getTopFreeApps());
        mListViewTopFreeApps.setAdapter(adapter);
    }


     
}
