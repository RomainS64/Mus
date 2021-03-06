package com.montaury.mus.jeu.joueur;

import com.montaury.mus.jeu.carte.Carte;
import java.util.List;

public class Joueur {
  public static Joueur humain(String nom) {
    return new Joueur(nom, new InterfaceJoueurHumain());
  }

  public static Joueur ordinateur(String nom) {
    return new Joueur(nom, new InterfaceJoueurOrdinateur());
  }

  private Equipe equipe;
  private final String nom;
  public final InterfaceJoueur interfaceJoueur;
  private final Main main = Main.vide();

  public Joueur(String nom, InterfaceJoueur interfaceJoueur) {
    this.nom = nom;
    this.interfaceJoueur = interfaceJoueur;
  }

  public String nom() {
    return nom;
  }

  public Main main() {
    return main;
  }

  public void donnerCartes(List<Carte> cartes) {
    main.ajouter(cartes);
    interfaceJoueur.nouvelleMain(main);
  }
  public Equipe equipe(){
    return equipe;
  };
  public void equipe(Equipe equipe){
    this.equipe = equipe;
  };
}
