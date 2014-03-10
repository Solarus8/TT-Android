package com.metapresence.android.terratraveler.event;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.maps.model.LatLng;
import com.metapresence.android.terratraveler.CustomMapFragment;
import com.metapresence.android.terratraveler.R;
import com.metapresence.android.terratraveler.api.APICall;
import com.metapresence.android.terratraveler.api.APICallback;
import com.metapresence.android.terratraveler.api.APIName;
import com.metapresence.android.terratraveler.api.ActivityType;

public class CreateEventActivity extends Activity implements OnClickListener {
	
	private Button mCreateEventButton;
	private EditText mTitleEditText;
	private ImageView mLocationImageView;
	
	private String[] mLocations;
	private ArrayList<String> mLocationTitles; 
	private ArrayList<LatLng> mLatLngs;
	
	
	private Intent pIntent;
	private double mLatitude;
	private double mLongitude;
	private int mRadius;
	
	private String mFrom;
	private String mTo;
	private int mPlaceId;
	private String mTitle;
	private String mDescription;
	private int mMinSize;
	private int mMaxSize;
	private String mRsvpTotal;
	private String mWaitListTotal;
	private int mActivityType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_event);
		
		mLocationTitles = new ArrayList<String>();
		
		pIntent = getIntent();
		
		mLatitude = pIntent.getDoubleExtra(CustomMapFragment.SELECTED_LATITUDE, 0);
		mLongitude = pIntent.getDoubleExtra(CustomMapFragment.SELECTED_LONGITUDE, 0);
		mRadius = pIntent.getIntExtra(CustomMapFragment.SELECTED_RADIUS, 0);
		
		mCreateEventButton = (Button) findViewById(R.id.create_event_button);
		mCreateEventButton.setOnClickListener(this);
		
		mLocationImageView = (ImageView) findViewById(R.id.create_event_location_iv);
		mLocationImageView.setOnClickListener(this);
		
		mTitleEditText = (EditText) findViewById(R.id.create_event_title_et);
		
		ActionBar bar = getActionBar();
		bar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_event, menu);
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				this.finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) { // ############## STUB STUB STUB STUB ############
		
		switch (v.getId()) {
			case R.id.create_event_button:
	
				mFrom = "2014-02-23 10:30:00.0";
				mTo = "";
				mPlaceId = 1;
				mTitle = mTitleEditText.getText().toString();
				mDescription = "Outside Lands: best music festival in S.F.";
				mMinSize = 2;
				mMaxSize = 50;
				mRsvpTotal = "";
				mWaitListTotal = "";
				mActivityType = ActivityType.EVENT.getValue();
				
				APICall apiCall = new APICall(APIName.CREATE_EVENT, new APICallback() {
					
					@Override
					public void onAsyncTaskComplete(JSONObject responseJSONObject) {
						
					}
				});
				
				apiCall.execute(mFrom, mTo, mPlaceId, mTitle, mDescription , mMinSize, mMaxSize, mRsvpTotal, mWaitListTotal, mActivityType);
				break;
				
			case R.id.create_event_location_iv:
				String googlePlacesUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + mLatitude + "," + mLongitude + "&radius=" + mRadius + "&sensor=false&key=AIzaSyDHbzx6uMk-QVOKMA2wEgnbFl2wzgeoavk";
				APICall getPlacesApiCall = new APICall(APIName.GENERIC_GET, googlePlacesUrl, new APICallback() {
					
					@Override
					public void onAsyncTaskComplete(JSONObject responseJSONObject) {
						
						Log.d("PLACES RESPONSE", responseJSONObject.toString());
						
						try {
							JSONArray results = responseJSONObject.getJSONArray("results");
							
							for (int i=0; i < results.length(); i++) {
								JSONObject resultObject = results.getJSONObject(i);
								String name = resultObject.getString("name");
								mLocationTitles.add(name);
								Log.d("Name", name);
								
								JSONObject location = resultObject.getJSONObject("geometry").getJSONObject("location");				
								mLatLngs.add(new LatLng(location.getDouble("lat"), location.getDouble("lng")));
							}
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					    mLocations = new String[mLocationTitles.size()];
					    mLocations = mLocationTitles.toArray(mLocations);
						
						onCreateLocationDialog().show();
	
					}
				});
				
				getPlacesApiCall.execute();
				
				break;
	
			default:
				break;
		}
	}

	public Dialog onCreateLocationDialog() {
		
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setTitle("Pick Location")
	           .setItems(mLocations, new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int which) {
	               // The 'which' argument contains the index position
	               // of the selected item
	           }
	    });
	    return builder.create();
	}
	
	public Dialog onCreateActivityTypeDialog() {
		
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setTitle("Pick Activity Type")
           .setItems(mLocations, new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int which) {
               // The 'which' argument contains the index position
               // of the selected item
           }
	    });
	    return builder.create();
	}
	
	public class CustomListAdapter extends BaseAdapter {
		
		private ArrayList<String> mPlaces;
		
		public CustomListAdapter(ArrayList<String> places) {
			mPlaces = places;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mPlaces.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {
			
			view = getLayoutInflater().inflate(R.layout.place_item, parent, false);
			return view;
		}
		
	}
	

}
