package com.metapresence.android.terratraveler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;
import com.metapresence.android.terratraveler.api.APICall;
import com.metapresence.android.terratraveler.api.APICallback;
import com.metapresence.android.terratraveler.api.APIName;

public class CustomMapFragment extends MapFragment implements APICallback{
	
	private APICallback callback;
	private APICall apiCall;
	private GoogleMap mGoogleMap;
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		mGoogleMap = getMap();
		mGoogleMap.setMyLocationEnabled(true);
		
		
//		Marker test = googleMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Test Marker").snippet("Kind of..."));
//		test.showInfoWindow();
		
		callback = this;
		
		mGoogleMap.setOnCameraChangeListener(new OnCameraChangeListener() {
			
			@Override
			public void onCameraChange(CameraPosition position) {
				// TODO Auto-generated method stub
				
				Projection projection = mGoogleMap.getProjection();
				VisibleRegion visibleRegion = projection.getVisibleRegion();
				LatLngBounds latLngBounds = visibleRegion.latLngBounds;
				
				
				double northEastLat = latLngBounds.northeast.latitude;
				double northEastLong = latLngBounds.northeast.longitude;
				
				double southWestLat = latLngBounds.southwest.latitude;
				double southWestLong = latLngBounds.southwest.longitude;
				
				float[] results = new float[1];
				
				Location.distanceBetween(northEastLat, northEastLong, southWestLat, southWestLong, results);
//				Log.d("TEST RADIUS", String.valueOf((int)results[0]));
				
				double latitude = position.target.latitude;
				double longitude = position.target.longitude;
				int radius = (int) (results[0]/2); // in meters
				
				apiCall = new APICall(APIName.GET_EVENTS_BY_LATITUDE_LONGITUDE_RADIUS, callback);
				apiCall.execute(latitude, longitude, radius);
				
			}

		});
		
		
		super.onResume();
	}


	@Override
	public void onAsyncTaskComplete(JSONObject jsonObject) {
//		 TODO Auto-generated method stub
//		JSONObject jsonObject = apiCall.getResponseJsonObject();		

		
		Log.d("ASYNCTASKCOMPLETE", "ASYNCTASKCOMPLETE METHOD");
		Log.d("JSON OBJECT IN MAPS FRAGMENT" , jsonObject.toString());
		mGoogleMap.clear();
		
		try {
			JSONArray events = jsonObject.getJSONArray("events");
			
			for (int i = 0; i<events.length(); i++) {
				JSONObject pinJSONObject = events.getJSONObject(i);
				double pinLatitude = pinJSONObject.getDouble("lat");
				double pinLongitude = pinJSONObject.getDouble("lon");
				String pinTitle = pinJSONObject.getString("title");
				
				mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(pinLatitude, pinLongitude)).title(pinTitle).snippet("Kind of..."));
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


}
