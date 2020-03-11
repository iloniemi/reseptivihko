package reseptivihko.test;

import static org.junit.Assert.*;
import org.junit.*;
import reseptivihko.*;

/**
 * Testaa Ainesosalista -luokkaa
 * @author Juho
 * @version 9.3.2020
 *
 */
public class AinesosalistaTest {
    private Ainesosalista lista;
    
    /**
     * Alustaa testiss
     */
    @Before
    public void alusta() {
        this.lista = new Ainesosalista();
        String[] ainesosienNimia = {"munia", "tummaa suklaata", "voita", "vehnäjauhoja", "sokeria"};
        for (String nimi: ainesosienNimia) this.lista.lisaa(new Ainesosa(nimi));
    }
    
    /**
     * Testaa Ainesosien lisäämistä listalle
     */
    @Test
    public void testLisaa() {
        this.lista.lisaa(new Ainesosa("valkosuklaata"));
        assertEquals("Ainesosa -olion lisääminen ei onistunut.", 2, this.lista.haeAinesosat("suklaata").size());
        this.lista.lisaa("maitosuklaata");
        assertEquals("Ainesosan nimen pohjalta uuden ainesosan lisääminen epäonnistui.", 3, this.lista.haeAinesosat("suklaata").size());
    }
    
    /**
     * Testaa Ainesosien hakemista haeAinesosat ja getAinesosat metodeilla
     */
    @Test
    public void testHaeAinesosat() {
        assertEquals("Haun olisi pitänyt palauttaa kaikki 5 ", 5, this.lista.getAinesosat().size());
        assertEquals("Haun olisi pitänyt palauttaa kaikki 5 ", 5, this.lista.haeAinesosat("").size());
        assertEquals("Haun olis pitänyt löytää vain tummaa suklaata", "tummaa suklaata", this.lista.haeAinesosat("suklaat").get(0).getNimi());
        assertEquals("Haun olisi pitänyt palauttaa kaksi Ainesosaa, voita ja tummaa suklaata", 2, this.lista.haeAinesosat("ta").size());
    }
    
    /**
     * Testaa Ainesosan hakemista id:n perusteella
     */
    @Test
    public void testHaeAinesosa() {
        Ainesosa ainesosa33 = null; Ainesosa ainesosa23 = null;
        try {
            ainesosa33 = new Ainesosa().parse("33|nakkeja");
            ainesosa23 = new Ainesosa().parse("23|perunoita");
            this.lista.lisaa(ainesosa33);
            this.lista.lisaa(ainesosa23);
        } catch (VirheellinenSyottotietoException e) {
            fail("Testissä vikaa: " + e.getMessage());
        }
        assertEquals("Haetun Ainesosan id ei täsmää haun kanssa", 33, this.lista.haeAinesosa(33).getId());
        assertEquals("Haetun Ainesosan id ei täsmää haun kanssa", 23, this.lista.haeAinesosa(23).getId());
    }
    
    /**
     * Testaa Ainesosien poistamista
     */
    @Test
    public void testPoista() {
        this.lista.poista(this.lista.haeAinesosat("suklaata").get(0).getId());
        assertEquals("Ainesosa -olion poistaminen ei onistunut.", 0, this.lista.haeAinesosat("suklaata").size());
    }
}
