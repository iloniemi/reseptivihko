package reseptivihko;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import apufunktioita.Apufunktioita;

/** Ylläpitää listaa ainesosista
 * @author Juho
 * @version 9.3.2020
 *
 */
public class Ainesosalista {
    private ArrayList<Ainesosa> ainesosat;
    private final static String TIEDOSTON_NIMI = "ainesosat";
    
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
     * @param nimi Uuden Ainesosan nimi.
     * @return lisättiinkö uusi Ainesosa.
     */
    public boolean lisaa(String nimi) {
        String karsittuNimi = Apufunktioita.rajuTrim(nimi);
        for (Ainesosa ainesosa: this.ainesosat) 
            if (ainesosa.getNimi().equalsIgnoreCase(karsittuNimi)) return false;
        this.lisaa(new Ainesosa(karsittuNimi));
        return true;
    }
    
    /** Palauttaa listan Ainesosia, joiden nimessä esiintyy hakuteksti.
     * Tyhjä merkkijono palauttaa kaikki Ainesosat.
     * Palautettavat Ainesosat ovat aakkosjärjestyksessä.
     * @param hakuteksti teksti, jolla haetaan
     * @return ArrayList hakua vastaavia Ainesosia
     */
    public ArrayList<Ainesosa> haeAinesosat(String hakuteksti) {
        if (hakuteksti.length() == 0) return this.ainesosat;
        ArrayList<Ainesosa> palautettavat = new ArrayList<Ainesosa>();
        //Tarkistus erikoismerkkien varalta, etta kaaret eivat pilaa regexia.
        //TODO: tarvitaanko muita merkkejä?
        if (!hakuteksti.matches("^[a-zA-ZÀ-ÿ0-9\\*\\-\\s]*$")) return palautettavat;
        String regex = ".*" + hakuteksti.replace("*", ".*") + ".*";
        Pattern kuvio = Pattern.compile(regex.toLowerCase());
        for (Ainesosa ainesosa: this.ainesosat) {
            Matcher matcher = kuvio.matcher(ainesosa.getNimi().toLowerCase());
            if (matcher.matches()) palautettavat.add(ainesosa);
        }
        palautettavat.sort(null);
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

    /** Palauttaa haetun id:n omaavan Ainesosan.
     * @param id Haettavan ainesosa id
     * @return Haettu Ainesosa tai null.
     */
    public Ainesosa haeAinesosa(int id) {
        Ainesosa palautettava = null;
        for (Ainesosa ainesosa: this.ainesosat) {
            if (ainesosa.getId() == id) palautettava = ainesosa;
        }
        return palautettava;
    }

    /** Tallentaa Rivilistan Aineosarivien tiedot annettuun kansioon.
     * @param tallennuskansio johon tallennetaan
     */
    public void tallenna(File tallennuskansio) {
        File kohde = new File(tallennuskansio, Ainesosalista.TIEDOSTON_NIMI + ".dat");
        tallennuskansio.mkdirs();
        //File kopio = new File(tallennuskansio, Ainesosalista.TIEDOSTON_NIMI + ".bak");
        //TODO: varmuuskopionti toimimaan.
        
        try (PrintStream ulos = new PrintStream(new FileOutputStream(kohde))) {
            ulos.println(";id|nimi");
            this.ainesosat.forEach(ainesosa -> ulos.println(ainesosa.tiedostoriviksi()));
        } catch (FileNotFoundException e) {
            // TODO Parempi virheenkasittely?
            System.err.println(e.getMessage());
            System.err.flush();
        }
    }

    /** Lukee Ainesosarivit listalle annetusta kansiosta.
     * @param kansio josta Ainesosarivit luetaan.
     * @throws VirheellinenSyottotietoException jos tiedoston lukemisessa on ongelmia.
     * @throws FileNotFoundException jos tiedoston avaamisessa ongelmia.
     */
    public void lue(File kansio) throws VirheellinenSyottotietoException, FileNotFoundException {
        ArrayList<Ainesosa> uudetAinesosat = new ArrayList<>();
        File tiedosto = new File(kansio, Ainesosalista.TIEDOSTON_NIMI + ".dat");
        try (Scanner lukija = new Scanner(new FileInputStream(tiedosto))) {
            String virhe = "";
            int virheita = 0;
            while (lukija.hasNextLine()) {
                String rivi = lukija.nextLine();
                if (rivi.length() == 0 || rivi.charAt(0) == ';') continue;
                try {
                    uudetAinesosat.add(new Ainesosa().parse(rivi));
                } catch (VirheellinenSyottotietoException e) {
                   virhe = e.getMessage();
                   virheita++;
                }
            }
            if (virheita > 0) throw new VirheellinenSyottotietoException(
                    String.format("Viallisia rivejä %d kpl Ainesosarivejä luettaessa: %s", 
                            virheita, virhe));
        }
        this.ainesosat = uudetAinesosat;
    }
}                                                             