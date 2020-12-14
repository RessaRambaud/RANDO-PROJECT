package com.RandoDam.rando.ActivityClass.Navigation;

/**
 * Classe de gestion des fichiers vidéo
 */
public class ListeDesFichiersVideo {
    private int nombreDeFichiers = 0;
    private String[] listeDeNomsDeFichiers;

    public ListeDesFichiersVideo() {
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
