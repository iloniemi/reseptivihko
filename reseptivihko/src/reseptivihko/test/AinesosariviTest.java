package reseptivihko.test;

import reseptivihko.*;
import static org.junit.Assert.*;
import org.junit.*;

public class AinesosariviTest {
    //TODO konstruktoritestit
    //TODO määrätestit
    //TODO mittayksikkötestit

    /**
     * Testaa Reseptin id:n saamismetodia.
     */
    @Test
    public void testGetReseptiId() {
        Ainesosarivi rivi = new Ainesosarivi(1, 1, 200.0, "g");
        assertEquals(1, rivi.getReseptiId());
        Ainesosarivi rivi2 = new Ainesosarivi(33, 1, 200.0, "g");
        assertEquals(33, rivi2.getReseptiId());
    }
    
    /**
     * Testaa Reseptin id:n asettamismetodia.
     */
    @Test
    public void testSetReseptiId() {
        Ainesosarivi rivi = new Ainesosarivi(1, 1, 200.0, "g");
        rivi.setReseptiId(23);
        assertEquals(23, rivi.getReseptiId());
        Ainesosarivi rivi2 = new Ainesosarivi(33, 1, 200.0, "g");
        rivi2.setReseptiId(2);
        assertEquals(2, rivi2.getReseptiId());
    }
    
    /**
     * Testaa Ainesosan id:n saamismetodia.
     */
    @Test
    public void testGetAinesosaId() {
        Ainesosarivi rivi = new Ainesosarivi(1, 2, 200.0, "g");
        assertEquals(2, rivi.getAinesosaId());
        Ainesosarivi rivi2 = new Ainesosarivi(1, 5, 200.0, "g");
        assertEquals(5, rivi2.getAinesosaId());
    }
    
    /**
     * Testaa Ainesosan id:n asettamismetodia.
     */
    @Test
    public void testSetAinesosaId() {
        Ainesosarivi rivi = new Ainesosarivi(1, 1, 200.0, "g");
        rivi.setAinesosaId(23);
        assertEquals(23, rivi.getAinesosaId());
        Ainesosarivi rivi2 = new Ainesosarivi(33, 3, 200.0, "g");
        rivi2.setAinesosaId(6);
        assertEquals(6, rivi2.getAinesosaId());
    }

}
