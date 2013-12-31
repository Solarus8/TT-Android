package com.metapresence.android.terratraveler;

import org.osmdroid.util.GeoPoint;

public class LatLongPoint extends GeoPoint {

	public LatLongPoint(double latitude, double longitude) {
		super((int) (latitude * 1E6), (int) (longitude * 1E6));
	}

}
