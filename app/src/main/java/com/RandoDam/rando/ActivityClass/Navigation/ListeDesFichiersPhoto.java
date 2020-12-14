package com.RandoDam.rando.ActivityClass.Navigation;

/**
 * Classe de gestion des fichiers photo
 */
public class ListeDesFichiersPhoto {
    private int nombreDeFichiers = 0;
    private String[] listeDeNomsDeFichiers;

    public ListeDesFichiersPhoto() {
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
