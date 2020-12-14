package com.RandoDam.rando.ActivityClass.Navigation;


import android.media.AudioManager;
import android.media.ToneGenerator;

/**
 * Bip d'information sur l'activité de l'écouteur
 */
public class Test_Tone {

    final ToneGenerator tg;

    /**
     * création du générateur de tone
     */
    public Test_Tone() {
         tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 50);
    }

    /**
     * envoi du tone 1 bip
     */
    public void tone_one() {
        //Log.d("-x-Tone", "one");
        tg.startTone(ToneGenerator.TONE_PROP_BEEP);
    }

    /**
     * envoi du tone 2 bips
     */
    public void tone_two() {
        //Log.d("-x-Tone", "two");
        tg.startTone(ToneGenerator.TONE_PROP_ACK);
    }
}