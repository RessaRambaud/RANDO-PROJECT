package com.RandoDam.rando.Fragments;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.RandoDam.rando.R;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.io.InputStream;


public class Fragment_Apropos extends Fragment {

    private FirebaseAuth logout_FireBaseAuth;

    EditText multiLine_apropos;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment__apropos, container, false);


        multiLine_apropos=view.findViewById(R.id.tv_propostext);

        /** to load large text of terms and conditions in the a propos page.**/

        try {
            Resources res=getResources();
            InputStream inputStream=res.openRawResource(R.raw.term);
            byte[] b=new byte[inputStream.available()];
            inputStream.read(b);
            multiLine_apropos.setText(new String(b));
        } catch (IOException e) {
            e.printStackTrace();
            multiLine_apropos.setText("A propos n'est pas disponible");
        }

        return view;

    }
}