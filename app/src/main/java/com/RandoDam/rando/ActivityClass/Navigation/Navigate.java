package com.RandoDam.rando.ActivityClass.Navigation;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.RandoDam.rando.R;

public class Navigate extends AppCompatActivity {
    Context context;
    ImageButton bt_texte, bt_audio, bt_video, bt_photo, bt_demarrer, bt_assistant, bt_sauver;
    static TextView tv_latitude;
    static TextView tv_longitude;
    private static final int TAKE_PICTURE = 1;
    private Uri imageUri;
    static final int CAPTURE_PHOTO = 1;
    static final int CAPTURE_VIDEO = 1;
    static final int CAPTURE_AUDIO = 1;
    boolean demarrer = false;
    boolean assistant = false;
    boolean sauver = false;
    EnregistreurMedia enregistreurMedia = new EnregistreurMedia(this);
    // --------------------------------------------------------------------------------------------
    Test_Tone test_tone;
    // --------------------------------------------------------------------------------------------
    LocationManager locationManager = null;
    private String fournisseur=null;
    Location localisation = new Location(LocationManager.GPS_PROVIDER);
    Location localisation_sauve = new Location(LocationManager.GPS_PROVIDER);
    Location localisation_depart;
    MyLocationListener myLocationListener=null;
    final static int PERMISSION_REQUEST_CODE = 101;
    private String[] permissions;
    SharedPreferences prefs;
    // --------------------------------------------------------------------------------------------
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Controle_Fichiers fragment;
    // --------------------------------------------------------------------------------------------

    /**
     * création de l'IHM
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        bt_texte = (ImageButton) findViewById(R.id.bt_texte);
        bt_audio = (ImageButton) findViewById(R.id.bt_audio);
        bt_video = (ImageButton) findViewById(R.id.bt_video);
        bt_photo = (ImageButton) findViewById(R.id.bt_photo);
        bt_demarrer = (ImageButton) findViewById(R.id.bt_demarrer);
        bt_assistant = (ImageButton) findViewById(R.id.bt_assistant);
        bt_sauver = (ImageButton) findViewById(R.id.bt_sauver);
        tv_latitude = (TextView)findViewById(R.id.tv_latitude);
        tv_longitude = (TextView)findViewById(R.id.tv_longitude);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        test_tone = new Test_Tone();

        bt_demarrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                demarrer();
                //Log.i("OnClick Button ---- ", "demarrer");
            }
        });

        bt_assistant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assistant();
                //Log.i("OnClick Button ---- ", "demarrer");
            }
        });

        bt_sauver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sauver();
                //Log.i("OnClick Button ---- ", "demarrer");
            }
        });

        bt_texte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i("OnClick Button ---- ", "texte");
                takeText();
            }
        });

        bt_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i("OnClick Button ---- ", "audio");
                takeAudio();
            }
        });

        bt_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i("OnClick Button ---- ", "video");
                takeVideo();
            }
        });

        bt_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i("OnClick Button ---- ", "photo");
                takePicture();
                //Intent m_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //imageUri = getImageUri();
                //m_intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                //startActivityForResult(m_intent, REQUEST_IMAGE_CAPTURE);
            }
        });
    }

    /**
     * arrêt de l'activité
     * sauvegarde de la dernière position dans les préférences.
     */
    @Override
    protected void onStop() {
        localisation_sauve = localisation;
        float latitude_put = (float) localisation_sauve.getLatitude();
        float longitude_put = (float) localisation_sauve.getLongitude();
        SharedPreferences.Editor editeur = prefs.edit();
        editeur.putFloat("current_latitude", (float) latitude_put);
        editeur.putFloat("current_longitude", (float) longitude_put);
        editeur.commit();
        super.onStop();
    }

    /**
     * Destruction de l'activité
     * on arrête la localisation avant
     */
    @Override
    protected void onDestroy() {
        arreterLocalisation();
        super.onDestroy();
    }
    // ---------------------------------------------------------------------------------------------

