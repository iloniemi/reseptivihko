package reseptivihko.test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.*;

import fi.jyu.mit.ohj2.Tiedosto;
import fi.jyu.mit.ohj2.VertaaTiedosto;
import reseptivihko.*;

/**
 * @author Juho
 * @version 10.3.2020
 *
 */
public class ReseptivihkoTest {
    private Reseptivihko vihko;
    private ArrayList<Ainesosa> ainesosat = null;
    private ArrayList<Resepti> reseptit;
    private ArrayList<Ainesosarivi> rivit;
    
    /**
     * Alustaa Ainesosa, Resepti ja Ainesoarivilistat, jotta niitä voidaan käyttää
     * vihon alustamiseen.
     */
    public void alustaOsat() {
        String[] parsittavatAinesosat = {"1|tumma suklaata", "2|voita", 
                "3|kananmunia", "4|sokeria", "5|vehnäjauhoja", 
                "6|leivinjauhetta", "7|kermaa", "8|kaakaojauhetta"};
        this.ainesosat = new  ArrayList<Ainesosa>();
        try {
            for (String rivi: parsittavatAinesosat) this.ainesosat.add(new Ainesosa().parse(rivi));
        } catch (VirheellinenSyottotietoException e) {
            System.err.println("Virhe luotaessa testejä varten ainesosia: " + e.getMessage());
        }

        String[] parsittavatReseptit = {"1|Mud cake|Kuumenna uuni 200 asteeseen.§Vatkaa munat ja sokeri vaaleaksi vaahdoksi.§Sulata suklaa ja voi ja kaada munasokeriseokseen.§Lisää vehnäjauhot ja leivinjauhe.§Sekoita varovasti.§Kaada seos voideltuun, jauhoitettuun pyöreään vuokaan (n. 24-26cm).§Paista uunin alatasolla n. 15-20 min."
                ,"2|Suklaatryffelit|Kuumenna kerma kiehuvaksi.§Kaada kerma suklaan ja voin päälle.§Sekoita varovasti.§Mausta.§Muotoile käsissä.§Päällystä kaakaojauheella."
                };
        this.reseptit = new ArrayList<Resepti>();
        try {
            for (String rivi: parsittavatReseptit) this.reseptit.add(new Resepti().parse(rivi));
        } catch (VirheellinenSyottotietoException e) {
            System.err.println("Virhe luotaessa testejä varten reseptejä: " + e.getMessage());
        }
        
        String[] parsittavatRivit = {"1|1|200|g", "1|2|200|g", "1|3|4|kpl",
                "1|4|2|dl", "1|5|2.5|dl", "1|6|1|tl", "2|1|200|g",
                "2|7|100|g", "2|2|10|g", "2|8|10|g"};
        this.rivit = new  ArrayList<Ainesosarivi>();
        try {
            for (String rivi: parsittavatRivit) this.rivit.add(new Ainesosarivi(rivi));
        } catch (VirheellinenSyottotietoException e) {
            System.err.println("Virhe luotaessa testejä varten Ainesosarivejä: " + e.getMessage());
        }
    }
    
    
    /**
     * Alustaa vihon alkutilaan ennen kutakin testiä.
     */
    @Before
    public void alustaVihko() {
        if (this.ainesosat == null) this.alustaOsat();
        
        this.vihko = new Reseptivihko();
        for (Ainesosa ainesosa: this.ainesosat) this.vihko.lisaa(ainesosa);
        for (Resepti resepti: this.reseptit) this.vihko.lisaa(resepti);
        for (Ainesosarivi rivi: this.rivit) this.vihko.lisaa(rivi);        
    }    
    
    /**
     * Testataan Reseptivihon konstruktoria.
     */
    @Test
    public void testKonstruktori() {
        Reseptivihko uusivihko = new Reseptivihko();
        assertEquals("Ei pitäisi olla Reseptejä", 0, uusivihko.haeReseptit("", new ArrayList<Ainesosa>()).size());
        assertEquals("Ei pitäisi olla Ainesosia", 0, uusivihko.haeAinesosat("").size());
    }

    /**
     * Testataan Reseptivihon lisaa -metodeja.
     */
    @Test
    public void testLisaa() {
        Reseptivihko uusivihko = new Reseptivihko();
        Resepti resepti = null;
        try {resepti = new Resepti().parse("1|Mud cake|Kuumenna uuni 200 asteeseen.§Vatkaa");
        } catch (VirheellinenSyottotietoException e) {fail("Virhe testissä: " + e.getMessage());};
        uusivihko.lisaa(resepti);
        assertEquals("Resepti ei tallentunut", resepti, uusivihko.haeReseptit("", new ArrayList<Ainesosa>()).get(0));
        
        Ainesosa ainesosa = null;
        try {ainesosa = new Ainesosa().parse("1|tumma suklaa");
        } catch (VirheellinenSyottotietoException e) {fail("Virhe testissä: " + e.getMessage());};
        uusivihko.lisaa(ainesosa);
        assertEquals("Resepti ei tallentunut", ainesosa, uusivihko.haeAinesosat("tumma suklaa").get(0)); 
        
        Ainesosarivi rivi = new Ainesosarivi(1,1,200.0,"g");
        uusivihko.lisaa(rivi);
        assertEquals("Resepti ei tallentunut", rivi, uusivihko.haeRivit(resepti).get(0));
    }
    
