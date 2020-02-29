package reseptivihko;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import apufunktioita.Apufunktioita;

/**
 * Reseptilista, joka ylläpitää listaa Resepteistä ja osaa palauttaa listan Reseptejä, 
 * jotka vastaavat hakutermejä. Osaa myös palauttaa tietyn id:n omaavan Reseptin.
 * @author Juho
 * @version 29 Feb 2020
 *
 */
public class Reseptilista {
    private int lkm = 0;
    private int maxLkm = 50;
    private Resepti[] reseptit;

    
    
    /**
     * Konstruktori reseptilistalle.
     * Alustaa reseptit -taulukon
     */
    public Reseptilista() {
        this.reseptit = new Resepti[this.maxLkm];
    }
    
    /**
     * Lisää Reseptilistalle uuden Reseptin parsittavasta merkkijonosta.
     * Merkkijono muodossa "id|nimi|ohje§ohjeenSeuraavaRivi§seuraavaRivi".
     * @param reseptiTekstina Listalle lisättävä resepti.
     * @throws VirheellinenSyottotietoException Mikäli merkkijono on muotoiltu väärin.
     */
    public void lisaa(String reseptiTekstina) throws VirheellinenSyottotietoException {
        Resepti resepti = new Resepti();
        resepti.parse(reseptiTekstina);
        
        if (this.haeResepti(resepti.getId()) != null) throw new VirheellinenSyottotietoException("Reseptin id on jo käytössä");
        this.lisaaReseptiTaulukkoon(resepti);
    }

    /**
     * Lisää Reseptilistalle uuden Reseptin.
     * Mikäli lisättävä Resepti on jo listalla, toista saman id:n Reseptiä ei lisätä.
     * @param resepti Listalle lisättävä Resepti.
     */
    public void lisaa(Resepti resepti) {
        if (this.haeReseptinIndeksi(resepti.getId()) != -1) return;
        this.lisaaReseptiTaulukkoon(resepti);
    }
    
    private void lisaaReseptiTaulukkoon(Resepti resepti) {
        if (this.lkm == this.maxLkm) {
            this.maxLkm *= 1.5;
            Resepti[] uusiTaulukko = new Resepti[this.maxLkm];
            for (int i = 0; i < this.lkm; i++) uusiTaulukko[i] = this.reseptit[i];
            this.reseptit = uusiTaulukko;
        }
        this.reseptit[lkm] = resepti;
        this.lkm++;
    }
    
    /**
     * Hakee id:n perusteella Reseptin, jos se löytyy listalta.
     * @param reseptinId Haettavan reseptin id.
     * @return Kyseisen id:n omaava resepti tai null.
     */
    public Resepti haeResepti(int reseptinId) {
        int indeksi = this.haeReseptinIndeksi(reseptinId);
        if (indeksi < 0) return null;
        return this.reseptit[indeksi];
    }
    
    /**
     * Etsii missä indeksissä reseptit -taulukkoa haettavan indeksin omaava Resepti on.
     * Palauttaa -1, jos reseptiä ei löydy.
     * @param haettavanId Haettavan reseptin id.
     * @return reseptit -taulukon indeksi, jossa resepti on
     */
    private int haeReseptinIndeksi(int haettavanId) {
        int indeksi = -1;
        for (int i = 0; i < this.lkm; i++) {
            if (this.reseptit[i].getId() == haettavanId) {
                indeksi = i;
                break;
            }
        }
        return indeksi;
    }
    
    /**
     * Poistaa Reseptilistasta Reseptin, jolla on annettu id.
     * Palauttaa oliko poistettava Resepti Reseptilistassa.
     * @param poistettavanId Poistettavan reseptin id.
     * @return Oliko poistettavaa Reseptiä.
     */
    public boolean poistaResepti(int poistettavanId) {
        int indeksi = haeReseptinIndeksi(poistettavanId);
        if (indeksi == -1) return false;
        this.lkm--;
        this.reseptit[indeksi] = this.reseptit[this.lkm]; 
        return true;
    }

    /**
     * Hakee listan Reseptejä, joiden nimi vastaa hakusanoja.
     * Hakusanoista poistetaan ylimääräiset tyhjät merkit, ja *- merkki
     * tarkoittaa mitä tahansa merkkiä kuinka monta tahansa kertaa.
     * @param hakusanat Haussa käytettävät sanat.
     * @return Lista Reseptejä, joiden nimi vastaa hakua.
     */
    public ArrayList<Resepti> haeReseptit(String hakusanat) {
        ArrayList<Resepti> palautettavat = new ArrayList<>();
        String regex = Apufunktioita.rajuTrim(hakusanat).replace("*", ".*");
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        for (int i = 0; i < this.lkm; i++) {
            Matcher m = pattern.matcher(this.reseptit[i].getNimi());
            if (m.matches()) palautettavat.add(this.reseptit[i]);
        }
        return palautettavat;
    }

    /**
     * Metodi Reseptilista -luokan testaamiseen.
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Reseptilista reseptilista = new Reseptilista();
        for (int i = 0; i < 5; i++) reseptilista.lisaa(Resepti.luoTee());
        try {reseptilista.lisaa(new Resepti().parse("5|Tee|Kuumenna vesi kiehuvaksi.§Uita teepussia vedessä.§Nauti."));
        } catch (VirheellinenSyottotietoException e) {System.out.println(e.getMessage());};
        for (int i = 0; i < 5; i++) reseptilista.lisaa(Resepti.luoTee());
        ArrayList<Resepti> teeReseptit = reseptilista.haeReseptit("*tee*");
        System.out.println(teeReseptit.size());
        Resepti teeResepti = reseptilista.haeResepti(5);
        System.out.println(teeResepti.toString());
    }


    

}
