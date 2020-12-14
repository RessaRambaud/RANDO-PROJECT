package com.RandoDam.rando.ActivityClass.CritereDeRecherche;

public class Criteres {
    private int locomotion; // pieton | velo | auto
    private float distance; // km
    private float denivele; // m
    private float eloignement; // km
    private float longitude;
    private float latitude;

    public Criteres() {
        locomotion = 0;
        distance = 5.0f;
        denivele = 5.0f;
        eloignement = 5.0f;
        longitude = 48.800f;
        latitude = 2.300f;
    }

    public int getLocomotion() {
        return locomotion;
    }

    public void setLocomotion(int locomotion) {
        this.locomotion = locomotion;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getDenivele() {
        return denivele;
    }

    public void setDenivele(float denivele) {
        this.denivele = denivele;
    }

    public float getEloignement() {
        return eloignement;
    }

    public void setEloignement(float eloignement) {
        this.eloignement = eloignement;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }
}
