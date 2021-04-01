package jeu;

import java.util.*;

public class Joueur {
    public static final int NB_MAX_MAIN = 6;
    public static final int MIN_VALEUR_CARTE = 2;
    public static final int MAX_VALEUR_CARTE = 59;
    public static final int MAX_PIOCHE = 58;
    /** le nom du joueur */
    private String nomJoueur;
    /** le Plateau du joueur */
    private Plateau base;
    /** le tableau, correspondant à la pioche du joueur */
    private ArrayList<Integer> piocheJoueur;
    /** le tableau, correspond à la main du joueur */
    private ArrayList<Integer> mainJoueur;
    /** le boolean permettant de savoir si le joueur a joué sur une des piles de l'adversaire */
    private boolean aJouéSurLaPileDeLAdversaire;
    /** le tableau temporaire ascendant du joueur */
    private ArrayList<Integer> tmpAsc;
    /** le tableau temporaire descendant du joueur */
    private ArrayList<Integer> tmpDesc;

    public Joueur(String nomJoueur) {
        base = new Plateau();
        this.nomJoueur = nomJoueur;
        this.piocheJoueur = new ArrayList<>();
        for (int i = MIN_VALEUR_CARTE; i < MAX_VALEUR_CARTE + 1; ++i)
            this.piocheJoueur.add(i);
        this.mainJoueur = new ArrayList<>();
        this.mélanger();
        this.aJouéSurLaPileDeLAdversaire = false;
        this.piocherUneCarte();
        this.tmpAsc = new ArrayList<>();
        this.tmpAsc.add(this.base.getJeuAsc());
        this.tmpDesc = new ArrayList<>();
        this.tmpDesc.add(this.base.getJeuDesc());
    }

    /* --------- Méthode privée --------- */

    /** Retourne la valeur présente sur le sommet de la pile ascendante du joueur.
     * @return un int, la valeur de la carte qui a été jouée en dernière.
     */
    private int sommetTmpAsc(){
        return this.tmpAsc.get(this.tmpAsc.size() - 1);
    }

    /** Retourne la valeur présente sur le sommet de la pile descendante du joueur.
     * @return un int, la valeur de la carte qui a été jouée en dernière.
     */
    private int sommetTmpDesc(){
        return this.tmpDesc.get(this.tmpDesc.size() - 1);
    }

    /** Mélange la pioche du joueur.
     */
    private void mélanger() {
        Collections.shuffle(this.piocheJoueur);
    }

    /** Tri la main du joueur.
     */
    private void tri() {
        this.mainJoueur.sort(null);
    }

    /** Permet de savoir si la pioche du joueur est vide ou non en retournant un booléen en conséquence.
     * @return Si la pioche du joueur est vide, alors la fonction retourne vrai, sinon retourne faux.
     */
    private boolean deckVide() {
        return this.piocheJoueur.size() == 0;
    }

    /** Permet de savoir si un joueur, lors de sa phase de jeu, joue une carte de sa main deux fois. Cela permet d'identifier
     * une erreur de saisie. Si oui, alors la fonction retourne faux, sinon elle retourne vraie.
     * @param sommeChiffre le String correspond à la valeur de la carte saisie par le joueur.
     * @param thisTmpAsc l'ArrayList temporaire ascendant du joueur donné.
     * @param thisTmpDesc l'ArrayList temporaire descendant du joueur donné.
     * @param advTmpAsc l'ArrayList temporaire ascendant de l'adversaire du joueur donné.
     * @param advTmpDesc l'ArrayList temporaire descendant de l'adversaire du joueur donné.
     * @return Retourne vraie si le joueur a saisie une carte au minimum deux fois, sinon retourne faux.
     */
    private boolean mêmeCarte(String sommeChiffre, ArrayList<Integer> thisTmpAsc, ArrayList<Integer> thisTmpDesc,
                              ArrayList<Integer> advTmpAsc, ArrayList<Integer> advTmpDesc) {
        boolean mêmeCarte = false;
        mêmeCarte = isMêmeCarte(sommeChiffre, thisTmpAsc, thisTmpDesc, mêmeCarte);
        mêmeCarte = isMêmeCarte(sommeChiffre, advTmpAsc, advTmpDesc, mêmeCarte);
        return mêmeCarte;
    }

