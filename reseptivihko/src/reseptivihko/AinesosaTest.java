package reseptivihko;

import static org.junit.Assert.*;
import org.junit.*;

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
    public void getNimi() {
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
    public void getId() {
        Ainesosa ainesosa3;
        try { ainesosa3 = new Ainesosa().parse("5|vehnäjauhoja");
        assertEquals("Id:n kuuluisi olla 5.", 5, ainesosa3.getId());
        } catch (VirheellinenSyottotietoException e) {
            fail("Virhe: " + e.getMessage());
        }
    }
}
