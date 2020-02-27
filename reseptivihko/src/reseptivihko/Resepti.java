package reseptivihko;

/**
 * Resepti -luokka, joka pystyy asettamaan itselleen id:n.
 * @author Juho
 * @version 27 Feb 2020
 *
 */
public class Resepti {
    private static int seuraavaId;
    private int id;
    private String nimi;
    private String ohje;
    
    
    
    

    public Resepti(int id, String nimi, String ohje) {
        this.id = id;
        this.nimi = nimi;
        this.ohje = ohje;
    }
    
    
    public void setId(int id) {
        this.id = id;
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
        return String.format("ID: %d\n%s\n\n%s", this.id, this.nimi, this.ohje);
    }
    
    /**
     * Metodi Resepti -luokan testaamiseen.
     * @param args ei kaytossa
     */
    public static void main(String[] args) {
//        Resepti resepti = new Resepti();
//        resepti.parsi("2|Suklaatryffelit|Kuumenna kerma kiehuvaksi.§Kaada kerma suklaan ja voin päälle.§Sekoita varovasti.§Mausta.§Muotoile käsissä.§Päällystä kaakaojauheella.");
//        int reseptinId = resepti.getId();
//        String reseptinNimi = resepti.getNimi();
//        String reseptinOhje = resepti.getOhje();
//        System.out.println("Reseptin id: " + reseptinId);
//        System.out.println("Reseptin nimi: " + reseptinNimi);
//        System.out.println("Reseptin ohje: " + reseptinOhje);
        
        System.out.println();
        
//        Resepti resepti2 = new Resepti();
//        resepti2.setNimi("Tee");
//        resepti2.setOhje("Keita vesi.\nUita teepussia vedessa.");
//        boolean onkoReseptilla2Id = resepti2.getId() != -1;
//        System.out.println("Reseptilla2 on id: " + onkoReseptilla2Id);
//        if (resepti2.hankiId()) System.out.println("Nyt on id.1");
//        else System.out.println("Oli jo id.1");
//        if (resepti2.hankiId()) System.out.println("Nyt on id.2");
//        else System.out.println("Oli jo id.2");
//        System.out.println(resepti2.toString());
    }
}
