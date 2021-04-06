package com.bello.papa.Model;

public class Troupeaux {

    private int Nombres_Boeufs;
    private int Male;
    private int Femelle;

    public Troupeaux(int nombres_Boeufs, int male, int femelle) {
        Nombres_Boeufs = nombres_Boeufs;
        Male = male;
        Femelle = femelle;
    }

    public Troupeaux() {
    }

    public int getNombres_Boeufs() {
        return Nombres_Boeufs;
    }

    public int getMale() {
        return Male;
    }

    public int getFemelle() {
        return Femelle;
    }
}