    /**
     * Démarrage du parcours
     * On démarre la localisation
     */
    private void demarrer() {
        if (demarrer == false) {
            demarrer = true;
            bt_demarrer.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_adjust_2_24));
            initialiserLocalisation();
        } else if (demarrer == true) {
            demarrer = false;
            bt_demarrer.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_adjust_24));
            arreterLocalisation();
        }
    }

    /**
     * Demarrage de l'assistance
     * (pas encore codé)
     */
    private void assistant() {
        if (assistant == false) {
            assistant = true;
            bt_assistant.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_campaign_2_24));
            //fragment = new Controle_Fichiers(); // <--------------------------------------------
            //fragmentManager = getSupportFragmentManager();
            //Fragment fragment;
            //fragment = new Controle_Fichiers();
            //fragmentTransaction = fragmentManager.beginTransaction();
            //fragmentTransaction.add(R.id.nav_host_fragment, fragment);
            //fragmentTransaction.commit();
        } else if (assistant == true) {
            assistant = false;
            bt_assistant.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_campaign_24));
            //fragmentTransaction.remove(fragment);
            //fragmentTransaction.commit();
        }
    }

    /**
     * Sauvegarger les données du parcours
     * (pas encore codé)
     */
    private void sauver() {
        if (sauver == false) {
            sauver = true;
            bt_sauver.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_check_circle_outline_2_24));
            //  retour à l'accueil ici
        } else if (sauver == true) {
            sauver = false;
        }
    }

    /**
     * On active la photo
     */
    private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, CAPTURE_PHOTO);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }

    /**
     * On active la vidéo
     */
    private void takeVideo() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        //intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(imageToStore));
        try {
            startActivityForResult(intent, CAPTURE_VIDEO);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }

    /**
     * On active l'audio
     */
    private void takeAudio() {
        Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        //intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(imageToStore));
        try {
            startActivityForResult(intent, CAPTURE_AUDIO);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }

    /**
     * On active un editeur de texte pour enregistrer une note
     */
    private void takeText() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Navigate.this);
        builder.setMessage("Ecrivez votre note:");
        builder.setCancelable(true);
        final EditText contenu = new EditText(Navigate.this);

        builder.setPositiveButton(
                "SAUVER",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Log.i("--x-- sauver","step 1");
                        //Log.i("--x-- takeText " , contenu.getText().toString());
                        enregistreurMedia.ecrireFichierTexte(contenu.getText().toString());
                        //Log.i("--x-- sauver","step 2");
                        enregistreurMedia.autoTestListe();
                        enregistreurMedia.autoTestRepertoires();
                        //Log.i("--x-- sauver","step 3");
                    }
                });

        builder.setNegativeButton(
                "ABANDONNER",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Log.i("--x-- passage","Abandonner");
                        dialog.cancel();
                    }
                });

        //final EditText contenu = new EditText(MainActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        contenu.setLayoutParams(lp);
        contenu.setHint("note");
        builder.setView(contenu);

        AlertDialog alert1 = builder.create();
        alert1.show();
    }
    // ---------------------------------------------------------------------------------------------

    /**
     * Indication de la position sur l'interface
     * @param latitude
     * @param longitude
     */
    static public void miseAJourPosition(double latitude, double longitude) {
        tv_latitude.setText(Double.toString(latitude));
        tv_longitude.setText(Double.toString(longitude));
    }
    // ---------------------------------------------------------------------------------------------

    /**
     * Démarrage de la localisation
     * localisationManager fournit le service actif (gps ou réseau)
     */
    private void initialiserLocalisation() {
        if (locationManager == null) {
            //test_tone.tone_two();
            //Log.d("-x-initialisation", "locationmanager == null ");
            locationManager = (LocationManager)this.getSystemService(LOCATION_SERVICE);
            Criteria criteres = new Criteria();
            criteres.setAccuracy(Criteria.ACCURACY_FINE);
            criteres.setAltitudeRequired(true);
            criteres.setBearingRequired(true);
            criteres.setSpeedRequired(true);
            criteres.setCostAllowed(true);
            criteres.setPowerRequirement(Criteria.POWER_HIGH);
            fournisseur = locationManager.getBestProvider(criteres, true);
            //Log.d("-x-GPS", "fournisseur : " + fournisseur);
        }
        if (fournisseur != null) {
            // dernière position connue
            if ( (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED )
                    &&
                    ( ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) ) {
                //requestPermissions(permissions, PERMISSION_REQUEST_CODE);
            } else {
            }

            //localisation = locationManager.getLastKnownLocation(fournisseur);
            //Log.d("-x-GPS", "localisation : " + localisation.toString());
            //String coordonnees = String.format("Latitude : %f - Longitude : %f\n", localisation.getLatitude(), localisation.getLongitude());
            //Log.d("GPS", "coordonnees : " + coordonnees);
            //String autres = String.format("Vitesse : %f - Altitude : %f - Cap : %f\n", localisation.getSpeed(), localisation.getAltitude(), localisation.getBearing());
            //Log.d("GPS", autres);
            //String timestamp = String.format("Timestamp : %d\n", localisation.getTime());
            //Log.d("GPS", "timestamp : " + timestamp);
            //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            //Date date = new Date(localisation.getTime());
            //Log.d("GPS", sdf.format(date));
            // ------------------------------------------------------------------------------------
            //localisation = locationManager.getLastKnownLocation(fournisseur);
            double latitude_get = prefs.getFloat("current_latitude", 0);
            double longitude_get = prefs.getFloat("current_longitude", 0);
            localisation.setLatitude(latitude_get);//long latitude = prefs.getLong("current_latitude", 0);
            localisation.setLongitude(longitude_get);//long longitude = prefs.getLong("current_longitude", 0);
            myLocationListener = new MyLocationListener(this);
            if(localisation != null) {
                // on notifie la localisation
                myLocationListener.onLocationChanged(localisation);
                //Log.d("-x-GPS", "localisation : " + localisation.toString());
            }
            // on configure la mise à jour automatique : au moins 10 mètres et 15 secondes
            locationManager.requestLocationUpdates(fournisseur, 3000, 3, myLocationListener);
        }
    }

    /**
     * Activation des permissions
     * @param requestCode
     * @param permissions
     * @param grantResult
     */
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResult){
        if (requestCode!=PERMISSION_REQUEST_CODE) {
            return;
        }
        int idxPermFine=-1;
        for(int i=0;i<permissions.length && idxPermFine==-1;i++){
            if (permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                idxPermFine = i;
            }
        }
        if (grantResult[idxPermFine] == PackageManager.PERMISSION_GRANTED)
            //doLocation();
            return;
    }

    /**
     * Arrêt de la localisation par le localisationManager
     */
    private void arreterLocalisation() {
        if(locationManager != null) {
            locationManager.removeUpdates(myLocationListener);
            myLocationListener = null;
        }
    }

    /**
     * Utility
     * @return
     */
    public Context getContext() {
        return context;
    }

    /**
     * Utility
     * @param context
     */
    public void setContext(Context context) {
        this.context = context;
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Fragment d'affichage de la carte
     * (pas encore codé)
     */
    private void addFragment() {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.nav_host_fragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
