package reseptivihko;

import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import apufunktioita.Apufunktioita;

/**
 * Ainesosarivi -luokka yhdistää reseptin ja ainesosan sekä sailyttää tietoa
 * määrästä ja mittayksiköstä.
 * @author Juho
 * @version 29 Feb 2020
 */
public class Ainesosarivi {
    private int reseptiId;
    private int ainesosaId;
    private double maara;
    private String yksikko;

    /** Konstruktori Aineosariville
     * @param reseptiId Reseptin id
     * @param ainesosaId Ainesosan id
     * @param maara Määrä
     * @param yksikko Mittayksikkö
     */
    public Ainesosarivi(int reseptiId, int ainesosaId, double maara, String yksikko) {
        this.reseptiId = reseptiId;
        this.ainesosaId = ainesosaId;
        this.maara = maara;
        this.yksikko = Apufunktioita.rajuTrim(yksikko);
    }

    /** Poimii Ainesosarivin tiedot merkkijonosta.
     * Merkkijono muotoa "reseptinId|ainesosanId|määrä|mittayksikkö".
     * @param tiedostorivi Ainesosarivin tiedot merkkijonona
     * @throws VirheellinenSyottotietoException Mikäli merkkijono on väärää muotoa.
     */
    public Ainesosarivi(String tiedostorivi) throws VirheellinenSyottotietoException {
        this.parse(tiedostorivi);
    }
    
    private void parse(String tiedostorivi) throws VirheellinenSyottotietoException {
        String regexAinesosarivi = "^([0-9]+)\\|([0-9]+)\\|([0-9]+\\.?[0-9]*)\\|(.+)$";
        //              1.reseptiId  |--1.--|   |--2.--|   |-------3.-------|   |4.|
        //                                    2.ainesosaId       3.määrä         4.mittayksikkö
        Pattern kuvio = Pattern.compile(regexAinesosarivi);
        Matcher matcher =  kuvio.matcher(tiedostorivi);
        
        if (!matcher.matches()) throw new VirheellinenSyottotietoException("Virheellinen reseptirivi!");
        
        this.setReseptiId(Integer.parseInt(matcher.group(1)));
        this.setAinesosaId(Integer.parseInt(matcher.group(2)));
        this.setMaara(Double.parseDouble(matcher.group(3)));
        this.setYksikko(Apufunktioita.rajuTrim(matcher.group(4)));
    }

    /** Palauttaa sen reseptin id:n, johon rivi kuuluu
     * @return reseptin id
     */
    public int getReseptiId() {
        return reseptiId;
    }

    /** Asettaa reseptin
     * @param reseptiId Uusi reseptin id
     */
    public void setReseptiId(int reseptiId) {
        this.reseptiId = reseptiId;
    }

    /** Palauttaa sen aineososan id:n, jota rivi koskee.
     * @return Ainesosa id
     */
    public int getAinesosaId() {
        return ainesosaId;
    }

    /** Aseta ainesosa id:n
     * @param ainesosaId Uusi  id
     */
    public void setAinesosaId(int ainesosaId) {
        this.ainesosaId = ainesosaId;
    }

    /** Palauttaa Aineosan määrän
     * @return Aineosan määrä
     */
    public double getMaara() {
        return maara;
    }

    /** Asettaa Aineosan määrän
     * @param maara Uusi määrä
     */
    public void setMaara(double maara) {
        this.maara = maara;
    }

    /** Palauttaa Ainesosarivin mittayksikön
     * @return mittayksikkö
     */
    public String getYksikko() {
        return yksikko;
    }

    /** Asettaa Ainesosarivin mittayksikön
     * @param yksikko mittayksikkö
     */
    public void setYksikko(String yksikko) {
        this.yksikko = yksikko;
    }
    
    
    
    
//TODO:poista kun ei tarvita.
    /** Luo Aineososarivejä annetulle reseptin id:lle.
     * @param reseptinId Käytettävä Reseptin id.
     * @return Luodit Ainesosarivit.
     */
    public static ArrayList<Ainesosarivi> luoMallirivit(int reseptinId) {
        ArrayList<Ainesosarivi> mallirivit = new ArrayList<Ainesosarivi>();
        mallirivit.add(new Ainesosarivi(reseptinId, 9, 2, "dl"));
        mallirivit.add(new Ainesosarivi(reseptinId, 10, 1, "kpl"));
        return mallirivit;
    }

    /** Palauttaa rivin tiedot muotoiltuna tallennusta varten.
     * resepti_id|ainesosa_id|maara|yksikko
     *         %d|         %d| %.3f|  %s
     * @return tallennusta varten muotoiltu rivi.
     */
    public String tiedostoriviksi() {
        return String.format(Locale.US, "%d|%d|%.3f|%s",
                this.reseptiId, this.ainesosaId, this.maara, this.yksikko);
    }
}
