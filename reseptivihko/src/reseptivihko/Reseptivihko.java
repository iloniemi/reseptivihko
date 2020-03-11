package reseptivihko;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.junit.Before;

/** Reseptivihko -luokka, joka ylläpitää listoja Resepteistä, Ainesosista ja Ainesosariveistä.
 * Käyttöliittymät ovat yhteydessä tämän luokan olioihin.
 * @author Juho
 * @version 10.3.2020
 *
 */
public class Reseptivihko {
    private Reseptilista reseptilista = null;
    private Rivilista rivilista = null;
    private Ainesosalista ainesosalista = null;
    
    /**
     * Alustaa Reseptivihon.
     */
    public Reseptivihko() {
        this.reseptilista = new Reseptilista();
        this.rivilista = new Rivilista();
        this.ainesosalista = new Ainesosalista();
    }

    /** Metodi Reseptivihko -luokan testaamiseen
     * @param args ei käytössä
     */
    public static void main(String[] args) {
//        ArrayList<Ainesosa> ainesosiaHakuun = new ArrayList<Ainesosa>();
//        Reseptivihko vihko = new Reseptivihko();
//        ArrayList<Resepti> reseptit = vihko.haeReseptit("*kakku", ainesosiaHakuun);
//        ArrayList<Ainesosarivi> reseptinRivit = vihko.haeRivit(reseptit.get(0));
//        ArrayList<Ainesosa> ainesosat = vihko.haeAinesosat();
//        vihko.lisaa(reseptit.get(0));

    }

    /** Lisää Reseptin vihkon Reseptilistalle.
     * @param resepti Lisättävä resepti.
     */
    public void lisaa(Resepti resepti) {
        this.reseptilista.lisaa(resepti);
        
    }

    /** Lisää Ainesosarivin vihkon Rivilistalle.
     * @param rivi Lisättävä rivi.
     */
    public void lisaa(Ainesosarivi rivi) {
        this.rivilista.lisaa(rivi);
        
    }

    /** Lisää Ainesosan vihkon Ainesosalistalle.
     * @param ainesosa Lisättävä ainesosa.
     */
    public void lisaa(Ainesosa ainesosa) {
        this.ainesosalista.lisaa(ainesosa);
    }

    /** Hakee Ainesosat, joiden nimessä esiintyy hakuteksti.
     * *-merkit tulkitaan minä tahansa merkkinä kuinka monta kertaa tahansa.
     * @param hakuteksti Teksti, joka pitää löytyä ainesosan nimestä.
     * @return Ainesosat, joiden nimi vastaa hakua.
     */
    public ArrayList<Ainesosa> haeAinesosat(String hakuteksti) {
        return this.ainesosalista.haeAinesosat(hakuteksti);
    }

    /**
     * Hakee Reseptit, joiden nimessä esiintyy hakuteksti ja sisältää annetut ainesosat.
     * *-merkki hakutekstissä tarkoittaa mitä tahansa merkkiä kuinka tahansa monta kertaa.
     * Tyhjä haku palauttaa kaikki Reseptit.
     * @param hakuteksti Teksti, joka pitää esiintyä reseptin nimessä.
     * @param ainesosat Ainesosat, jotka pitää löytyä reseptistä.
     * @return Hakuehtoja vastaavat reseptit.
     */
    public ArrayList<Resepti> haeReseptit(String hakuteksti, Collection<Ainesosa> ainesosat) {
        String teksti = hakuteksti;
        if (teksti.equals("")) teksti = "*";
        
        ArrayList<Resepti> palautettavat = this.reseptilista.haeReseptit(teksti);
        for (Ainesosa ainesosa: ainesosat) {
            if (ainesosa == null) continue;
            int[] ainesosaaSisaltavatReseptit = this.rivilista.reseptitAinesosalla(ainesosa.getId());
            
            palautettavat.removeIf(resepti -> {
                for (int id: ainesosaaSisaltavatReseptit) if (id == resepti.getId()) return false;
                return true;
                });
        }
        return palautettavat;
    }


    /** Hakee Reseptin Ainesosarivit.
     * @param resepti Resepti, jonka Ainesosarivit palautetaan.
     * @return Reseptille kuuluvat Ainesosarivit.
     */
    public ArrayList<Ainesosarivi> haeRivit(Resepti resepti) {
        return this.rivilista.haeReseptinRivit(resepti.getId());
    }

    
    
    
    
    /** Luo mallivihon
     * @return Luotu mallivihko
     */
    public static Reseptivihko mallivihko() {
        //TODO: Poista kun ei ole tarvetta
        
        String[] parsittavatAinesosat = {"1|tumma suklaata", "2|voita", 
                "3|kananmunia", "4|sokeria", "5|vehnäjauhoja", 
                "6|leivinjauhetta", "7|kermaa", "8|kaakaojauhetta"};
        ArrayList<Ainesosa> ainesosat = new  ArrayList<Ainesosa>();
        try {
            for (String rivi: parsittavatAinesosat) ainesosat.add(new Ainesosa().parse(rivi));
        } catch (VirheellinenSyottotietoException e) {
            System.err.println("Virhe luotaessa testejä varten ainesosia: " + e.getMessage());
        }
        
        String[] parsittavatReseptit = {"1|Mud cake|Kuumenna uuni 200 asteeseen.§Vatkaa munat ja sokeri vaaleaksi vaahdoksi.§Sulata suklaa ja voi ja kaada munasokeriseokseen.§Lisää vehnäjauhot ja leivinjauhe.§Sekoita varovasti.§Kaada seos voideltuun, jauhoitettuun pyöreään vuokaan (n. 24-26cm).§Paista uunin alatasolla n. 15-20 min."
                ,"2|Suklaatryffelit|Kuumenna kerma kiehuvaksi.§Kaada kerma suklaan ja voin päälle.§Sekoita varovasti.§Mausta.§Muotoile käsissä.§Päällystä kaakaojauheella."
        };
        ArrayList<Resepti> reseptit = new ArrayList<Resepti>();
        try {
            for (String rivi: parsittavatReseptit) reseptit.add(new Resepti().parse(rivi));
        } catch (VirheellinenSyottotietoException e) {
            System.err.println("Virhe luotaessa testejä varten reseptejä: " + e.getMessage());
        }
        
        String[] parsittavatRivit = {"1|1|200|g", "1|2|200|g", "1|3|4|kpl",
                "1|4|2|dl", "1|5|2.5|dl", "1|6|1|tl", "2|1|200|g",
                "2|7|100|g", "2|2|10|g", "2|8|10|g"};
        ArrayList<Ainesosarivi> rivit = new  ArrayList<Ainesosarivi>();
        try {
            for (String rivi: parsittavatRivit) rivit.add(new Ainesosarivi(rivi));
        } catch (VirheellinenSyottotietoException e) {
            System.err.println("Virhe luotaessa testejä varten Ainesosarivejä: " + e.getMessage());
        }
        
        Reseptivihko mallivihko = new Reseptivihko();
        for (Ainesosa ainesosa: ainesosat) mallivihko.lisaa(ainesosa);
        for (Resepti resepti: reseptit) mallivihko.lisaa(resepti);
        for (Ainesosarivi rivi: rivit) mallivihko.lisaa(rivi);        

        return mallivihko;
    }

    /** Hakee Ainesosan sen id:n perusteella.
     * @param id Haettavan Ainesosan id.
     * @return Haettu ainesosa tai null.
     */
    public Ainesosa haeAinesosa(int id) {
        return this.ainesosalista.haeAinesosa(id);
    }
    
    
    
    
    

}
