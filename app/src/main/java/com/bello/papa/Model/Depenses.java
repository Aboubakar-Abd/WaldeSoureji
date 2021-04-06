package com.bello.papa.Model;

public class Depenses {

    private String Description;
    private String Date;
    private int quantite;
    private double prix_unitaire;

    public Depenses(){}

    public Depenses(String description, String date, int quantite, double prix_unitaire) {
        this.Description = description;
        this.Date = date;
        this.quantite = quantite;
        this.prix_unitaire = prix_unitaire;

    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getPrix_unitaire() {
        return prix_unitaire;
    }

    public void setPrix_unitaire(double prix_unitaire) {
        this.prix_unitaire = prix_unitaire;
    }

}
