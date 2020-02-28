package apufunktioita.test;

import apufunktioita.*;
import static org.junit.Assert.*;
import org.junit.*;

/**
 * @author Juho
 * @version 28 Feb 2020
 * Apufunktioita luokan funktioiden testausta
 */
public class ApufunktioitaTest {

    /**
     * Testaa rajuTrim -funktioita
     */
    @Test
    public void testRajuTrim() {
        assertEquals("Ei poistanut kaikkea haluttua", "1 223 332 4434 53434", Apufunktioita.rajuTrim("\n1 223 \n332\t4434    53434  "));
    }

}
