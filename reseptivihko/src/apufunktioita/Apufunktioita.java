package apufunktioita;

/**
 * Hyödyllisiä funktioita ohjelman kannalta
 * @author Juho
 * @version 28 Feb 2020
 *
 */
public class Apufunktioita {
    /**
     * Poistaa alusta ja lopusta 1-useamman white space -merkin kohdat 
     * ja yhdeksi välilyönniksi ja jäljelle jääneet korvaa yhdellä välilyönnillä.
     * @param merkkijono Käsiteltävä merkkijono
     * @return Karsittu merkkijono
     */
    public static String rajuTrim(String merkkijono) {
        return merkkijono.replaceAll("^\\s+|\\s+$", "").replaceAll("\\s+", " ");
    }
}
