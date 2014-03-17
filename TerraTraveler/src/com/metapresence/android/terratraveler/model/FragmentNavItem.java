package com.metapresence.android.terratraveler.model;

import com.google.android.gms.maps.MapFragment;

import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.app.Fragment;

public class FragmentNavItem {
	private Class<? extends Fragment> fragmentClass;
	private String title;
	private Bundle fragmentArgs;
	private int icon;
	private String count = "0";
	// boolean to set visibility of the counter
	private boolean isCounterVisible = false;
	
	/****************** FragmentNavItem Constructors ******************/
	
	public FragmentNavItem(){}
	
	public FragmentNavItem(String title, Class<? extends Fragment> fragmentClass) {
		this.title = title;
		this.fragmentClass = fragmentClass;
	}
	
	public FragmentNavItem(String title, Class<? extends Fragment> fragmentClass, int icon) {
		this.title = title;
		this.fragmentClass = fragmentClass;
		this.icon = icon;
	}
	
	public FragmentNavItem(String title, Class<? extends Fragment> fragmentClass, Bundle args) {
		this.fragmentClass = fragmentClass;
		this.fragmentArgs = args;
		this.title = title;
	}
	
	public FragmentNavItem(String title, Class<? extends Fragment> fragmentClass, 
			Bundle args, int icon) {
		this.fragmentClass = fragmentClass;
		this.fragmentArgs = args;
		this.title = title;
		this.icon = icon;
	}
	
	public FragmentNavItem(String title, Class<? extends Fragment> fragmentClass, 
			boolean isCounterVisible, String count) {
		this.title = title;
		this.fragmentClass = fragmentClass;
		this.isCounterVisible = isCounterVisible;
		this.count = count;
	}
	
	public FragmentNavItem(String title, Class<? extends Fragment> fragmentClass, 
			int icon, boolean isCounterVisible, String count) {
		this.title = title;
		this.fragmentClass = fragmentClass;
		this.icon = icon;
		this.isCounterVisible = isCounterVisible;
		this.count = count;
	}
	
	public FragmentNavItem(String title, Class<? extends Fragment> fragmentClass, 
			Bundle args, boolean isCounterVisible, String count) {
		this.fragmentClass = fragmentClass;
		this.fragmentArgs = args;
		this.title = title;
		this.isCounterVisible = isCounterVisible;
		this.count = count;
	}
	
	public FragmentNavItem(String title, Class<? extends Fragment> fragmentClass, 
			Bundle args, int icon, boolean isCounterVisible, String count) {
		this.fragmentClass = fragmentClass;
		this.fragmentArgs = args;
		this.title = title;
		this.icon = icon;
		this.isCounterVisible = isCounterVisible;
		this.count = count;
	}
	
	public FragmentNavItem(String title, Class<? extends MapFragment> fragmentClass, int icon, int flag) {
		this.fragmentClass = fragmentClass;
	}
	
	//---------------- end - FragmentNavItem Constructors ---------------//

	public Class<? extends Fragment> getFragmentClass() {
		return fragmentClass;
	}
	
	public String getTitle() {
		return title;
	}
	
	public Bundle getFragmentArgs() {
		return fragmentArgs;
	}
	
	public void setIcon(int icon){
		this.icon = icon;
	}
	
	public void setCount(String count){
		this.count = count;
	}
	
	public void setCounterVisibility(boolean isCounterVisible){
		this.isCounterVisible = isCounterVisible;
	}
	
	public int getIcon(){
		return this.icon;
	}
	
	public String getCount(){
		return this.count;
	}
	
	public boolean getCounterVisibility(){
		return this.isCounterVisible;
	}
} // end - FragmentNavItem