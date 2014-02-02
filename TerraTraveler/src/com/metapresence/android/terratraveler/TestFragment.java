package com.metapresence.android.terratraveler;

import java.util.ArrayList;

import org.json.JSONObject;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.metapresence.android.terratraveler.api.APICall;
import com.metapresence.android.terratraveler.api.APICallback;
import com.metapresence.android.terratraveler.api.APIName;

public class TestFragment extends Fragment implements OnClickListener, APICallback {

	@SuppressWarnings("unused")
	private String TAG = "TEST_FRAGMENT";
	
	ArrayList<Button> buttons;
	
	Button createEventButton;
	Button getAllUsersButton;
	Button createPlaceButton;
	Button getEventsByLatLongRadButton;
	



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_test, container, false);
		
		buttons = new ArrayList<Button>();	

		
		//connect buttons
		createEventButton = (Button) view.findViewById(R.id.test_create_event);
		getAllUsersButton = (Button) view.findViewById(R.id.test_get_all_users);
		createPlaceButton = (Button) view.findViewById(R.id.test_create_place);
		getEventsByLatLongRadButton = (Button) view.findViewById(R.id.test_get_events_by_latitude_longitude_radius);
		
		buttons.add(createEventButton);
		buttons.add(getAllUsersButton);
		buttons.add(createPlaceButton);
		buttons.add(getEventsByLatLongRadButton);
				
		//add listeners and modify buttons
		for (Button button : buttons) {
			button.setOnClickListener(this);
		}
		
		return view;
	}

	@Override
	public void onClick(View v) {
		
		APICall apiCall;
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case (R.id.test_get_all_users):
			apiCall = new APICall(APIName.GET_ALL_USERS, this);
			apiCall.execute();
			break;

		case (R.id.test_get_user_by_id):
			break;

		case (R.id.test_create_user):
			break;

		case (R.id.test_create_user_profile):
			break;

		case (R.id.test_get_user_profile):
			break;

		case (R.id.test_create_event):
			apiCall = new APICall(APIName.CREATE_EVENT, this);
			apiCall.execute();
		break;

		case (R.id.test_get_events_by_user_id):
			break;

		case (R.id.test_get_event_by_id):
			break;

		case (R.id.test_get_events_by_location_radius_using_location_id):
			break;

		case (R.id.test_get_events_by_latitude_longitude_radius):
			apiCall = new APICall(APIName.GET_EVENTS_BY_LATITUDE_LONGITUDE_RADIUS, this);
			apiCall.execute(37.774929, -122.419416, 7100);
			break;

		case (R.id.test_create_place):
			apiCall = new APICall(APIName.CREATE_PLACE, this);
//			apiCall.execute();
			break;

		}

	}

	@Override
	public void onAsyncTaskComplete(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		Toast.makeText(getActivity(), jsonObject.toString(), Toast.LENGTH_LONG).show();;
	}

}
