package com.nerdgeeks.bdclean.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
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

        View rootView = inflater.inflate(R.layout.fragment_event, container, false);

        loadFragment(new JoinedFragment());

        BottomNavigationView eventNavigation = (BottomNavigationView) rootView.findViewById(R.id.event_bottomNavigation);
        eventNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        return rootView;

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment eventFragment;
            switch (item.getItemId()){

                case R.id.joined:
                    eventFragment = new JoinedFragment();
                    loadFragment(eventFragment);
                    return true;

                case R.id.upcoming:
                    eventFragment = new UpcomingFragment();
                    loadFragment(eventFragment);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment eventFragment) {
        FragmentTransaction eventTransaction = getChildFragmentManager().beginTransaction();
        eventTransaction.replace(R.id.event_frame_container, eventFragment);
        eventTransaction.addToBackStack(null);
        eventTransaction.commit();
    }

}


