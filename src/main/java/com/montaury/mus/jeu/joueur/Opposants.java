package com.montaury.mus.jeu.joueur;

import java.util.Iterator;
import java.util.List;

public class Opposants {

  private Joueur joueurEsku;
  private Joueur joueurZaku;

  private Equipe equipe1;
  private Equipe equipe2;

  public Opposants(Equipe e1,Equipe e2) {
    equipe1 = e1;
    equipe2 = e2;

    this.joueurEsku = equipe1.getJ1();
    this.joueurZaku = equipe2.getJ1();

  }

  public void tourner() {
    Joueur tmp = joueurEsku;
    joueurEsku = joueurZaku;
    joueurZaku = tmp;
  }

  public Joueur joueurEsku() {
    return joueurEsku;
  }
  public Joueur joueurZaku() {
    return joueurZaku;
  }

  public Equipe getEquipe1() {
    return equipe1;
  }
  public Equipe getEquipe2() {
    return equipe2;
  }

  public Iterator<Joueur> itererDansLOrdre() {
    return new IteratorInfini(this);
  }

  public List<Joueur> dansLOrdre() {
    return List.of(equipe1.j1,equipe1.j2,equipe2.j1,equipe2.j2);
  }

  private static class IteratorInfini implements Iterator<Joueur> {
    private final Opposants opposants;
    private Joueur suivant;

    public IteratorInfini(Opposants opposants) {
      this.opposants = opposants;
      suivant = opposants.joueurEsku;
    }

    @Override
    public boolean hasNext() {
      return true;
    }

    @Override
    public Joueur next() {
      Joueur next = suivant;
      suivant = suivant == opposants.joueurEsku ? opposants.joueurZaku : opposants.joueurEsku;
      return next;
    }
  }
}
