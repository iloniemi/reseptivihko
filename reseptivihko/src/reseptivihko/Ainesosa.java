package reseptivihko;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Ainesosa -luokka säilöö tiedon ainesosan id:stä ja nimestä. 
 * @author Juho
 * @version 1 Mar 2020
 *
 */
public class Ainesosa {
    private static int SEURAAVA_ID = 0;
    private int id = -1;
    private String nimi = "";
    
    /** Luo Ainesosan ja asettaa sille id:n ja nimen.
     * @param nimi Ainesosan nimi
     */
    public Ainesosa(String nimi) {
        this.id = SEURAAVA_ID;
        SEURAAVA_ID++;
        this.nimi = nimi;
    }
    
    /** Luo Ainesosan.
     */
    public Ainesosa() {
        //
    }
    
    /** Parsii merkkijonosta tiedot id:n ja nimen Ainesosa -olioon.
     * Merkkijono oltava muotoa "id|nimi"
     * @param tiedostorivi Parsittava merkkijono
     * @return Tämä Ainesosa -olio
     * @throws VirheellinenSyottotietoException Mikäli parsittava merkkijono on väärää muotoa.
     */
    public Ainesosa parse(String tiedostorivi) throws VirheellinenSyottotietoException {
        String regex = "^([0-9]+)\\|(.+)";
        //          1.id |--1.--|   |2.| 2.nimi
        Matcher matcher = Pattern.compile(regex).matcher(tiedostorivi);
        if (!matcher.matches()) throw new VirheellinenSyottotietoException("Ainesosan tiedot väärässä muodossa");
        this.id = Integer.parseInt(matcher.group(1));
        this.nimi = matcher.group(2);
        if (this.id > SEURAAVA_ID) SEURAAVA_ID = this.id + 1;
        return this;
    }

    /**
     * @return Ainesosan id
     */
    public int getId() {
        return id;
    }

    /**
     * @return Ainesosan nimi
     */
    public String getNimi() {
        return nimi;
    }
}
