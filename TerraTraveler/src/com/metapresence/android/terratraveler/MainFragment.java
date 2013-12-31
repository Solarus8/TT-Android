package com.metapresence.android.terratraveler;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.util.ResourceProxyImpl;
import org.osmdroid.views.MapView;

import android.R.integer;
import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainFragment extends Fragment {
	
	private MapView mMapView;
	private ResourceProxyImpl mResourceProxyImpl;
	private MyItemizedOverlay mItemizedOverlay = null;
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		setRetainInstance(true);
		
		mResourceProxyImpl = new ResourceProxyImpl(inflater.getContext().getApplicationContext());
		mMapView = new MapView(inflater.getContext(), 256, mResourceProxyImpl);
		
		mMapView.setUseSafeCanvas(true);
		mMapView.setBuiltInZoomControls(true);
		mMapView.setMultiTouchControls(true);
		
		Drawable marker = getResources().getDrawable(R.drawable.ic_action_person);
		int marker_width = marker.getIntrinsicWidth();
		int marker_height = marker.getIntrinsicHeight();
		
		marker.setBounds(0, marker_height, marker_width, 0);
		mItemizedOverlay = new MyItemizedOverlay(marker, mResourceProxyImpl);
		mMapView.getOverlays().add(mItemizedOverlay);
		
		GeoPoint point1 = new GeoPoint(0*1000000, 0*1000000);
		mItemizedOverlay.addItem(point1, "Point1", "Point1");
		
		
		return mMapView;
	}
	
	

}
