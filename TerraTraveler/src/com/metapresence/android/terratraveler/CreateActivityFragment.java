package com.metapresence.android.terratraveler;

//import android.app.Fragment;
//import android.support.v4.app.Fragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CreateActivityFragment extends Fragment {
	
	public CreateActivityFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_create_activity, container, false);
         
        return rootView;
    }
}
