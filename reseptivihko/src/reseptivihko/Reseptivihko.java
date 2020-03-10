package reseptivihko;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

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

}
