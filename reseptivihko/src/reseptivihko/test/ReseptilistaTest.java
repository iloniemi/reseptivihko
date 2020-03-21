package reseptivihko.test;

import reseptivihko.*;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import org.junit.*;

import fi.jyu.mit.ohj2.VertaaTiedosto;

/**
 * @author Juho
 * @version 28 Feb 2020
 * Testataan Reseptilista -luokkaa
 */
public class ReseptilistaTest {
        
    /**
     * Testaa reseptin lisäämistä reseptilistaan
     */
    @Test
    public void testLisaa() {
        Reseptilista reseptilista = new Reseptilista();
        Resepti resepti1 = new Resepti();
        try {resepti1.parse("1|Tee|Kuumenna vesi kiehuvaksi.§Uita teepussia vedessä.§Nauti.");
        reseptilista.lisaa(resepti1);
        reseptilista.lisaa(resepti1);
        assertEquals("Resepti olis pitänyt lisätä vain kerran", 1, reseptilista.haeReseptit("*").size());
        } catch (VirheellinenSyottotietoException e) { e.getMessage(); };
        for (int i = 0; i < 51; i++) {
            Resepti resepti = new Resepti();
            resepti.setId();
            resepti.setNimi("Tee" + i); 
            resepti.setOhje("Kuumenna vesi kiehuvaksi.\nUita teepussia vedessä.\nNauti.");
            reseptilista.lisaa(resepti);
        }
        assertEquals("Lisätty väärä määrä reseptejä", 52, reseptilista.haeReseptit("*").size());
    }
    
    /**
     * Testaa reseptien hakemista
     */
    @Test
    public void testHaeReseptit() {
        Reseptilista reseptilista = new Reseptilista();
        for (int i = 0; i < 3; i++) {
            Resepti resepti = new Resepti();
            resepti.setId();
            resepti.setNimi("Tee" + i); 
            resepti.setOhje("Kuumenna vesi kiehuvaksi.\nUita teepussia vedessä.\nNauti.");
            reseptilista.lisaa(resepti);
        }
        Resepti pelkkaTee = new Resepti();
        pelkkaTee.setId();
        pelkkaTee.setNimi("Tee"); 
        pelkkaTee.setOhje("Kuumenna vesi kiehuvaksi.\nUita teepussia vedessä.\nNauti.");
        reseptilista.lisaa(pelkkaTee);
        for (int i = 0; i < 2; i++) {
            Resepti resepti = new Resepti();
            resepti.setId();
            resepti.setNimi("" + i +"Tee"); 
            resepti.setOhje("Kuumenna vesi kiehuvaksi.\nUita teepussia vedessä.\nNauti.");
            reseptilista.lisaa(resepti);
        }
        
        List<Resepti> haun1tulos = reseptilista.haeReseptit(" tee   ");
        assertEquals("Väärä määrä reseptejä haun tuloksena", 1, haun1tulos.size());
        assertEquals("Piti tulla pelkästään tee", pelkkaTee, haun1tulos.get(0));
        
        List<Resepti> haun2tulos = reseptilista.haeReseptit("tee*");
        assertEquals("Piti tulla tee ja teei:t", 4, haun2tulos.size());
        
        List<Resepti> haun3tulos = reseptilista.haeReseptit("*tee*");
        assertEquals("Piti tulla kaikki teet", 6, haun3tulos.size());

        List<Resepti> haun4tulos = reseptilista.haeReseptit("*tee");
        assertEquals("Piti tulla tee ja itee:t", 3, haun4tulos.size());
        
        List<Resepti> haun5tulos = reseptilista.haeReseptit("*");
        assertEquals("Piti tulla kaikki teet", 6, haun5tulos.size());
    }

    /**
     * Testaa reseptin poistamista
     */
    @Test
    public void testPoistaResepti() {
        Reseptilista reseptilista = new Reseptilista();
        Resepti resepti1 = new Resepti();
        try {resepti1.parse("1|Tee1|Kuumenna vesi kiehuvaksi.§Uita teepussia vedessä.§Nauti.");
        reseptilista.lisaa(resepti1);
        } catch (VirheellinenSyottotietoException e) { e.getMessage(); };
        Resepti resepti = new Resepti();
        resepti.setId();
        resepti.setNimi("Tee2"); 
        reseptilista.lisaa(resepti);
        
        assertTrue("Piti olla Resepti, joka poistaa", reseptilista.poistaResepti(1));
        assertFalse("Ei pitänyt olla Reseptiä, jonka id on 1", reseptilista.poistaResepti(1));
        List<Resepti> hauntulos = reseptilista.haeReseptit("*");
        assertEquals("Väärä määrä Reseptejä", 1, hauntulos.size());
        assertEquals("Oikea määrä Reseptejä, mutta väärä resepti", resepti, hauntulos.get(0));        
        }
    