    /** Vérifie et retourne un booléen en conséquence si un joueur saisit une carte afin de la jouer au minimum deux fois.
     * @param sommeChiffre le String correspond à la valeur de la carte saisie par le joueur.
     * @param TmpAsc l'ArrayList temporaire ascendant du joueur donné.
     * @param TmpDesc l'ArrayList temporaire descendant du joueur donné.
     * @param mêmeCarte le booléen permettant de savoir si le joueur donné veut jouer une carte plusieurs fois.
     * @return Retourne vrai si le joueur a saisie une carte au minimum deux fois, sinon retourne faux.
     */
    private boolean isMêmeCarte(String sommeChiffre, ArrayList<Integer> TmpAsc, ArrayList<Integer> TmpDesc, boolean mêmeCarte) {
        for (int i = 1; i < TmpAsc.size(); ++i)
            if (sommeChiffre.equals(String.format("%02d", TmpAsc.get(i)))) {
                mêmeCarte = true;
                break;
            }
        for (int i = 1; i < TmpDesc.size(); ++i)
            if (sommeChiffre.equals(String.format("%02d", TmpDesc.get(i)))) {
                mêmeCarte = true;
                break;
            }
        return mêmeCarte;
    }

    /** Pour une carte donnée, vérifie au préalable qu'elle peut être jouée, puis vérifie pour la carte suivante, si
     * celle-ci pourrait être jouée successivement. Retourne un booléen en conséquence.
     * @param carte le int correspondant à la valeur de la carte que l'on veut vérifier.
     * @param adversaire le Joueur adverse.
     * @return un booléen. Vrai si le joueur peut jouer au moins de cartes, sinon retourne faux.
     */
    private boolean isPeutJouer(int carte, Joueur adversaire) {
        if (vérificationRèglesDePose(carte, adversaire)) {
            for (Integer valeurCarte : this.mainJoueur) {
                if (carte != valeurCarte)
                    if (vérificationRèglesDePose(valeurCarte, adversaire))
                        return true;
            }
            return false;
        }
        return false;
    }

    /** Vérifie pour une carte donnée si elle peut théoriquement être jouée selon les règles de pose, et retourne un
     * booléen en conséquence.
     * @param carte la valeur de la carte sélectionnée.
     * @param adv le Joueur adverse.
     * @return un booléen. Vrai si la carte peut être jouée selon les règles de poses, sinon retourne faux.
     */
    private boolean vérificationRèglesDePose(int carte, Joueur adv) {
        if (carte > sommetTmpAsc() || carte == sommetTmpAsc() - 10){
            this.tmpAsc.add(carte);
            return true;

        } else if (carte < sommetTmpDesc() || carte == sommetTmpDesc() + 10) {
            this.tmpDesc.add(carte);
            return true;
        }

        else if (carte < adv.sommetTmpAsc() && !this.aJouéSurLaPileDeLAdversaire) {
            adv.tmpAsc.add(carte);
            this.aJouéSurLaPileDeLAdversaire = true;
            return true;

        } else if (carte > adv.sommetTmpDesc() && !this.aJouéSurLaPileDeLAdversaire) {
            adv.tmpDesc.add(carte);
            this.aJouéSurLaPileDeLAdversaire = true;
            return true;
        }
        return false;
    }

    /* --------- Méthode public --------- */

    /* ---GETTERS--- */

    public ArrayList<Integer> getMainJoueur() {
        return mainJoueur;
    }

    public ArrayList<Integer> getPiocheJoueur() {
        return piocheJoueur;
    }

    public ArrayList<Integer> getTmpAsc() {
        return tmpAsc;
    }

    public ArrayList<Integer> getTmpDesc() {
        return tmpDesc;
    }

    public Plateau getBase() {
        return base;
    }

    /** Pour un joueur donné, donne son nom en format String.
     * @return retourne le nom du joueur.
     */
    public String getNomJoueur() {
        return this.nomJoueur;
    }

    /* ---SETTER--- */

    /** Permet de modifier la valeur du boolean permettant de savoir si un joueur a joué sur une des piles
     * de l'adversaire.
     * @param oui la valeur booléenne.
     */
    public void setaJouéSurLaPileDeLAdversaire(boolean oui) {
        this.aJouéSurLaPileDeLAdversaire = oui;
    }

    /* ---Méthodes pour savoir si une partie est finie--- */

    /** Permet de savoir si pour un joueur donné, ce joueur n'a plus qu'une seule carte dans sa main et retourne un
     * boolean en fonction.
     * @return retourne faux si le joueur possède deux cartes ou plus dans sa main, sinon la fonction retourne vraie.
     */
    public boolean aUneCarte(){
        return this.mainJoueur.size() == 1 && this.deckVide();
    }

