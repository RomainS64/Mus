package com.montaury.mus.jeu.joueur;

public class Equipe {
    Joueur j1;
    Joueur j2;
    String nom;
    public static Equipe equipeJoueur(Joueur humain){
        return new Equipe(
                humain,
                Joueur.ordinateur("Ordinateur 1"),
                "Equipe 1"
        );
    };
    public static Equipe equipeOrdi(){
        return new Equipe(
                Joueur.ordinateur("Ordinateur 2"),
                Joueur.ordinateur("Ordinateur 3"),
                "Equipe 2"
        );
    };

    public Equipe(Joueur j1,Joueur j2,String nomEquipe){
        this.j1 = j1;
        this.j2 = j2;
        this.nom = nomEquipe;
    }

    public Joueur getJ1() {
        return j1;
    }

    public Joueur getJ2() {
        return j2;
    }

    public String getNom() {
        return nom;
    }
}
