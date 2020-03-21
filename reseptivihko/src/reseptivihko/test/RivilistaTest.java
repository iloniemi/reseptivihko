package reseptivihko.test;

import reseptivihko.*;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.*;

import fi.jyu.mit.ohj2.VertaaTiedosto;

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
        assertEquals("Väärä määrä rivejä.", 6, rivilista.haeReseptinRivit(1).size());
        assertEquals("Väärä määrä rivejä.", 4, rivilista.haeReseptinRivit(2).size());
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
        assertEquals("Väärän reseptin rivejä poistettiin.", 4, rivilista.haeReseptinRivit(2).size());
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
    
    /**
     * Testaa rivien tallentamista kansioon.
     */
    @Test
    public void testTallenna() {
        File kansio = new File("testikansioaineosarivi");
        kansio.delete();
        VertaaTiedosto.tuhoaTiedosto("testikansioaineosarivi");
        this.rivilista.tallenna(kansio);
        String pitaisiOlla = ";resepti_id|ainesosa_id|maara|yksikko\r\n" + 
                "1|1|200.000|g\r\n" + 
                "1|2|200.000|g\r\n" + 
                "1|3|4.000|kpl\r\n" + 
                "1|4|2.000|dl\r\n" + 
                "1|5|2.500|dl\r\n" + 
                "1|6|1.000|tl\r\n" + 
                "2|1|200.000|g\r\n" + 
                "2|7|100.000|g\r\n" + 
                "2|2|10.000|g\r\n" + 
                "2|8|10.000|g";
        String tiedostonNimi = "ainesosarivit.dat";
        File tarkistustiedosto = new File(tiedostonNimi);
        File luotuTiedosto = new File(kansio, tiedostonNimi);
        luotuTiedosto.renameTo(tarkistustiedosto);
        try {
            assertEquals("Tiedosto ei täsmää", null, 
                    VertaaTiedosto.vertaaFileString(tiedostonNimi, pitaisiOlla));
        } catch (IOException e) {
            fail("Virhe vertailussa: " + e.getMessage());
        }
        
        //Tiedostojen poisto
        tarkistustiedosto.delete();
        kansio.delete();
    }
    
    /**
     * Testaa rivilistan lukemista tiedostosta.
     */
    @Test
    public void testLue() {
        File kansio = new File("testi23452340895");
        kansio.delete();
        kansio.mkdirs();
        String tiedostonNimi = "ainesosarivit.dat";
        
        String sisalto = ";resepti_id|ainesosa_id|maara|yksikko\r\n" + 
                "1|1|200.000|g\r\n" + 
                "1|2|200.000|g\r\n" + 
                "1|3|4.000|kpl\r\n" + 
                "1|4|2.000|dl\r\n" + 
                "1|5|2.500|dl\r\n" + 
                "1|6|1.000|tl\r\n" + 
                "2|1|200.000|g\r\n" + 
                "2|7|100.000|g\r\n" + 
                "2|2|10.000|g\r\n" + 
                "2|8|10.000|g";
        try {
            VertaaTiedosto.kirjoitaTiedosto(tiedostonNimi, sisalto);
        } catch (IOException e) {
            fail("Testitiedoston luomisessa virhe: " + e.getMessage());
        }
        File luotutiedosto = new File(tiedostonNimi);
        File oikeaPaikka = new File(kansio, tiedostonNimi);
        luotutiedosto.renameTo(oikeaPaikka);
        
        Rivilista lista = new Rivilista();
        try {
            lista.lue(kansio);
        } catch (FileNotFoundException | VirheellinenSyottotietoException e) {
            fail("Virhe lukiessa: " + e.getMessage());
        }
        
        List<Ainesosarivi> haetutrivit = lista.haeReseptinRivit(2);
        Ainesosarivi tutkittavarivi = null;
        for (Ainesosarivi rivi : haetutrivit) {
            if (rivi.getAinesosaId() == 7) tutkittavarivi = rivi;
        }
        if (tutkittavarivi == null) {
            fail("Rivi olisi pitänyt löytyä.");
        } else {
            assertEquals("Reseptin id väärin.", 2, tutkittavarivi.getReseptiId());
            assertEquals("Ainesosan id väärin.", 7, tutkittavarivi.getAinesosaId());
            assertEquals("Määrä väärin.", 100.000, tutkittavarivi.getMaara(), 0.001);
            assertEquals("Mittayksikkö väärin.", "g", tutkittavarivi.getYksikko());
        }
        
        //tehtyjen tiedostojen poisto
        oikeaPaikka.delete();
        kansio.delete();
    }
}
