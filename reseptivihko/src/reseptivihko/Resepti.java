package reseptivihko;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Resepti -luokka, joka pystyy asettamaan itselleen id:n.
 * @author Juho
 * @version 27 Feb 2020
 *
 */
public class Resepti {
    private static int SEURAAVA_ID = 0;
    private int id = -1;
    private String nimi = "";
    private String ohje = "";
    

    /**
     * Parametritön konstruktori 
     */
    public Resepti() {
        //
    }
    
    
    /**
     * @return asetettu id
     */
    public int asetaId() {
        if (this.id < 0) this.asetaId(SEURAAVA_ID);
        return this.id;
    }
    
    /**
     * @param uusiId haluttu id
     * @return asetettu id
     */
    private int asetaId(int uusiId) {
        this.id = uusiId;
        if (this.id >= SEURAAVA_ID) SEURAAVA_ID = this.id + 1;
        return this.id;
    }



    /**
     * @return reseptin id
     */
    public int getId() {
        return id;
    }

    /**
     * @return reseptin nimi
     */
    public String getNimi() {
        return nimi;
    }

    /**
     * @param nimi asettaa reseptille nimen
     */
    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    /**
     * @return reseptin työohje
     */
    public String getOhje() {
        return ohje;
    }

    /**
     * @param ohje uusi reseptin työohje
     */
    public void setOhje(String ohje) {
        this.ohje = ohje;
    }

    /**
     * Palauttaa merkkijonoesityksen Resepti -oliosta.
     */
    @Override
    public String toString() {
        return String.format("ID: %d\n%s\n%s", this.id, this.nimi, this.ohje);
    }
    
    /**
     * Poimii merkkijonosta tiedot Reseptin attribuutteihin.
     * Merkkijono muodossa "id|nimi|ohje§ohjeenSeuraavaRivi§seuraavaRivi".
     * Palauttaa tehtiinkö parse onnistuneesti.
     * @param merkkijono jäsenneltävä merkkijono
     * @return onnistuiko rivin parsiminen
     */
    public boolean parse(String merkkijono) {
        String regexReseptirivi = "^([0-9]+)\\|(.+)\\|(.*)$";
        //1.id  2.nimi  3.ohje      |   1  |   | 2|   | 3|
        Pattern kuvio = Pattern.compile(regexReseptirivi);
        Matcher matcher =  kuvio.matcher(merkkijono);
        
        if (!matcher.matches()) return false;
        
        this.asetaId(Integer.parseInt(matcher.group(1)));
        this.nimi = matcher.group(2);
        this.ohje = matcher.group(3).replace('§', '\n');
        return true;
    }
    
    /**
     * Metodi Resepti -luokan testaamiseen.
     * @param args ei kaytossa
     */
    public static void main(String[] args) {
        Resepti resepti = new Resepti();
        resepti.parse("2|Suklaatryffelit|Kuumenna kerma kiehuvaksi.§Kaada kerma suklaan ja voin päälle.§Sekoita varovasti.§Mausta.§Muotoile käsissä.§Päällystä kaakaojauheella.");
        int reseptinId = resepti.getId();
        String reseptinNimi = resepti.getNimi();
        String reseptinOhje = resepti.getOhje();
        System.out.println("Reseptin id: " + reseptinId);
        System.out.println("Reseptin nimi: " + reseptinNimi);
        System.out.println("Reseptin ohje: " + reseptinOhje);
        System.out.println(resepti.toString());
        
        Resepti resepti2 = new Resepti();
        resepti2.setNimi("Tee");
        resepti2.setOhje("Keita vesi.\nUita teepussia vedessa.");
        boolean onkoReseptilla2Id = resepti2.getId() != -1;
        System.out.println("Reseptilla2 on id: " + onkoReseptilla2Id);
        if (resepti2.asetaId() > 0) System.out.println("Nyt on id.1");
        else System.out.println("Oli jo id.1");
        System.out.println(resepti2.toString());
    }

        
}