    /**
     * Testaa Reseptien hakemista Reseptivihosta
     */
    @Test
    public void testHaeReseptit() {
        assertEquals("Jokerimerkki ei palauttanut kaikkia reseptejä.", 2,this.vihko.haeReseptit("*", new ArrayList<Ainesosa>()).size());
        assertEquals("Tyhjä haku ei palauttanut kaikkia reseptejä.", 2,this.vihko.haeReseptit("*", new ArrayList<Ainesosa>()).size());
        assertEquals("Jokerimerkki alussa palautti väärän reseptin.","Mud cake",this.vihko.haeReseptit("*cake", new ArrayList<Ainesosa>()).get(0).getNimi());
        assertEquals("Jokerimerkki lopussa palautti väärän reseptin.","Mud cake",this.vihko.haeReseptit("Mud*", new ArrayList<Ainesosa>()).get(0).getNimi());
        assertEquals("Jokerimerkki lopussa palautti väärän reseptin.","Mud cake",this.vihko.haeReseptit("Mud*", new ArrayList<Ainesosa>()).get(0).getNimi());
        ArrayList<Ainesosa> ainesosatHakuun = new ArrayList<Ainesosa>();
        ainesosatHakuun.add(this.ainesosat.get(0)); //tumma suklaa listalle
        assertEquals("Tummaa suklaata ei palauttanut kahta reseptiä.", 2, this.vihko.haeReseptit("", ainesosatHakuun).size());
        assertEquals("Tummaa suklaata ja hakusana *cake ei palauttanut yhtä reseptiä.", 1, this.vihko.haeReseptit("*cake", ainesosatHakuun).size());
        ainesosatHakuun.add(this.ainesosat.get(4)); //vehnäjauhoja listalle
        assertEquals("Tummaa suklaata ja vehnäjauhoja ei palauttanut yhtä reseptiä.", 1, this.vihko.haeReseptit("", ainesosatHakuun).size());
    }
    
    /**
     * Testaa Reseptin Ainesosarivien hakemista Reseptivihosta
     */
    @Test
    public void testHaeRivit() {
        ArrayList<Ainesosarivi> haetutRivit = this.vihko.haeRivit(this.reseptit.get(0));
        assertEquals("Mud cakelle tuli väärä määrä rivejä.", 6, haetutRivit.size());
        for (int i = 0; i < 6; i++) assertTrue("Reseptin rivi puuttuu.", haetutRivit.contains(this.rivit.get(i)));
    }
    
    /**
     * Testaa Ainesosien hakemista Reseptivihosta
     */
    @Test
    public void testHaeAinesosat() {
        ArrayList<Ainesosa> haetutAinesosat = this.vihko.haeAinesosat("");
        assertEquals("Tyhjän rivin pitäisi palauttaa kaikki ainesoat.", this.ainesosat.size(), haetutAinesosat.size());
        haetutAinesosat = this.vihko.haeAinesosat("ke");
        assertEquals("\"ke\" haun pitäisi palauttaa sokeria ja kermaa.", 2, haetutAinesosat.size());
        assertTrue("\"ke\" haku ei palauttanut kermaa.", haetutAinesosat.contains(this.ainesosat.get(6)));
        assertTrue("\"ke\" haku ei palauttanut sokeria.", haetutAinesosat.contains(this.ainesosat.get(3)));
        haetutAinesosat = this.vihko.haeAinesosat("munia");
        assertTrue("\"munia\" haku ei palauttanut kananmunia.", haetutAinesosat.contains(this.ainesosat.get(2)));
    }
    
    /**
     * Testaa Ainesosan hakemista Reseptivihosta
     */
    @Test
    public void testHaeAinesosa() {
        assertEquals("Ainesosan id ei täsmännyt haun kanssa", 1, this.vihko.haeAinesosa(1).getId());
        assertEquals("Ainesosan id ei täsmännyt haun kanssa", 4, this.vihko.haeAinesosa(4).getId());
    }
    
    /**
     * Testaa tiedostojen tallentamista ja lukemista sekä tallennuskansion asettamista.
     */
    public void testTiedostot() {
        VertaaTiedosto.tuhoaTiedosto("testi");
        this.vihko.asetaKansio("testi");
        this.vihko.tallenna();
        Reseptivihko uusiVihko = new Reseptivihko();
        uusiVihko.asetaKansio("testi");
        uusiVihko.lue();
        for (int i = 1; i <= 8; i++) {
            assertEquals(String.format("Ainesosa %d ei ollut sama.", i), 
                    this.vihko.haeAinesosa(i).getNimi(), uusiVihko.haeAinesosa(i).getNimi());            
        }
        Resepti uudenVihkonKakku = uusiVihko.haeReseptit("Mud cake", new LinkedList<Ainesosa>()).get(0);
        Resepti tamanVihkonKakku = this.vihko.haeReseptit("Mud cake", new LinkedList<Ainesosa>()).get(0);
        assertEquals("Luetun reseptin ID ei vastannut tallennettua", tamanVihkonKakku.getId(), uudenVihkonKakku.getId());
        assertEquals("Luetun reseptin ohje ei vastannut tallennettua", tamanVihkonKakku.getOhje(), uudenVihkonKakku.getOhje());
    }
}
