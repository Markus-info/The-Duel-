package jeu;

public class Plateau {
    /** la valeur de la carte sur la pile ascendante du joueur actuel */
    private int jeuAsc;
    /** la valeur de la carte sur la pile descendante du joueur actuel */
    private int jeuDesc;

    public Plateau() {
        this.jeuAsc = 1;
        this.jeuDesc = 60;
    }

    /* ---GETTERS--- */

    /** Permet de retourner la valeur de la carte sur la pile ascendante du joueur actuel.
     * @return retourne la valeur de la carte sur la pile ascendante du joueur actuel.
     */
    public int getJeuAsc() {
        return this.jeuAsc;
    }

    /** Permet de retourner la valeur de la carte sur la pile descendante du joueur actuel.
     * @return retourne la valeur de la carte sur la pile descendante du joueur actuel.
     */
    public int getJeuDesc() {
        return this.jeuDesc;
    }

    /* ---SETTERS--- */

    /** Permet de modifier la valeur de la carte sur la pile ascendante du joueur actuel.
     * @param valeur le nombre avec lequel on souhaite modifier la valeur de la pile ascendante du joueur actuel.
     */
    public void setJeuAsc(int valeur){
        this.jeuAsc = valeur;
    }

    /** Permet de modifier la valeur de la carte sur la pile descendante du joueur actuel.
     * @param valeur le nombre avec lequel on souhaite modifier la valeur de la pile descendante du joueur actuel.
     */
    public void setJeuDesc(int valeur){
        this.jeuDesc = valeur;
    }

}
