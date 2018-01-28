package com.nerdgeeks.bdclean.Fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nerdgeeks.bdclean.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment {


    public EventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_event, container, false);


//        View inflatedView = inflater.inflate(R.layout.fragment_event, container, false);
//
//        TabLayout tabEvent = (TabLayout) inflatedView.findViewById(R.id.event_tab);
//        tabEvent.addTab(tabEvent.newTab().setText("I Joined"));
//        tabEvent.addTab(tabEvent.newTab().setText("Upcoming"));

//        tabEvent.addOnTabSelectedListener(new tabEvent.OnTabSelectedListener(){
//            @Override
//            public void onTabSelected(tabEvent.Tab tab){
//                if (tabEvent.getSelectedTabPosition() == 0){}
//                else if (tabEvent.getSelectedTabPosition() == 1){}
//            }
//        });
//
//        return inflatedView;
   }

}