    /** Permet de savoir si pour un joueur donné, ce joueur a gagné la partie en retournant un boolean en fonction.
     * @return retourne un boolean. Retourne vrai si le joueur ne possède plus aucune carte dans sa main et dans sa pioche.
     * Sinon la fonction retourne faux.
     */
    public boolean aGagné(){
        return this.mainJoueur.size() == 0 && this.deckVide();
    }

    /** Permet de savoir si pour un joueur donné, ce joueur a perdu la partie en retournant un boolean en fonction.
     * @param valeurCarte le nombre de carte joué par le joueur en un seul tour de jeu.
     * @return retourne un boolean. Retourne vrai si le joueur a joué au moins deux cartes. Sinon la fonction retourne faux.
     */
    public boolean aPerdu(int valeurCarte){
        return valeurCarte < 2;
    }

    /** Permet une vérification, avant même que le joueur ne saisisse des données, de savoir si le joueur actuel peut
     * ou non jouer au moins deux cartes. Pour cela, on étudie la main du joueur actuel, son plateau ainsi que le plateau
     * de son adversaire et l'on regarde les possibilités. Retourne un booléen en conséquence.
     * @param adversaire le joueur adverse.
     * @return retourne un boolean. Vrai si le joueur peut jouer au moins deux cartes, sinon faux s'il ne peut jouer aucune carte
     * ou une et une seule.
     */
    public boolean peutJouer(Joueur adversaire) {
        for (Integer valeurCarte : this.mainJoueur) {
            if (isPeutJouer(valeurCarte, adversaire)){
                this.reset(adversaire);
                return true;
            }
            this.reset(adversaire);
        }
        return false;
    }


    /* ---Méthodes qui permettent au joueur de jouer un tour de jeu--- */

    /** Permet pour un joueur donné de piocher des cartes. Il y a deux possibilités. Si le joueur en question a joué
     * sur la pile de l'adversaire, alors il piochera un nombre de cartes n lui permettant d'avoir 6 cartes en main. Sinon
     * il piochera deux et seulement deux cartes.
     * @return retourne le nombre de carte pioché.
     */
    public int piocherUneCarte() {
        this.tri();
        /* Permet d'éviter de piocher des cartes si le joueur a déjà 6 cartes en main, ou s'il n'a plus aucune carte dans
        sa main. */
        if (this.mainJoueur.size() == 6 || this.deckVide())
            return 0;
        int cpt = 0;

        /* Piocher un nombre de carte n jusqu'à que le joueur a 6 cartes dans sa main
        si le joueur a joué sur une des piles de l'adversaire, à moins que la pioche soit vide.*/
        if (this.aJouéSurLaPileDeLAdversaire || this.piocheJoueur.size() == MAX_PIOCHE)
            for (int i = this.mainJoueur.size(); i < NB_MAX_MAIN; ++i) {
                if (!this.deckVide()) {
                    this.mainJoueur.add(piocheJoueur.remove(0));
                    cpt++;
                }
            }
            /* Piocher deux cartes et seulement deux cartes, à moins que la pioche soit vide. */
        else {
            do {
                this.mainJoueur.add(piocheJoueur.remove(0));
                cpt++;
            } while (!deckVide() && cpt < 2);
        }
        return cpt;
    }

    /** Permet pour un joueur donné de jouer les cartes saisies ultérieurement par l'utilisateur.
     * @param adversaire le joueur adverse.
     * @return retourne le nombre de carte joué.
     */
    public int jouerUneCarte(Joueur adversaire) {
        int before = this.mainJoueur.size();

        /* Jouer sur sa pile ascendante */
        for (int valeurTmp = 1; valeurTmp < tmpAsc.size(); valeurTmp++)
            for (int i = 0; i < this.mainJoueur.size(); ++i)
                if (this.mainJoueur.get(i).equals(this.tmpAsc.get(valeurTmp))) {
                    this.base.setJeuAsc(this.tmpAsc.get(valeurTmp));
                    this.mainJoueur.remove(i);
                }

        /* Jouer sur sa pile descendante */
        for (int valeurTmp = 1; valeurTmp < tmpDesc.size(); valeurTmp++)
            for (int i = 0; i < this.mainJoueur.size(); ++i)
                if (this.mainJoueur.get(i).equals(this.tmpDesc.get(valeurTmp))) {
                    this.base.setJeuDesc(this.tmpDesc.get(valeurTmp));
                    this.mainJoueur.remove(i);
                }

        /* Jouer sur la pile ascendante de l'adversaire */
        for (int valeurTmp = 1; valeurTmp < adversaire.tmpAsc.size(); valeurTmp++)
            for (int i = 0; i < this.mainJoueur.size(); ++i)
                if (this.mainJoueur.get(i).equals(adversaire.tmpAsc.get(valeurTmp))) {
                    adversaire.base.setJeuAsc(adversaire.tmpAsc.get(valeurTmp));
                    this.mainJoueur.remove(i);
                    this.aJouéSurLaPileDeLAdversaire = true;
                }

        /* Jouer sur la pile descendante de l'adversaire */
        for (int valeurTmp = 1; valeurTmp < adversaire.tmpDesc.size(); valeurTmp++)
            for (int i = 0; i < this.mainJoueur.size(); ++i)
                if (this.mainJoueur.get(i).equals(adversaire.tmpDesc.get(valeurTmp))) {
                    adversaire.base.setJeuDesc(adversaire.tmpDesc.get(valeurTmp));
                    this.mainJoueur.remove(i);
                    this.aJouéSurLaPileDeLAdversaire = true;
                }

        int after = this.mainJoueur.size();
        return before - after;
    }

