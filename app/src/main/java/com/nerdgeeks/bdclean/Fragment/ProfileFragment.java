package com.nerdgeeks.bdclean.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nerdgeeks.bdclean.R;
import com.nerdgeeks.bdclean.helper.SQLiteHandler;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private SQLiteHandler db;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        db = new SQLiteHandler(getActivity().getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();
        String username = user.get("name");

        //casting layout components
        TextView profileView = (TextView) rootView.findViewById(R.id.nameText);
        profileView.setText(username);

        return rootView;
    }

}
