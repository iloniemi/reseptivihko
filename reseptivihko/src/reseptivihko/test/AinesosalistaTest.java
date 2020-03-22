package reseptivihko.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.*;

import fi.jyu.mit.ohj2.VertaaTiedosto;
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
     * Alustaa testeissä käytettävän Ainesosalistan.
     */
    @Before
    public void alusta() {
        this.lista = new Ainesosalista();
        String[] ainesosia = {"1|tummaa suklaata",
                            "2|voita",
                            "3|kananmunia",
                            "4|sokeria",
                            "5|vehnäjauhoja",
                            "6|leivinjauhetta",
                            "7|kermaa",
                            "8|kaakaojauhetta"};
        try {
            for (String rivi: ainesosia) this.lista.lisaa(new Ainesosa().parse(rivi));            
        } catch (VirheellinenSyottotietoException e) {
            fail("Virhe testin alustamisessa: " + e.getMessage());
        }
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
        
        this.lista.lisaa(" Perunaa  ");
        assertEquals("Ainesosan nimen pitäisi olla karsittu.", 
                "Perunaa", this.lista.haeAinesosat("Perunaa").get(0).getNimi());
    }
    
    /**
     * Testaa Ainesosien hakemista haeAinesosat ja getAinesosat metodeilla
     */
    @Test
    public void testHaeAinesosat() {
        assertEquals("Haun olisi pitänyt palauttaa kaikki 8 ", 8, this.lista.getAinesosat().size());
        assertEquals("Haun olisi pitänyt palauttaa kaikki 8 ", 8, this.lista.haeAinesosat("").size());
        assertEquals("Haun olis pitänyt löytää vain tummaa suklaata", "tummaa suklaata", this.lista.haeAinesosat("suklaAt").get(0).getNimi());
        assertEquals("Haun \"ta\" olisi pitänyt palauttaa neljä Ainesosaa", 4, this.lista.haeAinesosat("ta").size());
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
    
    /**
     * Testaa Ainesosien tallentamista kansioon.
     */
    @Test
    public void testTallenna() {
        File kansio = new File("testikansioaineosarivi");
        String tiedostonNimi = "ainesosat.dat";
        File tarkistustiedosto = new File(tiedostonNimi);
        File luotavaTiedosto = new File(kansio, tiedostonNimi);
        
        //Poistetaan mahdollisesti aiemmin jääneet tiedostot.
        luotavaTiedosto.delete();
        tarkistustiedosto.delete();
        kansio.delete();
        
        this.lista.tallenna(kansio);
        String pitaisiOlla = ";id|nimi\r\n" + 
                "1|tummaa suklaata\r\n" + 
                "2|voita\r\n" + 
                "3|kananmunia\r\n" + 
                "4|sokeria\r\n" + 
                "5|vehnäjauhoja\r\n" + 
                "6|leivinjauhetta\r\n" + 
                "7|kermaa\r\n" + 
                "8|kaakaojauhetta";
        
        //Siirretään tiedosto vertaaFileStringiä varten samaan kansioon.
        luotavaTiedosto.renameTo(tarkistustiedosto);
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
     * Testaa Ainesosalistan lukemista tiedostosta.
     */
    @Test
    public void testLue() {
        File kansio = new File("testi89375923475");
        String tiedostonNimi = "ainesosat.dat";
        File luotavatiedosto = new File(tiedostonNimi);
        File oikeaPaikka = new File(kansio, tiedostonNimi);
        
        //Poistetaan mahdollisesti edelliseltä kerralta jääneet tiedostot.
        luotavatiedosto.delete();
        oikeaPaikka.delete();
        kansio.delete();
        kansio.mkdirs();
        
        //Luodaan tiedosto ja siirretään se tarkistusta varten.
        String sisalto = ";id|nimi\r\n" + 
                "1|tummaa suklaata\r\n" + 
                "2|voita\r\n" + 
                "3|kananmunia\r\n" + 
                "4|sokeria\r\n" + 
                "5|vehnäjauhoja\r\n" + 
                "6|leivinjauhetta\r\n" + 
                "7|kermaa\r\n" + 
                "8|kaakaojauhetta";
        try {
            VertaaTiedosto.kirjoitaTiedosto(tiedostonNimi, sisalto);
        } catch (IOException e) {
            fail("Testitiedoston luomisessa virhe: " + e.getMessage());
        }
        luotavatiedosto.renameTo(oikeaPaikka);
        
        Ainesosalista luettuLista = new Ainesosalista();
        try {
            luettuLista.lue(kansio);
        } catch (FileNotFoundException | VirheellinenSyottotietoException e) {
            fail("Virhe lukiessa: " + e.getMessage());
        }
        
        for (int i = 1; i <= 8; i++) {
            assertEquals(this.lista.haeAinesosa(i).getNimi(), 
                    luettuLista.haeAinesosa(i).getNimi());
        }
        
        //tehtyjen tiedostojen poisto
        oikeaPaikka.delete();
        kansio.delete();
    }
}
