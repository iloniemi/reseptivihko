package reseptivihko;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/** Rivilista -luokka pitää yllä listaa Ainesosariveistä.
 * @author Juho
 * @version 1 Mar 2020
 *
 */
public class Rivilista {
    LinkedList<Ainesosarivi> rivit;
    
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
    public List<Ainesosarivi> haeReseptinRivit(int id) {
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

}
