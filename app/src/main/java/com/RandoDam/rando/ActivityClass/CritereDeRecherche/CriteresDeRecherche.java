package com.RandoDam.rando.ActivityClass.CritereDeRecherche;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.RandoDam.rando.R;

public class CriteresDeRecherche extends AppCompatActivity {
    ImageButton bt_pieton, bt_velo, bt_voiture, bt_chercher;
    TextView tv_distance, tv_affichage_distance,tv_denivele, tv_affichage_denivele,tv_eloignement, tv_affichage_eloignement;
    SeekBar sb_distance, sb_denivele, sb_eloignement;
    ImageView france;

    Criteres criteres = new Criteres();

    private int distance;
    private int denivele;
    private int eloignement;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ZoomInZoomOut fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_critere);

        bt_pieton = (ImageButton)findViewById(R.id.bt_pieton);
        bt_velo = (ImageButton)findViewById(R.id.bt_velo);
        bt_voiture = (ImageButton)findViewById(R.id.bt_voiture);
        bt_pieton.setBackgroundColor(Color.parseColor("#D3D3D3"));
        bt_velo.setBackgroundColor(Color.parseColor("#D3D3D3"));
        bt_voiture.setBackgroundColor(Color.parseColor("#D3D3D3"));
        bt_chercher = (ImageButton)findViewById(R.id.bt_chercher);
        tv_affichage_distance = (TextView)findViewById(R.id.tv_affichage_distance);
        sb_distance = (SeekBar)findViewById(R.id.sb_distance);
        tv_affichage_denivele = (TextView)findViewById(R.id.tv_affichage_denivele);
        sb_denivele = (SeekBar)findViewById(R.id.sb_denivele);
        tv_affichage_eloignement = (TextView)findViewById(R.id.tv_affichage_eloignement);
        sb_eloignement = (SeekBar)findViewById(R.id.sb_eloignement);
        france = (ImageView)findViewById(R.id.france);

        fragment = new ZoomInZoomOut(); // <--------------------------------------------
        fragmentManager = getSupportFragmentManager();
        addFragment();

        bt_pieton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i("OnClick Button ---- ", "pieton");
                bt_pieton.setBackgroundColor(Color.parseColor("#E6B121"));
                bt_velo.setBackgroundColor(Color.parseColor("#D3D3D3"));
                bt_voiture.setBackgroundColor(Color.parseColor("#D3D3D3"));
                bt_chercher.setBackgroundColor(Color.parseColor("#D3D3D3"));
                criteres.setLocomotion(1);
            }
        });

        bt_velo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i("OnClick Button ---- ", "velo");
                bt_pieton.setBackgroundColor(Color.parseColor("#D3D3D3"));
                bt_velo.setBackgroundColor(Color.parseColor("#E6B121"));
                bt_voiture.setBackgroundColor(Color.parseColor("#D3D3D3"));
                bt_chercher.setBackgroundColor(Color.parseColor("#D3D3D3"));
                criteres.setLocomotion(2);
            }
        });

        bt_voiture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i("OnClick Button ---- ", "voiture");
                bt_pieton.setBackgroundColor(Color.parseColor("#D3D3D3"));
                bt_velo.setBackgroundColor(Color.parseColor("#D3D3D3"));
                bt_voiture.setBackgroundColor(Color.parseColor("#E6B121"));
                bt_chercher.setBackgroundColor(Color.parseColor("#D3D3D3"));
                criteres.setLocomotion(3);
            }
        });

        sb_distance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                distance = progress/2;
                criteres.setDistance(distance);
                tv_affichage_distance.setText( Integer.toString(distance) + " km");
                bt_chercher.setBackgroundColor(Color.parseColor("#D3D3D3"));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //onStartTrackingTouch();
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //onStopTrackingTouch();
            }
        });

        sb_denivele.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                denivele = progress*20;
                criteres.setDenivele(denivele);
                tv_affichage_denivele.setText( Integer.toString(denivele) + " m");
                bt_chercher.setBackgroundColor(Color.parseColor("#D3D3D3"));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //onStartTrackingTouch();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //onStopTrackingTouch();
            }
        });

        sb_eloignement.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                eloignement = progress*3;
                criteres.setEloignement(eloignement);
                tv_affichage_eloignement.setText( Integer.toString(eloignement) + " km");
                bt_chercher.setBackgroundColor(Color.parseColor("#D3D3D3"));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //onStartTrackingTouch();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //onStopTrackingTouch();
            }
        });

        bt_chercher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i("OnClick Button ---- ", "chercher"
                criteres.setLongitude(fragment.getLongitude());
                criteres.setLatitude(fragment.getLatitude());
                System.out.println("-------------------");
                System.out.println(criteres.getLocomotion());
                System.out.println(criteres.getDistance());
                System.out.println(criteres.getDenivele());
                System.out.println(criteres.getEloignement());
                System.out.println(criteres.getLongitude() + " / " + criteres.getLatitude());
            }
        });
    }

    public void boutonOk() {
        bt_chercher.setBackgroundColor(Color.parseColor("#E6B121"));
    }

    private void addFragment() {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.nav_host_fragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}