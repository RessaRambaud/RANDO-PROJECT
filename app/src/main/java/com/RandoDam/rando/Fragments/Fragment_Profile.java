package com.RandoDam.rando.Fragments;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.RandoDam.rando.ProfileActivity;
import com.RandoDam.rando.R;

public class Fragment_Profile extends Fragment {

    Button myProfile;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /** Inflate the layout for this fragment **/
        View view = inflater.inflate(R.layout.fragment__profile, container, false);


     myProfile=view.findViewById(R.id.btn_myProfile);
     myProfile.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent in =new Intent(getActivity(), ProfileActivity.class);
             startActivity(in);
         }
     });

        return view;
    }

}

