package com.metapresence.android.terratraveler;

//import android.support.v4.app.Fragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyActivitiesFragment extends Fragment {
	
	public MyActivitiesFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_my_activities, container, false);
         
        return rootView;
    }
}
