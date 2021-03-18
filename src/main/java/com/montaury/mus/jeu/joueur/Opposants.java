package com.montaury.mus.jeu.joueur;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Opposants {
  private Joueur joueurEsku;
  private Joueur joueurZaku;

  private List<Equipe> equipes;

  private LinkedList<Joueur> joueursDansLordre;

  public Opposants(Equipe equipe1,Equipe equipe2) {
    equipes = new ArrayList<Equipe>();
    equipes.add(equipe1);
    equipes.add(equipe2);
    joueursDansLordre = new LinkedList<Joueur>();

    for(int i=0;i<equipe1.getJoueurs().size();i++){
      joueursDansLordre.add(equipe1.getJoueurs().get(i));
      joueursDansLordre.add(equipe2.getJoueurs().get(i));
    }
    definirRoles();

  }

  //A modifier
  public void tourner() {
    Joueur jouerTemp = joueursDansLordre.removeLast();
    joueursDansLordre.addFirst(jouerTemp);
    definirRoles();
  }

  public Joueur joueurEsku() {
    return joueurEsku;
  }

  public Joueur joueurZaku() {
    return joueurZaku;
  }

  public Iterator<Joueur> itererDansLOrdre() {
    return new IteratorInfini(this);
  }

  public List<Joueur> dansLOrdre() {

    return joueursDansLordre;
  }
  public List<Equipe> equipes(){
    return equipes;
  }
  private void definirRoles(){
    joueurEsku = joueursDansLordre.getFirst();
    joueurZaku = joueursDansLordre.getLast();
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
