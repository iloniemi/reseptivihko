package reseptivihko.test;

import reseptivihko.*;
import static org.junit.Assert.*;
import org.junit.*;

/** Testataan Rivilista -luokan toimintaa.
 * @author Juho
 * @version 1 Mar 2020
 *
 */
public  class RivilistaTest {
    private Rivilista rivilista;

    /**
     * Alustetaan rivilista
     */
    @Before
    public void aluksi() {
        rivilista = new Rivilista();
        rivilista.lisaa(new Ainesosarivi(1, 1, 200.0, "g"));
        rivilista.lisaa(new Ainesosarivi(1, 2, 200.0, "g"));
        rivilista.lisaa(new Ainesosarivi(1, 3, 4, "kpl"));
        rivilista.lisaa(new Ainesosarivi(1, 4, 2, "dl"));
        rivilista.lisaa(new Ainesosarivi(1, 5, 2.5, "dl"));
        rivilista.lisaa(new Ainesosarivi(1, 6, 1, "tl"));
        rivilista.lisaa(new Ainesosarivi(2, 1, 200, "g"));
        rivilista.lisaa(new Ainesosarivi(2, 1, 200, "g"));
        rivilista.lisaa(new Ainesosarivi(2, 7, 100, "g"));
        rivilista.lisaa(new Ainesosarivi(2, 2, 10, "g"));
        rivilista.lisaa(new Ainesosarivi(2, 8, 10, "g"));
    }
    
    /**
     * Nollataan rivilista
     */
    @After
    public void jalkeen() {
        rivilista = null;
    }
    
    /**
     * Testaa Reseptin rivien hakemista Reseptin id:n avulla.
     */
    @Test
    public void testHaeReseptinRivit() {
        assertEquals("Rivejä ei tullut yksi lisää.", 6, rivilista.haeReseptinRivit(1).size());
        assertEquals("Rivejä ei tullut yksi lisää.", 5, rivilista.haeReseptinRivit(2).size());
    }
    
    /**
     * Testaa lisaa -metodia
     */
    @Test
    public void testLisaa() {
        Ainesosarivi rivi = new Ainesosarivi(1, 6, 1, "tl");
        rivilista.lisaa(rivi);
        assertEquals("Rivejä ei tullut yksi lisää.", 7, rivilista.haeReseptinRivit(1).size());
        assertTrue("Lisätty rivi puuttuu.", rivilista.haeReseptinRivit(1).contains(rivi));
    }
    
    /**
     * Testaa reseptin rivien poistamista
     */
    @Test
    public void testPoistaReseptinRivit() {
        rivilista.poistaReseptinRivit(1);
        assertEquals("Rivejä ei poistettu.", 0, rivilista.haeReseptinRivit(1).size());
        assertEquals("Väärän reseptin rivejä poistettiin.", 5, rivilista.haeReseptinRivit(2).size());
        rivilista.poistaReseptinRivit(2);
        assertEquals("Rivejä ei poistettu.", 0, rivilista.haeReseptinRivit(2).size());
    }
    
    /**
     * Testaa reseptien id:n hakemista ainesosan id:n avulla.
     */
    @Test
    public void testReseptitAinesosalla() {
        int[] reseptit = rivilista.reseptitAinesosalla(1);
        assertEquals("Väärä määrä reseptejä", 2, reseptit.length);
        rivilista.lisaa(new Ainesosarivi(2, 1, 20, "g"));
        rivilista.lisaa(new Ainesosarivi(2, 1, 10, "g"));
        reseptit = rivilista.reseptitAinesosalla(1);
        int reseptia2 = 0; for (int id: reseptit) if (id == 2) reseptia2++;
        assertEquals("Reseptin id:n pitäisi esiintyä vain kerran", 1, reseptia2);
    }

}
