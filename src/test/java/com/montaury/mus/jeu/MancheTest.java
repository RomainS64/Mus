package com.montaury.mus.jeu;

import com.montaury.mus.jeu.joueur.*;
import com.montaury.mus.jeu.tour.phases.dialogue.Gehiago;
import com.montaury.mus.jeu.tour.phases.dialogue.Hordago;
import com.montaury.mus.jeu.tour.phases.dialogue.Imido;
import com.montaury.mus.jeu.tour.phases.dialogue.Kanta;
import com.montaury.mus.jeu.tour.phases.dialogue.Paso;
import com.montaury.mus.jeu.tour.phases.dialogue.Tira;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MancheTest {

  private Manche manche;

  @BeforeEach
  void setUp() {
    interfaceJoueurEsku = mock(InterfaceJoueur.class);
    interfaceJoueurZaku = mock(InterfaceJoueur.class);
    interfaceOrdi1 = mock(InterfaceJoueur.class);
    interfaceOrdi2 = mock(InterfaceJoueur.class);
    joueurEsku = new Joueur("J1", interfaceJoueurEsku);
    joueurZaku = new Joueur("J2", interfaceJoueurZaku);
    joueurOrdi1 =new Joueur("J3", interfaceOrdi1);
    joueurOrdi2 =new Joueur("J4", interfaceOrdi2);
    equipe1 = new Equipe(joueurEsku,joueurOrdi1,"e1");
    equipe2 = new Equipe(joueurOrdi2,joueurZaku,"e2");
    opposants = new Opposants(equipe1,equipe2);
    manche = new Manche(mock(AffichageEvenementsDeJeu.class));
  }

  @Test
  void devrait_terminer_la_manche_si_hordago_au_grand() {
    when(interfaceJoueurEsku.faireChoixParmi(any())).thenReturn(new Hordago());
    when(interfaceOrdi1.faireChoixParmi(any())).thenReturn(new Kanta());
    when(interfaceJoueurZaku.faireChoixParmi(any())).thenReturn(new Kanta());
    when(interfaceOrdi2.faireChoixParmi(any())).thenReturn(new Kanta());

    Manche.Resultat resultat = manche.jouer(opposants);

    assertThat(resultat.vainqueur()).isNotNull();
    assertThat(resultat.pointsVaincu()).isZero();
  }

  @Test
  void devrait_terminer_la_manche_si_un_joueur_a_40_points() {
    when(interfaceJoueurEsku.faireChoixParmi(any())).thenReturn(new Paso());
    when(interfaceOrdi2.faireChoixParmi(any())).thenReturn(new Gehiago(40));
    when(interfaceJoueurZaku.faireChoixParmi(any())).thenReturn(new Tira());
    when(interfaceOrdi1.faireChoixParmi(any())).thenReturn(new Gehiago(2));

    Manche.Resultat resultat = manche.jouer(opposants);

    assertThat(resultat.vainqueur()).isEqualTo(equipe1);
    assertThat(resultat.pointsVaincu()).isZero();
  }

  @Test
  void devrait_changer_l_ordre_des_opposants_a_la_fin_du_tour() {
    when(interfaceJoueurEsku.faireChoixParmi(any())).thenReturn(new Hordago());
    when(interfaceOrdi1.faireChoixParmi(any())).thenReturn(new Kanta());
    when(interfaceJoueurZaku.faireChoixParmi(any())).thenReturn(new Kanta());
    when(interfaceOrdi2.faireChoixParmi(any())).thenReturn(new Kanta());

    manche.jouer(opposants);

    assertThat(opposants.dansLOrdre()).containsExactly(joueurZaku,joueurEsku,joueurOrdi2,joueurOrdi1);
  }

  private InterfaceJoueur interfaceJoueurEsku;
  private InterfaceJoueur interfaceJoueurZaku;
  private InterfaceJoueur interfaceOrdi1;
  private InterfaceJoueur interfaceOrdi2;
  private Joueur joueurEsku;
  private Joueur joueurZaku;
  private Joueur joueurOrdi1;
  private Joueur joueurOrdi2;
  private Equipe equipe1;
  private Equipe equipe2;
  private Opposants opposants;

}