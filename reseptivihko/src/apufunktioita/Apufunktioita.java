package apufunktioita;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

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
    
    
    
    // TODO: POISTA jos ei loydy ratkaisua miten toteuttaa rivinvaihdot.
    /** Lukee tiedoston ja palauttaa sen sisällön merkkijonona tai 
     *  virheviestin.
     *  +++ TARKOITETTU TESTAAMISEEN +++
     * @param tiedosto joka luetaan.
     * @return tiedoston sisältö tai virheviesti.
     */
    public static String lueTiedosto(File tiedosto) {
        StringBuilder jono = new StringBuilder();
        try (Scanner lukija = new Scanner(new  FileInputStream(tiedosto))) {
            while (lukija.hasNext()) jono.append('\n').append(lukija.nextLine());
        } catch (Exception e) {
            return e.getMessage();
        }
        jono.append('\n');
        return (jono.length() >= 2) ? jono.substring(1) : "";
    }
    
    /** Luo vaaditut kansiot ja tallentaa annetun tekstin annettuun tiedostoon.
     *  +++ TARKOITETTU TESTAAMISEEN +++
     * @param tiedosto mihin tallennetaan.
     * @param sisalto mitä on tarkoitus tallentaa.
     * @return onnistuiko luominen.
     */
    public static boolean luoTiedostoKansioineen(File tiedosto, String sisalto) {
        File vanhempitiedosto = tiedosto.getParentFile();
        if (vanhempitiedosto != null) vanhempitiedosto.mkdirs();
        try (PrintStream ulos = new PrintStream(new FileOutputStream(tiedosto))) {
            ulos.print(sisalto);
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
