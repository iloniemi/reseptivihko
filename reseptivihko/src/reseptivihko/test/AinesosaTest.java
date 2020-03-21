package reseptivihko.test;

import static org.junit.Assert.*;
import org.junit.*;

import reseptivihko.Ainesosa;
import reseptivihko.VirheellinenSyottotietoException;

/** Testataan Ainesosa -luokan toimintaa.
 * @author Juho
 * @version 1 Mar 2020
 *
 */
public class AinesosaTest {

    /**
     * Testaa konstruktoreita
     */
    @Test
    public void testKonstruktorit() {
        Ainesosa ainesosa1 = new Ainesosa();
        assertEquals("Nimen kuuluisi olla tyhjä.", "", ainesosa1.getNimi());
        assertEquals("Id:n kuuluisi olla -1.", -1, ainesosa1.getId());
        ainesosa1 = new Ainesosa("tummaa suklaata");
        Ainesosa ainesosa2 = new Ainesosa("voita");
        assertEquals("Nimen kuuluisi olla \"voita\".", "voita", ainesosa2.getNimi());
        assertEquals("Id:n kuuluisi olla yhden suurempi kuin edellisen.", ainesosa1.getId() + 1, ainesosa2.getId());
    }
    
    /**
     * Testaa parse -metodia
     */
    @Test
    public void testParse() {
        Ainesosa ainesosa5;
        try { ainesosa5 = new Ainesosa().parse("5|vehnäjauhoja");
        assertEquals("Nimen kuuluisi olla \"vehnäjauhoja\".", "vehnäjauhoja", ainesosa5.getNimi());
        assertEquals("Id:n kuuluisi olla 5.", 5, ainesosa5.getId());
        assertEquals("Id:n kuuluisi olla 5.", 5, ainesosa5.getId());
        assertEquals("Seuraavan ainesosan id:n kuuluisi olla yhden suurempi kuin edellisen.", 
                ainesosa5.getId() + 1, new Ainesosa("nakit").getId());
        } catch (VirheellinenSyottotietoException e) {
            fail("Virhe: " + e.getMessage());
        }
    }

    /**
     * Testaa nimen palauttamista
     */
    @Test
    public void testGetNimi() {
        Ainesosa ainesosa3;
        try { ainesosa3 = new Ainesosa().parse("5|vehnäjauhoja");
        assertEquals("Nimen kuuluisi olla \"vehnäjauhoja\".", "vehnäjauhoja", ainesosa3.getNimi());
        } catch (VirheellinenSyottotietoException e) {
            fail("Virhe: " + e.getMessage());
        }
    }
    
    /**
     * Testaa id:n palauttamista
     */
    @Test
    public void testGetId() {
        Ainesosa ainesosa;
        try { ainesosa = new Ainesosa().parse("5|vehnäjauhoja");
        assertEquals("Id:n kuuluisi olla 5.", 5, ainesosa.getId());
        } catch (VirheellinenSyottotietoException e) {
            fail("Virhe: " + e.getMessage());
        }
    }
    
    
    /**
     * Testaa Reseptien vertailua.
     */
    @Test
    public void testCompare() {
        Ainesosa ahven1 = new Ainesosa("ahven");
        Ainesosa ahven2 = new Ainesosa("ahvEn");
        Ainesosa ahven3 = new Ainesosa("ahvCn");
        assertEquals("Kirjaimen koolla ei pitäisi olla väliä.", 0, ahven1.compareTo(ahven2));
        assertEquals("ahven pitäisi olla ennen ahvCn", 2, ahven1.compareTo(ahven3));
    }
    
    /**
     * Testaa tiedostoriviksi muotoilua.
     */
    @Test
    public void testTiedostoriviksi() {
        Ainesosa ainesosa;
        try { 
            String eka = "5|vehnäjauhoja";
            ainesosa = new Ainesosa().parse(eka);
            assertEquals(eka, ainesosa.tiedostoriviksi());
            String toka = "2|voita";
            ainesosa = new Ainesosa().parse(toka);
            assertEquals(toka, ainesosa.tiedostoriviksi());
        } catch (VirheellinenSyottotietoException e) {
            fail("Virhe parsiessa: " + e.getMessage());            
        }
    }
}
