package com.RandoDam.rando.ActivityClass.Navigation;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Date;

/**
 * Enregistreur de média
 * Gére la sauvegarde et la relecture des médias photo, vidéo, audio, texte
 */
public class EnregistreurMedia {
    private Context ctx;

  ListeDesFichiersTexte listeDesFichiersTexte = new ListeDesFichiersTexte();

    public EnregistreurMedia(Context ctx) {
        this.ctx = ctx;
    }

    /**
     * Fournit une date pour marquer le moment ou les fichiers on été céé
     * @return
     */
    public String fournirDate() {
        Date myDate = new Date();
        StringBuilder strDate = new StringBuilder(myDate.toString());
        //strDate.delete(19, 34);
        String sFic = new String(strDate);
        String tFic = sFic.replace(" ", "_");
        String zFic = tFic.replace(":", "_");
        return zFic;
    }

    /**
     * Liste les fichiers textes écrits
     * @return
     */
    public String listerFichiersTexte() {
        Log.d("-x- ecritFicTexte", "debut");
        return "truc";
    }

    /**
     * Ecrit un fichier texte sur le disque
     * @param contenu
     */
    public void ecrireFichierTexte(String contenu) {
        //Log.d("-x- ecrireFichierTexte", "debut");

        PackageManager m = ctx.getPackageManager();
        String s = ctx.getPackageName();
        PackageInfo p = null;
        try {
            p = m.getPackageInfo(s, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        s = p.applicationInfo.dataDir;
        String str = new String(s + "/" + fournirDate() + ".txt");
        File myFile = new File(str);
        listeDesFichiersTexte.stockerUnNomDeFichier(str);
        s = p.applicationInfo.dataDir;
        try {
            myFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(myFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
        try {
            myOutWriter.append(contenu);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            myOutWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Log.d("-x- ecritFicTexte", "fin");
    }

    /**
     * Lit un fichier texte sur le disque
     * @param nom
     * @return
     */
    public String lireFichierTexte(String nom) {
        //Log.d("-x- lireFichierTexte", "debut");
        File myFile = new File(nom);
        FileInputStream fIn = null;
        try {
            fIn = new FileInputStream(myFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        InputStreamReader myInReader = new InputStreamReader(fIn);
        BufferedReader bufferedReader = new BufferedReader(myInReader);
        String receiveString = "";
        StringBuilder str = new StringBuilder();
        while (true) {
            try {
                if (!((receiveString = bufferedReader.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            str.append(receiveString);
        }
        String strOut = new String(str);
        try {
            myInReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Log.d("-x- litFicTexte", "fin");
        return strOut;
    }

    /**
     * Ecrit un fichier audio sur le disque
     * (pas encore codé)
     * @param contenu
     */
    public void ecrireFichierAudio(String contenu) { }

    /**
     * Lit un fichier audio sur le disque
     * (pas encore codé)
     * @param contenu
     */
    public void lireFichierAudio(String contenu) { }

    /**
     * Ecrit un fichier video sur le disque
     * (pas encore codé)
     * @param contenu
     */
    public void ecrireFichierVideo(String contenu) { }

    /**
     * Lit un fichier video sur le disque
     * (pas encore codé)
     * @param contenu
     */
    public void lireFichierVideo(String contenu) { }

    /**
     * Ecrit un fichier photo sur le disque
     * (pas encore codé)
     * @param contenu
     */
    public void ecrireFichierPhoto(String contenu) { }

    /**
     * Lit un fichier photo sur le disque
     * (pas encore codé)
     * @param contenu
     */
    public void lireFichierPhoto(String contenu) { }

    /**
     * Auto test des fichiers présents
     */
    public void autoTestListe() {
        int n = listeDesFichiersTexte.donnerLeNombreDeFichiers();
        Log.d("-x- auto test fic", "nombre de fic: " + String.valueOf(n));
        for (int i=1;i<=n;i++) {
            Log.d("-x- auto test" , "fichier contenu: " + lireFichierTexte(listeDesFichiersTexte.lireUnNomDeFichier(i)));
        }
    }

    /**
     * Autotest des repertoires présents
     */
    public void autoTestRepertoires() {
        PackageManager m = ctx.getPackageManager();
        String s = ctx.getPackageName();
        PackageInfo p = null;
        try {
            p = m.getPackageInfo(s, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        s = p.applicationInfo.dataDir;
        Log.d("-x- auto test rep", "repertoire: " + s);
        File directory = new File(s);
        File[] files = directory.listFiles();
        Log.d("-x-Files", "Size: "+ files.length);
        for (int i = 0; i < files.length; i++)
        {
            Log.d("-x-Files", "FileName:" + files[i].getName());
        }
    }
}
