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
        String[] ainesosienNimia = {"munia", "tummaa suklaata", "voita", "vehnäjauhoja, sokeria"};
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
        assertEquals("Haun olis pitänyt löytää vain tummaa suklaata", "tummaa suklaaata", this.lista.haeAinesosat("suklaat").get(0).getNimi());
        assertEquals("Haun olisi pitänyt palauttaa kaksi Ainesosaa, voita ja tummaa suklaata", 2, this.lista.haeAinesosat("ta").size());
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
