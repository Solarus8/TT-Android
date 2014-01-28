package com.metapresence.android.terratraveler.api;

import org.json.JSONObject;

public interface APICallback {
	public void onAsyncTaskComplete(JSONObject responseJSONObject);
}
