package com.metapresence.android.terratraveler.event;

import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.metapresence.android.terratraveler.CustomMapFragment;
import com.metapresence.android.terratraveler.R;
import com.metapresence.android.terratraveler.api.APICall;
import com.metapresence.android.terratraveler.api.APICallback;
import com.metapresence.android.terratraveler.api.APIName;
import com.metapresence.android.terratraveler.api.ActivityType;

public class CreateEventActivity extends Activity implements OnClickListener, APICallback {
	
	private Button mCreateEventButton;
	private Intent pIntent;
	private double mLatitude;
	private double mLongitude;
	
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
		
		pIntent = getIntent();
		
		mLatitude = pIntent.getIntExtra(CustomMapFragment.SELECTED_LATITUDE, 0);
		mLongitude = pIntent.getIntExtra(CustomMapFragment.SELECTED_LONGITUDE, 0);
		
		mCreateEventButton = (Button) findViewById(R.id.create_event_button);
		
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
	public boolean onOptionsItemSelected(MenuItem item) 

		{
			switch (item.getItemId()) {
				case android.R.id.home:
					this.finish();
					return true;
			}
			return super.onOptionsItemSelected(item);
		}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.create_event_button:
			APICall apiCall = new APICall(APIName.CREATE_EVENT, this);
			mFrom = "2014-02-23 10:30:00.0";
			mTo = "";
			mPlaceId = 1;
			mTitle = "Outside Lands";
			mDescription = "Outside Lands: best music festival in S.F.";
			mMinSize = 2;
			mMaxSize = 50;
			mRsvpTotal = "";
			mWaitListTotal = "";
			mActivityType = ActivityType.EVENT.getValue();
			
			
			apiCall.execute(mFrom, mTo, mPlaceId, mTitle, mDescription , mMinSize, mMaxSize, mRsvpTotal, mWaitListTotal, mActivityType);
			break;

		default:
			break;
		}
		
	}

	@Override
	public void onAsyncTaskComplete(JSONObject responseJSONObject) {
		// TODO Auto-generated method stub
		
	}

}
