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
	APIName mAPIName;
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

		case CREATE_EVENT:
			return createEvent();
			
		case CREATE_PLACE:
			return createPlace();
			
		case CREATE_USER:
			break;
			
		case CREATE_USER_PROFILE:
			break;
			
		case GET_ALL_USERS:
			return getAllUsers();

		case GET_EVENTS_BY_LATITUDE_LONGITUDE_RADIUS:
			return getEventByLatLongRad((Double) params[0], (Double) params[1], (Integer) params[2]);			
			
		case GET_EVENTS_BY_LOCATION_RADIUS_USING_LOCATION_ID:
			break;
			
		case GET_EVENTS_BY_USER_ID:
			break;
			
		case GET_EVENT_BY_ID:
			break;
			
		case GET_USER_BY_ID:
			break;
			
		case GET_USER_PROFILE:
			break;
			
		default:
			break;
		}

		return null;
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
	
	public String createEvent() {
		mURL = BASE_URL + "events";
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("from", "2014-02-23 10:30:00.0");
			jsonObject.put("to", "");
			jsonObject.put("placeId", 1);
			jsonObject.put("desc", "Outside Lands: best music festival in S.F.");
			jsonObject.put("minSize", 2);
			jsonObject.put("maxSize", 50);
			jsonObject.put("rsvpTot", "");
			jsonObject.put("waitListTot", "");
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

	public String createPlace() {
		mURL = BASE_URL + "/locations/place";
		JSONObject jsonObject = new JSONObject();
		
		try {
			jsonObject.put("name", "Stern Grove");
			jsonObject.put("desc", "Free Summer Concerts!");
			jsonObject.put("cat", "PARK");
			jsonObject.put("url", "http://www.sterngrove.org/");
			jsonObject.put("latitude", 37.735681);
			jsonObject.put("longitude", -122.476959);
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



