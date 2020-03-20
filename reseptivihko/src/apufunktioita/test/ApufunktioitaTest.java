package apufunktioita.test;

import apufunktioita.*;
import static org.junit.Assert.*;

import java.io.File;

import org.junit.*;

/**
 * @author Juho
 * @version 28 Feb 2020
 * Apufunktioita luokan funktioiden testausta
 */
public class ApufunktioitaTest {

    /**
     * Testaa rajuTrim -funktiota
     */
    @Test
    public void testRajuTrim() {
        assertEquals("Ei poistanut kaikkea haluttua", "1 223 332 4434 53434", 
                Apufunktioita.rajuTrim("\n1 223 \n332\t4434    53434  "));
    }

    /**
     * Testaa lueTiedosto ja luoTiedostoKansioineen -funktioita
     */
    @Test
    public void testLueTiedostoJaLuoTiedostoKansioineen() {
        String teksti = "sana  kissa koira \n sana \t orava  ";        
        File kansio = new File("testi398742034823452452542345");
        File tiedosto = new File(kansio, "testitiedosto.txt");
        assertTrue("Vikaa tiedoston luomisessa", 
                Apufunktioita.luoTiedostoKansioineen(tiedosto, teksti));
        assertEquals("Tiedoston kirjoittaminen ja lukeminen muuttivat tekstiä", 
                teksti, Apufunktioita.lueTiedosto(tiedosto));
        kansio.delete();
    }
}
