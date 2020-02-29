package reseptivihko;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        this.yksikko = yksikko;
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
        //1.reseptiId               |   1  |   |   2  |   |        3       |   | 4|
        //2.ainesosaId 3.määrä 4.mittayksikkö
        Pattern kuvio = Pattern.compile(regexAinesosarivi);
        Matcher matcher =  kuvio.matcher(tiedostorivi);
        
        if (!matcher.matches()) throw new VirheellinenSyottotietoException("Virheellinen reseptirivi!");
        
        this.setReseptiId(Integer.parseInt(matcher.group(1)));
        this.setAinesosaId(Integer.parseInt(matcher.group(2)));
        this.setMaara(Double.parseDouble(matcher.group(3)));
        this.setYksikko(matcher.group(4));
    }


    /** Palauttaa sen reseptin id:n, johon rivi kuuluu
     * @return reseptin id
     */
    public int getReseptiId() {
        return reseptiId;
    }



    /** Asettaa reseptin
     * @param reseptiId 
     */
    public void setReseptiId(int reseptiId) {
        this.reseptiId = reseptiId;
    }



    /** Palauttaa sen aineososan id:n, jota rivi koskee.
     * @return
     */
    public int getAinesosaId() {
        return ainesosaId;
    }



    /**
     * @param ainesosaId
     */
    public void setAinesosaId(int ainesosaId) {
        this.ainesosaId = ainesosaId;
    }



    /**
     * @return
     */
    public double getMaara() {
        return maara;
    }



    /**
     * @param maara
     */
    public void setMaara(double maara) {
        this.maara = maara;
    }



    /**
     * @return
     */
    public String getYksikko() {
        return yksikko;
    }



    /**
     * @param yksikko
     */
    public void setYksikko(String yksikko) {
        this.yksikko = yksikko;
    }



    /** Metodi Ainesosarivi -luokan testaamiseen
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
