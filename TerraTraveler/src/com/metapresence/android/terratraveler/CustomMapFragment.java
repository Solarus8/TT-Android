package com.metapresence.android.terratraveler;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;
import com.metapresence.android.terratraveler.api.APICall;
import com.metapresence.android.terratraveler.api.APICallback;
import com.metapresence.android.terratraveler.api.APIName;
import com.metapresence.android.terratraveler.event.CreateEventActivity;

public class CustomMapFragment extends MapFragment implements APICallback {
	
	private APICallback callback;
	private APICall apiCall;
	private GoogleMap mGoogleMap;
	private ArrayList<Marker> mPlaceMarkers;
	private double prev_lat;
	private double prev_lon;
	private int prev_rad;
	private boolean mMarkerClickToggle;
	private Marker mSelectMarker;
	
	public final static String SELECTED_LATITUDE = "selected_latitude";
	public final static String SELECTED_LONGITUDE = "selected_longitude";
	public final static String SELECTED_RADIUS = "selected_radius";
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		
		setRetainInstance(true);
		mGoogleMap = getMap();
		mGoogleMap.setMyLocationEnabled(true);
		
		moveMapToMyLocation();
		
		mPlaceMarkers = new ArrayList<Marker>();
		
		callback = this;
		
		prev_lat = 0;
		prev_lon = 0;
		prev_rad = 0;
		mMarkerClickToggle = false;
		
		mGoogleMap.setOnMapLongClickListener(new OnMapLongClickListener() {
			
			@Override
			public void onMapLongClick(LatLng point) {
				
				if (mSelectMarker != null)
					mSelectMarker.remove();
				
				mSelectMarker = mGoogleMap.addMarker(new MarkerOptions().position(point).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).title("Add Event Here"));
				mSelectMarker.showInfoWindow();
			}
		});
		
		mGoogleMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
			
			@Override
			public void onInfoWindowClick(Marker marker) {
				// TODO Auto-generated method stub
				if (marker.equals(mSelectMarker)) {
					Intent intent = new Intent(getActivity(), CreateEventActivity.class);
					intent.putExtra(SELECTED_LATITUDE, marker.getPosition().latitude);
					intent.putExtra(SELECTED_LONGITUDE, marker.getPosition().longitude);
					intent.putExtra(SELECTED_RADIUS, prev_rad);
					startActivity(intent);
				}
			}
		});
		
		mGoogleMap.setOnCameraChangeListener(new OnCameraChangeListener() {
			
			@Override
			public void onCameraChange(CameraPosition position) {
				
				Projection projection = mGoogleMap.getProjection();
				VisibleRegion visibleRegion = projection.getVisibleRegion();
				LatLngBounds latLngBounds = visibleRegion.latLngBounds;
				
				double northEastLat = latLngBounds.northeast.latitude;
				double northEastLong = latLngBounds.northeast.longitude;
				
				double southWestLat = latLngBounds.southwest.latitude;
				double southWestLong = latLngBounds.southwest.longitude;
				
				float[] radius_result = new float[1];
				
				//calculate radius of API call with respect to viewport size
				Location.distanceBetween(northEastLat, northEastLong, southWestLat, southWestLong, radius_result); 
				
				double latitude = position.target.latitude;
				double longitude = position.target.longitude;
				int radius = (int) (radius_result[0]/2); // in meters
				
				float[] movement_amount = new float[1];
				//calculate distance between camera movements to ensure the camera has moved passed certain threshold before retrigger of APICall
				Location.distanceBetween(prev_lat, prev_lon, latitude, longitude, movement_amount);
				
				int radius_delta = Math.abs(prev_rad - radius);
				
				Log.d("RADIUS MOVEMENT THRESHOLD", String.valueOf(radius));
				Log.d("MOVEMENT AMOUNT", String.valueOf(movement_amount[0]));
				Log.d("RADIUS DELTA", String.valueOf(radius_delta));
				
				if (movement_amount[0] > radius || Math.abs(prev_rad - radius) > 5) {
					prev_lat = latitude;
					prev_lon = longitude;
					prev_rad = radius;
									
					if (!mMarkerClickToggle) {
						apiCall = new APICall(APIName.GET_EVENTS_BY_LATITUDE_LONGITUDE_RADIUS, callback);
						apiCall.execute(latitude, longitude, radius);
					}
					
					mMarkerClickToggle = false;
				}
				
			}

		});
		
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onAsyncTaskComplete(JSONObject jsonObject) {	
		
		Log.d("ASYNCTASKCOMPLETE", "ASYNCTASKCOMPLETE METHOD");
		Log.d("JSON OBJECT IN MAPS FRAGMENT" , jsonObject.toString());
//		mGoogleMap.clear();
		
		for (Marker m : mPlaceMarkers) {
			m.remove();
		}
		
		try {
			JSONArray events = jsonObject.getJSONArray("events");
			
			for (int i = 0; i<events.length(); i++) {
				JSONObject pinJSONObject = events.getJSONObject(i);
				double pinLatitude = pinJSONObject.getDouble("lat");
				double pinLongitude = pinJSONObject.getDouble("lon");
				String pinTitle = pinJSONObject.getString("title");
				
				Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(pinLatitude, pinLongitude)).title(pinTitle));
				mPlaceMarkers.add(marker);
				
				mGoogleMap.setOnMarkerClickListener(new OnMarkerClickListener() {				
					@Override
					public boolean onMarkerClick(Marker marker) {
						mMarkerClickToggle = true;
						return false;
					}
				});
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void moveMapToMyLocation() {

		LocationManager locMan = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

		Criteria crit = new Criteria();
		Location loc = locMan.getLastKnownLocation(locMan.getBestProvider(crit, false));

		if(loc != null) {
			CameraPosition camPos = new CameraPosition.Builder().target(new LatLng(loc.getLatitude(), loc.getLongitude())).zoom(12.8f).build();
			CameraUpdate camUpdate = CameraUpdateFactory.newCameraPosition(camPos);
			getMap().moveCamera(camUpdate);		  
		}
		else {
			Log.d("CustomMapFragment.moveMapToMyLocation", "Location is NULL");
		}
	}
}
