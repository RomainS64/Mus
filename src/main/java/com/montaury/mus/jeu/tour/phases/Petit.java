package com.montaury.mus.jeu.tour.phases;

import com.montaury.mus.jeu.carte.Carte;
import com.montaury.mus.jeu.carte.ValeurCarte;
import com.montaury.mus.jeu.joueur.Joueur;
import com.montaury.mus.jeu.joueur.Main;
import com.montaury.mus.jeu.joueur.Opposants;
import java.util.List;

import static com.montaury.mus.jeu.carte.ValeurCarte.Comparaison.*;

public class Petit extends Phase {
  public Petit() {
    super("Petit");
  }

  @Override
  protected Joueur meilleurParmi(Opposants opposants) {
    Joueur meilleurJoueurEquipe1 = comparerDeuxJoueurs(opposants.joueurEsku(), opposants.dansLOrdre().get(2));
    Joueur meilleurJoueurEquipe2 = comparerDeuxJoueurs(opposants.dansLOrdre().get(1), opposants.joueurZaku());
    return comparerDeuxJoueurs(meilleurJoueurEquipe1, meilleurJoueurEquipe2);

  }

  protected Joueur comparerDeuxJoueurs(Joueur joueur1, Joueur joueur2) {
    List<Carte> cartesJoueur1 = joueur1.main().cartesDuPlusGrandAuPlusPetit();
    List<Carte> cartesJoueur2 = joueur2.main().cartesDuPlusGrandAuPlusPetit();
    for (int i = Main.TAILLE - 1; i >= 0; i--) {
      ValeurCarte.Comparaison compare = cartesJoueur1.get(i).comparerAvec(cartesJoueur2.get(i));
      if (compare == PLUS_PETITE) {
        return joueur1;
      }
      if (compare == PLUS_GRANDE) {
        return joueur2;
      }
    }
    // En cas d'égalité, le 1er joueur passé en paramètres est considéré comme prioritaire
    return joueur1;
  }

}
