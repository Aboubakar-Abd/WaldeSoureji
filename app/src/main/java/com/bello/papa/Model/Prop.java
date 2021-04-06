package com.bello.papa.Model;

public class Prop {

    private String Nom;
    private String Prenom;
    private String Id;
    private int Nombre;
    private int Depense;
    private String ProfilePic;

    public Prop() { }

    public Prop(String nom, String prenom, String id, int nombre, int depense, String profilePic) {
        Nom = nom;
        Prenom = prenom;
        Id = id;
        Nombre = nombre;
        Depense = depense;
        ProfilePic = profilePic;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public int getNombre() {
        return Nombre;
    }

    public void setNombre(int nombre) {
        Nombre = nombre;
    }

    public String getProfilePic() {
        return ProfilePic;
    }

    public int getDepense() { return Depense; }

    public void setProfilePic(String profilePic) {
        ProfilePic = profilePic;
    }
}