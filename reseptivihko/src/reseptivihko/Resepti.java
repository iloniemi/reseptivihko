package reseptivihko;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import apufunktioita.Apufunktioita;

/**
 * Resepti -luokka, joka pystyy asettamaan itselleen id:n.
 * @author Juho
 * @version 27 Feb 2020
 *
 */
public class Resepti implements Comparable<Resepti> {
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
     * Vertailu tapahtuu nimen perusteella välittämättä kirjainkoosta.
     */
    @Override
    public int compareTo(Resepti resepti2) {
        return this.getNimi().compareToIgnoreCase(resepti2.getNimi());
    }
    
    /**
     * @return asetettu id
     */
    public int setId() {
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

    /** Asettaa nimen reseptille
     * Muuttaa 1-useamman white space merkin kohdat yhdeksi välilyönniksi ja 
     * poistaa ne alusta ja lopusta. 
     * Jos nimi on pelkkää white space, nimeksi tulee "Nimetön resepti"
     * @param nimi Asetettava nimi
     */
    public void setNimi(String nimi) {
        String uusiNimi = Apufunktioita.rajuTrim(nimi);
        if (uusiNimi.equals("")) this.nimi = "Nimetön resepti";
        else this.nimi = uusiNimi;
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
     * Palauttaa viitteen tähän Resepti -olioon.
     * @param merkkijono jäsenneltävä merkkijono
     * @return tämä Resepti
     * @throws VirheellinenSyottotietoException jos merkkijono ei ole oikeassa muodossa
     */
    public Resepti parse(String merkkijono) throws VirheellinenSyottotietoException {
        String regexReseptirivi = "^([0-9]+)\\|(.+)\\|(.*)$";
        //1.id  2.nimi  3.ohje      |   1  |   | 2|   | 3|
        Pattern kuvio = Pattern.compile(regexReseptirivi);
        Matcher matcher =  kuvio.matcher(merkkijono);
        
        if (!matcher.matches()) throw new VirheellinenSyottotietoException("Virheellinen reseptirivi!");
        
        this.asetaId(Integer.parseInt(matcher.group(1)));
        this.setNimi(matcher.group(2));
        this.setOhje(matcher.group(3).replace('§', '\n'));
        return this;
    }
    
    /**
     * Antaa reseptin tiedot Reseptit.dat tiedostoon tallnnettavassa muodossa
     * @return tiedostorivi
     */
    public String tiedostoriviksi() {
        StringBuilder palautetaan = new StringBuilder();
        palautetaan.append(this.id).append('|')
                    .append(this.nimi).append('|')
                    .append(this.ohje.replace('\n', '§'));
        return palautetaan.toString();
    }

    /**
     * TESTAAMISTA VARTEN 
     * Luo tee -reseptin ja antaa sille id:n
     * @return Tee -resepti omalla id:llä
     */
    public static Resepti luoTee() {
        //TODO: Poista luoTee() -metodi, kun ei enää tarvita!
        int alaraja = 97; //  'a'
        int ylaraja = 122; // 'z'
        int erotus = ylaraja - alaraja + 1;
        StringBuilder nimi = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            int luku = (int) Math.round(erotus * Math.random());
            luku += alaraja;
            nimi.append((char) luku);
        }
        nimi.append(" -tee");
        
        Resepti teeResepti = new Resepti();
        teeResepti.setNimi(nimi.toString()); 
        teeResepti.setId();
        teeResepti.setOhje("Keita vesi.\nUita teepussia vedessa.");
        return teeResepti;
    }
    
    /**
     * Metodi Resepti -luokan testaamiseen.
     * @param args ei kaytossa
     */
    public static void main(String[] args) {
        Resepti resepti = new Resepti();
        try {resepti.parse("2|Suklaatryffelit|Kuumenna kerma kiehuvaksi.§Kaada kerma suklaan ja voin päälle.§Sekoita varovasti.§Mausta.§Muotoile käsissä.§Päällystä kaakaojauheella.");
        } catch (VirheellinenSyottotietoException e) {System.out.println(e.getMessage());};
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
        if (resepti2.setId() > 0) System.out.println("Nyt on id.1");
        else System.out.println("Oli jo id.1");
        System.out.println(resepti2.toString());
        System.out.println(resepti2.tiedostoriviksi());
    }




        
}
