package com.metapresence.android.terratraveler;

import com.metapresence.android.terratraveler.model.FragmentNavItem;

import android.os.Bundle;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class MainActivity extends FragmentActivity {
	
	@SuppressWarnings("unused")
	private static final String TAG = "TTMainActivity";
	private FragmentNavigationDrawer mFragNavDrawer;
	private CharSequence mTitle;
	private CharSequence mDrawerTitle;
    private ActionBarDrawerToggle mDrawerToggle;
	private String[] mNavMenuTitles;
	private TypedArray mNavMenuIcons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Left Nav Drawer
        mTitle = mDrawerTitle = getTitle();
        mFragNavDrawer = (FragmentNavigationDrawer) findViewById(R.id.drawer_layout);
        mFragNavDrawer.setupDrawerConfiguration((ListView) findViewById(R.id.leftLvDrawer), 
                     R.layout.drawer_list_item, R.id.flContainer);
        
        // nav drawer icons from resources
        mNavMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
        mNavMenuTitles = getResources().getStringArray(R.array.feature_views_array);
        
        // Add feature Fragments and nav
        mFragNavDrawer.addNavItem(new FragmentNavItem(mNavMenuTitles[0], LocationMapFragment.class, mNavMenuIcons.getResourceId(0, -1)));
        mFragNavDrawer.addNavItem(new FragmentNavItem(mNavMenuTitles[1], CreateActivityFragment.class, mNavMenuIcons.getResourceId(1, -1)));
        mFragNavDrawer.addNavItem(new FragmentNavItem(mNavMenuTitles[2], MyActivitiesFragment.class, mNavMenuIcons.getResourceId(2, -1), true, "6"));
        mFragNavDrawer.addNavItem(new FragmentNavItem(mNavMenuTitles[3], MyJournalFragment.class, mNavMenuIcons.getResourceId(3, -1)));
        mFragNavDrawer.addNavItem(new FragmentNavItem(mNavMenuTitles[4], ContactsFragment.class, mNavMenuIcons.getResourceId(4, -1), true, "50+"));
        mFragNavDrawer.addNavItem(new FragmentNavItem(mNavMenuTitles[5], SettingsFragment.class, mNavMenuIcons.getResourceId(5, -1)));
  
        // enable ActionBar app icon to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        
        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
            this,                  //host Activity
            mFragNavDrawer,         // DrawerLayout object
            R.drawable.ic_drawer,  // nav drawer image to replace 'Up' caret 
            R.string.drawer_open,  // "open drawer" description for accessibility 
            R.string.drawer_close  // "close drawer" description for accessibility 
        ){
            public void onDrawerClosed(View view) {
                //getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mFragNavDrawer.setDrawerListener(mDrawerToggle);
        
        // Recycle the typed array
     	mNavMenuIcons.recycle();
        
        // Select default Activity
        if (savedInstanceState == null) {
        	mFragNavDrawer.selectDrawerItem(0);   
        }
    } // end - onCreate
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
        
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return false;
    }
    
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}





