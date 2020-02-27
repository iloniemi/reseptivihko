package reseptivihko.test;

import reseptivihko.*;
import static org.junit.Assert.*;
import org.junit.*;

/**
 * Testit Resepti -luokalle
 * @author Juho
 * @version 27 Feb 2020
 *
 */
public class ReseptiTest {

    /**
     * Testaa parametritöntä konstruktoria
     */
    @Test
    public void testKonstruktoriIlmanParametreja() {
        String tyhjaResepti = "ID: -1\n\n";
        Resepti resepti = new Resepti();
        assertEquals("Tyhja konstruktori ei luonut Reseptia oikein", tyhjaResepti, resepti.toString());
    }
    
    /**
     * Testaa nimen asettamista
     */
    @Test
    public void testSetNimi() {
        Resepti resepti = new Resepti(); resepti.setNimi("Tee");
        assertEquals("Nimeä ei asetettu oikein", "Tee", resepti.getNimi());
        resepti.setNimi("Kahvi");
        assertEquals("Nimi ei vaihtunut toisella .setNimi() -kutsulla", "Kahvi", resepti.getNimi());
    }
    
    /**
     * Testaa nimen hakemista
     */
    @Test
    public void testGetNimi() {
        Resepti resepti = new Resepti(); resepti.setNimi("Tee");
        assertEquals("Nimi on väärin", "Tee", resepti.getNimi());
    }
    
    /**
     * Testaa ohjeen asettamista
     */
    @Test
    public void testSetOhje() {
        Resepti resepti = new Resepti(); resepti.setOhje("Keita vesi.\nUita teepussia vedessa.");
        assertEquals("Nimeä ei asetettu oikein", "Keita vesi.\nUita teepussia vedessa.", resepti.getOhje());
        resepti.setOhje("Keita vesi.\nUita teepussia vedessa.\nNauti.");
        assertEquals("Ohje ei vaihtunut toisella .setOhje() -kutsulla", "Keita vesi.\nUita teepussia vedessa.\nNauti.", resepti.getOhje());
    }
    
    /**
     * Testaa ohjeen hakemista
     */
    @Test
    public void testGetOhje() {
        Resepti resepti = new Resepti(); resepti.setOhje("Keita vesi.\nUita teepussia vedessa.");
        assertEquals("Nimi on väärin", "Keita vesi.\nUita teepussia vedessa.", resepti.getOhje());
    }
    
    /**
     * Testaa id:n asettamista
     */
    @Test
    public void testAsetaId() {
        Resepti resepti1 = new Resepti(); int reseptin1Id = resepti1.asetaId();
        assertEquals("Asettaminen ei palauttanut id:tä", reseptin1Id, resepti1.getId());
        resepti1.asetaId();
        assertEquals("Toinen asetaId() -kutsu vaihtoi id:tä", reseptin1Id, resepti1.getId());
        Resepti resepti2 = new Resepti();
        assertEquals("Seuraava asetettu id ei ollut yhtä suurempi kuin edellinen", resepti2.asetaId(), resepti1.getId() + 1);
    }

    /**
     * Testaa parse() -metodia
     */
    @Test
    public void testParse() {
        Resepti resepti = new Resepti(); 
        resepti.parse("3|Tee|Kuumenna vesi kiehuvaksi.§Uita teepussia vedessä.§Nauti.");
        assertEquals("Väärä id", 3, resepti.getId());
        assertEquals("Väärä nimi", "Tee", resepti.getNimi());
        assertEquals("Väärä ohje", "Kuumenna vesi kiehuvaksi.\nUita teepussia vedessä.\nNauti.", resepti.getOhje());
        assertFalse("Vääränlaisessa järjestyksessä oleva syöte toimi", resepti.parse("Tee|3|Kuumenna vesi kiehuvaksi.§Uita teepussia vedessä.§Nauti."));
        assertFalse("Väärä määrä tietueita syötteessä toimi", resepti.parse("Tee3|Kuumenna vesi kiehuvaksi.§Uita teepussia vedessä.§Nauti."));
    }

 
    

}
