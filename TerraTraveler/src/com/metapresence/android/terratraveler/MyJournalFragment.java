package com.metapresence.android.terratraveler;

import android.app.Fragment;
//import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyJournalFragment extends Fragment {
	
	public MyJournalFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_my_journal, container, false);
         
        return rootView;
    }
}
