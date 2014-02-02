package com.metapresence.android.terratraveler.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class APICall extends AsyncTask<Object, String, String>{

	private static String BASE_URL = "http://ec2-54-193-80-119.us-west-1.compute.amazonaws.com:9000/api/v1";
	private String mURL;
	private APIName mAPIName;
	private String TAG = "APICALL";
	private APICallback mCallback;

	public String getURL() {
		return mURL;
	}


	public void setURL(String URL) {
		this.mURL = URL;
	}


	public APICall(APIName apiName, APICallback callback) {
		mAPIName = apiName;
		mCallback = callback;
	}


	@Override
	protected String doInBackground(Object... params) {
		// TODO Auto-generated method stub
		
		
		switch (mAPIName) {
		
		case ASSOCIATE_USER_AND_EVENT:
			break;
		
		case ASSOCIATE_USER_TO_CONTACT:
			break;

		case CREATE_EVENT:
			return createEvent((String) params[0], (String) params[1], (Integer) params[2], (String) params[3], (String) params[4], (Integer) params[5], (Integer) params[6], (String) params[7], (String) params[8], (Integer) params[9]);
			
		case CREATE_PLACE:
			return createPlace((String) params[0], (String) params[1], (String) params[2], (String) params[3], (Double) params[4], (Double) params[5] );
			
		case CREATE_PLACE_BY_3RD_PARTY_REF:
			break;
			
		case CREATE_USER:
			return createUser((String) params[0], (String) params[1], (String) params[2], (String) params[3], (Double) params[4], (Double) params[5]);
			
		case CREATE_USER_PROFILE:
			break;
			
		case GET_ALL_ACTIVITY_CATEGORIES_AND_TYPES:
			break;
			
		case GET_ALL_USERS:
			return getAllUsers();
			
		case GET_EVENT_BY_ID:
			break;

		case GET_EVENTS_BY_LATITUDE_LONGITUDE_RADIUS:
			return getEventByLatLongRad((Double) params[0], (Double) params[1], (Integer) params[2]);			
				
		case GET_EVENTS_BY_LOCATION_RADIUS_USING_LOCATION_ID:
			break;
			
		case GET_EVENTS_BY_USER_ID:
			break;
			
		case GET_PLACE_3RD_PARTY_REF_BY_ID:
			break;
			
		case GET_PLACE_3RD_PARTY_REF_BY_TT_PLACE_ID:
			break;
			
		case GET_PLACE_BY_ID:
			break;
			
		case GET_PLACES_BY_LATITUDE_LONGITUDE_RADIUS:
			break;
			
		case GET_USER_BY_ID:
			return getUserById((Integer) params[0]);
			
		case GET_USER_CONTACTS_BY_USER_ID:
			break;
			
		case GET_USER_PROFILE:
			break;
			
		case GET_USERS_BY_EVENT_ID:
			break;
			
		default:
			break;
		}

		return null;
	}
	
	private String createUser(String userName, String email, String password, String role, Double latitude, Double longitude) {
		mURL = BASE_URL + "/users";
		
		JSONObject jsonObject = new JSONObject();
		
		try {
			jsonObject.put("userName", userName);
			jsonObject.put("email", email);
			jsonObject.put("password", password);
			jsonObject.put("role", role);
			jsonObject.put("latitude", latitude);
			jsonObject.put("longitude", longitude);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return postData(jsonObject);
	}


	private String getUserById(int userId) {
		// TODO Auto-generated method stub
		mURL = BASE_URL + "/users/" + userId;
		String response = getData();
		return response;
	}


	@Override
	protected void onPostExecute(String result) {

		JSONObject jsonObject = null;
		
		
		try {
			jsonObject = new JSONObject(result);
			mCallback.onAsyncTaskComplete(jsonObject);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		

		
	}
	
	
	
	
	public String createEvent(String from, String to, int placeId, String title, String description, int minSize, int maxSize, String rsvpTot, String waitListTot, int activityType) {
		mURL = BASE_URL + "/events";
		
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("from", from);
			jsonObject.put("to", to);
			jsonObject.put("placeId", placeId);
			jsonObject.put("title", title);
			jsonObject.put("desc", description);
			jsonObject.put("minSize", minSize);
			jsonObject.put("maxSize", maxSize);
			jsonObject.put("rsvpTot", rsvpTot);
			jsonObject.put("waitListTot", waitListTot);
			jsonObject.put("activityType", activityType);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return postData(jsonObject);		
	}

	public String getAllUsers() {
		mURL = BASE_URL + "/users";
		String response = getData();
		return response;
		
	}
	
	public String createPlace(String name, String description, String category, String url, double latitude, double longitude) {
		mURL = BASE_URL + "/locations/place";
		JSONObject jsonObject = new JSONObject();
		
		try {
			jsonObject.put("name", name);
			jsonObject.put("desc", description);
			jsonObject.put("cat", category);
			jsonObject.put("url", url);
			jsonObject.put("latitude", latitude);
			jsonObject.put("longitude", longitude);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return postData(jsonObject);
	}
	
	public String getEventByLatLongRad(double latitude, double longitude, int radius) {
		Log.d("LATITUDE", String.valueOf(latitude));
		Log.d("LONGITUDE", String.valueOf(longitude));
		Log.d("RADIUS", String.valueOf(radius));
		mURL = BASE_URL + "/events/location/" + latitude +"/" + longitude + "/" + radius;
		String response = getData();
		return response;
	}
	
	private String convertStreamToString(InputStream is)
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try
		{
			while ((line = reader.readLine()) != null)
			{
				sb.append(line + "\n");
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				is.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	public String postData(JSONObject jsonObject) {
		
		Log.d(TAG, "POST REQUEST HERE"); //test
		HttpClient client = new DefaultHttpClient();
		HttpResponse responsePost;
	
		try {
			HttpPost post = new HttpPost(mURL);
			post.setHeader("Content-type", "application/json");
	
			StringEntity se = new StringEntity(jsonObject.toString());	
			post.setEntity(se);
			responsePost = client.execute(post);
			HttpEntity entity = responsePost.getEntity();
			InputStream inputStream = entity.getContent();
			String response = convertStreamToString(inputStream);
			return response;
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;

	}
	
	public String getData() {
		
		Log.d(TAG, "GET REQUEST HERE"); //test
		HttpClient client = new DefaultHttpClient();
		
		try {
		
		HttpGet get = new HttpGet(mURL);
		
		HttpResponse responseGet = client.execute(get);
		HttpEntity entity = responseGet.getEntity();
		
		if (entity != null) {
			InputStream stream = entity.getContent();
			String response = convertStreamToString(stream);
			return response;
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}


}



