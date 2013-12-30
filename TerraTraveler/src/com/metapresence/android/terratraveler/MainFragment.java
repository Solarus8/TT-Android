package com.metapresence.android.terratraveler;

import org.osmdroid.util.ResourceProxyImpl;
import org.osmdroid.views.MapView;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainFragment extends Fragment {
	
	private MapView mMapView;
	private ResourceProxyImpl mResourceProxyImpl;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		setRetainInstance(true);
		
		mResourceProxyImpl = new ResourceProxyImpl(inflater.getContext().getApplicationContext());
		mMapView = new MapView(inflater.getContext(), 256, mResourceProxyImpl);
		
		mMapView.setUseSafeCanvas(true);
		mMapView.setBuiltInZoomControls(true);
		mMapView.setMultiTouchControls(true);
		
		return mMapView;
	}
	
	

}
