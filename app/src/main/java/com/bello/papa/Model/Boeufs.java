package com.bello.papa.Model;

public class Boeufs {

     String Designation;
    String NaissBoeuf;
    String Boeuf_photos;
    String Sexe_Boeufs;
    String Proprietaire;

    public String getProprietaire() {
        return Proprietaire;
    }

    public void setProprietaire(String proprietaire) {
        Proprietaire = proprietaire;
    }



    public Boeufs() { }

    public Boeufs(String designation,String naissBoeuf, String proprietaire, String boeuf_photos, String sexe_Boeufs) {
        Designation = designation;
        NaissBoeuf = naissBoeuf;
        Boeuf_photos = boeuf_photos;
        Sexe_Boeufs = sexe_Boeufs;
        Proprietaire = proprietaire;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public String getNaissBoeuf() {
        return NaissBoeuf;
    }

    public void setNaissBoeuf(String naissBoeuf) {
        NaissBoeuf = naissBoeuf;
    }

    public String getBoeuf_photos() {
        return Boeuf_photos;
    }

    public void setBoeuf_photos(String boeuf_photos) {
        Boeuf_photos = boeuf_photos;
    }

    public String getSexe_Boeufs() {
        return Sexe_Boeufs;
    }

    public void setSexe_Boeufs(String sexe_Boeufs) {
        Sexe_Boeufs = sexe_Boeufs;
    }
}
