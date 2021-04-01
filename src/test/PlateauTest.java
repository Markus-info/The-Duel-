package test;

import jeu.Plateau;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PlateauTest {


    @Test
    void setJeuAsc() {
        Plateau plateau = new Plateau();
        assertEquals(plateau.getJeuAsc(), 1);
        plateau.setJeuAsc(50);
        assertEquals(plateau.getJeuAsc(), 50);
        plateau.setJeuAsc(31);
        assertEquals(plateau.getJeuAsc(), 31);
    }

    @Test
    void setJeuDesc() {
        Plateau plateau = new Plateau();
        assertEquals(plateau.getJeuDesc(), 60);
        plateau.setJeuDesc(30);
        assertEquals(plateau.getJeuDesc(), 30);
        plateau.setJeuDesc(21);
        assertEquals(plateau.getJeuDesc(), 21);
    }
}