package com.montaury.mus.jeu.joueur;

import java.util.ArrayList;
import java.util.List;

public class Equipe {
    List<Joueur> joueurs;
    String nom;

    public Equipe(Joueur j1,Joueur j2,String nomEquipe){
        j1.equipe(this);
        j2.equipe(this);
        joueurs = new ArrayList<Joueur>();
        joueurs.add(j1);
        joueurs.add(j2);
        this.nom = nomEquipe;
    }

    public static Equipe equipeJoueur(Joueur humain){
        return new Equipe(
                humain,
                Joueur.ordinateur("Ordi1"),
                "Equipe 1"
        );
    };
    public static Equipe equipeOrdi(){
        return new Equipe(
                Joueur.ordinateur("Ordi2"),
                Joueur.ordinateur("Ordi3"),
                "Equipe 2"
        );
    };

    public String getNom() {
        return nom;
    }
    public List<Joueur> getJoueurs(){
        return joueurs;
    }
}