package test;

import jeu.Joueur;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JoueurTest {

    private String getString(Joueur j1) {
        String s = "cartes " + j1.getNomJoueur() + " { ";
        for (int ValueCard : j1.getMainJoueur()) {
            s += (String.format("%02d", ValueCard) + " ");
        }
        s += "}";
        return s;
    }

    private void ajouterCarte(Joueur j, int n){
        if (n < 1)
            return;
        for (int i = 0; i < n; ++i)
            j.getPiocheJoueur().add(i);
    }

    private void remove(Joueur j) {
        j.getTmpAsc().remove(0);
        j.getTmpDesc().remove(0);
    }

    private void resetBase(Joueur joueur) {
        joueur.getBase().setJeuAsc(1);
        joueur.getBase().setJeuDesc(60);

        joueur.getTmpAsc().clear();
        joueur.getTmpDesc().clear();
    }

    private void resetTotal(Joueur joueur, Joueur adversaire) {
        joueur.setaJouéSurLaPileDeLAdversaire(true);
        adversaire.setaJouéSurLaPileDeLAdversaire(true);
        resetBase(joueur);
        resetBase(adversaire);
        joueur.getMainJoueur().clear();
        adversaire.getMainJoueur().clear();
        joueur.piocherUneCarte();
        adversaire.piocherUneCarte();
    }

    @Test
    void getNomJoueurTest() {
        Joueur test = new Joueur("test");
        assertEquals(test.getNomJoueur(), "test");

        Joueur adversaire = new Joueur("adversaire");
        assertNotEquals(adversaire.getNomJoueur(), "faux");
        assertEquals(adversaire.getNomJoueur(), "adversaire");
    }

    @Test
    void aUneCarteTest() {
        Joueur test = new Joueur("test");
        for (int i = 0; i < test.getMainJoueur().size() - 2; i++){
            test.getMainJoueur().remove(i);
            assertFalse(test.aUneCarte());
        }
        test.getMainJoueur().clear();
        assertFalse(test.aUneCarte());
        test.getMainJoueur().add(15);
        assertFalse(test.aUneCarte());
        test.getPiocheJoueur().clear();
    }

    @Test
    void aGagnéTest() {
        Joueur test = new Joueur("test");
        assertFalse(test.aGagné());
        test.getMainJoueur().clear();
        test.getPiocheJoueur().clear();
        assertTrue(test.aGagné());
    }

    @Test
    void aPerduTest() {
        Joueur test = new Joueur("test");
        assertTrue(test.aPerdu(1));
        for (int nbCarte = 2; nbCarte < test.getMainJoueur().size(); nbCarte++)
            assertFalse(test.aPerdu(nbCarte));
    }

    @Test
    void piocherUneCarteTestMaxCartes() {
        Joueur test = new Joueur("test");
        test.setaJouéSurLaPileDeLAdversaire(true);
        int nbCartePiochée = 0;

        /* Main pleine */
        nbCartePiochée = test.piocherUneCarte();
        assertEquals(nbCartePiochée, 0);

        /* Pioche vide dès le début */
        test.getMainJoueur().clear();
        test.getPiocheJoueur().clear();
        test.piocherUneCarte();
        assertEquals(nbCartePiochée, 0);

        /* Pioche vide au bout d'un coup */
        ajouterCarte(test, 1);
        nbCartePiochée = test.piocherUneCarte();
        assertEquals(nbCartePiochée, 1);
        test.getMainJoueur().clear();

        /* Pioche vide au bout de deux coups */
        ajouterCarte(test, 2);
        nbCartePiochée = test.piocherUneCarte();
        assertEquals(nbCartePiochée, 2);
        test.getMainJoueur().clear();

        /* Pioche vide au bout de trois coups */
        ajouterCarte(test, 3);
        nbCartePiochée = test.piocherUneCarte();
        assertEquals(nbCartePiochée, 3);
        test.getMainJoueur().clear();

        /* Pioche vide au bout de quatre coups */
        ajouterCarte(test, 4);
        nbCartePiochée = test.piocherUneCarte();
        assertEquals(nbCartePiochée, 4);
        test.getMainJoueur().clear();

        /* Pioche vide au bout de cinq coups */
        ajouterCarte(test, 5);
        nbCartePiochée = test.piocherUneCarte();
        assertEquals(nbCartePiochée, 5);
        test.getMainJoueur().clear();

        /* Pioche non vide */
        ajouterCarte(test, 10);
        nbCartePiochée = test.piocherUneCarte();
        assertEquals(nbCartePiochée, 6);
        test.getMainJoueur().clear();

    }

    @Test
    void piocherUneCarteTestDeuxCartes() {
        Joueur test = new Joueur("test");
        int nbCartePiochée = 0;
        /* Pioche vide dès le début */
        test.getMainJoueur().clear();
        test.getPiocheJoueur().clear();
        nbCartePiochée = test.piocherUneCarte();
        assertEquals(nbCartePiochée, 0);

        /* Pioche vide au bout d'un coup */
        ajouterCarte(test, 1);
        nbCartePiochée = test.piocherUneCarte();
        assertEquals(nbCartePiochée, 1);

        /* Pioche non vide */
        test.getMainJoueur().clear();
        test.getPiocheJoueur().add(5);
        test.getPiocheJoueur().add(15);
        test.getPiocheJoueur().add(25);
        test.getPiocheJoueur().add(35);
        nbCartePiochée = test.piocherUneCarte();
        assertEquals(nbCartePiochée, 2);
        nbCartePiochée = test.piocherUneCarte();
        assertEquals(nbCartePiochée, 2);


    }

    @Test
    void jouerUneCarte() {
        Joueur joueur = new Joueur("joueur");
        Joueur adversaire = new Joueur("adversaire");
        joueur.setaJouéSurLaPileDeLAdversaire(true);
        adversaire.setaJouéSurLaPileDeLAdversaire(true);
        int nbCarteJouéeJ1, nbCarteJouéeJ2 = 0;
        /* Jouer 6 cartes successivement sur sa pile ascendante */
        for (int i = 0; i < Joueur.NB_MAX_MAIN; ++i) {
            joueur.getTmpAsc().add(joueur.getMainJoueur().get(i));
            adversaire.getTmpAsc().add(adversaire.getMainJoueur().get(i));
        }
        nbCarteJouéeJ1 = joueur.jouerUneCarte(adversaire);
        nbCarteJouéeJ2 = adversaire.jouerUneCarte(joueur);
        assertEquals(nbCarteJouéeJ1, 6);
        assertEquals(nbCarteJouéeJ2, 6);

        joueur.piocherUneCarte();
        adversaire.piocherUneCarte();

        /* Jouer 6 cartes successivement sur sa pile descendante */
        for (int i = Joueur.NB_MAX_MAIN - 1; i >= 0; --i) {
            joueur.getTmpDesc().add(joueur.getMainJoueur().get(i));
            adversaire.getTmpDesc().add(adversaire.getMainJoueur().get(i));
        }
        nbCarteJouéeJ1 = joueur.jouerUneCarte(adversaire);
        nbCarteJouéeJ2 = adversaire.jouerUneCarte(joueur);
        assertEquals(nbCarteJouéeJ1, 6);
        assertEquals(nbCarteJouéeJ2, 6);

        resetTotal(joueur, adversaire);

        /* Jouer 2 cartes, une sur sa pile ascendante, l'autre sur sa pile descendante */
        for (int i = 0; i < 2; ++i) {
            joueur.getTmpAsc().add(joueur.getMainJoueur().get(i));
            joueur.getTmpDesc().add(joueur.getMainJoueur().get(i + 1));
        }
        nbCarteJouéeJ1 = joueur.jouerUneCarte(adversaire);
        assertEquals(nbCarteJouéeJ1, 2);

        /* Vérifie si par exemple le joueur NORD peut jouer une carte qui est déjà sur une des piles du joueur SUD sur
         * une de ses propres piles, on teste une sur la pile ascendante, et une sur la pile descendante */
        joueur.getMainJoueur().clear();
        joueur.getMainJoueur().add(20);
        joueur.getMainJoueur().add(50);
        adversaire.getBase().setJeuAsc(20);
        adversaire.getBase().setJeuDesc(50);
        joueur.getTmpAsc().add(20);
        joueur.getTmpDesc().add(50);
        nbCarteJouéeJ1 = joueur.jouerUneCarte(adversaire);
        assertEquals(nbCarteJouéeJ1, 2);

        resetTotal(joueur, adversaire);

        /* Jouer 2 cartes sur les piles de l'adversaire est interdit */
        joueur.getMainJoueur().clear();
        joueur.getMainJoueur().add(20);
        joueur.getMainJoueur().add(50);
        adversaire.getBase().setJeuAsc(30);
        adversaire.getBase().setJeuDesc(40);
        adversaire.getTmpAsc().add(20);
        adversaire.getTmpDesc().add(50);
        nbCarteJouéeJ1 = joueur.jouerUneCarte(adversaire);
        assertEquals(nbCarteJouéeJ1, 0);


    }

    @Test
    void vérification() {
        Joueur joueur = new Joueur("joueur");
        Joueur adversaire = new Joueur("adversaire");
        joueur.getMainJoueur().clear();
        joueur.getMainJoueur().add(10);
        joueur.getMainJoueur().add(50);
        joueur.getMainJoueur().add(25);
        String saisie = "10^ 25^ 50v";
        String[] tab = saisie.split(" ");
        int cpt = 0;
        while (cpt < tab.length){
            assertTrue(joueur.vérification(adversaire, tab[cpt]));
            cpt++;
        }
        joueur.getBase().setJeuAsc(10);
        cpt = 0;
        while (cpt < tab.length){
            assertFalse(joueur.vérification(adversaire, tab[cpt]));
            cpt++;
        }

    }

    @Test
    void vérificationV2(){
        Joueur joueur = new Joueur("joueur");
        Joueur adversaire = new Joueur("adversaire");
        joueur.getMainJoueur().clear();
        joueur.getMainJoueur().add(10);
        joueur.getMainJoueur().add(50);
        joueur.getMainJoueur().add(25);
        joueur.getMainJoueur().add(35);
        joueur.getMainJoueur().add(20);
        adversaire.getBase().setJeuDesc(30);
        String saisie = "20^ 10^ 50v 25v 35v";
        String[] tab = saisie.split(" ");
        int cpt = 0;
        boolean vérif = false;
        while (cpt < tab.length){
            vérif = joueur.vérification(adversaire, tab[cpt]);
            if (!vérif)
                break;
            cpt++;
        }
        assertTrue(vérif);
    }

    @Test
    void vérificationV3(){
        Joueur joueur = new Joueur("joueur");
        Joueur adversaire = new Joueur("adversaire");
        joueur.getMainJoueur().clear();
        joueur.getMainJoueur().add(10);
        joueur.getMainJoueur().add(50);
        joueur.getMainJoueur().add(25);
        joueur.getMainJoueur().add(35);
        joueur.getMainJoueur().add(20);
        adversaire.getBase().setJeuDesc(30);
        String saisie = "20^ 20v";
        String[] tab = saisie.split(" ");
        int cpt = 0;
        boolean vérif = false;
        while (cpt < tab.length){
            vérif = joueur.vérification(adversaire, tab[cpt]);
            if (!vérif)
                break;
            cpt++;
        }
        assertFalse(vérif);

    }

    @Test
    void testToString() {
        Joueur joueur = new Joueur("j1");
        Joueur adversaire = new Joueur("adversaire");
        joueur.getMainJoueur().sort(null);
        adversaire.getMainJoueur().sort(null);

        String s1 = getString(joueur);
        String s2 = getString(adversaire);
        assertEquals(joueur.toString(), s1);
        assertEquals(adversaire.toString(), s2);

        joueur.getMainJoueur().clear();
        adversaire.getMainJoueur().clear();
        joueur.setaJouéSurLaPileDeLAdversaire(true);
        adversaire.setaJouéSurLaPileDeLAdversaire(true);
        joueur.piocherUneCarte();
        adversaire.piocherUneCarte();
        joueur.getMainJoueur().sort(null);
        adversaire.getMainJoueur().sort(null);

        String s3 = getString(joueur);
        String s4 = getString(adversaire);

        assertEquals(joueur.toString(), s3);
        assertEquals(adversaire.toString(), s4);

    }

    @Test
    void toStringPlateau() {
        int minValeur = 1;
        int maxValeur = 60;
        Joueur joueur = new Joueur("j1");
        Joueur j2 = new Joueur("j2");
        for (int i = minValeur; i < maxValeur; ++i) {
            joueur.getBase().setJeuAsc(minValeur + i);
            joueur.getBase().setJeuAsc(maxValeur - i);
            assertEquals(joueur.toStringPlateau(), joueur.getNomJoueur() + "  ^[" + String.format("%02d", joueur.getBase().getJeuAsc())
                    + "] v[" + String.format("%02d", joueur.getBase().getJeuDesc()) + "] (m" + joueur.getMainJoueur().size() + "p"
                    + joueur.getPiocheJoueur().size() + ")");
        }
        for (int i = maxValeur; i > minValeur; --i) {
            joueur.getBase().setJeuAsc(maxValeur - i);
            joueur.getBase().setJeuAsc(minValeur + i);
            assertEquals(j2.toStringPlateau(), j2.getNomJoueur() + "  ^[" + String.format("%02d", j2.getBase().getJeuAsc())
                    + "] v[" + String.format("%02d", j2.getBase().getJeuDesc()) + "] (m" + j2.getMainJoueur().size() + "p"
                    + j2.getPiocheJoueur().size() + ")");
        }
    }

    @Test
    void peutJouer() {
        Joueur joueur = new Joueur("joueur");
        Joueur adversaire = new Joueur("adversaire");
        assertTrue(joueur.peutJouer(adversaire));
        assertTrue(adversaire.peutJouer(joueur));
    }

    @Test
    void nePeutPasJouer(){
        Joueur joueur = new Joueur("joueur");
        Joueur adversaire = new Joueur("adversaire");

        adversaire.getMainJoueur().clear();
        adversaire.getMainJoueur().add(29);
        assertFalse(adversaire.peutJouer(joueur));

        joueur.getBase().setJeuAsc(59);
        joueur.getBase().setJeuDesc(3);
        adversaire.getBase().setJeuAsc(47);
        adversaire.getBase().setJeuDesc(11);
        joueur.getTmpAsc().add(joueur.getBase().getJeuAsc());
        joueur.getTmpDesc().add(joueur.getBase().getJeuDesc());
        adversaire.getTmpAsc().add(adversaire.getBase().getJeuAsc());
        adversaire.getTmpDesc().add(adversaire.getBase().getJeuDesc());

        adversaire.getMainJoueur().clear();
        adversaire.getMainJoueur().add(14);
        adversaire.getMainJoueur().add(17);
        adversaire.getMainJoueur().add(19);
        adversaire.getMainJoueur().add(20);
        adversaire.getMainJoueur().add(26);
        adversaire.getMainJoueur().add(42);
        assertFalse(adversaire.peutJouer(joueur));


    }

    @Test
    void reset() {
        Joueur j1 = new Joueur("j1");
        Joueur j2 = new Joueur("j2");
        remove(j1);
        remove(j2);
        for (int i = 0; i < j1.getMainJoueur().size(); ++i){
            j1.getTmpAsc().add(j1.getMainJoueur().get(i));
            j1.getTmpDesc().add(j1.getMainJoueur().get(i));
        }
        for (int i = 0; i < j2.getMainJoueur().size(); ++i){
            j2.getTmpAsc().add(j2.getMainJoueur().get(i));
            j2.getTmpDesc().add(j2.getMainJoueur().get(i));
        }
        assertEquals(j1.getMainJoueur(), j1.getTmpAsc());
        assertEquals(j1.getMainJoueur(), j1.getTmpDesc());
        assertEquals(j2.getMainJoueur(), j2.getTmpAsc());
        assertEquals(j2.getMainJoueur(), j2.getTmpDesc());
        j1.reset(j2);
        assertEquals(j1.getTmpAsc().get(0), j1.getBase().getJeuAsc());
        assertEquals(j1.getTmpDesc().get(0), j1.getBase().getJeuDesc());
        assertEquals(j2.getTmpAsc().get(0), j2.getBase().getJeuAsc());
        assertEquals(j2.getTmpDesc().get(0), j2.getBase().getJeuDesc());
    }
}