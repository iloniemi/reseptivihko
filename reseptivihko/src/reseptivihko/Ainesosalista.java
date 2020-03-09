package reseptivihko;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Ylläpitää listaa ainesosista
 * @author Juho
 * @version 9.3.2020
 *
 */
public class Ainesosalista {
    private ArrayList<Ainesosa> ainesosat;
    
    /**
     * Alustaa Ainesosalistan
     */
    public Ainesosalista() {
        this.ainesosat = new ArrayList<Ainesosa>();
    }
    
    /** Lisää Ainesosan listalle.
     * @param ainesosa Lisättävä ainesosa
     */
    public void lisaa(Ainesosa ainesosa) {
        this.ainesosat.add(ainesosa);
    }
    
    /** Luo uuden Ainesosa -olion ja lisää sen listalle.
     * @param nimi Uuden Ainesosan nimi
     */
    public void lisaa(String nimi) {
        this.lisaa(new Ainesosa(nimi));    
    }
    
    /** Palauttaa listan Ainesosia, joiden nimessä esiintyy hakuteksti.
     * 
     * @param hakuteksti teksti, jolla haetaan
     * @return ArrayList hakua vastaavia Ainesosia
     */
    public List<Ainesosa> haeAinesosat(String hakuteksti) {
        String regex = ".*" + hakuteksti.replace("*", ".*") + ".*";
        Pattern kuvio = Pattern.compile(regex);
        ArrayList<Ainesosa> palautettavat = new ArrayList<Ainesosa>();
        for (Ainesosa ainesosa: this.ainesosat) {
            Matcher matcher = kuvio.matcher(ainesosa.getNimi());
            if (matcher.matches()) palautettavat.add(ainesosa);
        }
        return palautettavat;
    }
    
    /** Poistaa Ainesosan listalta id:n perusteella
     * @param id Poistettavan Ainesosan id
     */
    public void poista(int id) {
        this.ainesosat.removeIf(ainesosa -> ainesosa.getId() == id);
    }

    /** Palauttaa kaikki listan Ainesosat.
     * @return ArrayList Ainesosista
     */
    public List<Ainesosa> getAinesosat() {
        return this.ainesosat;
    }

}                                                             