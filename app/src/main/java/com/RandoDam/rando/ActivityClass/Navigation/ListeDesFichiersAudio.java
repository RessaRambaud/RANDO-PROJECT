package com.RandoDam.rando.ActivityClass.Navigation;

/**
 * Classe de gestion des fichiers audio
 */
public class ListeDesFichiersAudio {

    private int nombreDeFichiers = 0;
    private String[] listeDeNomsDeFichiers;

    public ListeDesFichiersAudio() {
        listeDeNomsDeFichiers = new String[100];
        this.nombreDeFichiers = 0;
    }

    public void stockerUnNomDeFichier(String nom) {
        nombreDeFichiers++;
        listeDeNomsDeFichiers[nombreDeFichiers] = new String(nom);
    }

    public int donnerLeNombreDeFichiers(){
        return this.nombreDeFichiers;
    }

    public String lireUnNomDeFichier(int numero) {
        return listeDeNomsDeFichiers[numero];
    }
}
