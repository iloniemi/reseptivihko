package reseptivihko.test;

import reseptivihko.*;
import static org.junit.Assert.*;
import org.junit.*;

/** Testaa Ainesosarivi -luokkaa
 * @author Juho
 * @version 1 Mar 2020
 *
 */
public class AinesosariviTest {
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
    
    /**
     * Testaa määrän saamismetodia.
     */
    @Test
    public void testGetMaara() {
        Ainesosarivi rivi = new Ainesosarivi(1, 2, 200.0, "g");
        assertEquals(200.0, rivi.getMaara(), 0.000001);
        Ainesosarivi rivi2 = new Ainesosarivi(1, 5, 24.5, "g");
        assertEquals(24.5, rivi2.getMaara(), 0.000001);
    }
    
    /**
     * Testaa määrän asettamismetodia.
     */
    @Test
    public void testSetMaara() {
        Ainesosarivi rivi = new Ainesosarivi(1, 1, 200.0, "g");
        rivi.setMaara(23.5);
        assertEquals(23.5, rivi.getMaara(), 0.000001);
        Ainesosarivi rivi2 = new Ainesosarivi(33, 3, 230.01, "g");
        rivi2.setMaara(29.454);
        assertEquals(29.454, rivi2.getMaara(), 0.000001);
    }
    
    /**
     * Testaa mittayksikön saamismetodia.
     */
    @Test
    public void testGetYksikko() {
        Ainesosarivi rivi = new Ainesosarivi(1, 2, 200.0, "g");
        assertEquals(200.0, rivi.getMaara(), 0.000001);
        Ainesosarivi rivi2 = new Ainesosarivi(1, 5, 24.5, "g");
        assertEquals(24.5, rivi2.getMaara(), 0.000001);
    }
    
    /**
     * Testaa mitttayksikön asettamismetodia.
     */
    @Test
    public void testSetYksikko() {
        Ainesosarivi rivi = new Ainesosarivi(1, 1, 200.0, "g");
        rivi.setYksikko("tl");
        assertEquals("tl", rivi.getYksikko());
        Ainesosarivi rivi2 = new Ainesosarivi(33, 3, 230.01, "g");
        rivi2.setYksikko("dl");
        assertEquals("dl", rivi2.getYksikko());
    }
    
    /**
     * Testaa konstruktoria.
     */
    @Test
    public void testKonstruktoriParametriton() {
        Ainesosarivi rivi = new Ainesosarivi(1, 2, 200.0, "g");
        assertEquals("Reseptin id asetettu väärin", 1, rivi.getReseptiId());
        assertEquals("Aineosan id asetettu väärin", 2, rivi.getAinesosaId());
        assertEquals("Määrä asetettu väärin", 200.0, rivi.getMaara(), 0.000001);
        assertEquals("Yksikkö asetettu väärin", "g", rivi.getYksikko());
        Ainesosarivi rivi2 = new Ainesosarivi(33, 4, 230.01, "kpl");
        assertEquals("Reseptin id asetettu väärin", 33, rivi2.getReseptiId());
        assertEquals("Aineosan id asetettu väärin", 4, rivi2.getAinesosaId());
        assertEquals("Määrä asetettu väärin", 230.01, rivi2.getMaara(), 0.000001);
        assertEquals("Yksikkö asetettu väärin", "kpl", rivi2.getYksikko());
    }
    
    /**
     * Testaa parsivaa konstruktoria.
     */
    @Test
    public void testKonstruktoriParsiva() {
        Ainesosarivi rivi;
        try {rivi = new Ainesosarivi("1|2|200|g");
        assertEquals("Reseptin id asetettu väärin", 1, rivi.getReseptiId());
        assertEquals("Aineosan id asetettu väärin", 2, rivi.getAinesosaId());
        assertEquals("Määrä asetettu väärin", 200, rivi.getMaara(), 0.000001);
        assertEquals("Yksikkö asetettu väärin", "g", rivi.getYksikko());
        } catch (VirheellinenSyottotietoException e) {fail("Parsiminen ei onnistunut. Virhe: " + e.getMessage());}
        Ainesosarivi rivi2;
        try { rivi2 = new Ainesosarivi("33|4|230.01|kpl");
        assertEquals("Reseptin id asetettu väärin", 33, rivi2.getReseptiId());
        assertEquals("Aineosan id asetettu väärin", 4, rivi2.getAinesosaId());
        assertEquals("Määrä asetettu väärin", 230.01, rivi2.getMaara(), 0.000001);
        assertEquals("Yksikkö asetettu väärin", "kpl", rivi2.getYksikko());
        } catch (VirheellinenSyottotietoException e) {fail("Parsiminen ei onnistunut. Virhe: " + e.getMessage());}
        try { rivi = new Ainesosarivi("334|230.01|kpl");
        fail("Liian vähän parametrejä sisältävän merkkijonon parsiminen ei olis pitänyt onnistua."); 
        } catch (VirheellinenSyottotietoException e) {e.getMessage();}
        try { rivi = new Ainesosarivi("33|4.2|230.01|kpl");
        fail("Vääränlaisia parametrejä sisältävän merkkijonon parsiminen ei olis pitänyt onnistua."); 
        } catch (VirheellinenSyottotietoException e) {e.getMessage();}
        try { rivi = new Ainesosarivi("tl|4|230.01|kpl");
        fail("Vääränlaisia parametrejä sisältävän merkkijonon parsiminen ei olis pitänyt onnistua."); 
        } catch (VirheellinenSyottotietoException e) {e.getMessage();}
    }
}