    /** Pour chaque donnée saisie par l'utilisateur ultérieurement, c'est-à-dire pour chaque chaîne de caractère,
     * vérifie si la saisie est conforme et donc si la carte pourrait être jouée sur une des piles, soit les siennes,
     * soit celles de l'adversaire.
     * @param adv le joueur adverse.
     * @param carte la chaîne de caractère que l'on veut vérifier.
     * @return retourne un boolean. Retourne vrai si la carte est conforme à la saisie, si elle est dans la main du
     * joueur et si elle peut être jouée conformément aux règles de pose. Sinon retourne faux.
     */
    public boolean vérification(Joueur adv, String carte) {
        /* Vérifie la longueur de la chaîne de caractère saisie. Si sa longueur est inférieur a 3 caractères ou
         * supérieur à 5 caractères alors la fonction retourne faux, il n'y a pas besoin de vérifier le reste. */
        if (carte.length() < 3 || carte.length() > 5)
            return false;
        char premierChiffre = carte.charAt(0);
        char dernierChiffre = carte.charAt(1);
        String sommeChiffre = "" + premierChiffre + dernierChiffre;

        /* Vérifier si l'utilisateur, c'est-à-dire le joueur en question, a déjà joué cette carte, si oui alors
         * il y a une faute de saisie, et le joueur doit rejouer. */
        if (this.mêmeCarte(sommeChiffre, this.tmpAsc, this.tmpDesc, adv.tmpDesc, adv.tmpDesc))
            return false;
        /* Pour une chaîne de caractère égal à 3, c'est-à-dire que le joueur veut jouer la carte saisie sur une de
         * ses propres piles */
        if (carte.length() == 3) {
            /* On vérifie le dernier caractère, ici le joueur jouera sur la pile ascendante. */
            if (carte.charAt(2) == '^') {
                /* On a vérifié que le dernier caractère est '^', alors on va tout d'abord vérifier que les deux premiers
                 * caractères correspondent à une carte de la main du joueur actuelle. Puis on range dans un tableau
                 * temporaire la valeur de la carte jouée si les règles de pose sont vérifier.
                 */
                for (Integer integer : this.mainJoueur) {
                    if (sommeChiffre.equals(String.format("%02d", integer)))
                        if (integer > this.sommetTmpAsc() || integer == this.sommetTmpAsc() - 10) {
                            this.tmpAsc.add(integer);
                            return true;
                        }
                }
                /* On vérifie le dernier caractère, ici le joueur jouera sur la pile descendante. */
            } else if (carte.charAt(2) == 'v') {
                /* On a vérifié que le dernier caractère est 'v', alors on va tout d'abord vérifier que les deux premiers
                 * caractères correspondent à une carte de la main du joueur actuelle. Puis on range dans un tableau
                 * temporaire la valeur de la carte jouée si les règles de pose sont vérifier.
                 */
                for (Integer integer : this.mainJoueur) {
                    if (sommeChiffre.equals(String.format("%02d", integer)))
                        if (integer < this.sommetTmpDesc() || integer == this.sommetTmpDesc() + 10) {
                            this.tmpDesc.add(integer);
                            return true;
                        }
                }
            }
            /* Pour une chaîne de caractère égal à 4, c'est-à-dire que le joueur veut jouer la carte saisie sur une des
             * piles de l'adversaire.*/
        } else if (carte.length() == 4) {
            /* On vérifie l'avant dernier caractère, ici le joueur jouera sur la pile ascendante. */
            if (carte.charAt(2) == '^') {
                /* On vérifie le dernier caractère et aussi si le joueur n'a pas déjà joué sur une des piles de l'adversaire. */
                if (carte.charAt(3) == '\'' && !this.aJouéSurLaPileDeLAdversaire) {
                    /* On a vérifié que l'avant dernier caractère est '^', et que le dernier est '\'' alors on va
                     * tout d'abord vérifier que les deux premiers caractères correspondent à une carte de la main du
                     * joueur actuelle. Puis on range dans un tableau temporaire la valeur de la carte jouée si
                     * les règles de pose sont vérifier.
                     */
                    for (Integer integer : this.mainJoueur) {
                        if (sommeChiffre.equals(String.format("%02d", integer)))
                            if (integer < adv.sommetTmpAsc()) {
                                adv.tmpAsc.add(integer);
                                this.aJouéSurLaPileDeLAdversaire = true;
                                return true;
                            }
                    }
                }
                /* On vérifie l'avant dernier caractère, ici le joueur jouera sur la pile ascendante. */
            } else if (carte.charAt(2) == 'v') {
                /* On vérifie le dernier caractère et aussi si le joueur n'a pas déjà joué sur une des piles de l'adversaire. */
                if (carte.charAt(3) == '\'' && !this.aJouéSurLaPileDeLAdversaire) {
                    /* On a vérifié que l'avant dernier caractère est 'v', et que le dernier est '\'' alors on va
                     * tout d'abord vérifier que les deux premiers caractères correspondent à une carte de la main du
                     * joueur actuelle. Puis on range dans un tableau temporaire la valeur de la carte jouée si
                     * les règles de pose sont vérifier.
                     */
                    for (Integer integer : this.mainJoueur) {
                        if (sommeChiffre.equals(String.format("%02d", integer)))
                            if (integer > adv.sommetTmpDesc()) {
                                adv.tmpDesc.add(integer);
                                this.aJouéSurLaPileDeLAdversaire = true;
                                return true;
                            }
                    }
                }
            }
        }
        return false;
    }

