package com.montaury.mus.jeu.tour;

import com.montaury.mus.jeu.Manche;
import com.montaury.mus.jeu.Partie;
import com.montaury.mus.jeu.carte.Carte;
import com.montaury.mus.jeu.carte.Defausse;
import com.montaury.mus.jeu.joueur.*;
import com.montaury.mus.jeu.tour.phases.dialogue.Gehiago;
import com.montaury.mus.jeu.tour.phases.dialogue.Hordago;
import com.montaury.mus.jeu.tour.phases.dialogue.Idoki;
import com.montaury.mus.jeu.tour.phases.dialogue.Imido;
import com.montaury.mus.jeu.tour.phases.dialogue.Kanta;
import com.montaury.mus.jeu.tour.phases.dialogue.Paso;
import com.montaury.mus.jeu.tour.phases.dialogue.Tira;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.montaury.mus.jeu.carte.Fixtures.paquetAvec;
import static com.montaury.mus.jeu.carte.Fixtures.paquetEntierCroissant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TourTest {

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
    score = new Manche.Score(opposants);
    evenementsDeJeu = mock(AffichageEvenementsDeJeu.class);
    tour = new Tour(evenementsDeJeu, paquetEntierCroissant(), new Defausse());
  }

  @Test
  void devrait_donner_tous_les_points_au_joueur_esku_si_le_joueur_zaku_fait_tira() {
    when(interfaceJoueurEsku.faireChoixParmi(any())).thenReturn(new Imido());
    when(interfaceOrdi1.faireChoixParmi(any())).thenReturn(new Tira());
    when(interfaceJoueurZaku.faireChoixParmi(any())).thenReturn(new Tira());
    when(interfaceOrdi2.faireChoixParmi(any())).thenReturn(new Tira());
    tour.jouer(opposants, score);

    assertThat(score.vainqueur()).isEmpty();
    assertThat(score.scoreParEquipe()).containsEntry(equipe1, 8);
    assertThat(score.scoreParEquipe()).containsEntry(equipe2, 0);
  }

  @Test
  void devrait_repartir_les_points_si_tout_est_paso() {
    when(interfaceJoueurEsku.faireChoixParmi(any())).thenReturn(new Paso());
    when(interfaceJoueurZaku.faireChoixParmi(any())).thenReturn(new Paso());
    when(interfaceOrdi2.faireChoixParmi(any())).thenReturn(new Paso());
    when(interfaceOrdi1.faireChoixParmi(any())).thenReturn(new Paso());

    tour.jouer(opposants, score);

    assertThat(score.vainqueur()).isEmpty();
    assertThat(score.scoreParEquipe()).containsEntry(equipe1, 1);
    assertThat(score.scoreParEquipe()).containsEntry(equipe2, 5);
  }

  @Test
  void devrait_faire_gagner_le_equipe_zaku_si_hordago_au_grand() {
    when(interfaceJoueurEsku.faireChoixParmi(any())).thenReturn(new Hordago());
    when(interfaceOrdi2.faireChoixParmi(any())).thenReturn(new Kanta());
    when(interfaceOrdi1.faireChoixParmi(any())).thenReturn(new Kanta());
    when(interfaceJoueurZaku.faireChoixParmi(any())).thenReturn(new Kanta());

    tour.jouer(opposants, score);

    assertThat(score.vainqueur()).contains(equipe2);
    assertThat(score.scoreParEquipe()).containsEntry(equipe1, 0);
    assertThat(score.scoreParEquipe()).containsEntry(equipe2, 40);
  }

  @Test
  void devrait_partager_les_points_si_tout_est_idoki() {
    when(interfaceJoueurEsku.faireChoixParmi(any())).thenReturn(new Imido());
    when(interfaceJoueurZaku.faireChoixParmi(any())).thenReturn(new Idoki());
    when(interfaceOrdi1.faireChoixParmi(any())).thenReturn(new Idoki());
    when(interfaceOrdi2.faireChoixParmi(any())).thenReturn(new Idoki());

    tour.jouer(opposants, score);

    assertThat(score.vainqueur()).isEmpty();
    assertThat(score.scoreParEquipe()).containsEntry(equipe1, 2);
    assertThat(score.scoreParEquipe()).containsEntry(equipe2, 10);
  }

  @Test
  void devrait_partager_les_points_si_tout_est_gehiago_puis_idoki() {
    when(interfaceJoueurEsku.faireChoixParmi(any())).thenReturn(new Imido(), new Idoki(), new Imido(), new Idoki(), new Imido(), new Idoki(), new Imido(), new Idoki());
    when(interfaceJoueurZaku.faireChoixParmi(any())).thenReturn(new Idoki());
    when(interfaceOrdi2.faireChoixParmi(any())).thenReturn(new Gehiago(2));
    when(interfaceOrdi1.faireChoixParmi(any())).thenReturn(new Idoki());
    tour.jouer(opposants, score);

    assertThat(score.vainqueur()).isEmpty();
    assertThat(score.scoreParEquipe()).containsEntry(equipe1,2);
    assertThat(score.scoreParEquipe()).containsEntry(equipe2,14);
  }

  @Test
  void devrait_privilegier_le_joueur_esku_si_les_mains_sont_identiques() {
    when(interfaceJoueurEsku.faireChoixParmi(any())).thenReturn(new Imido());
    when(interfaceJoueurZaku.faireChoixParmi(any())).thenReturn(new Idoki());
    when(interfaceOrdi2.faireChoixParmi(any())).thenReturn(new Idoki());
    when(interfaceOrdi1.faireChoixParmi(any())).thenReturn(new Idoki());

    Tour tour = new Tour(evenementsDeJeu, paquetAvec(Carte.AS_BATON, Carte.DEUX_BATON, Carte.TROIS_BATON, Carte.QUATRE_BATON, Carte.AS_COUPE, Carte.DEUX_COUPE, Carte.TROIS_COUPE, Carte.QUATRE_COUPE
                                                    ,Carte.AS_BATON, Carte.DEUX_BATON, Carte.TROIS_BATON, Carte.QUATRE_BATON, Carte.AS_COUPE, Carte.DEUX_COUPE, Carte.TROIS_COUPE, Carte.QUATRE_COUPE), new Defausse());

    tour.jouer(opposants, score);

    assertThat(score.vainqueur()).isEmpty();
    assertThat(score.scoreParEquipe()).containsEntry(equipe1, 7);
    assertThat(score.scoreParEquipe()).containsEntry(equipe2, 0);
  }

  private InterfaceJoueur interfaceJoueurEsku;
  private InterfaceJoueur interfaceJoueurZaku;
  private InterfaceJoueur interfaceOrdi1;
  private InterfaceJoueur interfaceOrdi2;
  private Joueur joueurOrdi1;
  private Joueur joueurOrdi2;
  private Joueur joueurEsku;
  private Joueur joueurZaku;
  private  Equipe equipe1;
  private Equipe equipe2;
  private Opposants opposants;
  private Manche.Score score;
  private AffichageEvenementsDeJeu evenementsDeJeu;
  private Tour tour;
}
