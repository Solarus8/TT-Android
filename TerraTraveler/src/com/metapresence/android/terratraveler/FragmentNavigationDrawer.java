package com.metapresence.android.terratraveler;

import java.util.ArrayList;

import com.metapresence.android.terratraveler.adapter.NavDrawerListAdapter;
import com.metapresence.android.terratraveler.model.FragmentNavItem;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
//import android.support.v4.app.Fragment;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.app.FragmentManager;
//import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * See com.codepath
 */
public class FragmentNavigationDrawer extends DrawerLayout {
	
	private NavDrawerListAdapter drawerAdapter;
	private ArrayList<FragmentNavItem> drawerNavItems;
	private int drawerContainerRes;
	private ListView lvDrawer;
	private ActionBarDrawerToggle drawerToggle;
	
	/****************** FragmentNavigationDrawer Constructors ******************/

	public FragmentNavigationDrawer(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public FragmentNavigationDrawer(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FragmentNavigationDrawer(Context context) {
		super(context);
	}
	
	//---------------- end FragmentNavigationDrawer Constructors ---------------//
	
	public void addNavItem(FragmentNavItem navItem) {
		drawerNavItems.add(navItem);
	}
	
	private FragmentActivity getActivity() {
		return (FragmentActivity) getContext();
	}
	
	private void setTitle(CharSequence title) {
		getActionBar().setTitle(title);
	}
	
	private ActionBar getActionBar() {
		return getActivity().getActionBar();
	}
	
	private class FragmentDrawerItemListener implements ListView.OnItemClickListener {		
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			selectDrawerItem(position);
		}	
	}
	
	/** Swaps fragments in the main content view */
	public void selectDrawerItem(int position) {
		// Create a new fragment and specify the item to show based on position
		FragmentNavItem navItem = drawerNavItems.get(position);
		Fragment fragment = null;

		try {
			// TODO: RW: Why new instance if it's already created
			fragment = navItem.getFragmentClass().newInstance();
			Bundle args = navItem.getFragmentArgs();
			if (args != null) { 
			  fragment.setArguments(args);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Insert the fragment by replacing any existing fragment
		FragmentManager fragmentManager = getActivity().getFragmentManager();
		fragmentManager.beginTransaction().replace(drawerContainerRes, fragment).commit();

		// Highlight the selected item, update the title, and close the drawer
		lvDrawer.setItemChecked(position, true);
		setTitle(navItem.getTitle());
		closeDrawer(lvDrawer);
	}
	
	public void setupDrawerConfiguration(
		ListView drawerListView, int drawerItemRes, int drawerContainerRes) 
	{
		// Setup navigation items array
		drawerNavItems = new ArrayList<FragmentNavItem>(); // Empty at first
		// Set the adapter for the nav list view
		drawerAdapter = new NavDrawerListAdapter(getActivity(), drawerNavItems);
		this.drawerContainerRes = drawerContainerRes; 
		// Setup drawer list view and related adapter
		lvDrawer = drawerListView;
		lvDrawer.setAdapter(drawerAdapter);
		// Setup item listener
		lvDrawer.setOnItemClickListener(new FragmentDrawerItemListener());
		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		drawerToggle = setupDrawerToggle();
		setDrawerListener(drawerToggle);
		// set a custom shadow that overlays the main content when the drawer
		setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		// Setup action buttons
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
	}
	
	private ActionBarDrawerToggle setupDrawerToggle() {
		return new ActionBarDrawerToggle(getActivity(), /* host Activity */
		this, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		) {
			public void onDrawerClosed(View view) {
				//setTitle();
				getActivity().invalidateOptionsMenu(); // call onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				setTitle("Navigate");
				getActivity().invalidateOptionsMenu(); // call onPrepareOptionsMenu()
			}
		};
	}
}
