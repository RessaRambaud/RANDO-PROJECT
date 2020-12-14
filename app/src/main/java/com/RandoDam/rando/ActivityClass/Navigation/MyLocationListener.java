package com.RandoDam.rando.ActivityClass.Navigation;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**
 * Listener du GPS
 */
class MyLocationListener implements LocationListener {

    private Context context;
    LocationManager locationManager;

    Test_Tone test_tone = new Test_Tone();
    TextView tv_latitude, tv_longitude;

    MyLocationListener(Context ctx){
        this.context = ctx;
    };

    /**
     * Message quand la localisation a évolué
     * on émet un tone
     * on met à jours la position sur l'interface
     * @param localisation
     */
    public void onLocationChanged(Location localisation) {
        test_tone.tone_two();
        Log.d("-x-GPS listener", "localisation : " + localisation.toString());
        //String coordonnees = String.format("Latitude : %f - Longitude : %f\n", localisation.getLatitude(), localisation.getLongitude());
        //Log.d("GPS", "latitude : " + localisation.getLatitude());
        //Log.d("GPS", "longitude : " + localisation.getLongitude());
        //Log.d("GPS", "coordonnees : " + coordonnees);

        //Toast.makeText(context,  " état : " + " nouvelle position" , Toast.LENGTH_SHORT).show();
      Navigate.miseAJourPosition(localisation.getLatitude(), localisation.getLongitude());
    }

    /**
     * Message quand changement de status
     * (on utilise pas)
     * @param fournisseur
     */
    @Override
    public void onStatusChanged(String fournisseur, int status, Bundle extras) {
        test_tone.tone_two();
        //Toast.makeText(context, fournisseur + " état : " + status, Toast.LENGTH_SHORT).show();
    }

    /**
     * Message quand fournisseur activé
     * (on utilise pas)
     * @param fournisseur
     */
    @Override
    public void onProviderEnabled(String fournisseur) {
        test_tone.tone_two();
        //Toast.makeText(context, fournisseur + " activé !", Toast.LENGTH_SHORT).show();
    }

    /**
     * Message quand fournisseur désactivé
     * (on utilise pas)
     * @param fournisseur
     */
    @Override
    public void onProviderDisabled(String fournisseur) {
        test_tone.tone_two();
        //Toast.makeText(context, fournisseur + " désactivé !", Toast.LENGTH_SHORT).show();
    }
}
