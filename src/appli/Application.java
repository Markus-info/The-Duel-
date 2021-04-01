package appli;

import jeu.Joueur;

import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        Joueur NORD = new Joueur("NORD");
        Joueur SUD = new Joueur("SUD");
        boolean tour = true;
        int carteJouéeNORD, carteJouéeSUD;
        boolean loseNORD = false;
        boolean loseSUD = false;
        do {
            /* C'est le tour du joueur NORD */
            if (tour) {
                System.out.print(NORD.toStringPlateau() + "\n" + SUD.toStringPlateau() + "\n" + NORD + "\n");
                /* On vérifie si le joueur NORD a au moins deux cartes dans sa main, sinon il a perdu */
                if (NORD.aUneCarte()) {
                    loseNORD = true;
                    break;
                }
                /* On vérifie si le joueur NORD peut au moins jouer deux cartes, sinon il a perdu */
                if(!NORD.peutJouer(SUD)){
                    loseNORD = true;
                    break;
                }
                /* Le joueur NORD saisie, joue, et pioche des cartes */
                carteJouéeNORD = déroulement(NORD, SUD);

                /* On vérifie si le joueur NORD a joué au moins deux cartes, sinon il a perdu */
                if (NORD.aPerdu(carteJouéeNORD)) {
                    loseNORD = true;
                    break;
                }
                tour = false;
            }
            /* C'est le tour du joueur SUD */
            else {
                System.out.print(NORD.toStringPlateau() + "\n" + SUD.toStringPlateau() + "\n" + SUD + "\n");
                /* On vérifie si le joueur SUD a au moins deux cartes dans sa main, sinon il a perdu */
                if (SUD.aUneCarte()) {
                    loseSUD = true;
                    break;
                }
                /* On vérifie si le joueur SUD peut au moins jouer deux cartes, sinon il a perdu */
                if(!SUD.peutJouer(NORD)){
                    loseSUD = true;
                    break;
                }
                /* Le joueur SUD saisie, joue, et pioche des cartes */
                carteJouéeSUD = déroulement(SUD, NORD);

                /* On vérifie si le joueur SUD a joué au moins deux cartes, sinon il a perdu */
                if (SUD.aPerdu(carteJouéeSUD)) {
                    loseSUD = true;
                    break;
                }
                tour = true;
            }
        } while (!NORD.aGagné() && !SUD.aGagné());

        /* Message de fin si le joueur NORD a gagné */
        if (loseSUD) {
            System.out.println("partie finie, " + NORD.getNomJoueur() + " a gagné");
        }
        else if(NORD.aGagné()){
            System.out.print(NORD.toStringPlateau() + "\n" + SUD.toStringPlateau() + "\n" + SUD + "\n");
            System.out.println("partie finie, " + NORD.getNomJoueur() + " a gagné");
        }

        /* Message de fin si le joueur SUD a gagné */
        else {
            if (loseNORD) {
                System.out.println("partie finie, " + SUD.getNomJoueur() + " a gagné");
            }
            else if(SUD.aGagné()){
                System.out.print(NORD.toStringPlateau() + "\n" + SUD.toStringPlateau() + "\n" + NORD + "\n");
                System.out.println("partie finie, " + SUD.getNomJoueur() + " a gagné");
            }
        }
    }

    /** Le joueur actuel saisie des données, joue et pioche des cartes et retourne le nombre de carte joué par le joueur
     * actuel.
     * @param joueur le joueur actuel.
     * @param adversaire le joueur adverse.
     * @return le nombre de carte joué par le joueur atuel.
     */
    public static int déroulement(Joueur joueur, Joueur adversaire){
        boolean finSaisie = false;
        System.out.print("> ");
        /* Vérification de la saisie du joueur */
        do {
            joueur.setaJouéSurLaPileDeLAdversaire(false);
            boolean erreur = false;
            Scanner scanner = new Scanner(System.in);
            String ligne = scanner.nextLine();
            if (ligne.isEmpty())
                erreur = true;
            else {
                Scanner saisie = new Scanner(ligne);
                while (saisie.hasNext()) {
                    String carte = saisie.next();
                    if (!joueur.vérification(adversaire, carte))
                        erreur = true;
                    if (erreur)
                        break;
                }
                saisie.close();
            }
            if (!erreur)
                finSaisie = true;
            else {
                System.out.print("#> ");
                joueur.reset(adversaire);
            }
        } while (!finSaisie);

        /* Le joueur joue les cartes saisies et pioche en conséquence. */
        int carteJouée = joueur.jouerUneCarte(adversaire);
        int cartePiochée = joueur.piocherUneCarte();
        if (carteJouée > 1)
            System.out.println(carteJouée + " cartes posées, " + cartePiochée + " cartes piochées");
        return carteJouée;
    }
}