    /* ---Méthodes toString pour afficher le jeu--- */

    /** Permet de retourner pour un joueur donné, son nom ainsi que toutes les cartes dans sa main.
     * @return retourne la chaîne de caractère correspondant au nom du joueur actuel ainsi que les cartes qu'il a dans sa main.
     */
    public String toString(){
        this.tri();
        StringBuilder s = new StringBuilder("cartes " + this.nomJoueur + " { ");
        for (int ValueCard : this.mainJoueur) {
            s.append(String.format("%02d", ValueCard) + " ");
        }
        s.append("}");
        return s.toString();
    }

    /** Permet de retourner pour un joueur donné son plateau, c'est-à-dire que l'on retourne le nom du joueur actuel
     * ainsi que la valeur de la carte sur sa pile ascendante et la valeur de la carte sur sa pile descendante.
     * @return retourne la chaîne de caractère correspondant au nom du joueur actuel ainsi que son plateau, c'est-à-dire
     * la carte sur sa pile ascendante et la carte sur sa pile descendante.
     */
    public String toStringPlateau() {
        return new StringBuilder().append(this.nomJoueur.equals("NORD") ? this.nomJoueur + " ^[" + String.format("%02d", base.getJeuAsc()) + "]" + " v["
                + String.format("%02d", base.getJeuDesc()) + "] " + "(m" + this.mainJoueur.size() + "p" + this.piocheJoueur.size() + ")" :
                this.nomJoueur + "  ^[" + String.format("%02d",  base.getJeuAsc()) + "]" + " v["
                        + String.format("%02d", base.getJeuDesc()) + "] " + "(m" + this.mainJoueur.size() + "p"
                        + this.piocheJoueur.size() + ")").toString();
    }

    /* ---Méthode pour réinitialiser avant de réellement jouer--- */

    /** Permet de réinitialiser les tableaux temporaires en les vidant complètement et en ajoutant
     * uniquement la valeur des cartes sur les différents plateaux.
     * @param adversaire le joueur adverse.
     */
    public void reset(Joueur adversaire){

        /* Suppression de tous les éléments des tableaux temporaires */
        this.tmpAsc.clear();
        this.tmpDesc.clear();
        adversaire.tmpAsc.clear();
        adversaire.tmpDesc.clear();

        /* Réinitialisation des tableaux temporaires avec le vrai plateau */
        this.tmpAsc.add(this.base.getJeuAsc());
        this.tmpDesc.add(this.base.getJeuDesc());
        adversaire.tmpAsc.add(adversaire.base.getJeuAsc());
        adversaire.tmpDesc.add(adversaire.base.getJeuDesc());

        this.aJouéSurLaPileDeLAdversaire = false;
    }

}