    /**
     * Testaa reseptin hakemista
     */
    @Test
    public void testHaeResepti() {
        Reseptilista reseptilista = new Reseptilista();
        Resepti resepti1 = new Resepti();
        try {resepti1.parse("1|Tee1|Kuumenna vesi kiehuvaksi.§Uita teepussia vedessä.§Nauti.");
        reseptilista.lisaa(resepti1);
        } catch (VirheellinenSyottotietoException e) { e.getMessage(); };
        Resepti resepti2 = new Resepti();
        try {resepti2.parse("2|Tee2|Kuumenna vesi kiehuvaksi.§Uita teepussia vedessä.§Nauti.");            
        reseptilista.lisaa(resepti2);
        } catch (VirheellinenSyottotietoException e) { e.getMessage(); };
        
        assertEquals("Ei pitäisi olla id 0 Reseptiä", null, reseptilista.haeResepti(0));      
        assertEquals("Väärä Resepti", resepti2, reseptilista.haeResepti(2));      
        assertEquals("Väärä Resepti", resepti1, reseptilista.haeResepti(1));      
    }
    
    /**
     * Testaa reseptilistan tallentamista tiedostoon.
     */
    @Test
    public void testTallenna() {
        Reseptilista reseptilista = new Reseptilista();
        Resepti resepti1 = new Resepti();
        try {resepti1.parse("1|Tee|Kuumenna vesi kiehuvaksi.§Uita teepussia vedessä.§Nauti.");
        reseptilista.lisaa(resepti1);
        } catch (VirheellinenSyottotietoException e) { fail("Testissa vikaa: " + e.getMessage()); };
        Resepti resepti2 = new Resepti();
        try {resepti2.parse("2|Tee2|Kuumenna vesi.§Uita teepussia vedessä 10 min.§Nauti.");            
        reseptilista.lisaa(resepti2);
        } catch (VirheellinenSyottotietoException e) { fail("Testissa vikaa: " + e.getMessage()); };
        
        File kansio = new File("testi089780898977789");
        kansio.delete();
        File tiedosto = new File(kansio,"reseptit.dat");
        File tarkistustiedosto = new File("reseptit.dat");
        try {
            reseptilista.tallenna(kansio);
        } catch (FileNotFoundException e) {
            fail("Virhe tallentaessa: " + e.getMessage());
        }
        
        String pitaisiOlla = ";id|nimi|ohje\n"
                + "1|Tee|Kuumenna vesi kiehuvaksi.§Uita teepussia vedessä.§Nauti.\n"
                + "2|Tee2|Kuumenna vesi.§Uita teepussia vedessä 10 min.§Nauti.\n";
        
        try {
            tiedosto.renameTo(tarkistustiedosto);
            assertEquals(null, VertaaTiedosto.vertaaFileString("reseptit.dat", pitaisiOlla));
        } catch (IOException e) {
            fail("Ei tehnyt tiedoston vertauksia. " + e.getMessage());
        }
        
        //tehtyjen tiedostojen poisto
        tarkistustiedosto.delete();
        kansio.delete();
    }
    
    /**
     * Testaa reseptilistan lukemista tiedostosta.
     */
    @Test
    public void testLue() {
        File kansio = new File("testi03423452345");
        kansio.delete();
        kansio.mkdirs();
        
        String sisalto = ";id|nimi|ohje\n"
                + "1|Tee|Kuumenna vesi kiehuvaksi.§Uita teepussia vedessä.§Nauti.\n"
                + "2|Tee2|Kuumenna vesi.§Uita teepussia vedessä 10 min.§Nauti.\n";
        try {
            VertaaTiedosto.kirjoitaTiedosto("reseptit.dat", sisalto);
        } catch (IOException e) {
            fail("Testitiedoston luomisessa virhe: " + e.getMessage());
        }
        File luotutiedosto = new File("reseptit.dat");
        File oikeaPaikka = new File(kansio, "reseptit.dat");
        luotutiedosto.renameTo(oikeaPaikka);
        
        Reseptilista reseptilista = new Reseptilista();
        try {
            reseptilista.lue(kansio);
        } catch (FileNotFoundException | VirheellinenSyottotietoException e) {
            fail("Virhe lukiessa: " + e.getMessage());
        }
        
        Resepti tee = reseptilista.haeResepti(1);
        Resepti tee2 = reseptilista.haeResepti(2);
        
        assertEquals("Tee", tee.getNimi());
        assertEquals("Kuumenna vesi kiehuvaksi.\nUita teepussia vedessä.\nNauti."
                , tee.getOhje());
        assertEquals("Tee2", tee2.getNimi());
        assertEquals("Kuumenna vesi.\nUita teepussia vedessä 10 min.\nNauti."
                , tee2.getOhje());
        
        //tehtyjen tiedostojen poisto
        oikeaPaikka.delete();
        kansio.delete();
    }
}

