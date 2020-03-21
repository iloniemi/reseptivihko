package reseptivihko;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.stream.Collectors;

/** Rivilista -luokka pitää yllä listaa Ainesosariveistä.
 * @author Juho
 * @version 1 Mar 2020
 *
 */
public class Rivilista {
    private LinkedList<Ainesosarivi> rivit;
    private final static String TIEDOSTON_NIMI = "ainesosarivit";
    
    /**
     * Konstruktori alustaa rivit -listan.
     */
    public Rivilista() {
        this.rivit = new LinkedList<Ainesosarivi>();
    }

    /** Hakee reseptin id:tä vastaavat rivit.
     * @param id Reseptin id
     * @return Listan Ainesosarivejä, joiden reseptin id on 
     * sama kuin annettu id.
     */
    public ArrayList<Ainesosarivi> haeReseptinRivit(int id) {
        return this.rivit.stream()
                .filter(rivi -> rivi.getReseptiId() == id)
                .collect(Collectors.toCollection(ArrayList::new));
    }
    
    /** Lisää Ainesosarivin listalle.
     * @param ainesosarivi Lisättävä Ainesosarivi
     */
    public void lisaa(Ainesosarivi ainesosarivi) {
        this.rivit.add(ainesosarivi);
    }
    
    /** Poistaa reseptin id:tä vastaavat Ainesosarivit listalta.
     * @param id Reseptin id, jonka Ainesosarivit poistetaan
     */
    public void poistaReseptinRivit(int id) {
        this.rivit.removeIf(rivi -> rivi.getReseptiId() == id);
    }
    
    /** Hakee reseptien id:t, joilla on Ainesosarivi, jossa on annettu
     * Ainesosan id.
     * @param id Ainesosa id
     * @return Taulukko Reseptien id:itä
     */
    public int[] reseptitAinesosalla(int id) {
        return this.rivit.stream()
                .filter(rivi -> rivi.getAinesosaId() == id)
                .mapToInt(rivi -> rivi.getReseptiId())
                .distinct()
                .toArray();
    }

    /** Tallentaa Rivilistan Aineosarivien tiedot annettuun kansioon.
     * @param tallennuskansio johon tallennetaan
     */
    public void tallenna(File tallennuskansio) {
        File kohde = new File(tallennuskansio, Rivilista.TIEDOSTON_NIMI + ".dat");
        tallennuskansio.mkdirs();
        //File kopio = new File(tallennuskansio, Rivilista.tiedostonNimi + ".bak");
        //TODO: varmuuskopionti toimimaan.
        
        try (PrintStream ulos = new PrintStream(new FileOutputStream(kohde))) {
            ulos.println(";resepti_id|ainesosa_id|maara|yksikko");
            this.rivit.forEach(rivi -> ulos.println(rivi.tiedostoriviksi()));
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
        LinkedList<Ainesosarivi> uudetRivit = new LinkedList<>();
        File tiedosto = new File(kansio, Rivilista.TIEDOSTON_NIMI + ".dat");
        try (Scanner lukija = new Scanner(new FileInputStream(tiedosto))) {
            String virhe = "";
            int virheita = 0;
            while (lukija.hasNextLine()) {
                String rivi = lukija.nextLine();
                if (rivi.length() == 0 || rivi.charAt(0) == ';') continue;
                try {
                    uudetRivit.add(new Ainesosarivi(rivi));
                } catch (VirheellinenSyottotietoException e) {
                   virhe = e.getMessage();
                   virheita++;
                }
            }
            if (virheita > 0) throw new VirheellinenSyottotietoException(
                    String.format("Viallisia rivejä %d kpl Ainesosarivejä luettaessa: %s", 
                            virheita, virhe));
        }
        this.rivit = uudetRivit;
    }
}
