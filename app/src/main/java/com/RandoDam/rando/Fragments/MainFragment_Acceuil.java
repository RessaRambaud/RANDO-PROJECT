package com.RandoDam.rando.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.RandoDam.rando.Login;
import com.RandoDam.rando.Register;
import com.RandoDam.rando.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainFragment_Acceuil extends Fragment  {

    //For Login Activity

    Button autourDeMoi,critereDeRecherche,commencerUnParcours;
    TextView loginLink,creerUnCompteLink;
    ImageButton img_logout;
    FirebaseAuth fAuth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /** Inflate the layout for this fragment **/
        View view=inflater.inflate(R.layout.fragment_main, container, false);

        /** to connect to the login page **/
        loginLink=view.findViewById(R.id.tv_Login_Link);
        creerUnCompteLink=view.findViewById(R.id.tv_CreerUnCompte_Link);

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in =new Intent(getActivity(), Login.class);
                Log.i("debug", "je passe ici");
                in.putExtra("some","some data");
                startActivity(in);

            }
        });

        creerUnCompteLink.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent in=new Intent(getActivity(), Register.class);
                in.putExtra("some","some data");
                Log.i("debug", "je passe ici compte link");
                startActivity(in);
            }
        });
       /** disabling creer the login and acount creation link when the user is logged in **/

        fAuth = FirebaseAuth.getInstance();
        if (fAuth.getCurrentUser() != null) {
            creerUnCompteLink.setVisibility(view.GONE);
            loginLink.setVisibility(view.GONE);

        }

        return view;
    }



}