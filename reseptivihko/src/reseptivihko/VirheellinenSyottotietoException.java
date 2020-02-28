package reseptivihko;

/**
 * Virheilmoitus väärinmuotoillun tiedoston lukemisesta
 * @author Juho
 * @version 28 Feb 2020
 *
 */
public class VirheellinenSyottotietoException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * @param viesti Poikkeuksen viesti
     */
    VirheellinenSyottotietoException(String viesti) {
        super(viesti);
    }
}
